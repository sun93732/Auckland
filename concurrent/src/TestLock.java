
public class TestLock {
	
	public  synchronized void lock1()
	{
		System.out.println("lock1 entered");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("lock1 exiting");
	}
	public  synchronized void lock2()
	{
		System.out.println("lock2 entered");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("lock2 entered");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestLock lock = new TestLock();
		Tester test1 = new Tester(lock, 1);
		Tester test2 = new Tester(lock, 2);
		new Thread(test1).start();
		new Thread(test2).start();

	}

}
class Tester implements Runnable{
	TestLock lock;
	int num = 0;
	public Tester(TestLock lock, int num) {
		this.lock = lock;
		this.num = num;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(num == 1) {
			lock.lock1();
		} else {
			lock.lock2();
		}
		
	}
	
}