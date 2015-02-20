package org.string;
public class Outer {
	public  static class Inner{
		private int a;
		Inner(){
			a  = 1;
		}
		public void run()
		{			
		}
	}	
	public static void main(String[] args) {

        Outer o = new Outer();
        try {
			o.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Outer.Inner in  = new Outer.Inner();
        in.run();        
	}
}
