class HelloA {
public HelloA() {
System.out.println("HelloA");
}

{ System.out.println("Class A"); }

static { System.out.println("Static A"); }
}
 
public class HelloB extends HelloA {
public HelloB() {
System.out.println("HelloB");
}

{ System.out.println("Class B"); 
}

static { System.out.println("Static B"); }
   
public static void main(String[] args) {
new HelloB();
}
}

