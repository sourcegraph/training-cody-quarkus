package com.sourcegraph.petstore.controller;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RegisterForReflection
@ApplicationScoped
@Path("/")
public class StaticResourceController {
    
    @GET
    @Path("{path:(?!api).*}")
    public Response forwardToIndex() {
        return Response.ok().entity("").build();
    }
}