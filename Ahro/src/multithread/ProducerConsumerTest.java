package multithread;

public class ProducerConsumerTest {
	public static void main(String[] args) {
		PublicResource resource = new PublicResource();
		new Thread(new ProducerThread(1,resource)).start();
		new Thread(new ConsumerThread(resource)).start();
		new Thread(new ProducerThread(2,resource)).start();
		new Thread(new ConsumerThread(resource)).start();
		new Thread(new ProducerThread(3,resource)).start();
		new Thread(new ConsumerThread(resource)).start();
	}
}