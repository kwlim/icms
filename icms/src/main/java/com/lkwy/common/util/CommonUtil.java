package com.lkwy.common.util;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class CommonUtil {
	
	public static String convertNumberToStringCode(int value){
		return String.valueOf(value);
	}
	
	public static String convertNumberToStringCode(int length, int value) throws IllegalArgumentException{
		
		if(length < 1){
			throw new IllegalArgumentException("legnth must be more than 0");
		}
		if(value < 0){
			throw new IllegalArgumentException("value must be more than or equal to 0");
		}
		
		StringBuilder format = new StringBuilder();
		for(int i=0;i<length;i++){
			format.append("0");
		}
		DecimalFormat df = new DecimalFormat(format.toString());
		return df.format(value);
	}
	
	public static String addWildCard(String name){
		StringBuilder wildCardName = new StringBuilder();
		
		if(!StringUtils.isEmpty(name)){
			wildCardName = new StringBuilder(name);
		}
		
		if(!StringUtils.startsWith(name, "%")){
			wildCardName.insert(0, "%");
		}
		if(!StringUtils.endsWith(name, "%")){
			wildCardName.append("%");
		}
		return wildCardName.toString();
	}
	
	public static Date convertDateAsStartDate(Date date) {
        if (date != null) {
            DateTime datetime = new DateTime(date);
        	datetime = datetime.withTime(0, 0, 0, 0);
            return datetime.toDate();
        } else {
            return null;
        }
    }

    public static Date convertDateAsEndDate(Date date) {
        if (date != null) {
        	DateTime datetime = new DateTime(date);
        	datetime = datetime.withTime(23, 59, 59, 999);
            return datetime.toDate();
        } else {
            return null;
        }
    }

}
