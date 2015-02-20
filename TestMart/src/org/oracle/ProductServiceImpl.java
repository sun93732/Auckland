package org.oracle;

import java.util.ArrayList;
import java.util.List;

import org.oracle.model.Product;

public class ProductServiceImpl {

	private List<String> bookList = new ArrayList<String>();
	private List<String> musicList = new ArrayList<String>();
	private List<String> foodList = new ArrayList<String>();
	
	public ProductServiceImpl() {
		bookList.add("The lost world");
		bookList.add("Seven guns");
		bookList.add("Hunger games");
		
		musicList.add("xiaobang");
		musicList.add("Leest");
		musicList.add("Beetoven");
		
		foodList.add("Apple");
		foodList.add("Watermeloon");
		foodList.add("Green Orange");
	}
	
	public List<String> getProductCategories(String category) {
    	switch(category) {
    	case "book":
    		return bookList;
    		
    	case "food":
    		return foodList;
    	case "musci":
    		return musicList;
    	default:
    		return new ArrayList<String>();
    	}

    }
	
	public boolean addProductCategories(String category, String product) {
    	switch(category) {
    	case "book":
    		bookList.add(product);
    		break;
    	case "food":
    		foodList.add(product);
    		break;
    	case "musci":
    		musicList.add(product);
    		break;
    	default:
    		return false;
    	}
        return true;
    }
	
	public List<Product> getProductCategories() {
    	List<Product> lst = new ArrayList<Product>();
    	lst.add(new Product("name1", "1234", 1109));
    	lst.add(new Product("xxxx", "1234", 999));
    	lst.add(new Product("yyyyyy", "1234", 100));
    	return lst;

    }
	
}
