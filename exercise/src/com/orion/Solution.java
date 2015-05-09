package com.orion;

class Solution {
	public static void main(String[] args) {
		int val = 5;
		int valx  = solution(5, 70000);
		System.out.println(valx);

	}

	public static int solution(int M, int N) {
		// write your code in Java SE 8
		if (M < 0 || N < 0) {
			return -1;
		}
		if (M >= N) {
			return -1;
		}

		char[] result = getBit(M);


		for (int val = M + 1; val <= N; val++) {
			result = addBits(result, val);
	
		}
		int finalResult = 0;
		int len = result.length;
		for (int i = 0; i < len; i++) {
			if (result[i] == '1') {
				finalResult = finalResult + (int) Math.pow(2, len - i - 1);
			}
		}

		return finalResult;
	}

	public static char[] addBits(char[] fromBit, int to) {

		
		char[] toBit = getBit(to);
		char[] result = new char[toBit.length];
		if (fromBit.length < toBit.length) {
			char[] temp = new char[toBit.length];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = '0';
			}
			for (int i = 0; i < fromBit.length; i++) {
				temp[temp.length - fromBit.length + i] = fromBit[i];
			}
			fromBit = temp;
		}

		for (int i = 0; i < toBit.length; i++) {
			if (fromBit[i] == '1' && toBit[i] == '1') {
				result[i] = '1';
			} else {
				result[i] = '0';
			}
		}
		return result;
	}

	public static char[] getBit(int d) {
		String result = "";
		long inte = (long) d;

		while (inte > 0) {
			result += inte % 2;
			inte = inte / 2;

		}

		char[] c = result.toCharArray();
		char[] cc = new char[c.length];
		for (int i = c.length; i > 0; i--) {
			cc[cc.length - i] = c[i - 1];
		}
		return cc;
	}
}
