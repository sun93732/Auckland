package com.oracle;
//插入排序
public class InsertSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = {1,6,57,88,60,42,83,73,48,85};
        new InsertSort().sort(array, array.length);
        for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");
	}
	public void sort(int[] a, int n)
	{
		for(int i = 1; i < n; i ++)
		{
			int k = 0;
			int tmpc = a[i];
			for(k = 0; k < i; k ++)
			{
				if(a[k] >= tmpc)
					{
						break;						
					}
				
			}
			
			for(int tmp = i; tmp > k; tmp --)
			
			{
				a[tmp] = a[tmp -1];
			}
			a[k] = tmpc;//这句和上面的次序和重要
			
		}
	}

}
