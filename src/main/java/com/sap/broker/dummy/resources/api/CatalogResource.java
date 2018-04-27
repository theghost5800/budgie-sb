package com.sap.broker.dummy.resources.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.broker.dummy.domain.Catalog;
import com.sap.broker.dummy.impl.ServiceBroker;

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
