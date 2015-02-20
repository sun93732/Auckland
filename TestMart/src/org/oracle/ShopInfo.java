package org.oracle;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.DOCUMENT)
public class ShopInfo {

	@WebMethod
	@WebResult(partName="looupOutput")
	public String getShopInfo(@WebParam(partName="looupInput") String property) throws InvalidInputException{
		String resp = "invalid property";
		if("shopName".equals(property)) {
			return "Test Mart";
		} else if("since".equals(property)) {
			return "2012";
		} else {
			throw new InvalidInputException("Invalide Input", property + " is not valid input" );

		}
		
	}
}
