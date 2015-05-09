package com.codility;

import java.util.List;
//Perm-Missing-Elem
public class solution1 {
	
	public static void main(String[] arg) {
		int[] a = {1,2,3,5,6};
		int result = solution(a);
		System.out.println(result);
	}
	public static int solution(int[] A) {
		 // write your code here...
		int n = A.length, i;
		long r = n + 1;
		    for (i = 0; i < n; ++i) {
		        r = r + (i + 1) - A[i];
		    }
		    return (int)r;
	}

}
