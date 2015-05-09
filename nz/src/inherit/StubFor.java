package inherit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class StubFor {
	public StubFor()
	{
		
	}
	public int val  = 0;

	protected int testFunction(String val, boolean flag) {
		return 0;//
		
	}
	
	public static void main(String[] args) throws IOException{
		StubFor stub = new SubStub();
		System.out.println(stub.val);
		System.out.println(stub.testFunction("", true));
		
		
		StringBuilder builder =  new StringBuilder("welcome everybody");
		builder.replace(0, 7, "elcome");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		out.writeObject(new StringBuffer("Hello \uD801 \uDFFE"));
		byte[] b = baos.toByteArray();
		String s = new String(b);
		System.out.println(s);
		int i = 0;
		
	}

}

class SubStub extends StubFor{
	
	public SubStub(){
		
	}
	

	
	public int val = 1;
	/*
	@Override
	public int testFunction(String val, boolean flag) {
		return 0;//
		
	}
	
	
	@Override //不能抛出为定义的异常，只能抛出父类异常的子异常
	protected int testFunction(String val, boolean flag) throws  IOException
	{
		return 0;//
		
	}
	
	
	@Override
	protected short testFunction(String val, boolean flag) 
	{
		return 0;//
		
	}
	*/
	
	@Override
	public int testFunction(String val, boolean flag) 
	{

		return 1;//
		
	}
}
