package User;

public class deposit {
	
	double amount,profit, rate;
	String mdate, applieddate;
	
	

	public void setAmount(double amount) {
		this.amount = amount;
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
	
	public double getAmount() {
		return amount;
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

	public deposit(double amount, String mdate, double rate, double profit, String applieddate) {
		super();
		this.amount = amount;
		this.mdate = mdate;
		this.rate = rate;
		this.profit = profit;
		this.applieddate = applieddate;
		
	}
}

