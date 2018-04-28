package com.sap.broker.dummy.resources.api;

import java.util.Collection;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sap.broker.dummy.domain.ServiceInstance;
import com.sap.broker.dummy.impl.ServiceBroker;

@Path("/service_instances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceInstancesResource {

    private ServiceBroker serviceBroker;

    @Inject
    public ServiceInstancesResource(ServiceBroker serviceBroker) {
        this.serviceBroker = serviceBroker;
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
        serviceBroker.delete(id);
        return Response.ok()
            .entity(new Object())
            .build();
    }

}
