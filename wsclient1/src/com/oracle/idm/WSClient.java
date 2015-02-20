package com.oracle.idm;

import java.util.List;

import org.oracle.TestMartCatalog;
import org.oracle.TestMartCatalogService;

import com.oracle.Product;

public class WSClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        
        TestMartCatalogService service1 =  new TestMartCatalogService();
        TestMartCatalog catalog = service1.getTestMartCatalogPort();
        List<Product> products = catalog.getProductCategoriesV2();
        if(products.size() >0) {
        	System.out.println(products.toString());
        }
        for(Product p: products)
        {
        	System.out.println(p.getName() + "-" + p.getPrice()+ "-"+p.getLabel() + "-"+ p.getSku());
        }
        
	}

}
