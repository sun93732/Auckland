package com.oracle;

public class StackSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = {1,6,57,88,60,42,83,73,48,85};
		int[] result = new int[array.length ];
		
		MakeMinHeap(array, array.length);
		MinheapsortTodescendarray(array, array.length);
        for(int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		System.out.println(" \r\n");

	}

	// 新加入i结点 其父结点为(i - 1) / 2

	public static void MinHeapFixup(int a[], int i) {
		int j = (i - 1) / 2; // parent of i
		int temp = a[i];
		while (j >= 0) {
			if (a[j] <= a[i]) {
				break;
			}
			a[i] = a[j];
			i = j;
			j = (i - 1) / 2;
		}
		a[i] = temp;
	}

	// 从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2

	public static void MinHeapFixdown(int a[], int i, int n) {
		int temp = a[i];
		int j = 2 * i + 1;
		while (j < n) {
			if (j + 1 < n && a[j + 1] < a[j]) {
				j++;
			}
			// now a[j] is the smallest one of two children
			if (a[j] >= temp) {
				break;
			}
			a[i] = a[j];// move j upwards
			i = j;
			j = 2 * i + 1;
		}
		a[i] = temp;
	}
	
	//建立最小堆	 
	public static void MakeMinHeap(int a[], int n)	 
	{
        for(int i = n/2 -1; i >= 0; i --){
        	MinHeapFixdown(a, i, n);
        }
	}

	public static void MinheapsortTodescendarray(int a[], int n)
	{
		for(int i = n-1; i >= 1; i --)
		{
			int temp  = a[i];
			a[i] = a[0];
			a[0] = temp;
			MinHeapFixdown(a, 0, i);
		}
		
	}

	

}
