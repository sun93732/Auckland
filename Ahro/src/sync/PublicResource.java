package sync;

public class PublicResource {
	private int i = 0;
	
	private Object lock =  new Object();
	
	public void push() {
		synchronized(lock){
			
			while(i != 0) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			i = i + 1;
			System.out.println("add one, and i =" + i);
			lock.notifyAll();
			
		}
	}
	
	public void get() {
		synchronized(lock){
			while(i == 0){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			i --;
			System.out.println("delete one, and i =" + i);
			lock.notifyAll();
			
		}
	}
	 public static void main(String[] args) {  
	        PublicResource resource = new PublicResource();  
	        new Thread(new ProducerThread(resource)).start();  
	        new Thread(new ConsumerThread(resource)).start();  
	        new Thread(new ProducerThread(resource)).start();  
	        new Thread(new ConsumerThread(resource)).start();  
	        new Thread(new ProducerThread(resource)).start();  
	        new Thread(new ConsumerThread(resource)).start();  
	    } 
}

class ProducerThread implements Runnable {
	private PublicResource resource;
    
	public ProducerThread(PublicResource res) {
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
			resource.push();
		}	
		
	}
}

class ConsumerThread implements Runnable {
	private PublicResource resource;
    
	public ConsumerThread(PublicResource res) {
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
			resource.get();
		}	
		
	}
}

