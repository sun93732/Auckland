package multithread;

public class ProducerThread implements Runnable {  
    private PublicResource resource;  
    private int id;
  
    public ProducerThread(int id,PublicResource resource) {  
        this.resource = resource;
        this.id = id;
    }  
  
    @Override  
    public void run() {  
        for (int i = 0; i < 100; i++) {  
            try {  
                Thread.sleep((long) (Math.random() * 1000));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            resource.increace(id);  
        }  
    }  
}  