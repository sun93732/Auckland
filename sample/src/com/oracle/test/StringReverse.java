package com.oracle.test;

public class StringReverse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String name = "xxxxabcd";
        System.out.println(reverse2(name));
	}
	
	public String reverse1(String src){
		if(src == null || src.length() ==0) {
			return src;
		}
		StringBuffer buf = new StringBuffer(src);
		
		return buf.reverse().toString();
		
	}
	public static String reverse2(String src){
		if(src == null || src.length() ==0) {
			return src;
		}
		StringBuffer buf = new StringBuffer();
		char[] chars =  src.toCharArray();
		for(int i = src.length() -1; i >=0 ; i --) {
			buf.append(chars[i]);
		}
		return buf.toString();
		
		
	}

}
