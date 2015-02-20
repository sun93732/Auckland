package executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyCallable<Ojbect> implements Callable<Ojbect> {


	@Override
	public Ojbect call() throws Exception {
		// TODO Auto-generated method stub
		Thread.sleep(5*1000);
		return (Ojbect) Thread.currentThread().getName();
	}
	
	public static void main(String args[]) {
		ExecutorService executors = Executors.newFixedThreadPool(5);
		MyCallable<Object> call = new MyCallable<Object>();
		List<Future> lst = new ArrayList<Future>();
		for(int i = 0; i < 10; i ++ ){
			Future future  = executors.submit(call);
			lst.add(future);
		}
		 for(Future<String> fut : lst){
	            try {
	                //print the return value of Future, notice the output delay in console
	                // because Future.get() waits for task to get completed
	                System.out.println(new Date()+ "::"+fut.get());
	            } catch (InterruptedException | ExecutionException e) {
	                e.printStackTrace();
	            }
	        }
	        //shut down the executor service now
	        executors.shutdown();

	}

}
