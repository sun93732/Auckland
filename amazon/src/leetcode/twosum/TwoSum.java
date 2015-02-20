package leetcode.twosum;

public class TwoSum {

	/**
	 * Given an array of integers, find two numbers such that they add up to a
	 * specific target number.
	 * 
	 * The function twoSum should return indices of the two numbers such that
	 * they add up to the target, where index1 must be less than index2. Please
	 * note that your returned answers (both index1 and index2) are not
	 * zero-based.
	 * 
	 * You may assume that each input would have exactly one solution.
	 * 
	 * Input: numbers={2, 7, 11, 15}, target=9 Output: index1=1, index2=2
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       int[] numbers={2, 7, 11, 15};
       twoSum(numbers, 17);
	}
	public static void twoSum(int[] array, int target){
		if(array.length < 2)
		return;
		for(int i= 0; i < array.length - 1; i ++)
		{
			for(int j= 1; j < array.length; j ++)
			{
				if(array[i] + array[j] == target)
				{
					System.out.println("x = " + i + " y=" + j);
				}
			}
		}
	}

}
