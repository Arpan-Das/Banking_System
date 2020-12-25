package Admin;

public class fixeddeposit {
	
	int acconumber;
	double amount,profit, rate;
	String  mdate, applieddate;
	public int getAcconumber() {
		return acconumber;
	}
	public void setAcconumber(int acconumber) {
		this.acconumber = acconumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public String getApplieddate() {
		return applieddate;
	}
	public void setApplieddate(String applieddate) {
		this.applieddate = applieddate;
	}
	public fixeddeposit(int acconumber, double amount, double profit, double rate, String mdate, String applieddate) {
		super();
		this.acconumber = acconumber;
		this.amount = amount;
		this.profit = profit;
		this.rate = rate;
		this.mdate = mdate;
		this.applieddate = applieddate;
	}
	
}

