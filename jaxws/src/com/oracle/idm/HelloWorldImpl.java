package com.oracle.idm;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(endpointInterface="com.oracle.idm.HelloWorld")

public class HelloWorldImpl implements HelloWorld {

	@Override
	public String getHelloWorldMessage(String name) {
		// TODO Auto-generated method stub
		return "HelloWorld JAX_WS " + name;
	}

	

}
