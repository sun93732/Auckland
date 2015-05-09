import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class TestHash {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<Integer, String>();
		map.put(1, "1");
		map.put(2, "3");
		map.put(3, "4");
		map.put(4, "6");
		map.put(5, "5");
		
       Iterator<Map.Entry<Integer, String>> itr =  map.entrySet().iterator();
       while(itr.hasNext())
       {
    	   Map.Entry<Integer, String> entry =  itr.next();
    	   if(entry.getValue().equals("3"))
    		   itr.remove();
       }
       itr =  map.entrySet().iterator();
       while(itr.hasNext())
       {
    	   Map.Entry<Integer, String> entry =  itr.next();
    	   System.out.print("the key= " + entry.getKey());
    	   System.out.println("the val= " + entry.getValue());
       }
	}

}
