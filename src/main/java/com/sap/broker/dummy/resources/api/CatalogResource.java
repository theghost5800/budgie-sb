package com.sap.broker.dummy.resources.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.broker.dummy.configuration.ApplicationConfiguration;
import com.sap.broker.dummy.domain.Catalog;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

    private ApplicationConfiguration configuration;

    @Inject
    public CatalogResource(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    public Catalog getCatalog() {
        return configuration.getCatalog();
    }

}
