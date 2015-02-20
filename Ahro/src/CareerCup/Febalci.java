package CareerCup;

public class Febalci {

	private int printCursive(int n){
		int result;
		if(n == 0 || n ==1){
			System.out.println(1);
			return n;
		}
		result =  printCursive(n-2) + printCursive(n-1);
		System.out.println(result);
		return result;
	}
	private void print(int n){
		if(n == 0 || n ==1){
			System.out.println(1);
			return;
		}
		int k3, k2 , k1;
		k1 = 1; k2 = 1;
		System.out.print(k1);System.out.print(" ");
		System.out.print(k2);System.out.print(" ");
		for(int i = 2; i <= n; i ++){
			k3 = k2 + k1;
			System.out.print(k3);
			System.out.print(" ");
			
			k1 = k2;
			k2 = k3;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Febalci f = new Febalci();
		
		f.print(100);

	}

}
