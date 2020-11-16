package com.proximapp.gathering.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StdDateTimeManager {

	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
	private final SimpleDateFormat sdformat = new SimpleDateFormat(DATETIME_FORMAT);

	public String getCurrentDateTime() {
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public long getMillisFromDateTimes(String fromDt, String toDt) throws ParseException {
		Date fromDate = sdformat.parse(fromDt);
		Date toDate = sdformat.parse(toDt);
		return toDate.getTime() - fromDate.getTime();
	}

}
