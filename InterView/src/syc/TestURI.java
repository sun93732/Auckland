package syc;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;

public class TestURI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String val  = "FTP:FTP.IS.ZA/RFC1808.TXT";
		boolean b = isValidURL(val);

	}
	public static boolean isValidURL(String url) {  

	    URL u = null;

	    try {  
	        u = new URL(url);  
	    } catch (MalformedURLException e) {  
	        return false;  
	    }

	    try {  
	        u.toURI();  
	    } catch (URISyntaxException e) {  
	        return false;  
	    }  

	    return true;  
	} 

}
