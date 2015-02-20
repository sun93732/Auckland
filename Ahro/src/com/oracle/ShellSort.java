package com.oracle;
//Ï£¶ûÅÅÐò
public class ShellSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = {1,6,57,88,60,42,83,73,48,85};
		new ShellSort().sort(array, array.length);
        for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");
	}
	public void sort(int[] a, int n)
	{
		int i, j, gap, k;
		for(gap = n/2; gap >0; gap = gap/2)
		{
			for(i = 0; i < gap; i++)
				for(j = i+gap; j < n; j+=gap)
				{
					int tmp = a[j];
					for(k = i; k < j; k = k+gap)
					{
						if(a[k] > tmp) 
						break;
					}
					for(int s = j; s> k; s= s -gap)
						a[s] = a[s-gap];
                    a[k] = tmp;					
				}
		}
	}	
}
