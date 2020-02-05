package com.sap.broker.budgie.resources.api;

import com.sap.broker.budgie.configuration.behavior.BehaviorConfiguration;
import com.sap.broker.budgie.configuration.behavior.BehaviorEngine;
import com.sap.broker.budgie.helpers.BehaviorConfigurationValidator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationResource {

    private BehaviorEngine behaviorEngine;
    private BehaviorConfigurationValidator behaviorConfigurationValidator;

    @Inject
    public ConfigurationResource(BehaviorEngine behaviorEngine, BehaviorConfigurationValidator behaviorConfigurationValidator) {
        this.behaviorEngine = behaviorEngine;
        this.behaviorConfigurationValidator = behaviorConfigurationValidator;
    }

    @GET
    public Response getConfiguration() {
        return Response.ok(behaviorEngine.getConfiguration()).build();
    }

    @PUT
    public Response configure(BehaviorConfiguration configuration) {
        if (!behaviorConfigurationValidator.validate(configuration)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        behaviorEngine.setConfiguration(configuration);
        return Response.ok(configuration).build();
    }

    @DELETE
    public Response reset() {
        behaviorEngine.setConfiguration(null);
        return Response.ok().build();
    }
}
