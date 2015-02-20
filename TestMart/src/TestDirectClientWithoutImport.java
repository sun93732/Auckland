import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.oracle.ProductCatelog;
import org.oracle.ProductCatalogInterface;

public class TestDirectClientWithoutImport {
	public static void main(String[] args) throws Exception {

		URL url = new URL(
				"http://localhost:8080/TestMart/TestMartCatalogService?wsdl");

		// 1st argument service URI, refer to wsdl document above
		// 2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://www.oracle.com",
				"TestMartCatalogService");

		Service service = Service.create(url, qname);

		ProductCatalogInterface hello = (ProductCatalogInterface) service.getPort(ProductCatalogInterface.class);

		System.out.println(hello.getProductCategoriesV2());
	}

}
