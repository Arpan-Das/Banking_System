package Admin;

public class loansapplied {

	int accno;
	float amount;
	String username, status, why;
	public int getAccno() {
		return accno;
	}
	public float getAmount() {
		return amount;
	}
	public String getUsername() {
		return username;
	}
	public String getStatus() {
		return status;
	}
	public String getWhy() {
		return why;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	public loansapplied(int accno, float amount, String username, String status, String why) {
		super();
		this.accno = accno;
		this.amount = amount;
		this.username = username;
		this.status = status;
		this.why = why;
	}
	
	
	
	
}

