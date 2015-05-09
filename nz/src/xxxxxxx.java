import java.util.Set;


public class xxxxxxx <T> {

	/**
	 * @param args
	 */
	private static class iner{
		
	}
	private Set<T> set;
	public Set<T> getxx()
	{
		return this.set;
	}
	public void Test(xxxxxxx<?> collection) {
		Set<?> set =  getxx();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int x  = 5, y = 0;
		try {
			try {
				System.out.println(x);
				System.out.println(x/y);
				System.out.println(y);
			}catch (ArithmeticException e) {
				// TODO Auto-generated catch block
				System.out.println("Inner catch1");
				throw e;
			} 
			catch (RuntimeException e) {
				// TODO Auto-generated catch block
				System.out.println("Inner catch2");
				throw e;
			}
			finally
			{
				System.out.println("Inner Finally");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("outer CCCy");
		}

	}

}
