package sync;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * �ֿ���Storageʵ�ֻ�����
 * 
 * Email:530025983@qq.com
 * 
 * @author MONKEY.D.MENG 2011-03-15
 * 
 */
public class Storage
{
	// �ֿ����洢��
	private final int MAX_SIZE = 1;

	// �ֿ�洢������
	private LinkedList<Object> list = new LinkedList<Object>();

	// ��
	private final Lock lock = new ReentrantLock();

	// �ֿ�������������
	private final Condition full = lock.newCondition();

	// �ֿ�յ���������
	private final Condition empty = lock.newCondition();

	// ����num����Ʒ
	public void produce(int num)
	{
		// �����
		lock.lock();

		// ����ֿ�ʣ����������
		while (list.size() + num > MAX_SIZE)
		{
			System.out.println("��Ҫ�����Ĳ�Ʒ������:" + num + "/t���������:" + list.size()
			        + "/t��ʱ����ִ����������!");
			try
			{
				// �������������㣬��������
				full.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			//System.out.println("����һ�ֲ���" + list.size());
		}

		// ����������������£�����num����Ʒ
		for (int i = 1; i <= num; ++i)
		{
			list.add(new Object());
		}

		System.out.println(Thread.currentThread().getId() + "���Ѿ�������Ʒ����:" + num + "/t���ֲִ���Ϊ��:" + list.size());

		// �������������߳�
		//full.signalAll();
		empty.signalAll();

		// �ͷ���
		lock.unlock();
	}

	// ����num����Ʒ
	public void consume(int num)
	{
		// �����
		lock.lock();

		// ����ֿ�洢������
		while (list.size() < num)
		{
			System.out.println("��Ҫ���ѵĲ�Ʒ������:" + num + "/t���������:" + list.size()
			        + "/t��ʱ����ִ����������!");
			try
			{
				// �������������㣬��������
				empty.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		// ����������������£�����num����Ʒ
		for (int i = 1; i <= num; ++i)
		{
			list.remove();
		}

		System.out.println(Thread.currentThread().getId() + "���Ѿ����Ѳ�Ʒ����:" + num + "/t���ֲִ���Ϊ��:" + list.size());

		// �������������߳�
		full.signalAll();
		//empty.signalAll();

		// �ͷ���
		lock.unlock();
	}

	// set/get����
	public int getMAX_SIZE()
	{
		return MAX_SIZE;
	}

	public LinkedList<Object> getList()
	{
		return list;
	}

	public void setList(LinkedList<Object> list)
	{
		this.list = list;
	}
	
	 public static void main(String[] args) {  
		    Storage resource = new Storage();  
	        new Thread(new Producer(resource)).start();  
	        new Thread(new Producer(resource)).start();  
	        new Thread(new Producer(resource)).start();  
	        new Thread(new Producer(resource)).start();  
	        new Thread(new Consumer(resource)).start();  
	        new Thread(new Consumer(resource)).start();  
	        new Thread(new Consumer(resource)).start();  
	        new Thread(new Consumer(resource)).start();  
	    } 
}

class Producer implements Runnable {
	private Storage resource;
    
	public Producer(Storage res) {
		this.resource = res;
	}
	@Override
	public void run() {
		while(true) {
			 try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  	
			resource.produce(1);
		}	
		
	}
}

class Consumer implements Runnable {
	private Storage resource;
    
	public Consumer(Storage res) {
		this.resource = res;
	}
	@Override
	public void run() {
		while(true) {
			 try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  	
			resource.consume(1);
		}	
		
	}
}