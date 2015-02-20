package syc;

import java.math.BigInteger;

public class Test123 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

      plus(0);
      plus(1);
      plus(2);
      plus(23);
      plus(123);
      plus(9999);
	}
	public static void plus(int input){
		BigInteger  result =  new BigInteger("1");
		
		if(input <= 0)
		{
			System.out.println("final result is 0");
			return;
		}
		BigInteger bigNum = new BigInteger(input + "");
		int reverse = 0;
		int x =  input /10;//整除
		int y =  input%10;//求余
		reverse = y;
		input = x;
		while(x > 0)
		{
			x =  input /10;//整除
			y =  input%10;//求余
			input  = x;
			reverse = reverse * 10 + y;
		}

		//ok we got 321 here.
		for(int i = 1; i <= reverse; i ++)
		{
			result = result.multiply(bigNum);
		}
		System.out.println("final result is " + result.toString());
				
	}

}
