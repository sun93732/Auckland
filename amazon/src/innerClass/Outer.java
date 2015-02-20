package innerClass;

public class Outer {
    public static void main(String[] args){
    	Outer.Inner in  = (new Outer()).new Inner();
    	Outer.InnerStatic staticInner = new InnerStatic(); 
    	Outer.InnerStatic staticInne2r = new Outer.InnerStatic(); 
    }
    
    public class Inner{
    	public int age;
    }
    
    public static class InnerStatic{
    	public int age;
    }
}
