package multithread.newprodcustomer;

/*
 * �˿��࣬�����������
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
		// �ù˿�����10�����
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
