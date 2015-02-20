package multithread;

/**
 * ������Դ��
 */
public class PublicResource {
	protected int number = 0;

	/**
	 * ���ӹ�����Դ
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
	 * ���ٹ�����Դ
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
