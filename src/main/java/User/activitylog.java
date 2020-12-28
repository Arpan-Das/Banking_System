package User;

public class activitylog {

	String amount;
	String type, remark, date,balance;
	
	public void setAmount(String amount) {
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
	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAmount() {
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
	public String getBalance() {
		return balance;
	}

	public activitylog(String amount, String type, String remark, String date, String balance) {
		super();
		this.amount = amount;
		this.type = type;
		this.remark = remark;
		this.date = date;
		this.balance = balance;
	}
}
