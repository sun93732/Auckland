package leetcode.str2int;

import java.math.BigInteger;

/**
 * 
 * Requirements for atoi: The function first discards as many whitespace
 * characters as necessary until the first non-whitespace character is found.
 * Then, starting from this character, takes an optional initial plus or minus
 * sign followed by as many numerical digits as possible, and interprets them as
 * a numerical value.
 * 
 * The string can contain additional characters after those that form the
 * integral number, which are ignored and have no effect on the behavior of this
 * function.
 * 
 * If the first sequence of non-whitespace characters in str is not a valid
 * integral number, or if no such sequence exists because either str is empty or
 * it contains only whitespace characters, no conversion is performed.
 * 
 * If no valid conversion could be performed, a zero value is returned. If the
 * correct value is out of the range of representable values, INT_MAX
 * (2147483647) or INT_MIN (-2147483648) is returned.
 * */
public class String2Int {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       System.out.println(str2Integer("   -123456666    "));
	}
	public static int str2Integer(String src){
		if(null == src || src.length() ==0 ||src.trim().length() == 0){
			return 0;
		}		
		int minus = 1;
		int beginIndex = 0;
		src =  src.trim();
		char[] ints = src.toCharArray();
		if(ints[0] == '-')
		{
			minus = -1;
			beginIndex = 1;
		}
		else if(ints[0] == '+')
		{
			minus = 1;
			beginIndex = 1;
		}
		else if(!isNumber(ints[0]))
		{
			beginIndex = 1;
		}
		else if(isNumber(ints[0])){
			beginIndex = 0;
		}
		BigInteger number = BigInteger.ZERO;
		BigInteger ten = new BigInteger("10");
		for(int i = beginIndex; i < ints.length; i ++)
		{
			if(isNumber(ints[i]))
			{
				number = number.multiply(ten).add(new BigInteger(new String(ints,i,1)));
			    System.out.println("number = " + number.toString());	
			    {
			    	//add judge here
			    }
			}
			
		}
		
		return  number.intValue();
	}
    public static boolean isNumber(char chr){
    	   if(chr<48 || chr>57)
    	         return false;
    	   return true;   	
    }
}
