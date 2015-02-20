package com.oracle.test;

public class NameC extends NameB{
	public void show() {
		(NameA)this.show();
		System.out.println("C");
	}
	
	public  static void main(String argv[])
	{
		NameC c =  new NameC();
		c.show();
	}
}
class NameB extends NameA{
	public void show() {
		super.show();
		System.out.println("B");
	}
}

class NameA {
	public void show() {
		System.out.println("A");
	}
}