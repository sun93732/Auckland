package com.eric.jerseyrest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eric.jerseyrest.model.Message;

public class MessageService {
	
	public MessageService() {
		System.out.println((new Date()));
	}
	
	public List<Message> getMessages() {
		
		List lst = new ArrayList();
		Message msg1 = new Message(1, "Eric");
		Message msg2 = new Message(1, "Jacboson");
		
		lst.add(msg1);
		lst.add(msg2);
		return lst;
	}

}
