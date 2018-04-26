package com.sap.dummy.broker.resources.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.sap.dummy.broker.configuration.ApplicationConfiguration;
import com.sap.dummy.broker.domain.Catalog;

@Path("/catalog")
@Component
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
