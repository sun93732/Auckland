package com.oracle;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tutorials")
public class QueryParamService {
	
	@GET
	@Path("/java-tutorials")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public Response getTutorial(@QueryParam("site") String site,
			@QueryParam("tutorial") List<String> tutorial,
					@QueryParam("rank") int rank) {
		List<String> tags = new ArrayList<String>();
		tags.add("Javax");
		tags.add("C++");
		Book book  = new Book("xxxxx.oracle.com",tags,100);
	
		return Response
				.status(200)
				.entity(book).build();
			 
	}

}

