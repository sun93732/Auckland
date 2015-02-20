package com.oracle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class RestHelloWorld {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Welcome to Java Tutorials Corner";
	}

	@GET
	@Path("{site}")
	public Response getSite(@PathParam("site") String site) {
		return Response.status(200).entity("webSite :"+ site).build();
	}
}