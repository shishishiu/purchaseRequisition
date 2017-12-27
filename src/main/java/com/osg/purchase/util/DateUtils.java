package com.osg.purchase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
	
	static public long SubtractDates(Date from, Date to) {
		
		long dateTimeTo = to.getTime();
		long dateTimeFrom = from.getTime();
		
		long dayDiff = (dateTimeTo - dateTimeFrom) / (1000 * 60 * 60 * 24 );

		return dayDiff;
	}
	
	static public boolean IsDate(String str, String format) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
			Date formatDate = sdf.parse(str);
		} catch (ParseException e) {
			return false;
		}
        
        return true;

	}

}
