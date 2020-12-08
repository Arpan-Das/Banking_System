package User;

public class activeloans {
	
	double amount,dueamount, paid, topay;
	String type, remark, duedate, applieddate;
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setDate(String duedate) {
		this.duedate = duedate;
	}
	public void setDueamount(double dueamount) {
		this.dueamount = dueamount;
	}
	public void setPaid(double paid) {
		this.paid = paid;
	}
	public void setPay(double pay) {
		this.topay = pay;
	}

	public void setAppliedDate(String applieddate) {
		this.applieddate = applieddate;
	}
	
	public double getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}

	public String getDuedate() {
		return duedate;
	}
	
	public double getDueamount() {
		return dueamount;
	}
	
	public double getPaid() {
		return paid;
	}
	
	public double getTopay() {
		return topay;
	}
	
	public String getApplieddate() {
		return applieddate;
	}

	public activeloans(double amount, String type, String duedate,double dueamount, double topay, double paid,String remark, String applieddate) {
		super();
		this.amount = amount;
		this.type = type;
		this.remark = remark;
		this.duedate = duedate;
		this.dueamount = dueamount;
		this.applieddate = applieddate;
		this.paid = paid;
		this.topay= topay;
		
	}
}

