package org.oracle;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.oracle.model.Product;

@WebService(endpointInterface = "org.oracle.ProductCatalogInterface", portName = "TestMartCatalogPort", serviceName = "TestMartCatalog", targetNamespace = "http://www.oracle.com")
public class ProductCatelog implements ProductCatalogInterface {
	ProductServiceImpl productService = new ProductServiceImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.oracle.ProductCatalogInterface#getProductCategories()
	 */
	@Override
	public List<String> getProductCategoriesV1() {
		List<String> foodList = new ArrayList<String>();
		foodList.add("Apple");
		foodList.add("Watermeloon");
		foodList.add("Green Orange");
		return foodList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.oracle.ProductCatalogInterface#getProductCategories(java.lang.String)
	 */
	@Override
	public List<String> getProductCategories(String category) {
		return productService.getProductCategories(category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.oracle.ProductCatalogInterface#addProductCategories(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean addProductCategories(String category, String product) {
		return productService.addProductCategories(category, product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.oracle.ProductCatalogInterface#getProductCategoriesV2()
	 */
	@Override
	@WebMethod
	@WebResult(name = "PP")
	public List<Product> getProductCategoriesV2() {
		return productService.getProductCategories();
	}
}
