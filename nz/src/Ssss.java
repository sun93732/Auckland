import java.util.Formatter;


public class Ssss {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String stringA = "A";
		String stringB= "B";
		String stringnull = null;
		
		StringBuffer buff = new StringBuffer("C");
		Formatter fmt = new Formatter(buff);
		fmt.format("%s%s", stringA, stringB);
		System.out.println("line1 "+ fmt);
		fmt.format("%-2s", stringB);
		System.out.println("line2 "+ fmt);
		fmt.format("%b",  stringnull);
		System.out.println("line3 "+ fmt);
	}

}
