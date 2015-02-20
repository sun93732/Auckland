package com.oracle;

public class Thread2 {  
	public static boolean flag = true;
    public synchronized void m4t1() {  
          {  
              int i = 5;  
              while( i-- > 0) {  
                   System.out.println(Thread.currentThread().getName() + " : " + i);  
                   try {  
                        Thread.sleep(500);  
                        while( flag == false)
                        wait();
                        flag = true;
                   } catch (InterruptedException ie) {  
                   }  
                   notify();
              }  
         }  
    }  
    public synchronized void m4t2() {  
         int i = 5;  
         while( i-- > 0) {  
              System.out.println(Thread.currentThread().getName() + " : " + i);  
              try {  
                   Thread.sleep(500);
                   while(flag == true)
                   wait();
                   flag = false;
              } catch (InterruptedException ie) {  
              }  
              notify();
         }  
    }  
    public static void main(String[] args) {  
         final Thread2 myt2 = new Thread2();  
         Thread t1 = new Thread(  new Runnable() {  public void run() {  myt2.m4t1();  }  }, "t1"  );  
         Thread t2 = new Thread(  new Runnable() {  public void run() { myt2.m4t2();   }  }, "t2"  );  
         t1.start();  
         t2.start();  
    } 
}