package com.sap.broker.budgie.resources.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.domain.Service;
import com.sap.broker.budgie.domain.Visitor;
import com.sap.broker.budgie.impl.ServiceBroker;

import java.util.List;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

    private ServiceBroker serviceBroker;
    private List<Visitor> catalogVisitors;

    @Inject
    public CatalogResource(ServiceBroker serviceBroker, List<Visitor> catalogVisitors) {
        this.serviceBroker = serviceBroker;
        this.catalogVisitors = catalogVisitors;
    }

    @GET
    public Catalog getCatalog() {
        return serviceBroker.getCatalog();
    }

    @PUT
    public Response createServiceOffering(Service service) {
        Catalog catalog = serviceBroker.getCatalog();
        catalog.getServices().add(service);
        processCatalog(catalog);
        return Response.status(Response.Status.CREATED).entity(service).build();
    }

    private void processCatalog(Catalog catalog) {
        catalogVisitors.forEach(catalog::accept);
    }

}
