package userDom;

public class EMI {
	
	public static double emi(double p, double r, double n) {
		
		double amount = (p*(r/1200)*(Math.pow((1+(r/1200)), n*12)))/(Math.pow((1+(r/1200)), n*12)-1); 
		return amount;
	}
	
}
