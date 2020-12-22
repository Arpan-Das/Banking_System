package userDom;

public class DueDate {
	private static int year;
	private static int month;
	private static int day;
	public void duedate(int y, int m, int d) {
		year = y;
		month =m;
		day = d;
		day = day + 30;
		if(day > 30) {
			day = day - 30;
			month = month + 1;
			if(month > 12) {
				month = month - 12;
				year = year + 1;
			}
		}
	}
	public int getYear() {
		return (year);
	}
	public int getMonth() {
		return (month);
	}
	public int getDate() {
		return (day);
	}
}
