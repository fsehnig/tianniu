/**
 * 
 */
package com.tcl.shbc.mina.common;

/**
 * @author sumjohn
 *
 */
public class Constants {

	private Constants()
	{
		
	}
	
	/**
	 * base64数据包的头
	 */
	public static final String BASE64_PCK_HEADER = "^";
	
	/**
	 * base64数据包的尾
	 */
	public static final String BASE64_PCK_TAIL = "&";
	
	/**
	 * 普通告警
	 */
	public static final int ALERT_LVL_NORMAL = 1;
	
	/**
	 * 紧急告警
	 */
	public static final int ALERT_LVL_RED = 2;
	
	/**
	 * 设备ID长度
	 */
	public static final int DEVICE_ID_LEN = 20;
	
	/**
	 * 账户系统类型
	 */
	public static final String ACCOUNTTYPE_MIE = "1";//mie账户
	public static final String ACCOUNTTYPE_OTHER = "2";
}
