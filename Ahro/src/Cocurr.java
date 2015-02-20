import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Cocurr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> lst = Collections.synchronizedList(new ArrayList<Integer>());
		for(int i = 0; i <= 10000; i++)
			lst.add(i);
		ThreadLoop loop  = new ThreadLoop(lst);
		ThreadModify modify =  new ThreadModify(lst);
		new Thread(loop).start();
		new Thread(modify).start();
	}

}

class ThreadLoop implements Runnable{
	List<Integer> lst;
	public ThreadLoop(List<Integer> lst){
		this.lst = lst;
	}
	@Override
	public void run() {
		
		synchronized(this.lst){
		Iterator it =  lst.iterator();
		while(it.hasNext())
		{
			System.out.println("the number is =" + it.next());
		}}
			
	}
	
}
class ThreadModify implements Runnable{

	List<Integer> lst = new ArrayList<Integer>();
	public ThreadModify(List<Integer> lst)
	{
		this.lst = lst;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i =10001; i <=10100; i++)
			lst.add(i);
	}
	
}