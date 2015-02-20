package sync;

public class TestLock extends Thread{
	private Lock lock;
	private int id;
	public TestLock(Lock lock, int id){
		this.lock = lock;
		this.id = id;
	}
	public void run(){
		if(id == 0)
		{
			lock.metho1();
		}
		else{
			lock.metho2();
		}
	}
	public static void main(String[] arg){
		Lock lock = new Lock();
		TestLock t1 = new TestLock(lock,0);
		TestLock t2 = new TestLock(lock,1);
		t1.start();
		t2.start();
	}

}
