package com.oracle;

public class SelectSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = {72,6,57,88,60,42,83,73,48,85};
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");
		
		selectSort(array);
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");

	}
	
	public static void selectSort(int[] array)
	{
		for(int i = 0; i < array.length; i ++)
		{
			int min = array[i];
			int pos  = i;
			for(int j = i + 1; j < array.length; j ++)
			{
				if(array[j] < min)
				{
					min = array[j];
					pos = j;
				}
			}
			array[pos] = array[i]; 
			array[i] = min;
		}
	}

}
