package com.tianniu.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.tianniu.pojo.ResponseMessage;


public class HelpUtil {
	
	private static Logger logger = Logger.getLogger(HelpUtil.class);
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	

	public static String encrypt(String str, String salt, String algorithm) {
		String result = null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
			byte[] digest = md.digest((str+salt).getBytes("UTF-8"));
			result = byteArrayToHexString(digest);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("encrypt error", e);
		}
		return result;
	}
	
	
	private static String byteArrayToHexString(byte[] b) { 
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) { 
			resultSb.append(byteToHexString(b[i]));
		} 
		return resultSb.toString();
	} 
	
	
	private static String byteToHexString(byte b) { 
		int n = b;
		if (n < 0) n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	
	public static boolean check(String str, String regex) {
		Pattern p = Pattern.compile(regex);
		boolean matches = p.matcher(str).matches();
		return matches;
	}
	
	public static void main(String[] args) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus(1);
		rm.setErrorMsg("error");
		Map<String, Object> map = new HashMap<>();
		map.put("name", "vvalue");
		rm.setMap(map);
		
		String string = JSONObject.toJSONString(rm);
		System.out.println(string);
	}

}
