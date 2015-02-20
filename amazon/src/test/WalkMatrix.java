package test;

public class WalkMatrix {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int width = 5;
		int height = 3;
		int[] matrix = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		int[] matrix2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30 };
		//WalkMatrix.convertMatrix(5, 3, matrix);
		//WalkMatrix.convertMatrix(5, 6, matrix);
		int[] ints = convertMatrix(5, 6, matrix2);
		for(int k = 0; k < ints.length; k++){
			System.out.print(ints[k]+ " ");
		}

	}
	static int[] convertMatrix(int width, int height, int[] matrix) {
    int[]  result = new int[matrix.length];
    int[]  flags = new int[matrix.length];
    for(int i = 0; i < flags.length; i ++)
    {
    	flags[i] = 0;
    }
    int realWidth = 0;
    int pos = -1;
    realWidth = -1;
    for(int i = 0; i < matrix.length; )//only count
    {
    	
    	while(flags[pos + 1] != 1 && realWidth + 1 < width)//往东走
    	{
    		int temp  = pos + 1;
    		flags[temp] = 1;
    		result[i ++] = matrix[temp];
    		pos = temp;
    		realWidth = realWidth + 1;
    		
    	}   
    	
    	if(flags[pos + width -1] != 1 && realWidth >0){//左下角走
    		
    		do
    		{
    			int temp = pos + width -1; 
    			flags[temp] = 1;
        		result[i] = matrix[temp];
        		i ++;        		
        		pos = temp;
        		realWidth --; 
        		
    		}while(pos + width -1 < matrix.length && flags[pos + width -1] != 1 && realWidth >0);
    		
    	}
    	else
    	{
    		return result;
    	}
     
    	
    	if(flags[pos - 1] != 1 && realWidth > 0){//往左走
    		do
    		{
    			int temp = pos -1; 
    			flags[temp] = 1;
        		result[i] = matrix[temp];
        		i ++;
        		pos = temp;  	
        		realWidth --;
    		}while(flags[pos - 1] != 1 && realWidth >0);
    	}
    	if(flags[pos - width] != 1 && pos - width >0){//往上走
    		while(flags[pos - width] != 1 && pos - width >0)
    		{
    			int temp = pos -width; 
    			flags[temp] = 1;
        		result[i] = matrix[temp];
        		i ++;
        		pos = temp;  	
    		}
    	}else
    	{
    		return result;
    	}
    	
    	
    }
    
    
    return result;

    }


}
