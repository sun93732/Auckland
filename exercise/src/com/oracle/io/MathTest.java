package com.oracle.io;

public class MathTest {
	public static void main(String[] argv) {
		
		int i;  
		   int num = 0xFFFFFFE;  
		   System.out.println(num);  
		   for(i=0; i<4; i++) {  
		       num = num << 1;   
		     System.out.println(num);  
		   }  
		   
		   System.out.println("--------------------");  
		   int numx = 0xFFFFFF00;  
		   System.out.println(numx);  
		   for(i=0; i<4; i++) {  
			   numx = numx >> 1;   
		     System.out.println(numx);  
		   
		   }
		   System.out.println("--------------------");  
		   int numy = 0xFFFFFFFE;  
		   System.out.println(numy);  
		   for(i=0; i<4; i++) {  
			   numy = numy >>> 1;   
		     System.out.println(numy);  
		   }
	}

}
