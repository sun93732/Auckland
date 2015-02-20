package syc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class GlobalV {
	public final static Lock locker = new ReentrantLock();
	public final static Condition cond = locker.newCondition();
	public static boolean to_proceed = false;
}

class Response extends Thread {
	public void run() {
		while (true) {
			GlobalV.locker.lock();
			try {
				while (!GlobalV.to_proceed) {
					//GlobalV.cond.await();
				}
				System.out.println("Response:finish a job");
				GlobalV.to_proceed = false;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				GlobalV.locker.unlock();
			}
		}
	}
}

class Request extends Thread {
	public void run() {
		while (true) {
			GlobalV.locker.lock();
			try {
				GlobalV.to_proceed = true;
				//GlobalV.cond.signalAll();
				System.out.println("Request:send a job to Response");
			} finally {
				GlobalV.locker.unlock();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class Test6 {
	public static void main(String[] args) {
		Request req = new Request();
		Response res = new Response();
		req.start();
		res.start();
		
	}
}