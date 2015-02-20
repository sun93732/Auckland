package leetcode.threesum;

import java.util.Arrays;

/**
 * Given an array S of n integers, are there elements a, b, c in S such that a +
 * b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 * 
 * Note:  Elements in a triplet (a,b,c) must be in non-descending order. (ie, a
 * ≤ b ≤ c) The solution set must not contain duplicate triplets.
 * 
 * For example, given array S = [-1,0,1,2,-1,-4],
 * 
 * A solution set is: [ [-1, 0, 1], [-1, -1, 2] ]
 * */
public class Test3Sum {
	public static void main(String args[]) {
       int[] s = {-1,-1,0,1,2,3,3,-1,-4};
       get3Sum(s);
	}
	public static void get3Sum(int[] array){
		if(array.length < 3){
			System.out.println("wrong !");
		}
		Arrays.sort(array);
		for(int x: array)
		{
			System.out.print(x + " ");
		}
		System.out.println("");
		for(int i = 0; i < array.length - 2 && array[i] <=0; i ++){
			if (i>0 && array[i]==array[i-1]) 
				continue;
			int j = i + 1; 
			int k = array.length - 1;
			while(j < k)
			{
				if(array[i] + array[j] + array[k] < 0){
					j = j + 1;
					continue;
				}else if(array[i] + array[j] + array[k] > 0){
					k = k - 1;
					continue;
				}
				else if(array[i] + array[j] + array[k] == 0){
					System.out.println("got one !:" + array[i] + ", "+ array[j] + ", " + array[k]);
					do{
					    j = j + 1;
					}while(array[j] == array[j - 1]);
					do{
					    k = k - 1;
					}while(array[k] == array[k + 1]);
				}
				
			}
		}
		
	}
}
