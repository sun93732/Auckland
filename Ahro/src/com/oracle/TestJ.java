package com.oracle;

import java.util.HashSet;
import java.util.Set;

public class TestJ {
	public static void main(String[] args) {
		/*
		 * List<Object> list = new ArrayList<Object>(); list.add(....);
		 * list.add(....); ..... Object[] obj1 = list.toArray();
		 */
		// 以上注释部分代码意思是：可以自己给list中加入元素，然后赋给数组obj1
		Integer[] obj1 = { 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		Set<Integer> s = new HashSet<Integer>();// HashSet用来去掉重复
		for (Integer o : obj1) {
			s.add(o);
		} // 现在的集合s中无重复的包含obj1中的所有元素
		Integer[] obj2 = (Integer[]) s.toArray();// 把集合s中的元素存入数组obj2中
		int[] n = new int[obj2.length];// 这个数组用来存放每一个元素出现的次数
		int max = 0;
		for (int i = 0; i < obj2.length; i++) {
			int cout = 0;
			for (int j = 0; j < obj1.length; j++) {
				if (obj2[i].equals(obj1[j]))
					cout++;
				// 用obj2中的元素跟obj1中的每一个比较，如果相同cout自增
			}
			n[i] = cout;// 每一个元素出现的次数存入数组n
			// 数组n的下标i跟数组obj2的下标是一一对应的。
			if (max < cout) {// 得到元素出现次数最多是多少次
				max = cout;
			}
		}
		for (int i = 0; i < n.length; i++) {
			if (max == n[i]) {
				// 如果出现的次数等于最大次数，就输出对应数组obj2中的元素
				System.out.println(obj2[i]);

			}
		}

	}
}