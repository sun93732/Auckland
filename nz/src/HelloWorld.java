import java.util.Random;


public class HelloWorld {

	public String sayHello(String name)
	{
		return name  + " say hello world!";
	}
    public int getAge(int i)
    {
    	return i +  new Random().nextInt();
    }
}
