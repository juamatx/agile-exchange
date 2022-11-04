package com.shareversity.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/shareversity")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello Shareversity student Website";
    }

//    // output text with argument
//    @Path("/{name}")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello(@PathParam("name") String name) {
//        return "Jersey: hello " + name;
//    }

}