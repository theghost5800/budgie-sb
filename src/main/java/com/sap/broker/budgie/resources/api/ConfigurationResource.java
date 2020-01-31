package com.sap.broker.budgie.resources.api;

import com.sap.broker.budgie.configuration.behavior.BehaviorConfiguration;
import com.sap.broker.budgie.configuration.behavior.BehaviorEngine;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationResource {

    private BehaviorEngine behaviorEngine;

    @Inject
    public ConfigurationResource(BehaviorEngine behaviorEngine) {
        this.behaviorEngine = behaviorEngine;
    }

    @GET
    public Response getConfiguration() {
        return Response.ok(behaviorEngine.getConfiguration()).build();
    }

    @PUT
    public Response configure(BehaviorConfiguration configuration) {
        behaviorEngine.setConfiguration(configuration);
        return Response.ok(configuration).build();
    }

    @DELETE
    public Response reset() {
        behaviorEngine.setConfiguration(null);
        return Response.ok().build();
    }
}
