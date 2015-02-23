package org.oracle;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.oracle.model.Product;
@WebService(name="TestMartCatalog") 
public interface ProductCatalogInterface {

	@WebMethod(exclude = true)
	public abstract List<String> getProductCategoriesV1();

	@WebMethod(action = "fetch_categories_action", operationName = "fetch_categories")
	//not necessary 
	public abstract List<String> getProductCategories(String category);

	@WebMethod
	public abstract boolean addProductCategories(String category, String product);

	@WebMethod
	@WebResult(name="PP") 
	public abstract List<Product> getProductCategoriesV2();

}