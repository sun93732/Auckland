package com.orion;

public class Solution4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public int solution(IntList L) {
        // write your code in Java SE 8
        if(L == null) {
        	return 0;
        }
        int len = 1;
        while(L.next != null) {
        	len ++;
        	L = L.next;
        }
        return len;
    }
}

class IntList {
	public int value;
	
	public IntList next;
}
