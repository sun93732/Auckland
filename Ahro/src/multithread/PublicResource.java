package multithread;

/**
 * 公共资源类
 */
public class PublicResource {
	protected int number = 0;

	/**
	 * 增加公共资源
	 */
	public synchronized void increace(int id) {
		while (number != 0) {
			try {
				wait();
				System.out.println(id + " increase wait, after wait, the number is =" + number);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
