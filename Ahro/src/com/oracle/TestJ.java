package com.oracle;

import java.util.HashSet;
import java.util.Set;

public class TestJ {
	public static void main(String[] args) {
		/*
		 * List<Object> list = new ArrayList<Object>(); list.add(....);
		 * list.add(....); ..... Object[] obj1 = list.toArray();
		 */
		// ����ע�Ͳ��ִ�����˼�ǣ������Լ���list�м���Ԫ�أ�Ȼ�󸳸�����obj1
		Integer[] obj1 = { 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		Set<Integer> s = new HashSet<Integer>();// HashSet����ȥ���ظ�
		for (Integer o : obj1) {
			s.add(o);
		} // ���ڵļ���s�����ظ��İ���obj1�е�����Ԫ��
		Integer[] obj2 = (Integer[]) s.toArray();// �Ѽ���s�е�Ԫ�ش�������obj2��
		int[] n = new int[obj2.length];// ��������������ÿһ��Ԫ�س��ֵĴ���
		int max = 0;
		for (int i = 0; i < obj2.length; i++) {
			int cout = 0;
			for (int j = 0; j < obj1.length; j++) {
				if (obj2[i].equals(obj1[j]))
					cout++;
				// ��obj2�е�Ԫ�ظ�obj1�е�ÿһ���Ƚϣ������ͬcout����
			}
			n[i] = cout;// ÿһ��Ԫ�س��ֵĴ�����������n
			// ����n���±�i������obj2���±���һһ��Ӧ�ġ�
			if (max < cout) {// �õ�Ԫ�س��ִ�������Ƕ��ٴ�
				max = cout;
			}
		}
		for (int i = 0; i < n.length; i++) {
			if (max == n[i]) {
				// ������ֵĴ����������������������Ӧ����obj2�е�Ԫ��
				System.out.println(obj2[i]);

			}
		}

	}
}