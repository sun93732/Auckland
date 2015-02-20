
/**
 * 同一任务可以再次持有对象锁 create on 2010.08.04 08:27
 * 
 * @since jdk1.6
 * @author maozj
 * @version 1.0
 * 
 */
public class SynchronizedClassHolder {
	/**
	 * Test client
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				new SynchronizedClassHolder().f1();
			}
		}.start();
	}

	private static int count = 10;

	/**
	 * synchronized f1()
	 */
	public synchronized void f1() {
		if (--count > 0) {
			System.out.println("f1() calling f2() count " + count);
			f2();
		}
	}

	/**
	 * synchronized f2()
	 */
	public synchronized void f2() {
		if (--count > 0) {
			System.out.println("f2() calling f1() count " + count);
			f1();
		}
	}
}
