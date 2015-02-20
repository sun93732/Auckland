
public class Test {
public static void main(String[] args){
	Money m = new Money();
  Bank b1  = new Bank(m,0);
  Bank b2  =  new Bank(m,1);
  Thread t1 = new Thread(b1);
  Thread t2  =  new Thread(b2);
  t1.start();
  t2.start();

}
}
class Bank implements Runnable{
	Money money;
	int num = 0;
	public Bank(Money money, int k) {
		this.money = money;
		num = k;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(num == 0){
			money.put(this);
		} else {
			money.get(this);
		}
		
	}
	
}
class Money{
	public  synchronized void put(Object o) {
		System.out.println("put Ojbect " + o + " is ready to sleep");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("put Ojbect " + o + " is wakeup");
	}
	public  synchronized void get(Object o) {
		System.out.println("get Ojbect " + o + " is ready to sleep");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("get Ojbect " + o + " is wakeup");
	}
}
