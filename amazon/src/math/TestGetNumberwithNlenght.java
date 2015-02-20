package math;
public class TestGetNumberwithNlenght {

	public static int n = 3;
	public static boolean u[] = new boolean[10];
	public static int ans[] = new int[n + 1];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//generate(3);
		dfs(1,0);

	}
	public static void dfs(int d, int increase){
		if(d == n + 1){//长度够了
			for(int i =0; i < ans.length; i ++)
				System.out.print(ans[i]);
			System.out.println("");
			return;
		}
		for(int i = 1 + increase; i <= 9; i ++)
		{
			ans[d] = i;
			dfs(d + 1, i);
			/*if(!u[i]){
			ans[d] = i;
			u[i] = true;
			dfs(d + 1, i);
			u[i] = false;
			}*/
		}
	}
	
	//打印出所有n位的数，n =3, 则输出123， 124， 125, 789(9>8>7)，这个是笨蛋方法
	
	
	public static void generate(int n){
		long begin  =  (long) Math.pow(10, n-1);
		long end  =  (long) Math.pow(10, n);
		
		for(long i = begin; i < end; i ++ ){
			boolean available  = false;
			int first  =  (int)i%10;
			long yu = i/10;
			if(yu < 10)
			{
				if(yu < first){
					System.out.println(i);
				}
				continue;
			}
			while(yu != 0)
			{
				int second = (int) yu%10;
				yu = yu/10;
				if(second >= first){
					available  = true;
				break;
				}
				first = second;
			}
			if(available)
			{
				continue;
			}
			System.out.println(i);
		}
		
	}

}
