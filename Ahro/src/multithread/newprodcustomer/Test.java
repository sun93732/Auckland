package multithread.newprodcustomer;

public class Test {
	public static void main(String[] args) {
		Basket basket = new Basket();
		// 两个厨师两个客户
		Kitchener kitchener1 = new Kitchener(1,basket);
		//Kitchener kitchener2 = new Kitchener(2,basket);
		Customer customer1 = new Customer(1,basket);
		//Customer customer2 = new Customer(2,basket);
		new Thread(kitchener1).start();
		//new Thread(kitchener2).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(customer1).start();
		//new Thread(customer2).start();
	}

}
