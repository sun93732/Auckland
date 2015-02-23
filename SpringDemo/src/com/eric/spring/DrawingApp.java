package com.eric.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class DrawingApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//BeanFactory factory =  new XmlBeanFactory(new FileSystemResource("spring.xml"));
		ApplicationContext ctx =  new ClassPathXmlApplicationContext("spring.xml");
		Triangle tri = (Triangle) ctx.getBean("triangle");
		tri.draw();

	}

}