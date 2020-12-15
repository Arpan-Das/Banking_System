package userDom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class dateCalculator {

	public static long days(String from, String to) {
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date firstDate, secondDate;
		long diff = 0;
		try {
			firstDate = sdf.parse(from);
			secondDate = sdf.parse(to);

			long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;   
	}

}
