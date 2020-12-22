package userDom;

public class RemoveComma {
	public static double remove(String num) {
		for(int i =0; i< num.length() ; i++) {
			if(num.charAt(i) == ',') {
				num = num.substring(0, i) + num.substring(i+1, num.length());
			}
		}
		return Double.valueOf(num);
	}
}
