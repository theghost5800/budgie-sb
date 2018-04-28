package com.sap.broker.budgie.resources.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.impl.ServiceBroker;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

    private ServiceBroker serviceBroker;

    @Inject
    public CatalogResource(ServiceBroker serviceBroker) {
        this.serviceBroker = serviceBroker;
    }

    @GET
    public Catalog getCatalog() {
        return serviceBroker.getCatalog();
    }

}
