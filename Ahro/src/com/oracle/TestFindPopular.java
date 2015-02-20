package com.oracle;

import java.util.Map;
import java.util.TreeMap;

public class TestFindPopular {
	int[] a = {1,6,57,88,60,42,83,6,73,48,85,6};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestFindPopular().printPop();
		new TestFindPopular().printPop2();

	}
	public void printPop(){
		java.util.Arrays.sort(a);
		int count = 1;
		int lastPos = 0;
		int max = 0;
		for(int i = 1; i < a.length; i++)
		{
			if(a[i] == a[i -1])
			{
				count ++;
				if(count > max){
					max = count;
					lastPos = i;
				}				
			}
			else{
				count = 1;				
			}
			
		}
	
		System.out.println("most count= " + max);
		System.out.println("most Val= " + a[lastPos]);
	}
	public void printPop2()
	{
		Map<Integer, Integer> tree = new TreeMap<Integer, Integer>();
		for(int i = 0; i < a.length; i ++)
		{
			if(tree.containsKey(a[i]))
			{
				tree.put(a[i], tree.get(a[i]) +1);
			}
			else
			{
				tree.put(a[i], 1);
			}
		}
		for(Map.Entry<Integer, Integer> entry: tree.entrySet())
		{
			System.out.println("val = " + entry.getKey());
			System.out.println("count = " + entry.getValue());
		}
			
	}

}
