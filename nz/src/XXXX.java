import java.util.ArrayList;
import java.util.Calendar;


public class XXXX  {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringBuilder sb  =  new StringBuilder("buffering");
		sb.replace(2, 7, "BUFFER1234567");
		System.out.println(sb);
		sb.delete(2, 4);
		System.out.println(sb);
		String s =  sb.substring(1,5);
		System.out.println(s);
		
		Integer before  =  new Integer(25);
		Integer after = ++before ==26? 5: new Integer(10);
		System.out.println(after.intValue() - before.intValue());
		
		int c = 1;
		System.out.println("abc" == "abc");
		
		System.out.println(new String("abc") == "abc");
		
		int j = 0;
		int a[] = {2,4};
		do for(int i:a)
		{
			System.out.println(i + "");
			
		}
		while(j++ < 1);
		
		
		System.out.println(String.format("local time %tT", Calendar.getInstance()));

		System.out.println(String.format("local time %tT", Calendar.getInstance()));
		System.out.println(String.format("local time %tT", Calendar.getInstance()));
		System.out.println(String.format("local time %1$tB", Calendar.getInstance()));
		System.out.println(String.format("local time %tH:%tM:%tS", Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance()));
        
		int a5[]  = new int[]{1,2,3,4};
		int ax[][][] = {{{1,2}},{{3,4}},{{5,6}}};
		//int [,] a = new int{1,2,3,4};
	    int[] axx = new int[4];
	    char[] ccc= new char[4];
 		//int [][] a3 = new int[5][];
	    //YYY yyy = new YYY(2);
		System.out.println(Math.floor(10.24));
		System.out.println(Math.floor(10.54));
		System.out.println(Math.ceil(10.54));
		System.out.println(Math.ceil(10.44));
		System.out.println(Math.round(10.54));
		System.out.println(Math.round(10.44));
		
		System.out.println(Math.floor(-10.24));
		System.out.println(Math.floor(-10.54));
		System.out.println(Math.ceil(-10.54));
		System.out.println(Math.ceil(-10.44));
		System.out.println(Math.round(-10.54));
		System.out.println(Math.round(-10.44));

	}

}
class YYY{
	 YYY(int a, int b){
		
	}
}