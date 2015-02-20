package math;

public class Plainmdrom {

	/**
	 * @param args
	 * �����㷨��ֱ���������������٣���Ȼţ
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      System.out.println(checkPlain(1));
	}
	
	static boolean checkPlain(int x){
		if(x < 0) return false;
		if(x == 0) return true;
		int div = 1;
		while(x / div > 10){
			div = div * 10;
		}
		while(x > 0){
			int i = x % 10;
			int j = x / div;
			if(i != j) 
				return false;
			x = (x % div)/10;
			div = div/100;
		}
		return true;
	}
    /**
     * ����������ò�Ʋ����á�����
     * */
	static boolean  isPalindrome(int x, int y) {
		if (x < 0)
			return false;
		if (x == 0)
			return true;
		if (isPalindrome(x / 10, y) && (x % 10 == y % 10)) {
			y /= 10;
			return true;
		} else {
			return false;
		}
	}

	static boolean  isPalindrome(int x) {
		return isPalindrome(x, x);
	}
}
