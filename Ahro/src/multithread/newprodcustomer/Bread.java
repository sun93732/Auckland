package multithread.newprodcustomer;

/*
 *面包类，用于存放厨师生产的面包
 */
public class Bread {
	private String producer;

	public Bread(String producer) {
		super();
		this.producer = producer;
	}

	@Override
	public String toString() {
		return producer;
	}
}
