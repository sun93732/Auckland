package com.eric.spring;

public class Circle implements Shape {
    private Point center;
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	@Override
	public void draw() {
		System.out.println("Drawing an circle....");
		System.out.println("Circle: and the center is " +  center);

	}

}
