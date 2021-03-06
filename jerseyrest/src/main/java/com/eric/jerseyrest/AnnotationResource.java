package com.eric.jerseyrest;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectDemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class AnnotationResource {
	//http://localhost:8080/jerseyrest/webresources/injectDemo/annotatation;param=test ,and in the header ,we set a customHeader = 1000
    @GET
	@Path("/annotatation")
	public String getAnnoationParamValue(@MatrixParam("param") String param,
			@HeaderParam("customHeader") String header, @CookieParam("name") String name) {
		return "Matrix param: " +  param + " header param: " +  header;
	}
    
    @GET
   	@Path("/contexts")
   	public String getAnnoationParamValue(@Context UriInfo uri, @Context HttpHeaders header) {
    	String path =  uri.getAbsolutePath().toString();
    	String cookie  = header.getCookies().toString();
   		return "path: " +  path + " cookie: " +  cookie;
   	}

}
