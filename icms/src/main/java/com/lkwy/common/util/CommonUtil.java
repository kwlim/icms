package com.lkwy.common.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class CommonUtil {
	
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
