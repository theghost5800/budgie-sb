package com.sap.broker.budgie.resources.api;

import java.util.Collection;
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

import com.sap.broker.budgie.domain.ServiceInstance;
import com.sap.broker.budgie.domain.ServiceOperationState;
import com.sap.broker.budgie.handlers.AsyncParametersHandler;
import com.sap.broker.budgie.impl.ServiceBroker;

@Path("/service_instances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceInstancesResource {

    private ServiceBroker serviceBroker;
    private AsyncParametersHandler asyncParametersHandler;

    @Inject
    public ServiceInstancesResource(ServiceBroker serviceBroker, AsyncParametersHandler asyncParametersHandler) {
        this.serviceBroker = serviceBroker;
        this.asyncParametersHandler = asyncParametersHandler;
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
        ServiceInstance serviceInstance = serviceBroker.get(id, false);
        if (serviceInstance == null) {
            return Response.status(Status.GONE)
                .build();
        }
        return Response.status(Status.OK)
            .entity(serviceInstance.getServiceOperation())
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response create(@PathParam("id") UUID id, ServiceInstance serviceInstance) {
        serviceInstance.setId(id);
        return create(serviceInstance);
    }

    private Response create(ServiceInstance serviceInstance) {
        if (existsIdentical(serviceInstance)) {
            return Response.ok()
                .entity(new Object())
                .build();
        }
        if (isAsyncOperation(serviceInstance)) {
            int timeForCreation = asyncParametersHandler.getTimeOfAsyncExecutionInSeconds(serviceInstance.getParameters());
            serviceBroker.asyncCreate(serviceInstance, timeForCreation);
        } else {
            serviceBroker.create(serviceInstance);
        }
        return getAsyncOrSyncResponse(serviceInstance);
    }

    private Response getAsyncOrSyncResponse(ServiceInstance serviceInstance) {
        if (serviceInstance.getServiceOperation()
            .getState() == ServiceOperationState.IN_PROGRESS) {
            return Response.status(Status.ACCEPTED)
                .entity(new Object())
                .build();
        }
        return Response.status(Status.OK)
            .entity(new Object())
            .build();
    }

    private boolean existsIdentical(ServiceInstance serviceInstance) {
        ServiceInstance existingServiceInstance = serviceBroker.get(serviceInstance.getId(), false);
        return serviceInstance.equals(existingServiceInstance);
    }

    private boolean isAsyncOperation(ServiceInstance serviceInstance) {
        return asyncParametersHandler.shouldExecuteOperationAsync(serviceInstance.getParameters());
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, ServiceInstance serviceInstance) {
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
        if (isAsyncOperation(serviceInstance)) {
            int timeForDeletion = asyncParametersHandler.getTimeOfAsyncExecutionInSeconds(serviceInstance.getParameters());
            serviceBroker.asyncDelete(serviceInstance, timeForDeletion);
        } else {
            serviceBroker.delete(id);
        }
        return getAsyncOrSyncResponse(serviceInstance);
    }

}
