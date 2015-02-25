package com.eric.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Triangle implements InitializingBean, DisposableBean{
    private Point pointA;
    private Point pointB;
    private Point pointC;
   
	
	public Point getPointA() {
		return pointA;
	}

	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}

	public Point getPointC() {
		return pointC;
	}

	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}

	public void draw() {
		System.out.println("PointA: " + this.getPointA());
		System.out.println("PointB:" + this.getPointB());
		System.out.println("PointC:" + this.getPointC());
	}


	public void myInit() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("My initializing is done");
	}

	
	public void myDestroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" My deconstructing is done");
		
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("deconstructing is done");
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("initializing is done");
		
	}

	/*
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = ctx;
		
	}

	@Override
	public void setBeanName(String arg0) {
		// TODO Auto-generated method stub
		this.beanName = arg0;
		System.out.println("Bean name is " +  beanName);
		
	}
	*/	
}
