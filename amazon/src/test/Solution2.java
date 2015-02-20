package test;

import java.io.*;
import java.util.Vector;

public class Solution2 {
	public int getIntFromStr(String str) {
		int number = 0;
		try {
			number = Integer.valueOf(str);
		} catch (NumberFormatException e) {

			e.printStackTrace();
			System.out.println("the number input is wrong!");
			return -1;
		}
		return number;

	}
    
	public static void main(String args[]) throws Exception {
		class Mount {
	    	public int begin = 0;
	    	public int end  = 0;
	    	public int height = 0;
	    	public Mount(int a, int b, int c)
	    	{
	    		begin = a;
	    		end = b;
	    		height = c;
	    	}	    	
	    	
	    }
		Solution2 sl = new Solution2();
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
		Vector<String> vec = new Vector<String>();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		String str = "";
		while ((str = br.readLine()) != "DONE") {
			vec.add(str);
		}
		Mount[] array= new Mount[vec.size() -2];		
		
		for (int i = 1; i < vec.size(); i ++) {

			String[] cur = vec.get(i).split(",");

			int startCur = sl.getIntFromStr(cur[0]);
			int endCur = sl.getIntFromStr(cur[1]);
			int hightCur = sl.getIntFromStr(cur[2]);
			if (startCur == -1 || endCur == -1 || hightCur == -1) {
				System.out.println("the mount info input is wrong!");
				return;
			}
			Mount m =  new Mount(startCur, endCur, hightCur);
			array[i -1] = m;

		}
		int  total = 0;
		int markHeight = 0;
		for(int i = 0; i < array.length; i ++)
		{
			Mount mountX = array[i];
			boolean bup  = true;
			boolean bdown =  true;
			int markup = 0;
			int markupHeight = 0;
			
			int markdown = -1;
			
			for(int j = 0; j < array.length; j++)
			{
				Mount mountY = array[j];
				if(i == j)
					continue;
				
				//查找所有的爬高，看看其他爬高如果有重合的话，是不是比它高。
				if(mountX.begin >= mountY.begin && mountX.begin <= mountY.end)
				{
					if(mountX.height <= mountY.height)
					{
						bup = true;//不用爬了						
					}
					else{
						markupHeight =  mountX.height - mountY.height;
						if(i == 0){
							markupHeight = mountX.height;
						    bup = false;//必须爬第一座山
						}
							
					}
				}
			}
		}

	}
}

