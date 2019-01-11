package com.tianniu.common;

public class Constants {
	
	//每页显示条数 
	public final static Integer NUM = 10;
	
	public final static String SALT = "tianniu";
	
	public final static String REGEX_NAME = "\\d*\\w*[\u4E00-\u9FA5]*";
	
	public final static String REGEX_ID_CARD = "([1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|([1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx])";
}
