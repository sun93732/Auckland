package multithread;

import java.util.HashMap;

public class DeadLockCase {

	/**
	 * @param args
	 */
	
	public HashMap map = new HashMap();
	public static void main(String[] args) {
		DeadLockCase cc = new DeadLockCase();
		// TODO Auto-generated method stub
		
		
		int[] array = {1, 2,3};
		int[] bbb1 = array.clone();
		array[1] = 6;
		System.out.println(cc.getClass());
		System.out.println(bbb1.equals(array));

	}

}

class MuteThread implements Runnable {

	Object obj1, obj2;
	
	public MuteThread(Object o1, Object o2){
		this.obj1 = o1;
		this.obj2 = o2;
	}
	
	@Override
	public void run() {
		String name  =  Thread.currentThread().getName();
		System.out.println("Thread " + name + " is acquring the lock on " + obj1  );
		synchronized(obj1) {
			System.out.println("Thread " + name + " is acqured the lock on " + obj1  );
			work();
			System.out.println("Thread " + name + " is acquring the lock on " + obj2  );
			synchronized(obj2) {
				System.out.println("Thread " + name + " is acqured the lock on " + obj2  );
				work();
				System.out.println("Thread " + name + " released the lock on " + obj2  );
				
			}
			System.out.println("Thread " + name + " released the lock on " + obj1  );
			System.out.println("Thread " + name + " is done!"  );
			
		}
		
	}
	private void work() {
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}