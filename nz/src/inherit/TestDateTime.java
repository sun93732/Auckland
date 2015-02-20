package inherit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TestDateTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date date = new Date(1359641834000L);// 2013-1-31 22:17:14
		System.out.println(date);
		String dateStr = "2013-1-31 22:17:14";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date dateTmp = dateFormat.parse(dateStr);
			
			System.out.println(dateTmp);
			 int timeOffset = TimeZone.getTimeZone("GMT").getRawOffset() - TimeZone.getDefault().getRawOffset();  
		        dateTmp = new Date(date.getTime() - timeOffset); 
		        
		        SimpleDateFormat another = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
				another.setTimeZone(TimeZone.getTimeZone("GMT"));
		        String s = another.format(dateTmp);
		        System.out.println(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dateStrTmp = dateFormat.format(date);
		System.out.println(dateStrTmp);
		
		
		Calendar calendar1 = Calendar
			    .getInstance(TimeZone.getTimeZone("GMT+8"));
			  Calendar calendar2 = Calendar
			    .getInstance(TimeZone.getTimeZone("GMT+1"));

			  System.out.println("Millis = " + calendar1.getTimeInMillis());
			  System.out.println("Millis = " + calendar2.getTimeInMillis());

			  System.out.println("hour = " + calendar1.get(Calendar.HOUR));
			  System.out.println("hour = " + calendar2.get(Calendar.HOUR));

			  System.out.println("date = " + calendar1.getTime());
			  System.out.println("date = " + calendar2.getTime());
			  
			  String text = "1 Jan 2011 00:00:00 GMT";
		        // Note the explicit locale. Let's not leave it as the system default :)
		        DateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss", 
		                                                    Locale.US);

		        Date dt;
				try {
					dt = formatter.parse(text);
					System.out.println(dt);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
			  
	}
	
	public static Date getSomeDate(final String str, final TimeZone tz)
		    throws ParseException {
		  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		  sdf.setTimeZone(tz);
		  return sdf.parse(str);
		}
}
