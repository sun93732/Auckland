package org.oracle.asyn;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
@WebService(endpointInterface="org.oracle.asyn.EndpointSEI", portName="TestEndpointSEI", 
serviceName="TestEndpointSIB")
public class EndpointSIB implements EndpointSEI {

	@Override
	@WebMethod
	@WebResult(name = "result")
	public String echo(@WebParam(name = "what") String what) {
		// TODO Auto-generated method stub
		return what;
	}

}
