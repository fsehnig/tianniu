/**
 * 
 */
package com.tcl.shbc.mina.common;

import java.io.Serializable;
import java.util.List;



import com.tcl.shbc.mina.common.Constants;
import com.tcl.shbc.mina.common.Helper;

/**
 * @author sumjohn
 *
 */
public abstract class DeviceAbstractProtocolData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1479893538067096376L;
	
	public static final int VER_START = 0;
	
	public static final int VER_LEN = 1;
	
	/**
	 * 数据头部
	 */
	private DeviceHeader cmHeader = null;
	
	/**
	 * 数据体
	 */
	private byte[] cmData = null;

	/**
	 * 获得CMH数据
	 * @return CMH数据
	 */
	public DeviceHeader getDeviceHeader() {
		return cmHeader;
	}
	
	public DeviceAbstractProtocolData()
	{
		cmHeader = new DeviceHeader();
		cmData = new byte[200];
	}
	
	public DeviceAbstractProtocolData(DeviceHeader header, byte[] data)
	{
		cmHeader = header;
		cmData = data;
	}

	/**
	 * 获得CMD数据
	 * @return CMD数据
	 */
	public byte[] getCmData() {
		return cmData;
	}
	
	/**
	 * 获得CMD数据
	 * @return CMD数据
	 */
	public void setCmData(byte[] cmData) {
		this.cmData = cmData;
	}

	public void init(byte[] buffer,String secretKey, String shbcPrivateKey) throws Exception
	{
		fillHeader(buffer, secretKey, shbcPrivateKey);
//		fillData(buffer);//这个在fillHeader中调用
	}
	
	protected abstract void fillHeader(byte[] buffer,String secretKey, String shbcPrivateKey) throws Exception;
	
	protected abstract void fillData(byte[] buffer) throws Exception;
	
	/**
	 * 把类数据转换成协议字节流
	 * @return 传输字节流
	 */
	public abstract byte[] getByteArray();
	
	/**
	 * 类数据转换成字节流后的base64编码
	 * @return
	 */
	public String getBase64String()
	{
		return Helper.encodeBase64UrlSafe(getByteArray());
	}
	
	/**
	 * 类数据转换成字节流后的base64编码，包括头字符和尾字符
	 * @return
	 */
	public String getBase64StringWithHeadAndTail()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BASE64_PCK_HEADER);
		sb.append(getBase64String());
		sb.append(Constants.BASE64_PCK_TAIL);
		
		return sb.toString();
	}
	
	protected void setNumToBuffer(byte[] buffer, long num, int numWidth, int pos) {
		byte[] data = Helper.numToByteArray(num, numWidth);
		System.arraycopy(data, 0, buffer, pos, numWidth);
	}
	
	/**
	 * 多个数据包转换成base64字符串
	 * @param dataPackets
	 * @return
	 */
	public static String dataPackets2Base64(DeviceAbstractProtocolData[] dataPackets)
	{
		if (dataPackets == null || dataPackets.length == 0)
			return null;

		StringBuilder sb = new StringBuilder();
		for (DeviceAbstractProtocolData data : dataPackets)
		{
			sb.append(data.getBase64StringWithHeadAndTail());
		}
		return sb.toString();
	}
	
	/**
	 * 多个数据包转换成base64字符串
	 * @param dataPackets
	 * @return
	 */
	public static String dataPackets2Base64(List<DeviceAbstractProtocolData> dataPackets)
	{
		if (dataPackets == null || dataPackets.size() == 0) 
			return null;

		StringBuilder sb = new StringBuilder();
		for (DeviceAbstractProtocolData data : dataPackets)
		{
			sb.append(data.getBase64StringWithHeadAndTail());
		}
		return sb.toString();
	}
}
