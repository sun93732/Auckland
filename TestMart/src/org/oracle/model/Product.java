package org.oracle.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Product {
	private double price;
	private String name, sku;
	
	private Label label;
	
	public Product() {
		
	}
	public Product(String name, String sku, double price) {
		super();
		this.name = name;
		this.sku = sku;
		this.price = price;
		this.label = new Label();
		this.label.setId(name.length());
		this.label.setTag(name.concat("_sufix"));
	}
	@XmlElement(name="ProductName")
	public String getName() {
		return name;
	}
	@XmlElement(name="ProductLabel")
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku + "ddfadsfdsa";
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
