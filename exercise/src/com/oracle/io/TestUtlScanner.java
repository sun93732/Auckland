package com.oracle.io;
import java.util.Scanner;
public class TestUtlScanner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		String input ;
		int value;
		while(scanner.hasNext()) {
			value =  scanner.nextInt();
			//String s = scanner.next();
			System.out.println(value);
		}
		

	}

}
