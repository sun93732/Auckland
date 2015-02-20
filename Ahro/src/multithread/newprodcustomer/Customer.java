package multithread.newprodcustomer;

/*
 * 顾客类，用于消费面包
 */
public class Customer implements Runnable {
	private Basket basket;
	private int id;
	public Customer(int id,Basket basket) {
		super();
		this.id = id;
		this.basket = basket;
	}

	@Override
	public void run() {
		// 让顾客消费10个面包
		for (int i = 1; i <= 10; i++) {
			basket.pop(id);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
