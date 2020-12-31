package userDom;

public class SplitTheName {

	private static String firstname = null	;
	private static String lastname = null;
	public static String getFirstname() {
		return firstname;
	}
	public static String getLastname() {
		return lastname;
	}
	public static void setFirstname(String fname) {
		firstname = fname;
	}
	public static void setLastname(String lname) {
		lastname = lname;
	}
	public static void split(String name) {
		name = name.trim();
		for(int i = 0;i < name.length();i++) {
			if(name.charAt(i) == ' ') {
				firstname = name.substring(0, i);
				lastname = name.substring(i+1, name.length());
				break;
			}
		}
		if(firstname == null) {
			firstname = name;
			lastname = null;
		}
	}

}
