package com.oracle;
//������
public class HeapSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	//  �¼���i���  �丸���Ϊ(i - 1) / 2  
	void MinHeapFixup(int a[], int i)
	{
		int temp, k;
		temp = a[i];
		k =  (i-1)/2;
		while(k >=0 && i != 0)//������������Ҫ
		{
			if(a[k] <= temp)
			{
				break;
			}
			
			a[i] = a[k];//����Ų�����ˣ���
			i = k;
			k = (i-1)/2;
		}
		a[i] = temp;
	}

	//����С���м����µ�����nNum  
	void MinHeapAddNumber(int a[], int n, int nNum)  
	{  
	    a[n] = nNum;  
	    MinHeapFixup(a, n);  
	}  
	//  ��i�ڵ㿪ʼ����,nΪ�ڵ����� ��0��ʼ���� i�ڵ���ӽڵ�Ϊ 2*i+1, 2*i+2  
	void MinHeapFixdown(int a[], int i, int n)
	{
		int temp ,j;
		temp  =  a[i];
		j = 2*1 +1;
		while(j < n)
		{
			if(j+1 < n && a[j] >= a[j+1])
				j++;
			if(temp <= a[j])//����С�ı�
				break;
			a[i] = a[j];
			i = j;
			j = 2*i +1;
			
		}
		a[i] = temp;
	}


}
