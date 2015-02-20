package com.oracle.io;
import java.util.Scanner;
public class TestUtlScanner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input ;
		while(scanner.hasNext()) {
			input =  scanner.next();
			System.out.println(input);
		}
		

	}

}
