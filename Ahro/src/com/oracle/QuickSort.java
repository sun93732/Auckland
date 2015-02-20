package com.oracle;

public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = {72,6,57,88,60,42,83,73,48,85};
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");
		
		//new QuickSort().quickSort2(array, 0, array.length-1);
		new QuickSort().quickSort(array, 0, array.length-1);
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");

	}
	public void quickSort(int[] array, int l, int r)
	{
		if(l < r)
		{
			int b = l;
			int k = r;
			int midVal =  array[b];
			while(b < k)
			{
				while(b < k && midVal < array[k]) //从最右面开始找
					k --;
				if(b < k)
				{
					array[b] = array[k];
					b++;
				}
				while(b < k && midVal > array[b])//从左面开始找
					b ++;
				if(b < k)
				{
					array[k] = array[b];
					k--;
				}
				
				
			}
			array[k] = midVal;
			quickSort(array, l, b-1);
			quickSort(array, b+1, r);
			
		}
	}
	
	public void quickSort2(int[] array, int l, int r)
	{
		if(l < r)
		{
			int i = l, j = r;
			int x =  array[i];
			while (i < j)
			{
				while(i <j && array[j] >= x)
				{
					j = j - 1;
				}
				//ok while finish maybe find one number less than x
				if(i < j)//make sure 
				{
					array[i] = array[j];
					i = i + 1;
				}
				while(i <j && array[i] <= x)
				{
					i = i + 1;
				}
			
				if(i < j)//make sure 
				{
					array[j] = array[i];
					j = j - 1;
				}
			}
			array[i] = x;
			quickSort2(array, l,i -1);
			quickSort2(array, i + 1,r);
		}
	}

}
