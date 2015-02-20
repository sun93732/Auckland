import java.util.ArrayList;
import java.util.List;


public class Test {
	
	public  static  void show() {
		System.out.println("test static method could be called by a null obj");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test t = null;
		t.show();
		
		List<Integer> lst = new ArrayList<Integer>();
		//lst.add(1);
		//lst.add(2);
		
		int i = 0;
		for(i = 0; i < lst.size(); i ++)
		{
			
		}
		System.out.println("i = " + i);
		
	}
}
