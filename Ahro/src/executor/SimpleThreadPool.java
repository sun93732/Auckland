package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class SimpleThreadPool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executors = Executors.newFixedThreadPool(5);
		for(int i = 0; i < 10; i ++) {
			Worker worker = new Worker(i + "");
			executors.execute(worker);
		}
		executors.shutdown();
		while(executors.isShutdown()) 
		{
			
		}
		System.out.println("Get Done!");

	}

}
