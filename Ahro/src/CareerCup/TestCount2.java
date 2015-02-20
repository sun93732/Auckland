package CareerCup;

public class TestCount2 {
	private int count2(int n){
		int result = 0;
		if(n ==0){
			result = 0;
			return result;
		}
		while(n!=0)
		{
			int m = n%10;
			if(m == 2){
				result ++;
			}
			n  = n/10;
		}			
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestCount2 t = new TestCount2();
		int num = 0;
		for(int i = 0; i <= 35; i ++){
			num = num + t.count2(i);
		}
		System.out.println(num);
		

	}

}
