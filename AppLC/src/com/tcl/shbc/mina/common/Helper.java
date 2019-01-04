/**
 * 
 */
package com.tcl.shbc.mina.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author sumjohn
 *
 */
public class Helper {

	private Helper() {
	}

	/**
	 * �����ֽ�ת����int��
	 * 
	 * @param buffer
	 * @return
	 */
	public static short byteArrayToShort(byte[] buffer) {
		return byteArrayToShort(buffer, 0, 2);
	}

	/**
	 * ���ֽ������е��ĸ��ֽ�ת��int��
	 * 
	 * @param buffer
	 * @param startIdx
	 * @return
	 */
	public static short byteArrayToShort(byte[] buffer, int startIdx) {
		return byteArrayToShort(buffer, startIdx, 2);
	}

	/**
	 * ���ֽ������е�һ��ת��int��
	 * 
	 * @param buffer
	 * @param startIdx
	 * @param length
	 * @return
	 */
	public static short byteArrayToShort(byte[] buffer, int startIdx, int length) {
		short ret = 0;
		for (int i = 0; i < length; i++) {
			ret = (short) (ret << 8);
			byte v = buffer[i + startIdx];
			ret |= (v & 0xFF);
		}
		return ret;
	}

	/**
	 * �����ֽ�ת����int��
	 * 
	 * @param buffer
	 * @return
	 */
	public static int byteArrayToInt(byte[] buffer) {
		return byteArrayToInt(buffer, 0, 4);
	}

	/**
	 * ���ֽ������е��ĸ��ֽ�ת��int��
	 * 
	 * @param buffer
	 * @param startIdx
	 * @return
	 */
	public static int byteArrayToInt(byte[] buffer, int startIdx) {
		return byteArrayToInt(buffer, startIdx, 4);
	}

	/**
	 * ���ֽ������е�һ��ת��int��
	 * 
	 * @param buffer
	 * @param startIdx
	 * @param length
	 * @return
	 */
	public static int byteArrayToInt(byte[] buffer, int startIdx, int length) {

		int ret = 0;
		for (int i = 0; i < length; i++) {
			ret = ret << 8;
			byte v = buffer[i + startIdx];
			ret |= (v & 0xFF);
		}
		return ret;
	}

	/**
	 * �����ֽ�ת����int��
	 * 
	 * @param buffer
	 * @return
	 */
	public static long byteArrayToLong(byte[] buffer) {
		return byteArrayToLong(buffer, 0, 8);
	}

	/**
	 * ���ֽ������еİ˸��ֽ�ת��int��
	 * 
	 * @param buffer
	 * @param startIdx
	 * @return
	 */
	public static long byteArrayToLong(byte[] buffer, int startIdx) {
		return byteArrayToLong(buffer, startIdx, 8);
	}

	/**
	 * ���ֽ������е�һ��ת��int��
	 * 
	 * @param buffer
	 * @param startIdx
	 * @param length
	 * @return
	 */
	public static long byteArrayToLong(byte[] buffer, int startIdx, int length) {
		long ret = 0;
		for (int i = 0; i < length; i++) {
			ret = ret << 8;
			byte v = buffer[i + startIdx];
			ret |= (v & 0xFF);
		}
		return ret;
	}

	/**
	 * short��ת�ֽ�����
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] shortToByteArray(short value) {
		return numToByteArray(value, 2);
	}

	/**
	 * int��ת�ֽ�����
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] intToByteArray(int value) {
		return numToByteArray(value, 4);
	}

	/**
	 * long��ת�ֽ�����
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] longToByteArray(long value) {
		return numToByteArray(value, 8);
	}

	/**
	 * ��ֵת�����ֽ�����
	 * 
	 * @param num
	 *            ��ֵ
	 * @param width
	 *            λ��
	 * @return
	 */
	public static byte[] numToByteArray(long num, int width) {
		byte[] ret = new byte[width];

		for (int i = 0; i < ret.length; i++) {
			ret[i] = (byte) ((num >> (8 * (ret.length - i - 1))) & 0xFF);
		}

		return ret;
	}

	/**
	 * base64����
	 * 
	 * @param buffer
	 * @return
	 */
	public static String encodeBase64(byte[] buffer) {
		return Base64Util.encodeToString(buffer);
	}

	/**
	 * base64���루url��ȫ��
	 * 
	 * @param buffer
	 * @return
	 */
	public static String encodeBase64UrlSafe(byte[] buffer) {
		String szBase64 = encodeBase64(buffer);
		return szBase64.replaceAll("\\+", "_").replaceAll("/", "-");
	}

	/**
	 * base64����
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] decodeBase64(String data) throws IOException {
		return Base64Util.decode(data);
	}

	/**
	 * base64���루url��ȫ��
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] decodeBase64UrlSafe(String data) throws IOException {
		data = data.replaceAll("_", "+").replaceAll("-", "/");
		return decodeBase64(data);
	}

	/**
	 * ���byte����������ִ�
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getJsonText(byte[] data)
			throws UnsupportedEncodingException {
		return new String(data, "UTF-8");
	}

	/**
	 * 将byte[]的一部分转化成String
	 * 
	 * @param data
	 * @param startIdx
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getJsonText(byte[] data, int startIdx, int length)
			throws UnsupportedEncodingException {
		byte[] bs = new byte[length];
		for (int i = startIdx; i < startIdx + length; i++) {
			bs[i - startIdx] = data[i];
		}
		return new String(bs, "UTF-8");
	}

	/**
	 * ����ַ����byte����
	 * 
	 * @param jsonText
	 * @return
	 */
	public static byte[] getJsonByte(String jsonText) {
		try {
			return jsonText.getBytes("UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			return new byte[0];
		}
	}

	/**
	 * 随机字符串的生成
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 服务器IP的取得
	 * 
	 * @return 服务器IP
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}
	
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += "," + hex.toUpperCase();
		}
		return ret;
	}

}
