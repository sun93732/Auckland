package tstinterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Dog implements Animal {

	@Override
	public void bark() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	
    class StatisticComparator implements Comparator{
        public int compare(Object a, Object b) {
            return 0;
        }
        
    }
	public static void main(String[] args) {
		
		try {
			java.net.URI uri = new java.net.URI("http:// www.oracle.com:100");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		 List<Dog> list = Collections.emptyList();
		
		
		
		// TODO Auto-generated method stub
		Dog dog = new Dog();
		list.add(dog);
		dog.toString();
		Map<String, Integer> map  = new TreeMap<String, Integer>();
		map.put("eric", 16);
		map.put("dd", 15);
		map.put("xxx", 7);
		map.put("yyyy", 30);
		Set set  = map.entrySet();
		Iterator<Entry> itr = set.iterator();
		while(itr.hasNext())
		{
			Entry ent  =  itr.next();
			System.out.println("name = " + ent.getKey() + " and value =" + ent.getValue());
		}

	}

}
