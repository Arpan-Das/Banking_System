package User;

public class data {
	static private String username;
	static private String accno;
	static private String name;
	static private boolean loanflag = false;
	static private boolean trxflag = false;
	static private double loanamount = 0;
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		data.username = username;
	}
	public static String getAccno() {
		return accno;
	}
	public static void setAccno(String accno) {
		data.accno = accno;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		data.name = name;
	}
	public static boolean getLoanflag() {
		return loanflag;
	}
	public static void setLoanflag(boolean loanflag) {
		data.loanflag = loanflag;
	}
	public static boolean getTrxflag() {
		return trxflag;
	}
	public static void setTrxflag(boolean trxflag) {
		data.trxflag = trxflag;
	}
	public static double getLoanamount() {
		return loanamount;
	}
	public static void setLoanamount(double loanamount) {
		data.loanamount = loanamount;
	}
}
