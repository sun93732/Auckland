package com.oracle;
//πÈ≤¢≈≈–Ú
public class MergeSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = {1,6,57,88,60,42,83,73,48,85};
		int[] result = new int[array.length ];
		new MergeSort().mergeSort(array, 0,array.length-1, result);
        for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");

	}
	public void mergeArray(int a[], int first, int mid, int last, int temp[]){
		int i = first;
		int j = mid +1;
		int t = 0;
		while(i <= mid && j <= last)
		{
			if(a[i] <= a[j])
			{
				temp[t++] = a[i++];
				
			}
			else
			{
				temp[t++] = a[j++];
				
			}
			
		}
		while(i <= mid)
		{
			temp[t++] = a[i++];			
		}
		while(j <= last)
		{
			temp[t++] = a[j++];
		}
		for(i = 0; i < t ; i++)
		{
			a[first+i] = temp[i];
		}
	}
	public void mergeSort(int[] a, int begin, int last, int[] temp)
	{
		if(begin < last)
		{
			int mid  =  (last + begin) /2;
			System.out.println("begin = " + begin + " ,mid = " + mid + " ,last=" + last);
			mergeSort(a, begin, mid, temp);
			mergeSort(a, mid +1, last, temp);
			mergeArray(a, begin, mid, last, temp);
		}
			
		
	}

}
