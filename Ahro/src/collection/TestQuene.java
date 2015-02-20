package collection;

import java.util.LinkedList;
import java.util.Queue;

public class TestQuene {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<String> queue = new LinkedList<String>();
		queue.offer("Hello");
		queue.offer("world");
		System.out.println(queue.size());
		String str;
		while((str = queue.poll())!=null)
		{
			System.out.println(str);
		}
		//queue.peek();

	}

}
