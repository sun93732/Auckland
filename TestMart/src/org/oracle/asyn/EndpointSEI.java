package org.oracle.asyn;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="EndpointSET", targetNamespace="http://www.oracle.com/")
public interface EndpointSEI {
	@WebMethod
	@WebResult(name="result")
	public String echo(@WebParam(name="what") String what);

}
