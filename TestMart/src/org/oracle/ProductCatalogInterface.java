package org.oracle;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.oracle.model.Product;
@WebService(name="TestMartCatalog")
public interface ProductCatalogInterface {

	//@WebMethod(exclude = true) @WebMethod
	//public abstract List<String> getProductCategories();

	@WebMethod(action = "fetch_categories", operationName = "fetch_categories")
	//not necessary 
	public abstract List<String> getProductCategories(String category);

	@WebMethod
	public abstract boolean addProductCategories(String category, String product);

	@WebMethod
	@WebResult(name="PP") 
	public abstract List<Product> getProductCategoriesV2();

}