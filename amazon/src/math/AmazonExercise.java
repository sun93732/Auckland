package math;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <b>This is an exercise from Amazon</b>
 * <br/><br/>
 * Problem statement:<br/>
 * Given an array of n integers, find the sum of the most common integer.<br/>
 * Example:  (2,4,5,6,4) ¨C return 8 (as 4+4 = 8) .
 * (1,2,1,3,1) ¨C return 3 (as 1+1+1 = 3).
 *  
 * @author Steven Huang
 * @since 2012/03/10
 *
 */
public class AmazonExercise {
	
	/**
	 * It is a method to find the most common integer 
	 * in a given array of n integers. <br/>
	 * 
	 * Firstly, we sort the array in ascending order which puts the familiar elements together.
	 * It looks like that the elements now are in different types. <br/>
	 * For example: [1,2,2,1,3,3,4,3] -> [1,1,2,2,3,3,3,4].
	 * We got four types after sorting, they are : 1,2,3,4. <br/>
	 * 
	 * Then we find out the count of different types 
	 * by minus the started index with the last index of each type.<br/>
	 * 
	 * And then compare the latest count with the old one, 
	 * it will be recorded if it is bigger than or same to the old one. <br/> 
	 * 
	 * For the sorting, we choose the jdk method Arrays.sort() which is a tuned quicksort 
	 * and offers better performance. <br/>
	 * And then we need a linear search to get the sum of the frequency number. It costs O(n). <br/>
	 * 
	 * But for the quick sort, the average cost is O(nlogn), the worst situation is O(n^2). <br/> 
	 * for the memory cost : O(logn) ,for the worst : O(n).
	 * 
	 * So the cost of the method provided here is depended on the assumption of quicksort.
	 * 
	 * @param arr the target array
	 * @return the list of the sum of the most common integer(s)
	 */
	public static ArrayList<Integer> sumMostFreqNum(int[] arr) {  
		ArrayList<Integer> mostFreqNumSum = new ArrayList<Integer>();
		//First I need to sort the array in ascending order
		//The method offers O(n*log(n)) performance.
		Arrays.sort(arr);
		
		//the index of the most frequency number in the array
		int mostFreqNumIdx = 0;
		// the count of the most frequency number  
		int count = 0;
		
		// the started index of the new type element. 
		// Because we sort the array firstly, so the same elements just stay nearly.
		// It looks they are just in the different types.
		int tmpNumIdx = 0;
		int newCount = 0;
		
		//additional, a linear comparison here to put the sum of the most frequency integer(s) to an array.
		//actually maybe more than one integer are most common in the array.
		//it offers O(n) performance.
		
		for(int i = 0; i < arr.length + 1; i++) {
			if(i == arr.length /*meet the end of the array*/ || 
			   arr[tmpNumIdx] != arr[i] /*if a new type if found*/) {
				//calculate the count of elements in a specific type
				newCount = i - tmpNumIdx;
				
				//if count of the elements of the new type is greater than or equals to the old one. 
				if(newCount >= count) {
					mostFreqNumIdx = tmpNumIdx;
					if(newCount > count) {
						//update the count of the most frequency number
						count = newCount;
						//find the best candidate , then drop others
						mostFreqNumSum.clear(); 
					}
					//add it to the result list
					mostFreqNumSum.add(arr[mostFreqNumIdx] * count);
				} 
				// the started index of the new type 
				tmpNumIdx = i;
			}
		}
		
		return mostFreqNumSum;
	}
	public static void main(String[]  args){
		int[] arr = new int[]{1,3,2,3,2};
		ArrayList<Integer> result = AmazonExercise.sumMostFreqNum(arr);
		
	}
}