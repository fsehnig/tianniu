/**
 * 
 */
package test;

import java.util.Date;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSONObject;
import com.tcl.shbc.mina.common.Base64Util;
import com.tcl.shbc.mina.common.Helper;
import com.tcl.shbc.mina.common.SystemCfg;
import com.tcl.shbc.mina.security.RSAUtils;


/**
 * @author zsj
 *
 */
public class TestClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		String publicKey = "MIGJAoGBAIBtMNRHnIYfkOyQnkopGuZIIwUzvMEPTrQF3XCaFDeTKTMaX5XSzgow0ouLSZ4Y7XYNPYufCEBYtS+7wNHWAq2+NsaObaEhILzz8/u50Xk6DboY+H8d3AkE0dCVogHj2IYnjtysXJ/rO6KwEHnLqxFXXgbszx0Rpf0sDEJx1F9pAgMBAAE=";
		RSAUtils.encryptByPublicKey(jsonObj.toString().getBytes("UTF-8"), publicKey);*/
		
		
		
		
		
		
		/*String message = "^AACgAP////8AAAAAApOAZKEHAChUJ0rE2Hs1k5y0gznmVmn5MdYZhQ866/lCUHf6luS9V99+i/tt7Rny";
		String bufferData = message.toString().substring(1);
		byte[] protocolbyte = bufferData.getBytes();
		protocolbyte = Base64Util.decode(protocolbyte);
		// 获得协议体的数据对象
		DeviceAbstractProtocolData protocolData = createProtocolData(protocolbyte);

		DeviceHeader header = protocolData.getDeviceHeader();
		byte[] data = protocolData.getCmData();
		
		byte[] result = XXTeaHelper.decrypt(data, "0udkr8JVzHr8hIXi".getBytes("UTF-8"), header.getLen());
		System.out.println(Helper.getJsonText(result));*/
		short encodeCtrl = (short) (((short) 2 & 0xF0FF) | ((1 & 0xF) << 8));
		short encryptType =  (short)( (encodeCtrl>>8)&0xf);
		//协议版本
		short version = (short)((encodeCtrl >> 12)&0xf);
		System.out.println("encryptType"+encryptType);
		System.out.println("version"+version);
	}
	
	
	/**
	 * 向ios推送离线消息
	 * 
	 * @param jsonText
	 */
	public static void sendIosMsg(String jsonText) {
		JSONObject jsonObj = JSONObject.parseObject(jsonText);
		// Portal上注册应用时生成的 masterSecret
		String masterSecret = SystemCfg.getConfig("jpush.masterSecret");
		// Portal上注册应用时生成的 appKey
		String appKey = SystemCfg.getConfig("jpush.appKey");
		// jpushClient的生成
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		String alert = jsonObj.getString("Alert");
		String pushType = jsonObj.getString("PushType");
		String userId = jsonObj.getString("UserId");
		
		PushPayload payload = buildPushPayload(alert, pushType, userId);
		try {
			PushResult result = jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * push pay load的建立
	 * 
	 * @param notification
	 * @param userId
	 * @return
	 */
	private static PushPayload buildPushPayload(String alert, String pushType,
			String userId) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.all())
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification.newBuilder()
												.addExtra("PushType", pushType)
												.setAlert(alert).build())
								.build()).build();
	}
	
	/*public static DeviceAbstractProtocolData createProtocolData(byte[] buffer)
			throws Exception {
		DeviceAbstractProtocolData protocolData = (DeviceAbstractProtocolData) new DeviceProtocolData();
		protocolData.init(buffer);
		return protocolData;
	}*/
	
	public static String getCertPublicKeyAndDeviceId(String cert, Long userIdSrc) {
		// 使用根证书的公钥验证数字证书是否合法
		String rootPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnOVEoQpX1Uk00vMGIKau3av1DEKxKveKEc6+mnj1YW7nd0ihDP3ygYG2p2w2uF6H84GXwMeS+pRzhsSfGMI+9J/Hi35U3lYIRVlSD51YfwKfFNy05Fa/V3wDDbfqXt3BWy4quTedMxwdXzFs+2emLo0ZsoykogbcU7EY5kocJOewluDMde8B+f0MgAdqPOyNB/dheOOzQKah7esDvj8OHcqrMWxIUwhUkHFNvIZM/yakHnYrgelBrs+QhuC7GP+9cLoEEoj9eibswWkvGkPxhYsSgMAqXf1AsymFhxDzIQOQ7fPp9SDyv/i3SC3uea3lYBdVUrk962vmq3toryHzPQIDAQAB";
		byte[] encryptedData = Base64Util.decode(cert);
		byte[] decryData = null;
		try {
			decryData = RSAUtils.decryptByPublicKey_2048(encryptedData, rootPublicKey);
		} catch (Exception e) {
			return null;
		}
		// 能正确解密的情况下
		if (decryData != null && 14 < decryData.length) {
			// 取得数字证书的公钥
			short deviceIdLen = Helper.byteArrayToShort(decryData, 2, 2);
			if (decryData.length < 2 + 2 + deviceIdLen + 8 + 2) {
				return null;
			} else {
				// 设备Id的取得
				byte[] deviceIdByte = new byte[deviceIdLen];
				System.arraycopy(decryData, 2 + 2, deviceIdByte, 0, deviceIdLen);
				Long userId = Helper.byteArrayToLong(deviceIdByte);
				if (userId.equals(userIdSrc)) {
					Long validateDate = Helper.byteArrayToLong(decryData, 2 + 2 + deviceIdLen, 8);
					if (validateDate != null) {
						Date now = new Date();
						// 如果失效的话，断开长连接
						if (validateDate < now.getTime()) {
							return null;
						} else {
							Short publicKeyLen = Helper.byteArrayToShort(decryData, 2 + 2 + deviceIdLen + 8, 2);
							if (publicKeyLen == null) {
								return null;
							} else {
								if (decryData.length < 2 + 2 + deviceIdLen + 8 + 2 + publicKeyLen) {
									return null;
								} else {
									byte[] publicKey = new byte[publicKeyLen];
									System.arraycopy(decryData, 2 + 2 + deviceIdLen + 8 + 2, publicKey, 0, publicKeyLen);
									String publicKeyStr = Base64Util.encodeToString(publicKey);
									return publicKeyStr;
								}
							}
						}
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
	}

}
