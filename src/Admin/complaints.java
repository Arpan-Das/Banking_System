package Admin;

public class complaints {

	int accno;
	String type, remark, status;
	
	public void setAccno(int accno) {
		this.accno = accno;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public complaints(int accno, String type, String remark, String status) {
		super();
		this.accno = accno;
		this.type = type;
		this.remark = remark;
		this.status = status;
	}






}

