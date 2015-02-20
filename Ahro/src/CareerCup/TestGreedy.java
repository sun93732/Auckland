package CareerCup;

public class TestGreedy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(removeK(1432219,3));
		System.out.println(removeK(1432819,3));
		System.out.println(removeK(1232819,3));//1232819-->12219
		System.out.println(removeK(123456789,3));
		System.out.println(removeK(987654321,3));
		System.out.println(removeK(948372615,3));

	}
	//val = 1432219, k=3, return 1219
	//val = 1232819, k=3, return ?
    public static int removeK(int val, int k){
    	int result = 0;
    	char[] nums =  new String(val + "").toCharArray();
    	if(k >=  nums.length){
    		return 0;
    	}
    	if(k == 0){
    		return val;
    	}
    	int i = 0, j = 0;
    	boolean flag =  false;
    	
    	for(j = 0; j < nums.length - 1; j ++)
    	{
    		if(nums[j] > nums[j + 1] && i < k)//remove this,but how?
    		{
    			nums[j] =  'x';
    			i = i + 1;
    			flag = true;
    		}
    	}
    	if(!flag)
    	{
    		for(int x = nums.length -1; x > nums.length -1 - k; x --)
    		{
    			nums[x] = 'x';
    		}
    		j = 0;
          	for(; j < nums.length; j ++)
        	{
        		if(nums[j] != 'x'){
        			result = result*10 + (nums[j] - '0');
        		}
        	}
          	return result;
    	}
    	j = 0;
      	for(; j < nums.length; j ++)
    	{
    		if(nums[j] != 'x'){
    			result = result*10 + (nums[j] - '0');
    		}
    	}
      	return removeK(result, k - i);
    	/*if(i  < k ){
    		
    	}
    	*/
    	
    	
    }
}
