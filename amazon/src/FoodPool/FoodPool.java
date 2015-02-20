package FoodPool;

public class FoodPool {
	
	private int food = 0;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FoodPool food  = new FoodPool();
		Eater eat1 = new Eater(1,food);
		Eater eat2 = new Eater(2,food);
		Maker maker =  new Maker(1, food);
		new Thread(eat1).start();
		new Thread(maker).start();
		new Thread(eat2).start();

	}
	
	public synchronized int add() throws InterruptedException {
		while(food == 5) {
			wait();
		}
		food = food + 1;		
		notifyAll();
		return food;
	}
	
	public synchronized int remove() throws InterruptedException {
		while(food == 0) {
			wait();
		}
		food = food - 1;
		notifyAll();
		return food;
	}

}

class Eater implements Runnable {
	
	protected int i;
	protected FoodPool food;
	public Eater(int i, FoodPool food){
		this.i = i;
		this.food = food;
	}
	
	public void run() {
		while(true)
		{
			try {
				System.out.println("Eater "+ i + " is ready to eat");
				food.remove();
				
				System.out.println("Eater "+ i + " is finished eating");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Maker extends Eater{
	
	public Maker(int i, FoodPool food){
		super(i, food);
	}
	
	public void run() {
		while(true)
		{
			try {
				System.out.println("maker "+ i + " is ready to make one food");
				food.add();
				
				System.out.println("maker "+ i + " is finished making food");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
