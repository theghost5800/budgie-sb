package com.sap.broker.budgie.resources.api;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sap.broker.budgie.configuration.behavior.BehaviorEngine;
import com.sap.broker.budgie.domain.ServiceInstance;
import com.sap.broker.budgie.impl.ServiceBroker;

@Path("/service_instances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceInstancesResource {

    private ServiceBroker serviceBroker;
    private BehaviorEngine behaviorEngine;

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

    @PUT
    @Path("/{id}")
    public Response create(@PathParam("id") UUID id, ServiceInstance serviceInstance) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldCreateFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return Response.status(optionalStatusCode.get()).build();
        }
        serviceInstance.setId(id);
        return create(serviceInstance);
    }

    private Response create(ServiceInstance serviceInstance) {
        if (existsIdentical(serviceInstance)) {
            return Response.ok()
                .entity(new Object())
                .build();
        }
        serviceBroker.create(serviceInstance);
        return Response.status(Status.CREATED)
            .entity(new Object())
            .build();
    }

    private boolean existsIdentical(ServiceInstance serviceInstance) {
        ServiceInstance existingServiceInstance = serviceBroker.get(serviceInstance.getId(), false);
        return serviceInstance.equals(existingServiceInstance);
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, ServiceInstance serviceInstance) {
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldUpdateFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return Response.status(optionalStatusCode.get()).build();
        }
        serviceInstance.setId(id);
        return update(serviceInstance);
    }

    private Response update(ServiceInstance serviceInstance) {
        serviceBroker.update(serviceInstance);
        return Response.ok()
            .entity(new Object())
            .build();
    }

    @DELETE
    public Response deleteAll() {
        serviceBroker.deleteAll();
        return Response.ok()
            .entity(new Object())
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        if (serviceInstance == null) {
            return Response.status(Status.GONE)
                .entity(new Object())
                .build();
        }
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldDeleteFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return Response.status(optionalStatusCode.get()).build();
        }
        serviceBroker.delete(id);
        return Response.ok()
            .entity(new Object())
            .build();
    }


    @GET
    @Path("/{id}/service_bindings/{binding_id}")
    public Response getBinding(@PathParam("id") UUID id, @PathParam("binding_id") UUID bidingId) {
        // TODO
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @Path("/{id}/service_bindings/{binding_id}")
    public Response bind(@PathParam("id") UUID id, @PathParam("binding_id") UUID bidingId) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldCreateFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return Response.status(optionalStatusCode.get()).build();
        }
        // TODO
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @DELETE
    @Path("/{id}/service_bindings/{binding_id}")
    public Response unbind(@PathParam("id") UUID id, @PathParam("binding_id") UUID bidingId) {
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        Optional<Integer> optionalStatusCode = behaviorEngine.shouldCreateFail(serviceInstance);
        if (optionalStatusCode.isPresent()) {
            return Response.status(optionalStatusCode.get()).build();
        }
        // TODO
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

}
