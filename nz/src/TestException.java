import java.io.File;


public class TestException  implements java.io.Serializable{

	/**
	 * @param args
	 */
	
	static char[] getChar(byte[]  array)
	{
		char[] carr =  new char[4];
		int i = 0;
		for(byte c: array){
			carr[i] = (char)c++;
			i ++;
		}
		return carr;
	}
	
	private class Defin{
		public void p(){
			
			  System.out.println(TestException.this);
		}
		
	}
	
	public static void main(String[] args) {
		int [][] a3 = new int[5][];
		
	   byte x[] = new byte[] {2,3,4,5};
	   for(final int i:getChar(x))
		   System.out.println(i + "");

	
        double d =  -27.2345;
        System.out.println(Math.ceil(d));
        System.out.println(Math.round(d));
        System.out.println(Math.abs(d));
        System.out.println(Math.floor(d));
        
	}
	
}

