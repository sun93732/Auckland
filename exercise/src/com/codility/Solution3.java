package com.codility;

public class Solution3 {
	
	public static void main(String[]  arg) {
		int[] array = {Integer.MIN_VALUE,-7, -5, -3, -2,-1,0, 1,3,4,5,6};
		int x  = getAbsCount(array);
		System.out.println(x);
	}
	
	public static int getAbsCount(int array[]) {
		if(array == null) {
			return 0;
		}
		int count = 0;
		int head =  0;
		int tail =  array.length -1;
		
		if(array[head] == Integer.MIN_VALUE) {
			head ++;
			count ++;
		}
		
		while(head < tail) {
			if(Math.abs(array[head]) == Math.abs(array[tail]) ) {
				count ++;
				head ++;
				tail --;
			}
			else if(Math.abs(array[head]) > Math.abs(array[tail]) ) {
				count ++;
				head ++;
			}
			else {
				count ++;
				tail --;
			}
		}
		return count;
		
	}

}
