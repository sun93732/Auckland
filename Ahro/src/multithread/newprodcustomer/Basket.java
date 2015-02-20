package multithread.newprodcustomer;

/*
 * �����࣬���ڴ�����
 * ���Ӽٶ�����10�����
 */
public class Basket {
	private int index = 0;
	private Bread[] arrayBread = new Bread[10];

	/*
	 * �˷�������������������� ÿ����ʦ���ɺ�һ������������������ ���ڵ�ĳһ����ʦ��������������Ĺ��̣���û���꣬��������Ѿ����������
	 * ����һ����ʦҪ��������������� ��������������Ѿ���9������Ļ������һ����ʦ�Ͳ��������ˡ�
	 * ������Ҫ����������Ӱ�������һ����ʦ���������һ����ʦ�������������ӡ�
	 */
	public synchronized void push(int id, Bread bread) {
		System.out.println("����ǰ�������������" + index + " ��");
		// ����ʦ�����������ˣ��������ﲻͣ�ĵ���
		while (index == arrayBread.length) {
			System.out.println("�������ˣ��ҿ�ʼ�ȵȡ�����������");
			try {
				// ��ʦ��һ�������̣߳���ʼ��ͣ�ȴ�
				// ����Ҫ�ȴ��˿�(һ�������߳�)��������
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// ����һ�����ڵȴ����̣߳�������ѵ��߳�Ϊ�����̣߳����ֻ����ȴ�״̬��
		// ���Ϊ�����̣߳����������߳������������Ե�ʣ������߳̿��Խ�������
		
		arrayBread[index] = bread;
		index++;
		System.out.println("now" + bread);
		for(long i =0; i < Long.MAX_VALUE; i++)
		{
			;
		}
		this.notify();

	}

	/*
	 * �˷�������������������� ����ԭ����ϱ�һ��
	 */
	public synchronized Bread pop(int id) {
		System.out.println("����ǰ�������������" + index + " ��");
		while (index == 0) {
			System.out.println("���ӿ��ˣ��ҿ�ʼ�ȵȡ�����������");
			try {
				// �˿ͣ�һ�������̣߳���ʼ��ͣ�ȴ�
				// ����Ҫ�ȴ���ʦ(һ�������߳�)��������
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// ����һ�����ڵȴ����̣߳�������ѵ��߳�Ϊ�����̣߳����ֻ����ȴ�״̬��
		// ���Ϊ�����̣߳����������߳������������Ե�ʣ������߳̿��Խ�������
		
		index--;
		System.out.println("��" + id + "���˿������� -->" + arrayBread[index]);
		this.notify();
		return arrayBread[index];
	}
}