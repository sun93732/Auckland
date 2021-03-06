package com.oracle;
//堆排序
public class HeapSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	//  新加入i结点  其父结点为(i - 1) / 2  
	void MinHeapFixup(int a[], int i)
	{
		int temp, k;
		temp = a[i];
		k =  (i-1)/2;
		while(k >=0 && i != 0)//结束条件很重要
		{
			if(a[k] <= temp)
			{
				break;
			}
			
			a[i] = a[k];//往下挪，别反了！！
			i = k;
			k = (i-1)/2;
		}
		a[i] = temp;
	}

	//在最小堆中加入新的数据nNum  
	void MinHeapAddNumber(int a[], int n, int nNum)  
	{  
	    a[n] = nNum;  
	    MinHeapFixup(a, n);  
	}  
	//  从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2  
	void MinHeapFixdown(int a[], int i, int n)
	{
		int temp ,j;
		temp  =  a[i];
		j = 2*1 +1;
		while(j < n)
		{
			if(j+1 < n && a[j] >= a[j+1])
				j++;
			if(temp <= a[j])//和最小的比
				break;
			a[i] = a[j];
			i = j;
			j = 2*i +1;
			
		}
		a[i] = temp;
	}


}
