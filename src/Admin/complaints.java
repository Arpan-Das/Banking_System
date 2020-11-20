package Admin;

public class complaints {

	int accno;
	String type, remark;
	
	

	public void setAccno(int accno) {
		this.accno = accno;
	}

	public void setType(String type) {
		this.type = type;
	}



	public void setRemark(String remark) {
		this.remark= remark;
	}

	public int getAccno() {
		return accno;
	}



	public String getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}



	public complaints(int accountnumber, String type, String remark) {
		super();
		this.accno = accountnumber;
		this.type = type;
		this.remark = remark;
	}
	
}

