package com.osg.purchase.util;

import java.util.Date;

public class DateUtils {
	
	static public long SubtractDates(Date from, Date to) {
		
		long dateTimeTo = to.getTime();
		long dateTimeFrom = from.getTime();
		
		long dayDiff = (dateTimeTo - dateTimeFrom) / (1000 * 60 * 60 * 24 );

		return dayDiff;
	}

}
