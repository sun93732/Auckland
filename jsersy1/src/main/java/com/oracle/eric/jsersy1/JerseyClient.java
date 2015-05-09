package com.oracle.eric.jsersy1;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		// Get XML
	    System.out.println(service.path("webresources").path("myresource").accept(MediaType.TEXT_PLAIN).get(String.class));
    

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/jsersy1").build();
	}

}
