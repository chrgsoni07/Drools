package com.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Util {
	public static boolean isDateAfter2(Map<String, Date> dateMap, String columnName, String conditionDate) {
		Date columnDate = dateMap.get("Createdate"); 
		Date date = parseDate(conditionDate);
		
		return columnDate.after(date);
	}

	
	public static boolean isDateAfter(Object inputDate, String conditionDate) {
		Date firstDate = (Date)inputDate; 
		Date secondDate = parseDate(conditionDate);
		
		return firstDate.after(secondDate);
	}
	
	
	public static Date parseDate(String stringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
