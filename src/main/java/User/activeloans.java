package User;

public class activeloans {
	
	double dueloan;
	String amount,type, remark, duedate, applieddate, emi,status;
	
	public String getAmount() {
		return amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public double getDueloan() {
		return dueloan;
	}
	public void setDueloan(double dueloan) {
		this.dueloan = dueloan;
	}
	public String getEmi() {
		return emi;
	}
	public void setEmi(String emi) {
		this.emi = emi;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getApplieddate() {
		return applieddate;
	}
	public void setApplieddate(String applieddate) {
		this.applieddate = applieddate;
	}
	public activeloans(String amount, double dueloan, String emi, String type, String remark, String duedate,
			String applieddate,String status) {
		super();
		this.amount = amount;
		this.dueloan = dueloan;
		this.emi = emi;
		this.type = type;
		this.remark = remark;
		this.duedate = duedate;
		this.applieddate = applieddate;
		this.status = status;
	}
	
	
}

