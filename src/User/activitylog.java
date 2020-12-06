package User;

public class activitylog {

	double amount,balance;
	String type, remark, date;
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public void setBalance(double balance) {
		this.balance = balance;
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

	public String getDate() {
		return date;
	}
	public double getBalance() {
		return balance;
	}

	public activitylog(double amount, String type, String remark, String date, double balance) {
		super();
		this.amount = amount;
		this.type = type;
		this.remark = remark;
		this.date = date;
		this.balance = balance;
	}
}
