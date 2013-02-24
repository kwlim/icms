package com.innov.common.util;

import org.apache.commons.lang3.StringUtils;

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

}
