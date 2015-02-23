package com.eric.spring;

public class Triangle {

	private String type;
	private int height;
	
	public int getHeight() {
		return height;
	}

	public Triangle(String type, int height) {
		this.type =  type;
		this.height = height;
		
	}
	public void draw() {
		System.out.println(getType() + " Triangle drawing...." + this.getHeight());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
