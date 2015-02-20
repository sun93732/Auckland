
public class TestThread {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("main method started");
		MyDaemonThread thread = new MyDaemonThread();
		thread.start();
		thread.join();
		System.out.println(thread.isAlive());
	}

}

class MyDaemonThread extends Thread{
	public void run() {
		System.out.println("My Thread run method.....");
	}
	public MyDaemonThread(){
		setDaemon(true);
	}
}