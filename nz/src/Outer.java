
public class Outer {

	public static int age;
	
	public int xage;
	
	public static class Inner1{
		private void print(){
			System.out.println(age);
		}
	}
	public class Inner2{
		private void print(){
			System.out.println(xage);
		}
	}

}
