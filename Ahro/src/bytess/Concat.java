package bytess;

import java.util.HashMap;

public class Concat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      byte[][] test = {{1,2,3},{4,5},{6}};
      byte[] x = concat(test);
      String s = "abcdedf";
      HashMap<Integer,String> map  = new HashMap<Integer,String>();
      map.put(1, s);
      System.out.println(s);
      map.clear();
      System.out.println(s);
      
      
	}
    private static byte[] concat(byte[][] in){
    	if(null == in)
    		return null;
    	int len = 0;
    	for(int i = 0; i < in.length; i  ++)
    		len = len + in[i].length;
    	byte[]  result = new byte[len];
    	int count = 0;
    	for(int i = 0; i < in.length; i  ++)
    		for(int j = 0;j < in[i].length;j ++)
    		{
    			result[count] = in[i][j];
    			count = count +1;
    		}
    	return result;
    }
}
