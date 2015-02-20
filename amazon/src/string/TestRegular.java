package string;

public class TestRegular {
	public static void main(String[] args) {

	}

	public boolean isMatch(String src, String dst) {
		if (src == null && dst == null)
			return true;
		if (src == null || dst == null)
			return false;
		if (dst.length() == 1  ||dst.charAt(1) != '*') {//if dst [i] != '*'
			if (dst.charAt(0) == '.') {
				return isMatch(src.substring(1), dst.substring(1));
			} else {
				return src.charAt(0) == dst.charAt(0)
						&& isMatch(src.substring(1), dst.substring(1));
			}
		}//end if not *
		//now if == *
		else{
			int len = src.length();
			int i = -1;
			while(src.charAt(0) == dst.charAt(0) || dst.charAt(0) =='.' )
			{
			    if(isMatch(src, dst.substring(2)))
			    {
			    	return true;//b*, 这里考虑*代表0的情况 abbbc vs ab*c
			    }
			    src = src.substring(1);//b*，这里考虑* 代表1,2...n
			}
			return isMatch(src, dst.substring(2));//afbbc vs ab*fbbc, f != b，直接比较后面的
		}
	}

}
