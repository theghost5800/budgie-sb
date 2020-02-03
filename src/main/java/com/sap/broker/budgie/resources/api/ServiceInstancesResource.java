package com.sap.broker.budgie.resources.api;

import java.util.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sap.broker.budgie.configuration.behavior.BehaviorEngine;
import com.sap.broker.budgie.domain.Binding;
import com.sap.broker.budgie.domain.ServiceInstance;
import com.sap.broker.budgie.helpers.AsyncOperation;
import com.sap.broker.budgie.helpers.AsyncOperationState;
import com.sap.broker.budgie.impl.ServiceBroker;

@Path("/service_instances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceInstancesResource {

    private ServiceBroker serviceBroker;
    private BehaviorEngine behaviorEngine;
    private Map<UUID, AsyncOperation> asyncInstanceOperations = new HashMap<>();
    private Map<UUID, AsyncOperation> asyncBindingOperations = new HashMap<>();

    @Inject
    public ServiceInstancesResource(ServiceBroker serviceBroker, BehaviorEngine behaviorEngine) {
        this.serviceBroker = serviceBroker;
        this.behaviorEngine = behaviorEngine;
    }

    @GET
    public Collection<ServiceInstance> getAll() {
        return serviceBroker.getAll();
    }

    @GET
    @Path("/{id}")
    public ServiceInstance get(@PathParam("id") UUID id) {
        return serviceBroker.get(id);
    }

    @GET
    @Path("/{id}/last_operation")
    public Response getLastOperation(@PathParam("id") UUID id) {
        AsyncOperation asyncOperation = asyncInstanceOperations.get(id);
        if (asyncOperation == null) {
            return emptyBodyResponse(Status.BAD_REQUEST);
        }
        return Response.ok(asyncOperation.getOperationState()).build();
    }

    @PUT
    @Path("/{id}")
    public Response create(@PathParam("id") UUID id, @QueryParam("accepts_incomplete") boolean acceptIncomplete, ServiceInstance serviceInstance) {
        if (behaviorEngine.isAsync()) {
            if (!acceptIncomplete) {
                return emptyBodyResponse(422);
            }
            return createAsync(id, serviceInstance);
        }
        return createSync(id, serviceInstance);
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, @QueryParam("accepts_incomplete") boolean acceptIncomplete, ServiceInstance serviceInstance) {
        if (behaviorEngine.isAsync()) {
            if (!acceptIncomplete) {
                return emptyBodyResponse(422);
            }
            return updateAsync(id, serviceInstance);
        }
        return updateSync(id, serviceInstance);
    }

    @DELETE
    public Response deleteAll() {
        serviceBroker.deleteAll();
        return emptyBodyResponse(Status.OK);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id, @QueryParam("accepts_incomplete") boolean acceptIncomplete) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        if (serviceInstance == null) {
            return emptyBodyResponse(Status.GONE);
        }
        if (behaviorEngine.isAsync()) {
            if (!acceptIncomplete) {
                return emptyBodyResponse(422);
            }
            return deleteAsync(id, serviceInstance);
        }
        return deleteSync(id, serviceInstance);
    }

    @GET
    @Path("/{id}/service_bindings/{binding_id}/last_operation")
    public Response getLastBindOperation(@PathParam("id") UUID id, @PathParam("binding_id") UUID bindingId) {
        AsyncOperation asyncOperation = asyncBindingOperations.get(id);
        if (asyncOperation == null) {
            return emptyBodyResponse(Status.BAD_REQUEST);
        }
        return Response.ok(asyncOperation.getOperationState()).build();
    }

    @GET
    @Path("/{id}/service_bindings/{binding_id}")
    public Response getBinding(@PathParam("id") UUID id, @PathParam("binding_id") UUID bindingId) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        if (serviceInstance == null) {
            return emptyBodyResponse(Status.NOT_FOUND);
        }
        Binding binding = serviceInstance.getBinding(bindingId);
        if (binding == null) {
            return emptyBodyResponse(Status.NOT_FOUND);
        }
        return Response.ok(binding).build();
    }

    @PUT
    @Path("/{id}/service_bindings/{binding_id}")
    public Response bind(@PathParam("id") UUID id, @PathParam("binding_id") UUID bindingId,
        @QueryParam("accepts_incomplete") boolean acceptIncomplete, Binding binding) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        if (serviceInstance == null) {
            return emptyBodyResponse(Status.BAD_REQUEST);
        }
        if (behaviorEngine.isAsync()) {
            if (!acceptIncomplete) {
                return emptyBodyResponse(422);
            }
            return bindAsync(bindingId, serviceInstance, binding);
        }
        return bindSync(bindingId, serviceInstance, binding);
    }

    @DELETE
    @Path("/{id}/service_bindings/{binding_id}")
    public Response unbind(@PathParam("id") UUID id, @PathParam("binding_id") UUID bindingId,
        @QueryParam("accepts_incomplete") boolean acceptIncomplete) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        if (serviceInstance == null) {
            return emptyBodyResponse(Status.BAD_REQUEST);
        }
        if (behaviorEngine.isAsync()) {
            if (!acceptIncomplete) {
                return emptyBodyResponse(422);
            }
            return unbindAsync(bindingId, serviceInstance);
        }
        return unbindSync(bindingId, serviceInstance);
    }

    private Response unbindSync(UUID bindingId, ServiceInstance serviceInstance) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldUnbindFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return emptyBodyResponse(optionalStatusCode.get());
        }
        if (serviceInstance.getBinding(bindingId) == null) {
            return emptyBodyResponse(Status.GONE);
        }
        serviceInstance.unbind(bindingId);
        return emptyBodyResponse(Status.OK);
    }

    private Response unbindAsync(UUID bindingId, ServiceInstance serviceInstance) {
        AsyncOperation asyncOperation = new AsyncOperation(() -> {
            if (behaviorEngine.shouldUnbindFail(serviceInstance).isPresent()) {
                return new AsyncOperationState(AsyncOperationState.State.FAILED);
            }
            serviceInstance.unbind(bindingId);
            return new AsyncOperationState(AsyncOperationState.State.SUCCEEDED);
        }, behaviorEngine.getTimeout());
        asyncBindingOperations.put(bindingId, asyncOperation);
        return emptyBodyResponse(Status.ACCEPTED);
    }

    private Response createSync(UUID id, ServiceInstance serviceInstance) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldCreateFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return emptyBodyResponse(optionalStatusCode.get());
        }
        serviceInstance.setId(id);
        return create(serviceInstance);
    }

    private Response createAsync(UUID id, ServiceInstance serviceInstance) {
        AsyncOperation asyncOperation = new AsyncOperation(() -> {
            if (behaviorEngine.shouldCreateFail(serviceInstance).isPresent()) {
                return new AsyncOperationState(AsyncOperationState.State.FAILED);
            }
            serviceInstance.setId(id);
            create(serviceInstance);
            return new AsyncOperationState(AsyncOperationState.State.SUCCEEDED);
        }, behaviorEngine.getTimeout());
        asyncInstanceOperations.put(id, asyncOperation);
        return emptyBodyResponse(Status.ACCEPTED);
    }

    private Response create(ServiceInstance serviceInstance) {
        if (existsIdentical(serviceInstance)) {
            return emptyBodyResponse(Status.OK);
        }
        serviceBroker.create(serviceInstance);
        return emptyBodyResponse(Status.CREATED);
    }

    private boolean existsIdentical(ServiceInstance serviceInstance) {
        ServiceInstance existingServiceInstance = serviceBroker.get(serviceInstance.getId(), false);
        return serviceInstance.equals(existingServiceInstance);
    }

    private Response updateAsync(UUID id, ServiceInstance serviceInstance) {
        AsyncOperation asyncOperation = new AsyncOperation(() -> {
            if (behaviorEngine.shouldUpdateFail(serviceInstance).isPresent()) {
                return new AsyncOperationState(AsyncOperationState.State.FAILED);
            }
            serviceInstance.setId(id);
            update(serviceInstance);
            return new AsyncOperationState(AsyncOperationState.State.SUCCEEDED);
        }, behaviorEngine.getTimeout());
        asyncInstanceOperations.put(id, asyncOperation);
        return emptyBodyResponse(Status.ACCEPTED);
    }

    private Response updateSync(UUID id, ServiceInstance serviceInstance) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldUpdateFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return emptyBodyResponse(optionalStatusCode.get());
        }
        serviceInstance.setId(id);
        return update(serviceInstance);
    }

    private Response update(ServiceInstance serviceInstance) {
        serviceBroker.update(serviceInstance);
        return emptyBodyResponse(Status.OK);
    }

    private Response deleteSync(UUID id, ServiceInstance serviceInstance) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldDeleteFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return emptyBodyResponse(optionalStatusCode.get());
        }
        serviceBroker.delete(id);
        return emptyBodyResponse(Status.OK);
    }

    private Response deleteAsync(UUID id, ServiceInstance serviceInstance) {
        AsyncOperation asyncOperation = new AsyncOperation(() -> {
            if (behaviorEngine.shouldDeleteFail(serviceInstance).isPresent()) {
                return new AsyncOperationState(AsyncOperationState.State.FAILED);
            }
            serviceBroker.delete(id);
            return new AsyncOperationState(AsyncOperationState.State.SUCCEEDED);
        }, behaviorEngine.getTimeout());
        asyncInstanceOperations.put(id, asyncOperation);
        return emptyBodyResponse(Status.ACCEPTED);
    }

    private Response bindSync(UUID bindingId, ServiceInstance serviceInstance, Binding binding) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldBindFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return emptyBodyResponse(optionalStatusCode.get());
        }
        if (serviceInstance.getBinding(bindingId) != null) {
            return emptyBodyResponse(Status.CONFLICT);
        }
        serviceInstance.bind(bindingId, binding);
        return Response.status(Status.CREATED).entity(binding).build();
    }

    private Response bindAsync(UUID bindingId, ServiceInstance serviceInstance, Binding binding) {
        AsyncOperation asyncOperation = new AsyncOperation(() -> {
            if (behaviorEngine.shouldBindFail(serviceInstance).isPresent()) {
                return new AsyncOperationState(AsyncOperationState.State.FAILED);
            }
            if (serviceInstance.getBinding(bindingId) != null) {
                return new AsyncOperationState(AsyncOperationState.State.FAILED);
            }
            serviceInstance.bind(bindingId, binding);
            return new AsyncOperationState(AsyncOperationState.State.SUCCEEDED);
        }, behaviorEngine.getTimeout());
        asyncBindingOperations.put(bindingId, asyncOperation);
        return emptyBodyResponse(Status.ACCEPTED);
    }

    private Response emptyBodyResponse(Status httpStatus) {
        return emptyBodyResponse(httpStatus.getStatusCode());
    }

    private Response emptyBodyResponse(int httpStatus) {
        return Response.status(httpStatus).entity(new Object()).build();
    }
}
