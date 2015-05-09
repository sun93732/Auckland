package com.codility;

public class Solution4 {

	public static void main(String[] args) {
		int val  = 1041;
		int x  = bitgap(1041);
		System.out.println(x);

	}
	
	public static int bitgap(int val) {
		int count = 0;
		int max = 0;
		boolean flag  = false;
		while(val > 0) {
			
			if(val % 2 == 1){
			   if(flag == false) {
				   count = 0;
				   flag = true;
			   } else if(flag == true){
				   if(count > max) {
					   max = count;
					   
				   }
				   count = 0;
			   }
				   
				
			}
			else if (val % 2 == 0) {
				if(flag == true) {
					count = count + 1;
				}				
			}
			val = val >> 1;
		}
		
		return count;
	}

}
