import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;


public class TestWSClient {

	/**
	 * @param args
	 * @throws AxisFault 
	 */
	public static void main(String[] args) throws AxisFault {
		// TODO Auto-generated method stub
		
		RPCServiceClient client =  new RPCServiceClient();
		Options options  = client.getOptions();
		String address = "http://localhost:8080/axis2/services/HelloWorld";
		EndpointReference ref  = new EndpointReference(address);
		options.setTo(ref);
		QName qname  = new QName("http://ws.apache.org/axis2","sayHello");
		Object[] result  =  client.invokeBlocking(qname, new Object[]{"Jack"}, new Class[] {String.class});
		System.out.println(result[0]);

	}

}
