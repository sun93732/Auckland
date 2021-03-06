package matrix;
/*
 * 
 * Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column
is set to 0.
 * */
public class TestClearRowAndCol {

	/**
	 * @param args
	 */
	public void cleanMatrixColandRow(int[][] matrix){
		for(int row = 0; row < matrix.length; row ++)
			for(int col = 0; col < matrix[row].length; col ++)
			{
				System.out.println("the " + row + "," + col + " = " + matrix[row][col]);
			}
	}
	public void reverse(int[][] y){
		int[][] matrix = new int[y.length][y[0].length];
		
		for(int row = 0; row < matrix.length; row ++)
			for(int col = 0; col < matrix[row].length; col ++)
			{
				matrix[row][col] = y[col][row];
			}
		
		for(int row = 0; row < matrix.length; row ++)
			for(int col = 0; col < matrix[row].length; col ++)
			{
				System.out.println("the " + row + "," + col + " = " + matrix[row][col]);
			}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] in = { { 1, 2,3 }, { 4, 5,6 }, { 7, 8,9 } }; 
		//new TestClearRowAndCol().cleanMatrixColandRow(in);
		new TestClearRowAndCol().reverse(in);

	}

}
