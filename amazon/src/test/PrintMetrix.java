package test;

import java.util.ArrayList;
import java.util.List;

public class PrintMetrix {
	
	public class Pair{
		public int x;
		public int y;
		public Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	private boolean lstContain(List<Pair> pairLst, Pair startPoint){
		for(Pair p : pairLst){
			if(p.x == startPoint.x && p.y == startPoint.y){
				return true;
			}
		}
		return false;
	}
	
	private Pair right(int[][] intArr, List<Pair> pairLst, Pair point){
		System.out.print(intArr[point.y][point.x]+" ");
		pairLst.add(point);
		int x = 0;
		for(x = point.x + 1; x < intArr[0].length; x++){
			if(lstContain(pairLst,new Pair(x, point.y))){
				return new Pair(x-1, point.y);
			}else{
				System.out.print(intArr[point.y][x]+" ");
				pairLst.add(new Pair(x, point.y));
			}
		}
		return new Pair(x-1, point.y);
	}
	
	private Pair ld(int[][] intArr, List<Pair> pairLst, Pair point){
		System.out.print(intArr[point.y][point.x]+" ");
		pairLst.add(new Pair(point.x, point.y));
		int x = point.x;
		int y = point.y;
		Pair p = point;
		while(y<intArr.length-1 && x > 0){
			x = x -1;
			y = y + 1;
			if(lstContain(pairLst,new Pair(x,y))){
				return p;
			}else{
				System.out.print(intArr[y][x]+" ");
				p = new Pair(x, y);
				pairLst.add(p);
			}
		
		}
		return p;
	}
	
	private Pair l(int[][] intArr, List<Pair> pairLst, Pair point){
		System.out.print(intArr[point.y][point.x]+" ");
		pairLst.add(new Pair(point.x, point.y));
		int x = point.x;
		int y = point.y;
		Pair p = point;
		while(x > 0){
			x = x -1;
			if(lstContain(pairLst,new Pair(x,y))){
				return p;
			}else{
				System.out.print(intArr[y][x]+" ");
				p = new Pair(x, y);
				pairLst.add(p);
			}
		}
		return p;
	}
	
	private Pair up(int[][] intArr, List<Pair> pairLst, Pair point){
		System.out.print(intArr[point.y][point.x]+" ");
		pairLst.add(new Pair(point.x, point.y));
		int x = point.x;
		int y = point.y - 1;
		Pair p = point;
		while(y > 0){
			y = y -1;
			if(lstContain(pairLst,new Pair(x,y))){
				return p;
			}else{
				System.out.print(intArr[y][x]+" ");
				p = new Pair(x, y);
				pairLst.add(p);
			}
		}
		return p;
	}
	
	public void print(int[][] intArr, List<Pair> pairLst, Pair point){
		if(lstContain(pairLst,point)){
			return;
		}
		Pair p = right(intArr, pairLst, point);
		if(lstContain(pairLst, new Pair(p.x-1, p.y+1))){
			return;
		}else{
			p = ld(intArr, pairLst,new Pair(p.x-1, p.y+1));
		}
		p = new Pair(p.x-1, p.y);
		if(lstContain(pairLst, p)){
			return;
		}else{
			p = l(intArr, pairLst,p);
		}
		p = new Pair(p.x, p.y-1);
		if(lstContain(pairLst, p)){
			return;
		}else{
			p = up(intArr, pairLst,p);
		}
		p = new Pair(p.x+1, p.y);
		
		print(intArr, pairLst,p);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[][] a = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}};
		List<Pair> lst = new ArrayList<Pair>();
		PrintMetrix print = new PrintMetrix();
		Pair p = print.new Pair(0, 0);
		print.print(a, lst, p);

	}

}
