package Admin;

public class user {
	int accountnumber,id, mobilenumber;
	String username, gender, email, name;
	
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAccountNumber(int accountnumber) {
		this.accountnumber = accountnumber;
	}
	public void setMobileNumber(int mobilenumber) {
		this.mobilenumber =  mobilenumber;
	}
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getGender() {
		return gender;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public int getAccountNumber() {
		return accountnumber;
	}
	public int getMobileNumber() {
		return mobilenumber;
	}
	
	public user(int accountnumber,  String name, String gender, int id, String username, String email, int mobilenumber) {
		super();
		this.id = id;
		this.username = username;
		this.gender = gender;
		this.email = email;
		this.name = name;
		this.accountnumber = accountnumber;
		this.mobilenumber = mobilenumber;
	}
	
}
