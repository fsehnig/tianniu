/**
 * 
 */
package com.tcl.shbc.mina.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tcl.shbc.mina.security.RSAUtils;
import com.tcl.shbc.mina.security.XXTeaHelper;

/**
 * @author xumk
 * 
 */
@Component
public class DeviceProtocolData extends DeviceAbstractProtocolData {
	
	static Logger log = LoggerFactory.getLogger(DeviceProtocolData.class);
	
	public DeviceProtocolData() {
		super();
	}

	public DeviceProtocolData(DeviceHeader header, byte[] data) {
		super(header, data);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4511323897639303771L;

	@Override
	protected void fillHeader(byte[] buffer,String secretKey, String shbcPrivateKey) throws Exception {
		// 控制字的取得
		short ctrl = Helper.byteArrayToShort(buffer, DeviceDataPositionDef.H_CTRL_START, DeviceDataPositionDef.H_CTRL_LEN);
		//数据加解密类型
		short encryptType =  (short)( (ctrl>>8)&0xf);
		//协议版本
		short version = (short)((ctrl >> 12)&0xf);
		//解密以后的数据包
		byte[] decodedBuffer = null;
		if(encryptType==0){//不加密的直接处理
			if(version==2){
				initHeaderV2(buffer);
				fillData(buffer);
			}else{
				initHeaderV1(buffer);
				fillData(buffer);
			}
			return;
		}
		if(version!=2){
			byte [] needDecode = new byte [buffer.length - DeviceDataPositionDef.D_CMD_DATA_START] ;
  			System.arraycopy(buffer, DeviceDataPositionDef.D_CMD_DATA_START, needDecode, 0, needDecode.length);
  			//对需要解密的部分进行解密
			decodedBuffer = decode(encryptType, needDecode, secretKey, shbcPrivateKey);
			byte [] array1 = new byte[DeviceDataPositionDef.D_CMD_DATA_START];//不需要解密的部分的数据包
			System.arraycopy(buffer, 0, array1, 0, array1.length);
			byte[] result = new byte[array1.length + decodedBuffer.length]; 
			System.arraycopy(array1, 0, result, 0, array1.length);
			System.arraycopy(decodedBuffer, 0, result, array1.length, decodedBuffer.length);
			initHeaderV1(result);
			fillData(result);
		}else{
			byte [] needDecode = new byte [buffer.length - DeviceDataPositionDef.H_CTRL_LEN_V2] ;
			System.arraycopy(buffer, DeviceDataPositionDef.H_CTRL_LEN_V2, needDecode, 0, needDecode.length);
			//对需要解密的部分进行解密
			decodedBuffer = decode(encryptType, needDecode, secretKey, shbcPrivateKey);
			byte [] ctrlArray = Helper.shortToByteArray(ctrl);
			byte[] result = new byte[ctrlArray.length + decodedBuffer.length]; 
			System.arraycopy(ctrlArray, 0, result, 0, ctrlArray.length);
			System.arraycopy(decodedBuffer, 0, result, ctrlArray.length, decodedBuffer.length);
			initHeaderV2(result);
			fillData(result);
		}
	} 
	
	private byte [] decode(short encryptType,byte [] needDecode,String secretKey, String shbcPrivateKey) throws Exception{
		//解密以后的数据包
		byte[] decodedBuffer = null;
	
		switch (encryptType) {
		case 0://不需要加解密
			decodedBuffer = needDecode;
			break;
		case 1://xxT加解密
			SecretKey rSecretKey = JSON.parseObject(secretKey,
					SecretKey.class);
			log.debug("xxTea对称秘钥的secretKey:" + rSecretKey.getKey());
			decodedBuffer = XXTeaHelper.decrypt(needDecode,
					rSecretKey.getKey().getBytes("UTF-8"), needDecode.length);
			log.debug("xxT解密结果："+Helper.getJsonText(decodedBuffer));
			break;
		case 2://RSA加解密
			// 数字证书的私钥进行解密
			String base64AuthKey =shbcPrivateKey;
			// 根据私钥对设备进行解密
			decodedBuffer = RSAUtils.decryptByPrivateKey(needDecode, base64AuthKey);
			break;
		default:
			break;
		}
		return decodedBuffer;
	}
	
	
	/**
	 * 协议version 1 的协议解析方式
	 * 
	 * @param header
	 * @param decodedBuffer
	 * @return
	 */
	private DeviceHeader initHeaderV1(byte[] decodedBuffer){
		DeviceHeader header = getDeviceHeader();
		// 控制字的取得
		short ctrl = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_CTRL_START, DeviceDataPositionDef.H_CTRL_LEN);
		header.setCtrl(ctrl);
		// 信息包类型的取得
		short informationType = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_INFORMATIONTYPE_START, DeviceDataPositionDef.H_INFORMATIONTYPE_LEN);
		header.setInformationType(informationType);

		// 信息包接收方NetID
		int destinationID = Helper.byteArrayToInt(decodedBuffer, DeviceDataPositionDef.H_DESTINATIONID_START, DeviceDataPositionDef.H_DESTINATIONID_LEN);
		header.setDestinationID(destinationID);

		// 信息包发送方NetID
		int sourceID = Helper.byteArrayToInt(decodedBuffer, DeviceDataPositionDef.H_SOURCEID_START, DeviceDataPositionDef.H_SOURCEID_LEN);
		header.setSourceID(sourceID);

		// 序列号
		int sn = Helper.byteArrayToInt(decodedBuffer, DeviceDataPositionDef.H_SN_START, DeviceDataPositionDef.H_SN_LEN);
		header.setSn(sn);

		// 命令字
		short cmd = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_CMD_START, DeviceDataPositionDef.H_CMD_LEN);
		header.setCmd(cmd);

		// 消息体总数据长度
		short len = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_LEN_START, DeviceDataPositionDef.H_LEN_LEN);
		header.setLen(len);
		return header ;
	}
	
	/**
	 * 
	 * 协议version 2 的协议解析方式
	 * @param header
	 * @param decodedBuffer
	 * @return
	 */
	private DeviceHeader initHeaderV2(byte[] decodedBuffer){
		DeviceHeader header = getDeviceHeader();
		// 控制字的取得
		short ctrl = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_CTRL_START_V2, DeviceDataPositionDef.H_CTRL_LEN_V2);
		header.setCtrl(ctrl);
		// 信息包类型的取得
		short informationType = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_INFORMATIONTYPE_START_V2, DeviceDataPositionDef.H_INFORMATIONTYPE_LEN_V2);
		header.setInformationType(informationType);

		// 信息包接收方NetID
		int destinationID = Helper.byteArrayToInt(decodedBuffer, DeviceDataPositionDef.H_DESTINATIONID_START_V2, DeviceDataPositionDef.H_DESTINATIONID_LEN_V2);
		header.setDestinationID(destinationID);
		// 信息包发送方NetID
		int sourceID = Helper.byteArrayToInt(decodedBuffer, DeviceDataPositionDef.H_SOURCEID_START_V2, DeviceDataPositionDef.H_SOURCEID_LEN_V2);
		header.setSourceID(sourceID);

		// 序列号
		int sn = Helper.byteArrayToInt(decodedBuffer, DeviceDataPositionDef.H_SN_START_V2, DeviceDataPositionDef.H_SN_LEN_V2);
		header.setSn(sn);

		// 命令字
		short cmd = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_CMD_START_V2, DeviceDataPositionDef.H_CMD_LEN_V2);
		header.setCmd(cmd);

		// 消息体总数据长度
		short len = Helper.byteArrayToShort(decodedBuffer, DeviceDataPositionDef.H_LEN_START_V2, DeviceDataPositionDef.H_LEN_LEN_V2);
		header.setLen(len);
		
		// 时间戳
		Long timeStamp = Helper.byteArrayToLong(decodedBuffer, DeviceDataPositionDef.H_TIME_STAMP_START_V2, DeviceDataPositionDef.H_TIME_STAMP_LEN_V2);
		header.setTimeStamp(timeStamp);
		
		
		return header ;
	}

	@Override
	protected void fillData(byte[] decodedBuffer) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * int dataLen = getDeviceHeader().getLen(); JSONObject cmData; if
		 * (dataLen == 0) { cmData = new JSONObject(); } else { String jsonData
		 * = Helper.getJsonText(buffer, DeviceDataPositionDef.D_CMD_DATA_START,
		 * dataLen); cmData = JSON.parseObject(jsonData, JSONObject.class); }
		 * setCmData(cmData);
		 */
		
		DeviceHeader deviceHeader = getDeviceHeader();
		short ctrl = deviceHeader.getCtrl();
		//协议版本
		short version = (short)((ctrl >> 12)&0xf);
		if(version !=2){
			byte[] bytes = new byte[deviceHeader.getLen()];
			System.arraycopy(decodedBuffer, 20, bytes, 0, bytes.length);
			setCmData(bytes);
		}else{
			byte[] bytes = new byte[deviceHeader.getLen()];
			System.arraycopy(decodedBuffer, 28, bytes, 0, bytes.length);
			setCmData(bytes);
			log.debug("解密后的：================》"+Helper.getJsonText(bytes));
		}
	}

	@Override
	public byte[] getByteArray() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[DeviceDataPositionDef.D_CMD_DATA_START + getDeviceHeader().getLen()];
		return buffer;
	}
}
