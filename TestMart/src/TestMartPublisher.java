import javax.xml.ws.Endpoint;

import org.oracle.ProductCatelog;



public class TestMartPublisher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8888/productCatalogB", new ProductCatelog());

	}

}
