import javax.ws.rs.core.MediaType;

import com.oracle.Book;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class HelloWorldClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String baseURI = "http://localhost:8080/Restful/";
		ClientConfig config = new DefaultClientConfig();
		Client client =  Client.create(config);
		WebResource service =  client.resource(baseURI);
		System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(String.class));
        
		System.out.println(service.path("rest").path("tutorials/java-tutorials").
				queryParam("site", "www.oracle.com").queryParam("tutorials", "JAXWS").
				queryParam("tutorials", "JAVA").queryParam("rank", "1").get(ClientResponse.class).getEntity(String.class));
	}

}
