package com.qql.dagger.recommend.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	
	public static String getDateAsString(String dateStr, String format){
		if(TextUtils.isEmpty(dateStr)){
			return "";
		}
		
		Date date = null;
		try {
			SimpleDateFormat sFormat = new SimpleDateFormat(format);
			date = sFormat.parse(dateStr);
		} catch (ParseException e) {
			ToastUtil.show("String " + dateStr + " can not be parsed to a Date with format " + format);
			e.printStackTrace();
		}
		
		return getDateAsString(date, format);
	}
	
	public static String getDateAsString(Date date, String format){
		if(date == null){
			return  "";
		}
		SimpleDateFormat sFormat = new SimpleDateFormat(format);
		
		return sFormat.format(date);
	}
}
