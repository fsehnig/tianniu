package com.tcl.shbc.common.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tcl.shbc.bussiness.entity.uc.Message;
import com.tcl.shbc.bussiness.service.IShUserService;
import com.tcl.shbc.bussiness.sqlmaps.uc.MessageMapper;
import com.tcl.shbc.common.cache.RedisConn;
import com.tcl.shbc.common.service.IAppMinaService;
import com.tcl.shbc.mina.common.Base64Util;
import com.tcl.shbc.mina.common.DeviceAbstractProtocolData;
import com.tcl.shbc.mina.common.DeviceHeader;
import com.tcl.shbc.mina.common.Helper;
import com.tcl.shbc.mina.common.HttpClientUtil;
import com.tcl.shbc.mina.common.SecretKey;
import com.tcl.shbc.mina.common.SystemConfig;
import com.tcl.shbc.mina.handler.AppMinaHandler;
import com.tcl.shbc.mina.security.RSAUtils;
import com.tcl.shbc.mina.security.XXTeaHelper;
/**
 * 
 * @author mengke.xu
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AppMinaService implements IAppMinaService {
	static Logger log = LoggerFactory.getLogger(AppMinaService.class);
	@Autowired
	private AppService appService;
	
	@Autowired
	private IShUserService shUserService;
	
	@Autowired
	private EnDeCodeService enDeCodeService;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private RedisConn rConn;
	
	@Autowired
	private SystemConfig SystemCfg;

	/**
	 * Cmd 0x8001的处理
	 * 
	 * @param jsonObj
	 * @throws Exception 
	 */
	public Map<String, Object> runCmd8001(int len, IoSession session, byte[] data) throws Exception {
		// 该部分返回数据功能预留出来，返回数据的功能暂时不实现
		// TODO
		/*return new byte[0];*/
		String userId = (String) session.getAttribute("SessionUserId");
		// 用对称加密的密钥进行解密
		String secretKey = "LCUser:secretKey:" + userId;
		Map<String, Object> decodeMap = enDeCodeService.decode(data, secretKey, len);
		int resultCode = (int) decodeMap.get("resultCode");
		log.debug("解密的code值" + resultCode);
		if (resultCode == 1) {
			// 用对称加密的密钥进行加密
			Map<String, Object> encodeMap = enDeCodeService.encode("", secretKey);
			log.debug("加密的code值" + encodeMap.get("resultCode"));
			// 加密前数据的真实长度
			encodeMap.put("realLen", 0);
			return encodeMap;
		} else {
			AppMinaHandler.removeSession(session,"sessionClosed", true);
			return null;
		}
	}

	/**
	 * Cmd 0xB004的处理
	 * 
	 * @param session
	 * @param data
	 * @param jsonObj
	 * @return
	 * @throws Exception 
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public String runCmdB004(int len, IoSession session, byte[] data) throws Exception {
		String userId = (String) session.getAttribute("SessionUserId");
		// homeId和NetId的设定
		/*ShUserHomeRef shUserHomeRef = ShUserHomeRefMapper.getUserHomeByUserId(Integer.valueOf(userId));
		Integer homeId = null;
		if (shUserHomeRef != null) {
			homeId = shUserHomeRef.getHomeId();
		}*/
		// 用对称加密的密钥进行解密
			String jsonText = Helper.getJsonText(data);
			log.debug("解密的结果值" + jsonText);
			JSONObject resultJson = JSONObject.parseObject(jsonText);
			String randomKeyB = resultJson.getString("RandomKeyB");
			Integer homeId = resultJson.getInteger("HomeId");
			// 判断随机数B是否是之前生成的随机数B
			if (randomKeyB!= null && randomKeyB.equals((String) session.getAttribute("SessionRandomKeyB"))) {
				JSONObject jsonObj = new JSONObject();
				// 用户ID的设置  ios离线推送用
				jsonObj.put("UserId", userId);
				if (homeId != null) {
					/*jsonObj.put("HomeId", homeId);*/
					
					jsonObj.put("NetId", Integer.valueOf(rConn.STRINGS.get("GetNetIdByUserIdHomeId" + userId + homeId)));
				}
				// <UserId,serverIP>对应关系的存储
				// 服务器IP的取得
				String serverIP = SystemCfg.getConfig("http.service.ip");
				String serverPort = SystemCfg.getConfig("http.service.port");
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(serverIP);
				strBuffer.append(":");
				strBuffer.append(serverPort);
				appService.insertUserIP("LCUserId" + userId, strBuffer.toString());
				
				return jsonObj.toJSONString();
			} else {
				// 断开长连接
				AppMinaHandler.removeSession(session,"sessionClosed", true);
				return null;
			}
	}

	/**
	 * Cmd 0x800d的处理
	 * @param session
	 * @param data
	 * @param jsonObj
	 * @return
	 * @throws Exception 
	 */
	public String runCmd800d(String base64AuthKey, IoSession session, byte[] data, int dataLen) throws Exception {
		
		/*byte[] decryData = RSAHelper.decrypt(authkey, data, RSAHelper.KeyMode.PRIVATE_KEY);*/
		
		String decryString = Helper.getJsonText(data).trim();
		/*if (dataLen == decryString.length()) {*/
			JSONObject decryJson = JSONObject.parseObject(decryString);
			
			//获取userId
			Long userId = Long.valueOf(decryJson.getString("UserId"));
			// token的取得
			String token = decryJson.getString("Token");
			// 用户名的取得
			String userName = decryJson.getString("UserName");
			// 随机数A的取得
			String randomKeyA = decryJson.getString("RandomKeyA");
			// 平台的取得
			String platform = decryJson.getString("Platform");
			// homeId的取得
			Integer homeId = decryJson.getInteger("HomeId");
			// -----------------------------------------------------------------
			if (token == null || userName == null || randomKeyA == null) {
				// 断开长连接
				AppMinaHandler.removeSession(session,"sessionClosed", true);
				return "";
			} else {
				//默认为mie账户
				//String accountType =  Constants.ACCOUNTTYPE_MIE;
				//String isSaveUser = "1";//是否保存用户信息1:保存 0不保存
				log.info("用户名" + userName + "0x800d");
				String response = this.ssoValidateAuth(userId, token);
				JSONObject res = JSON.parseObject(response, JSONObject.class);
				String status = res.get("status").toString();
				 
				// 判断token的有效性
				boolean tokenUseful = false;
				// userInfo取得成功的情况下
				if ("1".equals(status)) {
					tokenUseful = true;
				}
				// 若token无效
				if (!tokenUseful) {
					// 断开长连接
					AppMinaHandler.removeSession(session,"sessionClosed", true);
					return "";
				} else {
					// 将用户ID保存在session中
					session.setAttribute("SessionUserId", String.valueOf(userId));
					/* ------------------------------更新用户ID对应的IP--------------------------------------------*/
					String clientIP = ((InetSocketAddress)session.getRemoteAddress()).getHostString();
					rConn.STRINGS.set("GetClientIPByUserId" + userId, clientIP);
					/* ------------------------------更新用户ID对应的IP--------------------------------------------*/
					// 密钥下发
					String secretkey = runCmd8610(Long.valueOf(userId), clientIP);
					JSONObject jsonobj = JSONObject.parseObject(secretkey);
					// 随机数A的设定
					jsonobj.put("RandomKeyA", randomKeyA);
					// 随机数B的设定
					String randomKeyB = XXTeaHelper.getRandomKey(16);
					session.setAttribute("SessionRandomKeyB", randomKeyB);
					jsonobj.put("RandomKeyB", randomKeyB);
					
					// ----------------------------------------
					if (platform != null) {
						/*rConn.STRINGS.set("GetPlatformByUserId" + userId, platform);*/
						session.setAttribute("SessionPlatform", platform);
					}
					// ----------------------------------------
					session.setAttribute("SessionHomeId", homeId);
					/* ------------------------------将用户上线的通知推送到中心节点--------------------------------------------*/
					// 将userId和NodeId同步到中心节点
					String nodeId = SystemCfg.getConfig("redirect.nodeId");
					JSONObject saveJson = new JSONObject();
					saveJson.put("userId", userId);
					saveJson.put("serverNodeId", Integer.valueOf(nodeId));
					String redirectServerUrl = SystemCfg.getConfig("proxyServer.url") + "/saveUserServerIps.do";
			        HttpClientUtil.doPostForJson(redirectServerUrl, saveJson.toString());
					/* ------------------------------将用户上线的通知推送到中心节点--------------------------------------------*/
					return jsonobj.toString();
				}
			}
		/*} else {
			// 断开长连接
			AppMinaHandler.removeSession(session,"sessionClosed", true);
			return "";
		}*/
	}
	
	public Map<String, Object> runCmdA105(IoSession session, byte[] data) throws Exception {
		try {
		String decryString = Helper.getJsonText(data).trim();
		JSONObject decryJson = JSONObject.parseObject(decryString);
		log.info(decryJson.toString());
			
		//获取userId
		Long userId = Long.valueOf(decryJson.getString("userId"));
		// token的取得
		String token = decryJson.getString("token");
		// 用户名的取得
		/*String userName = decryJson.getString("userName");*/
		// 随机数A的取得
		String randA = decryJson.getString("randA");
			// 平台的取得
			String platform = decryJson.getString("platform");
			// homeId的取得
			Integer homeId = decryJson.getInteger("homeId");
			// 版本号的取得
			String version = decryJson.getString("version");
			// 证书内容，经过base64 的取得
			String cert = decryJson.getString("cert");
			// -----------------------------------------------------------------
			if (token == null || randA == null || userId == null || version == null || cert == null) {
				// 断开长连接
				AppMinaHandler.removeSession(session,"sessionClosed", true);
				return null;
			} else {
				//默认为mie账户
				//String accountType =  Constants.ACCOUNTTYPE_MIE;
				//String isSaveUser = "1";//是否保存用户信息1:保存 0不保存
				log.info("用户Id" + userId + "0xA105");
				String response = this.ssoValidateAuth(userId, token);
				log.info("response" + response);
				JSONObject res = JSON.parseObject(response, JSONObject.class);
				String status = res.get("status").toString();
				 
				// 判断token的有效性
				boolean tokenUseful = false;
				// userInfo取得成功的情况下
				if ("1".equals(status)) {
					tokenUseful = true;
				}
				// 若token无效
				if (!tokenUseful) {
					// 断开长连接
					AppMinaHandler.removeSession(session,"sessionClosed", true);
					return null;
				} else {
					String publicKey = getCertPublicKeyAndDeviceId(cert, userId);
					if (publicKey == null) {
						// 断开长连接
						AppMinaHandler.removeSession(session,"sessionClosed", true);
						return null;
					} else {
						// 将用户ID保存在session中
						session.setAttribute("SessionUserId", String.valueOf(userId));
						/* ------------------------------更新用户ID对应的IP--------------------------------------------*/
						log.info("session" + session);
						String clientIP = ((InetSocketAddress)session.getRemoteAddress()).getHostString();
						rConn.STRINGS.set("GetClientIPByUserId" + userId, clientIP);
						/* ------------------------------更新用户ID对应的IP--------------------------------------------*/
						// 密钥下发
						String secretkey = runCmd8610(Long.valueOf(userId), clientIP);
						session.setAttribute("SessionSecretKey", secretkey);
						JSONObject jsonObj = JSONObject.parseObject(secretkey);
						// 0：成功，1：失败
						jsonObj.put("status", "0");
						// 随机数A的设定
						jsonObj.put("randA", randA);
						// 随机数B的设定
						String randB = XXTeaHelper.getRandomKey(16);
						session.setAttribute("SessionRandomKeyB", randB);
						jsonObj.put("randB", randB);
						
						// ----------------------------------------
						if (platform != null) {
							/*rConn.STRINGS.set("GetPlatformByUserId" + userId, platform);*/
							session.setAttribute("SessionPlatform", platform);
						}
						// ----------------------------------------
						session.setAttribute("SessionHomeId", homeId);
						/* ------------------------------将用户上线的通知推送到中心节点--------------------------------------------*/
						// 将userId和NodeId同步到中心节点
						String nodeId = SystemCfg.getConfig("redirect.nodeId");
						JSONObject saveJson = new JSONObject();
						saveJson.put("userId", userId);
						saveJson.put("serverNodeId", Integer.valueOf(nodeId));
						String redirectServerUrl = SystemCfg.getConfig("proxyServer.url") + "/saveUserServerIps.do";
				        HttpClientUtil.doPostForJson(redirectServerUrl, saveJson.toString());
						/* ------------------------------将用户上线的通知推送到中心节点--------------------------------------------*/
						Map<String, Object> returnMap = new HashMap<String, Object>();
						returnMap.put("resultJson", jsonObj.toString());
						returnMap.put("publicKey", publicKey);
						return returnMap;
					}
				}
			}
		} catch (Exception e) {
			log.error("A105异常", e);
			AppMinaHandler.removeSession(session,"sessionClosed", true);
			return null;
		}
	}
	
	public String runCmdA107(int len, IoSession session, byte[] data) throws Exception {
		try {
		String userId = (String) session.getAttribute("SessionUserId");
			String jsonText = Helper.getJsonText(data);
			log.info("解密的结果值" + jsonText);
			JSONObject resultJson = JSONObject.parseObject(jsonText);
			String randB = resultJson.getString("randB");
			Integer homeId = resultJson.getInteger("homeId");
			// 判断随机数B是否是之前生成的随机数B
			log.info("" + (randB!= null && randB.equals((String) session.getAttribute("SessionRandomKeyB"))));
			log.info("SessionRandomKeyB:" + session.getAttribute("SessionRandomKeyB"));
			if (randB!= null && randB.equals((String) session.getAttribute("SessionRandomKeyB"))) {
				JSONObject jsonObj = new JSONObject();
				// 0：成功，1：失败
				jsonObj.put("status", "0");
				// 用户ID的设置  ios离线推送用
				jsonObj.put("userId", userId);
				if (homeId != null) {
					/*jsonObj.put("HomeId", homeId);*/
					String netIdStr = rConn.STRINGS.get("GetNetIdByUserIdHomeId" + userId + homeId);
					if (netIdStr != null) {
						jsonObj.put("netId", Integer.valueOf(netIdStr));
					}
					
				}
				// <UserId,serverIP>对应关系的存储
				// 服务器IP的取得
				String serverIP = SystemCfg.getConfig("http.service.ip");
				String serverPort = SystemCfg.getConfig("http.service.port");
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(serverIP);
				strBuffer.append(":");
				strBuffer.append(serverPort);
				appService.insertUserIP("LCUserId" + userId, strBuffer.toString());
				
				return jsonObj.toJSONString();
			} else {
				// 断开长连接
				AppMinaHandler.removeSession(session,"sessionClosed", true);
				return null;
			}
		} catch (Exception e) {
			log.error("A107异常", e);
			AppMinaHandler.removeSession(session,"sessionClosed", true);
			return null;
		}
	}
	
	/**
	 * 获取证书的公钥和设备Id
	 * @param cert
	 * @return
	 */
	public String getCertPublicKeyAndDeviceId(String cert, Long userIdSrc) {
		// 使用根证书的公钥验证数字证书是否合法
		String rootPublicKey = rConn.STRINGS.get("rootPublicKey");
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
						// TODO
						/*if (validateDate < now.getTime()) {
							return null;
						} else {*/
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
						/*}*/
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
	
	/**
	 * 当用户已经登录时，要做的踢掉过程
	 * @param userId
	 * @return
	 */
	public String kickAway(String userId){
		JSONObject returnMsg = new JSONObject();
		Message message = new Message();
		message.setUserId(Long.valueOf(userId));
		message.setTitle("登录出错");
		message.setContent("该账户已在其他设备登录");
		message.setKind("3");
		message.setStartTime(new Date());
		message.setIsRead("0");
		messageMapper.insertMessage(message);
		
		returnMsg.put("UserId", Integer.parseInt(userId));
		/*returnMsg.put("Title", "登录错误");
		returnMsg.put("Content", "此用户已在其他设备登录!");*/
		returnMsg.put("Kind", "3");
		returnMsg.put("PushType", "103");
		
		return returnMsg.toJSONString();
	}
	
	public String ssoValidateAuth(Long userId, String token) {
		Map<String,Object> param = new HashMap<String,Object>();
		/*param.put("userName", userName);
		param.put("token", token);
		param.put("accountType", accountType);
		param.put("isSaveUser", isSaveUser);*/
		
		param.put("userId", userId);
		param.put("token", token);
		String authServerUrl = SystemCfg.getConfig("authServer.url")+"/authToken.do";
		
		String jsonStr = JSON.toJSONString(param);
		String result = HttpClientUtil.doPostForJson(authServerUrl, jsonStr);
		return result;
	}
	
	public String runCmd8610(Long userId, String clientIP) throws UnsupportedEncodingException {
		String key = "LCUser:secretKey:" + userId;
		SecretKey secretKey = enDeCodeService.saveSecret2Reids(key);
		
		JSONObject jsonObj = new JSONObject();
		/*JSONArray secretKeyArray = new JSONArray();
		JSONObject returnKey = new JSONObject();
		
		// 密钥的版本号
		returnKey.put("Version", secretKey.getVersion());
		// 前版本加密失效时间(T1)
		returnKey.put("T1", secretKey.getT1());
		// 前版本解密失效时间(T2)
		returnKey.put("T2", secretKey.getT2());
		// ASCII字符串
		returnKey.put("Key", secretKey.getKey());

		secretKeyArray.add(returnKey);*/
		jsonObj.put("xxteaKey", secretKey.getKey());
		return jsonObj.toString();
	}
	
	/**
	 * 获取服务器数字证书
	 * @param session
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> runCmdA103(IoSession session, byte[] data) throws Exception {
		//String rootPrivateKey = rConn.STRINGS.get("rootPrivateKey");
		//byte[] zqjRSAPrivateKeyByte = Base64Util.decode(zqjRSAPrivateKey);
		
		//String shbcRSAPrivateKey = rConn.STRINGS.get("shbcRSAPrivateKey");
		String shbcPublicKey = rConn.STRINGS.get("shbcPublicKey");
		//byte[] shbcRSAPublicKeyByte = Base64Util.decode(shbcPublicKey);
		
		String jsonText = Helper.getJsonText(data);
		JSONObject resultJson = JSONObject.parseObject(jsonText);
	    List<HashMap> versionList = JSONArray.parseArray(resultJson.getString("versionList"), HashMap.class);
	    
	    List<Integer> temp = new ArrayList<Integer>();
	    for(HashMap v : versionList){
	    	temp.add(Integer.valueOf(v.get("version").toString()));
	    }
	    Integer maxVersion = Collections.max(temp);
	    log.info("runCmdA103最大版本号:"+maxVersion);
	    //去查询maxVersion对应的证书是否存在
	    //todo
	    
		//---生成服务器数字证书---//
	    String caServerUrl = SystemCfg.getConfig("caServer.url");
	    JSONObject signRequest = new JSONObject();
	    signRequest.put("publicKey", shbcPublicKey);
	    signRequest.put("signType", "2");
	    signRequest.put("id", null);
	    String signResult = HttpClientUtil.doPostForJson(caServerUrl, signRequest.toJSONString());
	    log.info("请求url:{},result:{}",caServerUrl,signResult);
	    JSONObject signResultJo = JSONObject.parseObject(signResult);
	    int status = signResultJo.getIntValue("status");
	    
		Map<String,Object> result = new HashMap<String,Object>();
		if(status == 995){
			result.put("status", "0");
			result.put("cert", signResultJo.getString("signCert"));
			result.put("version", String.valueOf(maxVersion));
	    }else{
	    	result.put("status", "2");
	    }
		return result;
	}
	
	
	
	
	
	/**
	 * 不加密和对称xxTea加密用此方法
	 * 
	 * @param object
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public  String encode(Object object,IoSession session) throws Exception {
		// IoBuffer buffer = IoBuffer.allocate(2048).setAutoExpand(true);
		IoBuffer bufferForSend = IoBuffer.allocate(2048).setAutoExpand(true);
		bufferForSend.mark();
		DeviceAbstractProtocolData protocolData = (DeviceAbstractProtocolData) object;
		// 数据头部的取得
		DeviceHeader deviceHeader = protocolData.getDeviceHeader();
		// 数据体的取得
		byte[] cmData = protocolData.getCmData();
		short ctrl = deviceHeader.getCtrl();
		//数据加解密类型
		short encryptType =  (short)( (ctrl>>8)&0xf);
		//协议版本
		short version = (short)((ctrl >> 12)&0xf);
		log.debug("encryptType:" + encryptType);
		log.debug("version:" + version);
		log.debug("cmd:"+ deviceHeader.getCmd());
		if (version !=2) {// version1
			// 控制字
			bufferForSend.putShort(deviceHeader.getCtrl());
			// 信息包类型
			bufferForSend.putShort(deviceHeader.getInformationType());
			// 信息包接收方NetID
			bufferForSend.putInt(deviceHeader.getDestinationID());
			// 信息包发送发NetID
			bufferForSend.putInt(deviceHeader.getSourceID());
			// 序列号
			bufferForSend.putInt(deviceHeader.getSn());
			// 命令字
			bufferForSend.putShort(deviceHeader.getCmd());
			if (!Helper.getJsonText(cmData).equals("{}")) {
				// 消息体总数据长度
				bufferForSend.putShort(deviceHeader.getLen());
				// 消息体
				bufferForSend.put(cmData);
			} else {
				// 消息体总数据长度
				bufferForSend.putShort((short) 0x0000);
			}
		} else{// version2
			// 控制字
			bufferForSend.putShort(deviceHeader.getCtrl());
			// 消息体总数据长度
			bufferForSend.putShort(deviceHeader.getLen());
			// 序列号
			bufferForSend.putInt(deviceHeader.getSn());
			// 时间戳
			bufferForSend.putLong(System.currentTimeMillis());
			// 信息包类型
			bufferForSend.putShort(deviceHeader.getInformationType());
			// 信息包接收方NetID
			bufferForSend.putInt(deviceHeader.getDestinationID());
			// 信息包发送发NetID
			bufferForSend.putInt(deviceHeader.getSourceID());
			// 命令字
			bufferForSend.putShort(deviceHeader.getCmd());
			if (!Helper.getJsonText(cmData).equals("{}")) {
				// 消息体
				bufferForSend.put(cmData);
			} else {
				// 消息体总数据长度
				bufferForSend.putShort((short) 0x0000);
			}
		}
		//加密的源数据
		byte[] byteForSend = new byte[bufferForSend.position()];
		bufferForSend.reset();
		bufferForSend.get(byteForSend);
		
		//需要加密的部分
		byte [] needEncodeV1 = new byte [byteForSend.length-20];
		byte [] needEncodeV2 = new byte [byteForSend.length-2];
		// 设备ID的取得
		String skJson = (String) session.getAttribute("SessionSecretKey");
		JSONObject jsonobj = JSONObject.parseObject(skJson);
		byte [] result = null;
		if(encryptType==0){//不加密
			result = byteForSend;
		}else if(encryptType==1){//xxTea
			if(version!=2){
				System.arraycopy(byteForSend, 20, needEncodeV1, 0, needEncodeV1.length);
				// 用对称加密的密钥进行加密
				byte[] encodeBytes  = XXTeaHelper.encrypt(needEncodeV2,
						jsonobj.getString("xxteaKey").getBytes("UTF-8"));
				result = new byte [encodeBytes.length+20];
				System.arraycopy(byteForSend, 0, result, 0, 20);
				System.arraycopy(encodeBytes, 0, result, 20, encodeBytes.length);
			}else {
				System.arraycopy(byteForSend, 2, needEncodeV2, 0, needEncodeV2.length);
				// 用对称加密的密钥进行加密
				byte[] encodeBytes  = XXTeaHelper.encrypt(needEncodeV2,
						jsonobj.getString("xxteaKey").getBytes("UTF-8"));
				result  = new byte [encodeBytes.length+2];
				System.arraycopy(byteForSend, 0, result, 0, 2);
				System.arraycopy(encodeBytes, 0, result, 2, encodeBytes.length);
			}
		}
		String stringForSend = Base64Util.encodeToString(result);
		StringBuffer strBuilder = new StringBuffer();
		strBuilder.append('^');
		strBuilder.append(stringForSend);
		/*
		 * buffer.put((byte) '^'); buffer.put(byteForSend); //buffer.put((byte)
		 * '&');
		 */
		// buffer.flip();
		bufferForSend.flip();
		// byte[] sendData = new byte[buffer.limit()];
		// buffer.get(sendData);
		return strBuilder.toString();
	}
	
	
	
	public  String RsaEncode(Object object,IoSession session,String publicKey ) throws Exception {
		// IoBuffer buffer = IoBuffer.allocate(2048).setAutoExpand(true);
		IoBuffer bufferForSend = IoBuffer.allocate(2048).setAutoExpand(true);
		bufferForSend.mark();

		DeviceAbstractProtocolData protocolData = (DeviceAbstractProtocolData) object;
		// 数据头部的取得
		DeviceHeader deviceHeader = protocolData.getDeviceHeader();
		// 数据体的取得
		byte[] cmData = protocolData.getCmData();
		short ctrl = deviceHeader.getCtrl();
		// 协议版本
//		short version = (short)(ctrl & 0x000f);
		short version = (short)((ctrl >> 12)&0xf);

		if (version!=2) {// version1
			// 控制字
			bufferForSend.putShort(deviceHeader.getCtrl());
			// 信息包类型
			bufferForSend.putShort(deviceHeader.getInformationType());
			// 信息包接收方NetID
			bufferForSend.putInt(deviceHeader.getDestinationID());
			// 信息包发送发NetID
			bufferForSend.putInt(deviceHeader.getSourceID());
			// 序列号
			bufferForSend.putInt(deviceHeader.getSn());
			// 命令字
			bufferForSend.putShort(deviceHeader.getCmd());
			if (!Helper.getJsonText(cmData).equals("{}")) {
				// 消息体总数据长度
				bufferForSend.putShort(deviceHeader.getLen());
				// 消息体
				bufferForSend.put(cmData);
			} else {
				// 消息体总数据长度
				bufferForSend.putShort((short) 0x0000);
			}
		} else {// version2
			// 控制字
			bufferForSend.putShort(deviceHeader.getCtrl());
			// 消息体总数据长度
			bufferForSend.putShort(deviceHeader.getLen());
			// 序列号
			bufferForSend.putInt(deviceHeader.getSn());
			// 时间戳
			bufferForSend.putLong(System.currentTimeMillis());
			// 信息包类型
			bufferForSend.putShort(deviceHeader.getInformationType());
			// 信息包接收方NetID
			bufferForSend.putInt(deviceHeader.getDestinationID());
			// 信息包发送发NetID
			bufferForSend.putInt(deviceHeader.getSourceID());
			// 命令字
			bufferForSend.putShort(deviceHeader.getCmd());
			if (!Helper.getJsonText(cmData).equals("{}")) {
				// 消息体
				bufferForSend.put(cmData);
			} else {
				// 消息体总数据长度
				bufferForSend.putShort((short) 0x0000);
			}
		}
		//加密的源数据
		byte[] byteForSend = new byte[bufferForSend.position()];
		bufferForSend.reset();
		bufferForSend.get(byteForSend);
		//需要加密的部分
		byte [] needEncodeV1 = new byte [byteForSend.length-20];
		byte [] needEncodeV2 = new byte [byteForSend.length-2];
		byte [] result = null;
		
		if(version!=2){
			System.arraycopy(byteForSend, 20, needEncodeV1, 0, needEncodeV1.length);
			//加密
			byte[] encodeBytes = RSAUtils.encryptByPublicKey(needEncodeV1, publicKey);
			//拼接
			result = new byte [encodeBytes.length+20];
			System.arraycopy(byteForSend, 0, result, 0, 20);
			System.arraycopy(encodeBytes, 0, result, 20, encodeBytes.length);
		}else {
			System.arraycopy(byteForSend, 2, needEncodeV2, 0, needEncodeV2.length);
			//加密
			byte[] encodeBytes = RSAUtils.encryptByPublicKey(needEncodeV2, publicKey);
			//拼接
			result  = new byte [encodeBytes.length+2];
			System.arraycopy(byteForSend, 0, result, 0, 2);
			System.arraycopy(encodeBytes, 0, result, 2, encodeBytes.length);
		}
		String stringForSend = Base64Util.encodeToString(result);

		StringBuffer strBuilder = new StringBuffer();

		strBuilder.append('^');
		strBuilder.append(stringForSend);

		/*
		 * buffer.put((byte) '^'); buffer.put(byteForSend); //buffer.put((byte)
		 * '&');
		 */
		// buffer.flip();
		bufferForSend.flip();

		// byte[] sendData = new byte[buffer.limit()];
		// buffer.get(sendData);
		return strBuilder.toString();
	}
	
	/**
	 * 
	 * 业务消息返回结果的加密和发送
	 * 
	 * @param returnJson
	 *            业务处理完毕返回的json
	 * @param returnHeader
	 * @param returnProtocolData
	 * @param ctrl
	 * @param session
	 * @throws Exception
	 */
	public void encodeAndSendMessage(String resultJson, DeviceAbstractProtocolData returnProtocolData, short ctrl, IoSession session)
			throws Exception {
		DeviceHeader returnHeader = returnProtocolData.getDeviceHeader();
		short version = (short)((ctrl >> 12)&0xf);
	    if(version==2){
			returnHeader.setTimeStamp(System.currentTimeMillis());
		}
	    
		returnProtocolData.setCmData(resultJson.getBytes("UTF-8"));
		returnHeader.setLen((short)resultJson.getBytes("UTF-8").length);
		String sendData = encode(returnProtocolData,session);
		log.debug("最终给网关发送的数据加密字符串：===》" + sendData);
		session.write(sendData);	
	}

	
}
