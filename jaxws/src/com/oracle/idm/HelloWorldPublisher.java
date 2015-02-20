package com.oracle.idm;

import javax.xml.ws.Endpoint;

public class HelloWorldPublisher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());

	}

}
