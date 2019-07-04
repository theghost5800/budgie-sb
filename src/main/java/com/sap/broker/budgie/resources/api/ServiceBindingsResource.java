package com.sap.broker.budgie.resources.api;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sap.broker.budgie.domain.ServiceBinding;
import com.sap.broker.budgie.domain.ServiceInstance;
import com.sap.broker.budgie.impl.ServiceBroker;

@Path("/service_instances/{instance-id}/service_bindings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceBindingsResource {

    private ServiceBroker serviceBroker;

    @Inject
    public ServiceBindingsResource(ServiceBroker serviceBroker) {
        this.serviceBroker = serviceBroker;
    }

    @GET
    @Path("/{binding-id}")
    public ServiceBinding getServiceBiding(@PathParam("instance-id") UUID serviceInstanceId, @PathParam("binding-id") UUID bindingId) {
        return serviceBroker.getServiceBinding(serviceInstanceId, bindingId);
    }

    @PUT
    @Path("/{binding-id}")
    public Response createServiceBinding(@PathParam("instance-id") UUID serviceInstanceId, @PathParam("binding-id") UUID bindingId,
        ServiceBinding serviceBinding) {
        ServiceInstance serviceInstance = serviceBroker.get(serviceInstanceId);
        serviceBinding.setId(bindingId);
        return createServiceBinding(serviceInstance, serviceBinding);
    }

    private Response createServiceBinding(ServiceInstance serviceInstance, ServiceBinding serviceBinding) {
        if (existsIdentical(serviceInstance, serviceBinding)) {
            return Response.ok()
                .entity(new Object())
                .build();
        }
        serviceBroker.createServiceBinding(serviceInstance, serviceBinding);
        return Response.status(Status.CREATED)
            .entity(new Object())
            .build();
    }

    private boolean existsIdentical(ServiceInstance serviceInstance, ServiceBinding serviceBinding) {
        ServiceBinding existingServiceBinding = serviceInstance.getServiceBinding(serviceBinding.getId());
        return serviceBinding.equals(existingServiceBinding);
    }

    @DELETE
    @Path("/{binding-id}")
    public Response deleteServiceBinding(@PathParam("instance-id") UUID serviceInstanceId, @PathParam("binding-id") UUID bindingId,
        @QueryParam("service_id") UUID serviceId, @QueryParam("plan_id") UUID planId) {
        ServiceInstance serviceInstance = serviceBroker.get(serviceInstanceId, false);
        if (serviceInstance == null) {
            return Response.status(Status.GONE)
                .entity(new Object())
                .build();
        }
        serviceBroker.deleteBinding(serviceInstanceId, bindingId, serviceId, planId);
        return Response.ok()
            .entity(new Object())
            .build();
    }
}
