package CareerCup;

public class TestLineCross {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float[] line1 = {100, 100, 0, 0};
		float[] line2  = {78, 78, 54, 54};
		float slop1  =  (line1[1] - line1[3])/(line1[0] - line1[2]);
		float slop2  =  (line2[1] - line2[3])/(line2[0] - line2[2]);
		if(Math.abs(slop2 - slop1) == 0){
			System.out.println(true);
		}else
		System.out.println(false);
		

	}

}
