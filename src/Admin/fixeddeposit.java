package Admin;

public class fixeddeposit {
	
	int acconumber;
	double amount,profit, rate;
	String username, mdate, applieddate;
	
	
	public void setAccountnumber(int acco)
	{
		this.acconumber = acco;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setUsername(String uname) {
		this.username = uname;
	}

	public void setmDate(String mdate) {
		this.mdate = mdate;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public void setAppliedDate(String applieddate) {
		this.applieddate = applieddate;
	}
	
	public int getAccount() {
		return acconumber;
	}
	
	public double getAmount() {
		return amount;
	}

	public String getUsername() {
		return username;
	}

	public String getMdate() {
		return mdate;
	}
	
	public double getRate() {
		return rate;
	}
	
	public double getProfit() {
		return profit;
	}
	

	public String getApplieddate() {
		return applieddate;
	}

	public fixeddeposit(double amount, String username, String mdate, double rate, double profit, int acconumber, String applieddate) {
		super();
		this.amount = amount;
		this.username = username;
		this.mdate = mdate;
		this.rate = rate;
		this.profit = profit;
		this.applieddate = applieddate;
		this.acconumber = acconumber;
		
	}
}

