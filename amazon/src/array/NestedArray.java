package array;

import java.util.ArrayList;
import java.util.List;

public class NestedArray {

	/**
	 * For example, given the list {{1,1},2,{1,1}} the function should return 10
	 * (four 1's at depth 2, one *2 at depth 1)
	 * 
	 * Given the list {1,{4,{6}}} the function should return 27 (one 1 at depth
	 * 1, one 4 at depth 2, and *one 6 at depth 3)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList array = new ArrayList();
		ArrayList sub1 = new ArrayList();
		sub1.add(1);sub1.add(1);
		ArrayList sub2 = new ArrayList();
		array.add(2);
		ArrayList sub3= new ArrayList();
		sub3.add(1);sub3.add(1);
		array.add(sub1);
		array.add(sub2);
		array.add(sub3);
		int s = sum(array,1);
		System.out.println("sum = " + s);
		array.clear();
		sub1.clear();
		sub2.clear();
		sub3.clear();
		array.add(1);
		sub1.add(4);
		sub2.add(6);
		sub1.add(sub2);
		array.add(sub1);
		System.out.println("sum = " + sum(array,1));
	}
	public static int sum(List array, int depth){
		System.out.println("depth = " + depth);
		int sum = 0;
		for(Object obj: array){
			if(obj instanceof ArrayList){
				sum = sum + sum((ArrayList)obj, depth + 1);
			}
			else{
				sum = sum + ((Integer)obj)*depth;
			}
		}
		return sum;
	}

}
