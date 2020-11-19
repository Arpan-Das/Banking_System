package Admin;

public class loansapplied {

	int accno,amount;
	String username, remark;
	
	

	public void setAccno(int accno) {
		this.accno = accno;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	public void setRemark(String remark) {
		this.remark= remark;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAccno() {
		return accno;
	}



	public String getUsername() {
		return username;
	}

	public String getRemark() {
		return remark;
	}
	public int getAmount() {
		return amount;
	}



	public loansapplied(int accountnumber, String username, int amount, String remark) {
		super();
		this.accno = accountnumber;
		this.username = username;
		this.amount = amount;
		this.remark = remark;
	}
	
}

