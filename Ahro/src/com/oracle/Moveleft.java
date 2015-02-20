package com.oracle;

public class Moveleft {

	public static void move(int[] a, int begin, int k)
	{
		int step  = k;
		int zero = begin;
		
		if(step <= 0)
		{
			return;
		}
		int len = a.length;
		if(len - zero == step)
		{
			return;
		}
		
	    int pos =  zero;
	    int remain =  len - pos -step;
		while(remain >= step)
	
		{
			for(int s  = 0; s < step; s ++)
			swap(a, pos + s, pos + step + s);
		
			pos =  pos + step;
			remain =  len - pos - step;
		}
		if(remain != 0)
		{
			for(int f = 0; f < remain; f ++)
			{
				
				swap(a, pos , pos + step);
				pos =  pos  + 1;
				
			}
			move(a, pos, step - remain);
			//
		}
	}
	public static void swap(int[] a, int k, int l)
	{
		int temp = a[k];
		a[k] = a[l];
		a[l]= temp;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1,2,3,4,5,6,7,8};
		
		Moveleft.move(a, 0, 3);
		for(int i = 0; i < a.length; i ++)
			System.out.print(a[i]);
		System.out.print("\r\n");

	}

}
