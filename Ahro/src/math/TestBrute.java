package math;

public class TestBrute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=1; i < 100; i++)
		{
			System.out.println("the number " + i + " is " + TestBrute.isBrute(i));
		}

	}
    public static boolean isBrute(long val){
    	if(val ==1)
    	return false;
    	if(val ==2)
    		return true;
    	for(int i = 2; i <= Math.sqrt(val); i++)
    	{
    		if(val%i == 0)
    			return false;
    	}
		return true;
    }
    
    public static boolean Brute_Force(int n) {  
        if(n!=1){  
            for (int i = 2; i <= Math.sqrt(n); i++){  
                if (n % i == 0){  
                    return false;  
                }  
            }  
            return true;  
        }else{  
            return false;  
        }  
    }  
}
