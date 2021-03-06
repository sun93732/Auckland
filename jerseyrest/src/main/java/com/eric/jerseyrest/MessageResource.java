package com.eric.jerseyrest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.eric.jerseyrest.db.DataSource;
import com.eric.jerseyrest.model.Message;
import com.eric.jerseyrest.service.MessageService;

@Path("/messages")
//s@PerSession
public class MessageResource {
	MessageService service = new MessageService();
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getMesssage() {
		
		return "hello world message!";
	}
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
	public List<Message> getMesssageList(@QueryParam("name") String name) {
    	//http://localhost:8080/jerseyrest/webresources/messages/list?name=shit
    	// if not name, the name here will be null
    	System.out.println("the query parameter is " + name);
		
		return service.getMessages();
	}
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_XML)
	public Message queryMessage(@QueryParam("id") int id) {

        Message msg =  DataSource.getInstance().getMap().get(id);
        return msg;		
	}
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String queryMessage(Message message) {
    	DataSource.getInstance().getMap().put(message.getId(), message);
        return "message" + message + " posted!";	
	}
    @GET
    @Path("{messageId}")
    @Produces(MediaType.APPLICATION_XML)
	public Message queryMessagebyId(@PathParam("messageId") int id) {

        Message msg =  DataSource.getInstance().getMap().get(id);
        return msg;		
	}
    @PUT
    @Path("{messageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Message updateMessagebyId(@PathParam("messageId") int id, Message message) {

        Message msg =  DataSource.getInstance().getMap().get(id);
        msg.setMsg(message.getMsg());
        return msg;		
	}
    @DELETE
    @Path("{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Message deleteMessagebyId(@PathParam("messageId") int id) {

        Message msg =  DataSource.getInstance().getMap().remove(id);
        
        return msg;		
	}
    
}
