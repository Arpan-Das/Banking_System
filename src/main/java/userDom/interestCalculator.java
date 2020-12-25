package userDom;

public class interestCalculator {
	//*************** calculate the total profit(principal + interest ) or coumpound interest **********************
    public static double interest(double principal, double rate, long days) {
    	double amount = 0;
    	amount = principal*(Math.pow( (1 + (rate/(365*100))) , days ));  //calculating the total compound interest
    	return amount;
    }
}
