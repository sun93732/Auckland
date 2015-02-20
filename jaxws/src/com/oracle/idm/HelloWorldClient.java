package com.oracle.idm;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class HelloWorldClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			URL url = new URL("http://localhost:8888/ws/hello?wsdl");
			QName name = new QName("http://idm.oracle.com/", "HelloWorldImplService");
			Service service = Service.create(url, name);
			HelloWorld hello = service.getPort(HelloWorld.class);
			System.out.println(hello.getHelloWorldMessage("mkyong"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
