package multithread.newprodcustomer;

import java.util.concurrent.TimeUnit;

/*
 * ��ʦ�࣬�����������
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
		//�ó�ʦ����10�����
		for (int i = 1; i <= 10; i++) {
			Bread bread = new Bread("��" + id + "����ʦ���ɵ����");
			basket.push(id,bread);
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("�����������");
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
