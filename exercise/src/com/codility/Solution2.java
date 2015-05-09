package com.codility;


public class Solution2 {

	public static void main(String[] args) {
		int[]  array = {3,1,2,4,3};
		int index = solution(array);
		System.out.println(index);

	}
	public static int solution(int[] array) {
		if(array == null) 
			return 0;
		if(array.length == 1) {
			return 0;
		}
		long sum = 0;
		int index = 0;
		for(int i = 0; i < array.length; i ++) {
			sum += array[i];
		}
		long firstSum = 0;
		long lastSum = sum;
		long minAbs = sum;
		for(int i = 1; i < array.length; i ++) {
			firstSum = firstSum + array[i - 1];
			lastSum = lastSum - firstSum;
			if(Math.abs(firstSum - lastSum) <= minAbs) {
				minAbs = Math.abs(firstSum - lastSum);
				index = i;
			}			
		}
		
		return index;
		
	}
}
