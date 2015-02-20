package sync;

public class Lock {
	public  synchronized void metho1(){
		try {
			Thread.sleep(2000);
			System.out.println("running method1");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  synchronized void metho2(){
		try {
			Thread.sleep(2000);
			System.out.println("running method2");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
