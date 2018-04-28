package com.sap.broker.budgie.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class Hello {

    @GET
    public String getMessage() {
        return "Hello, world!";
    }

}
