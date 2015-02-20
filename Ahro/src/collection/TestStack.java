package collection;

import java.util.Stack;

public class TestStack {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<Integer> info = new Stack<Integer>();
		for(int i = 0; i <= 10; i ++)
		{
			info.push(i);
		}
		while(!info.isEmpty())
		{
			System.out.println(info.pop());
		}

	}

}
