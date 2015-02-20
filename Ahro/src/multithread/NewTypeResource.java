package multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NewTypeResource extends PublicResource {
	private ReentrantLock lock  = new ReentrantLock();
	Condition con = lock.newCondition();

	/**
	 * 增加公共资源
	 */
	public void increace(int id) {
		while (number != 0) {
			try {
				con.wait();
				System.out.println(id + " increase wait, after wait, the number is =" + number);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally{
				
			}
		}
		number++;
		System.out.println(id + " produce one ,current number =" + number);
		notify();
	}

	/**
	 * 减少公共资源
	 */
	public synchronized void decreace() {
		while (number == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		number--;
		System.out.println(number);
		notify();
	}


}
