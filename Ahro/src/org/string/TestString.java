package org.string;

public class TestString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int[] array= {49,38,65,97,76,13,27};
		int[] array= {6,5,4,3,2,1,0};
		TestString  t = new TestString();
		t.quickSort(array, 0, array.length -1);
       
	}
	public void quickSort(int[] array, int begin, int end )
	{
        if(begin < end)
        {
		int r = partition(array,begin, end);
		
			quickSort(array, begin, r-1);
			quickSort(array, r+1, end);
	
        }
	}
	public int partition(int[] array, int begin, int end )
	{
		
		
			
		int pivot = array[begin];
		int i = begin;
		for(int j = i +1; j<= end;j++)
		{
			if(array[j] <= pivot){							
				i = i +1;
				chg(array, i, j);
				continue;
			}
			
		}
		chg(array,begin, i);
		System.out.println("\r\n i = " + i);
		System.out.print("the curret array is ");
		for(int loop =0; loop < array.length; loop ++){
			System.out.print(" "+ array[loop]);
		}
		
		return i;
	}
	public void chg(int[] array, int i, int j)
	{
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

}
