package com.oracle;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="book")
public class Book {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	private String name;
	
	private List<String> tags;
	
	private int price;
	
	public Book()
	{
		
	}
	
	public Book(String name, List<String> tags, int price) {
		this.name = name;
		this.tags = tags;
		this.price = price;
	}

}
