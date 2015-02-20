package CareerCup;

import java.io.IOException;

public class TestArray1 {

	private int[] array = new int[100];
	
	private void init(){
		for(int i = 0; i < 50;  i ++)
		{
			array[i] = i;
			System.out.print(array[i] + " ");
		}
		for(int i = 50; i < 100;  i ++)
		{
			array[i] = 100 + i;
			System.out.print(array[i] + " ");
		}
		System.out.println( " ");
		
	}
	
	public void chg(){
		if(array.length % 2 != 0 || array.length <2)
		{
			System.out.println("invalid lengh of array");
		}
		int inc= array.length/2;
		for(int i = 0; i < (array.length/2) ; i++) {
			if(i %2 == 1){
				int temp = array[i + inc];
				array[i + inc ] = array[i];
				array[i]= temp;
			}
		}
		
		for(int i = 0; i < array.length; i ++)
			System.out.print(array[i] + " ");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ClassLoader.getSystemResources("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestArray1 test = new TestArray1();
		test.init();
		test.chg();

	}

}
