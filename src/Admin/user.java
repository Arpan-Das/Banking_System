package Admin;

public class user {
	int accno;
	String name, gender, dob, idno, email,username;
	String mobileno;
	
	

	public void setAccno(int accno) {
		this.accno = accno;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public void setDob(String dob) {
		this.dob = dob;
	}



	public void setIdno(String idno) {
		this.idno = idno;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}



	public int getAccno() {
		return accno;
	}



	public String getName() {
		return name;
	}



	public String getGender() {
		return gender;
	}



	public String getDob() {
		return dob;
	}



	public String getIdno() {
		return idno;
	}



	public String getEmail() {
		return email;
	}



	public String getUsername() {
		return username;
	}



	public String getMobileno() {
		return mobileno;
	}



	public user(int accountnumber, String name, String gender, String dob, String idno, String email, 
			String mnumber, String username) {
		super();
		this.accno = accountnumber;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.idno = idno;
		this.email = email;
		this.mobileno = mnumber;
		this.username = username;
	}
			
	
	
}
