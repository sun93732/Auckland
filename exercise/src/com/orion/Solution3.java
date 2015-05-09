package com.orion;

import java.util.HashSet;
import java.util.Set;

public class Solution3 {
	
	public static void main(String[] arg) {
		int[] array = {5,4,0,3,1,6,2};
		int max = solution(array);
	}
	
	 public static int solution(int[] A) {
	        // write your code in Java SE 8
		 if(A == null || A.length ==0) {
			 return 0;
		 }
		 int maxlen = 1;
		 
		 for(int index = 0; index < A.length; index ++) {
			 Set set = new HashSet();
			 set.add(index);
			 int count = 1;
			 int val =  A[index];
			 while(val < A.length && !set.contains(val)) {
				 count ++;
				 val = A[val];
			 }
			 if(maxlen <= count) {
				 maxlen = count;
			 }
		 }
		 
		 return maxlen;
	    }

}
