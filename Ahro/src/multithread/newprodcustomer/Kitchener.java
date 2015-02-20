package multithread.newprodcustomer;

import java.util.concurrent.TimeUnit;

/*
 * 厨师类，用于生产面包
 */
public class Kitchener implements Runnable {
	private Basket basket;
	private int id;

	public Kitchener(int id,Basket basket) {
		super();
		this.id = id;
		this.basket = basket;
	}

	@Override
	public void run() {
		//让厨师生产10个面包
		for (int i = 1; i <= 10; i++) {
			Bread bread = new Bread("第" + id + "个厨师生成的面包");
			basket.push(id,bread);
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("继续生产面包");
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
