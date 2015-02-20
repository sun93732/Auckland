package test;
/*
 * 
 * 两个字符串能否只通过一个字符的变化/replace, delete,add，变成另外的一个
 * cot cat :true
 * catt cat :true
 * 
 * */
public class TestString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str1 = "ebxcef";
		
		String str2 = "ebcxdef";
		new TestString().checkStr(str1, str2);		

	}
	public int checkStr(String str1, String str2){
		if(Math.abs(str1.length() - str2.length()) > 2){
			return -1;
		}
		String shortStr;
		String longStr;
		if(str1.length() > str2.length())
		{
			shortStr = str2;
			longStr = str1;
		} else{
			shortStr = str1;
			longStr = str2;
		}
		int diffCount = 0;
		if(shortStr.length() == longStr.length()){
			//check replacement
			for(int i = 0; i < shortStr.length(); i ++)
			{
				if(shortStr.charAt(i) != longStr.charAt(i)){
					diffCount ++;
					if(diffCount > 1){
						return -1;
					}
					
					shortStr = shortStr.substring(0, i) + longStr.charAt(i)  + shortStr.substring(i +1, shortStr.length());
					if(shortStr.equals(longStr)){
						return 1;
					}
					else{
						return -1;
					}
					
				}
			}
			if(diffCount == 0){
				return -1;
			}
		}
		diffCount = 0;
		int difIndex = 0;
		for(int i = 0; i < shortStr.length();)
		{//compare delete or add
			if(shortStr.charAt(i) != longStr.charAt(i + difIndex))
			{
				difIndex = difIndex + 1;
				if(difIndex >= 2){
					return -1;
				}
			}
			else{
			   i = i + 1;
			}
			
			
		}
	
		return 1;
	}

}
