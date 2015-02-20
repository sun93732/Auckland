import java.util.logging.Logger;


public class EscapeTest {
	private static Logger   log = Logger.getLogger("lavasoft"); 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String a = "Hello\nHow are you?\r\nFine thank you.";
		String b = "twenty-one%0A%0aINFO:+User+logged+out%3dbadguy";
		
        System.out.println(a); ;
        a = a.replaceAll("\n", "").replaceAll("\r", "");
        System.out.println(a); ;
        log.info(b);

	}

}
