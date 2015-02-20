package com.oracle;

public class PopSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = {72,6,57,88,60,42,83,73,48,85};
		new PopSort().popSort(array,array.length);
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");
	}
	
	public void swap(int[] data, int a, int b) {  
        int t = data[a];  
        data[a] = data[b];  
        data[b] = t;  
    } 
	
	public void popSort(int[] a, int n){
		int j, k, flag =n;
		while(flag >0)
		{
			k = flag;
			flag = 0;
			for(int i =1; i < k; i++)
			{				
				if(a[i -1 ] >= a[i])
				{
					swap(a, i -1, i);
					flag = i ; 
				}
				
			}
		}
		
		
	}

}
