package com.eric.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Circle implements Shape {
    private Point center;
	public Point getCenter() {
		return center;
	}
	@Autowired
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public void draw() {
		System.out.println("Drawing an circle....");
		System.out.println("Circle: and the center is " +  center);

	}
	@PostConstruct
	public void init() {
		System.out.println("initing an circle....");
	}
	@PreDestroy
	public void destroy() {
		System.out.println("initing an circle....");
	}

}
