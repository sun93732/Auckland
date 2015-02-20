package test;

import java.io.*;
import java.util.Vector;

public class Solution {
	public int getIntFromStr(String str) {
		int number = 0;
		try {
			number = Integer.valueOf(str);
		} catch (NumberFormatException e) {

			e.printStackTrace();
			System.out.println("the number input is wrong!");
			return -1;
		}
		return number;

	}

	public static void main(String args[]) throws Exception {
		Solution sl = new Solution();
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
		Vector<String> vec = new Vector<String>();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		String str = "";
		while ((str = br.readLine()) != "DONE") {
			vec.add(str);
		}
		if (vec.size() == 0 && vec.size() == 1) {
			System.out
					.println("the climber does not start or the mounts info is empty, so the result = 0");
			return;
		}

		int number = 0; // how many mounts to be done
		number = sl.getIntFromStr(vec.get(0).trim());
		if (number == -1) {
			System.out.println("the number input is wrong!");
			return;
		}

		if (vec.size() - 1 != number) {
			System.out.println("the number input is wrong!");
			return;
		}

		int total = 0;
		// ��������ܵĺ�ɣ�����ֻ��������ɽ
		for (int i = 1; i < vec.size(); i++) {

			int markHeight = 0;
			String[] cur = vec.get(i).split(",");

			int startCur = sl.getIntFromStr(cur[0]);
			int endCur = sl.getIntFromStr(cur[1]);
			int hightCur = sl.getIntFromStr(cur[2]);
			if (startCur == -1 || endCur == -1 || hightCur == -1) {
				System.out.println("the mount info input is wrong!");
				return;
			}
			if (i != vec.size() - 1)// i is not the last mount
			{
				String[] next = vec.get(i + 1).split(",");
				int startNext = sl.getIntFromStr(next[0]);
				int endNext = sl.getIntFromStr(next[1]);
				int hightNext = sl.getIntFromStr(next[2]);

				// ��һ����ֻ�����жϵ�
				if (startNext > startCur && startNext < endCur) // ����ɽ��ס�˱���
				{
					total = total + (hightCur - markHeight);// up
					markHeight = hightCur;// ��¼�����ڵĸ߶�
					if (hightNext < markHeight)// ������
					{
						total = total + (markHeight - hightNext);
						markHeight = hightNext;
					}
				} else {
					total = total + (hightCur - markHeight);// up
					total = total + hightCur;// down
					markHeight = 0;// ��������
				}

			} else// now let's deal with the last mount
			{
				if (startCur == -1 || endCur == -1 || hightCur == -1) {
					System.out.println("the mount info input is wrong!");
					return;
				}
				if (i >= 2)// ˵��������һ��ɽ����ǰ��
				{

					String[] pre = vec.get(i - 1).split(",");

					int startPre = sl.getIntFromStr(pre[0]);
					int endPre = sl.getIntFromStr(pre[1]);
					int hightPre = sl.getIntFromStr(pre[2]);

					// ǰһ����ֻ�����жϵ�
					if (startCur > startPre && startCur < endPre) // the two
																	// mount
																	// overlap,
																	// count the
																	// cur only
					{
						if (hightCur >= hightPre)// �����ɽ��
						{
							total = total + (hightCur - markHeight);// ������ڵڶ���ɽ�ȵ�һ���������
							total = total + hightCur;
						} else {// �����ɽ��
							total = total + hightCur;
						}

					}
				}// end if > 2
				else {// only one mount to clime
					total = total + hightCur * 2;

				}

			}

			if (i == vec.size() - 1) {
				total = total + endCur;
			}

		}

	}
}
