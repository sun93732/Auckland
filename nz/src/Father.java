
public class Father extends SuperFather {
    @Override
	public int mulExtra(int a, int b){
		return super.mulExtra(a, b);
	} 
    @Override
	protected int mulNumber(int a ,int b){
		return a*b*7;
	}
	public static void main(String[] args) {
		for(int x=3,  y = 0; x>y; x--,y ++)
		{
			System.out.println(x + "" + y+"");
		}
		
		SuperFather m = new Father();
		System.out.println(m.mulExtra(1, 2));
	}

}
class SuperFather{
	public int mulExtra(int a, int b){
		return this.mulNumber(a, b);
	}
	protected int mulNumber(int a ,int b){
		return a*b*3;
	}
}