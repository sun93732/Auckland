package enums;

public class TestEnum {

	public enum Color{
		BLUE, RED, GREY
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         Color color =  Color.RED;
         switch(color){
         case BLUE:
         case GREY:
         case RED:
        	 System.out.println(color.ordinal());
        	
        	 
         }
         for(Color s :Color.values())
        	 System.out.println(s);
	}

}
