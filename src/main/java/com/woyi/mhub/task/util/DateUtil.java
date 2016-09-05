package com.woyi.mhub.task.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getNow() {
		return sdf.format(new Date());
	}
	
	
	
}
