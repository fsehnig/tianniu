/**
 * 
 */
package com.tcl.shbc.mina.handler;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcl.shbc.common.cache.RedisConn;
import com.tcl.shbc.common.service.impl.AppMinaService;
import com.tcl.shbc.mina.common.Base64Util;
import com.tcl.shbc.mina.common.DeviceAbstractProtocolData;
import com.tcl.shbc.mina.common.DeviceHeader;
import com.tcl.shbc.mina.common.DeviceProtocolData;
import com.tcl.shbc.mina.common.Helper;
import com.tcl.shbc.mina.common.HttpClientUtil;
import com.tcl.shbc.mina.common.SystemConfig;
import com.tcl.shbc.mina.security.XXTeaHelper;

/**
 * 
 * @author mengke.xu
 *
 */
public class AppMinaHandler extends IoHandlerAdapter {

	@Autowired
	private AppMinaService appMinaService;
	@Autowired
	private RedisConn rConn;
	
	@Autowired
	private SystemConfig SystemCfg;

	static Logger log = LoggerFactory.getLogger(AppMinaHandler.class);
	

	/**
	 * 存放id与长连接映射关系的map
	 */
	public static Hashtable<String, Hashtable<String, IoSession>> mapSession = new Hashtable<String, Hashtable<String, IoSession>>();

	public static boolean writeMessage(String userId, String platform, String message) {
		boolean sendSucessFlg = false;
		if (mapSession.get("UserId" + userId) != null) {
			if (platform == null) {
				Set<String> platformList = mapSession.get("UserId" + userId).keySet();
				if (platformList != null) {
					for (String pf : platformList) {
						IoSession session = mapSession.get("UserId" + userId).get(pf);
						if (session != null) {
							if (session.isConnected()) {
								doResp(session, message);
								sendSucessFlg = true;
							}
						}
					}
				}
			} else {
				IoSession session = mapSession.get("UserId" + userId).get(platform);
				if (session != null) {
					if (session.isConnected()) {
						doResp(session, message);
						sendSucessFlg = true;
					}
				}
			}
		}
		return sendSucessFlg;
	}

	/**
	 * 主动断开TCP连接
	 * 
	 * @param apiKey
	 * @param id
	 * @return
	 */
	/*public static boolean blockDevice(String apiKey, String id) {
		id = id.toLowerCase();
		if (mapSession.containsKey(apiKey)) {
			if (mapSession.get(apiKey) != null) {
				removeSession(mapSession.get(apiKey), "block", true);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}*/

	@Override
	public void sessionOpened(IoSession session) {
		session.getConfig().setBothIdleTime(60);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		try {
			log.info(message.toString());
			log.info("收到的信息来自于" + session);
			String bufferData = message.toString().substring(1);
			byte[] protocolbyte = bufferData.getBytes();
			protocolbyte = Base64Util.decode(protocolbyte);
			// 获得协议体的数据对象
			final String userId = (String) session.getAttribute("SessionUserId");
			
			String shbcPrivateKey=  rConn.STRINGS.get("shbcPrivateKey");
			String secretKey=  rConn.STRINGS.get("LCUser:secretKey:" + userId);
			DeviceAbstractProtocolData protocolData = createProtocolData(protocolbyte,secretKey, shbcPrivateKey);

			DeviceHeader header = protocolData.getDeviceHeader();
			byte[] data = protocolData.getCmData();
			int dataLen = header.getLen();

			DeviceAbstractProtocolData returnProtocolData = (DeviceAbstractProtocolData) new DeviceProtocolData();
			DeviceHeader returnHeader = returnProtocolData.getDeviceHeader();
			// 控制字
			returnHeader.setCtrl(header.getCtrl());
			session.setAttribute("ctrl", header.getCtrl());
			short ctrl = header.getCtrl();
			// 根据ctrl取得密钥的版本号
			short secretVersion = (short) ((ctrl >> 8) & 0xF);
			log.debug("密钥的版本号" + secretVersion);
			// 信息包类型
			returnHeader.setInformationType(header.getInformationType());
			// 信息包接收方NetID
			// 消息返回时为消息接收时的信息包发送发NetID
			returnHeader.setDestinationID(header.getSourceID());
			// 信息包发送发NetID
			// 消息返回时为消息接收时的信息包接收方NetID
			returnHeader.setSourceID(header.getDestinationID());
			// 序列号
			returnHeader.setSn(header.getSn());
			
			String resultJson = "";

			// 命令字的取得
			Short cmd = header.getCmd();
			switch (cmd) {
			// 心跳指令 Heatbeat_req 0x8001
			case (short) 0x8001:
				if (userId == null) {
					log.debug("userId is null please forbid heart beat");
					removeSession(session, "sessionClosed", true);
					break;
				}
				// 命令字
				// 心跳返回指令 Heatbeat_resp 0x8002
				returnHeader.setCmd((short) 0x8002);
				appMinaService.encodeAndSendMessage("{}", returnProtocolData, ctrl, session);
				break;
			// App_Auth_req	0xB004	App长连接鉴权
			/*case (short) 0xB004:
				userId = (String) session.getAttribute("SessionUserId");
				if (userId == null) {
					removeSession(session, "sessionClosed", true);
					break;
				}
				// 命令字
				// App_Auth_res	0xB005	App长连接鉴权返回
				returnHeader.setCmd((short) 0xB005);
				resultJson = appMinaService.runCmdB004(dataLen, session, data);
				appMinaService.encodeAndSendMessage(resultJson, returnHeader, returnProtocolData, ctrl, session);
				break;*/
			// 发送加密字符串，当为对称加密时使用 device_key_req 0x800d
		/*	case (short) 0x800d:
				// 数字证书的私钥进行解密
				String base64AuthKey = rConn.STRINGS.get("shbcRSAPrivateKey");
			String base64AuthKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIHB/qnmkra5bDoSw3D1b02MSwjRk1SM6AKfm9QMCVBXob2DkKJGaQP6Xfsa7IfHNGfRRe2YkGCd5ATpC7hSXgo0b9A7fKMeFHfu+/cImoHEuyKYn0+GJ9j5ulhWp9VU6rvJSORK6s/y3meOOtn6u17yLqIMXsWyzkVNJj+sbkxJAgMBAAECgYB1iCSF/IXz4Kx5RbmlvYpdsa/dgwdyVFdq2Ouq/GQLU/T61grSEfjZU5SA+Hg63GNJjjNK7SAJg7qf//9Pq7vlKAWPWMIyB6TWoO73gJVXPYbGpiYuP/+A86YHbJ1BUCth0uxjdjkIJ3n2nzG1AC7nrgxW8oaKvdvaStMxtcAzAQJBAO8UQid4hhaG2JRNMJeuzPOGNx4r3PL2CSJwhtLJ0/xp9AzfxME0xNfjDeiSsVBcCPnN+ngAk0dzd97iaWI2d9ECQQCK8QD/NuTu0ks+ZyahKmTXkDTh0Y3AcVBiOZlEtjE+OmiTFBiIIW6lFegB0pxI5H1okD5KNkZXa25Pj+G9aiL5AkAbYcSZ5LHmzpbrlVOla73Jfmu9puRtZ0Rwj1bBuZ/v3uq2tsJAJp0M5NJo+rQYMhJNGgw/xkEYA9+MxX2wY/ChAkAFBKVTkmj8Uy4Sh+k2QvC3dGbkjQwAmlcb5RarC6WFqNhkD4K/T+3OGpdxkWjBnTcGUsddKM6rk569UuYFUFMJAkEArd89oKtpgPB7H9zePpfTb8UHu4OkFWojPWRJBxu3gJNlWQ0Usw078NtgWjrjvTDkNyf+XMy3ejpjb3xTqo7Mlg==";
			
				byte[] authkey = Base64Util.decode(base64AuthKey);
				String result800d = appMinaService.runCmd800d(base64AuthKey, session, data, dataLen);
				
				// 用户ID的取得
				userId = (String) session.getAttribute("SessionUserId");
				String platform = (String) session.getAttribute("SessionPlatform");
				Integer homeId = (Integer) session.getAttribute("SessionHomeId");
				// 如果用户在一个新设备上再次登陆的情况下，把原来的登陆踢掉
				synchronized (AppMinaHandler.class) {
					if (mapSession.containsKey("UserId" + userId)) {
						// TV的情况下
						if ("2".equals(platform)) {
							mapSession.get("UserId" + userId).remove(platform);
							mapSession.get("UserId" + userId).put(platform, session);
						} else {
							// 不是TV的情况下
							// 如果mapsession里面只有TV的情况下
							if (mapSession.get("UserId" + userId).containsKey("2") && mapSession.get("UserId" + userId).size() == 1) {
								mapSession.get("UserId" + userId).put(platform, session);
							} else {
								// 当前IP的取得
								String serverIP = SystemCfg.getConfig("http.service.ip");
								String serverPort = SystemCfg.getConfig("http.service.port");
								StringBuffer strBuffer = new StringBuffer();
								strBuffer.append(serverIP);
								strBuffer.append(":");
								strBuffer.append(serverPort);
								// 如果IP跟当前IP不一致的情况下
								if (! strBuffer.toString().equals(rConn.STRINGS.get("LCUserId" + userId)) && ((String) rConn.STRINGS.get("LCUserId" + userId) != null)) {
									JSONObject sendJson = new JSONObject();
									sendJson.put("UserId", userId);
									sendJson.put("HomeId", homeId);
									strBuffer = new StringBuffer();
									strBuffer.append("http://");
									strBuffer.append(rConn.STRINGS.get("LCUserId" + userId));
									strBuffer.append(SystemCfg.getConfig("app.service"));
									String response = HttpClientUtil.doPostForJson(
											strBuffer.toString(), sendJson.toJSONString());
									log.debug("异地登陆推送 : " + response);
								} else {
									Set<String> platformList = mapSession.get("UserId" + userId).keySet();
									Iterator iterator = platformList.iterator();
									while(iterator.hasNext()) {
										String pf = (String) iterator.next();
									for (String pf : platformList) {
										if (! "2".equals(pf)) {
											IoSession oldSession = mapSession.get("UserId"
													+ userId).get(pf);
											log.info("oldSession.isConnected()" + oldSession.isConnected());
											if (oldSession.isConnected()) {
												// 消息推送
												returnHeader.setCmd((short) 0xB002);
												// 消息体总数据长度
												returnHeader.setLen((short) 0);
												returnProtocolData.setCmData(new byte[0]);
												sendData = encode(returnProtocolData);
												oldSession.write(sendData);
												
												removeSession(mapSession.get("UserId" + userId).get(pf),
														"sessionClosed", true);
												Hashtable<String, IoSession> sessionList = mapSession.get("UserId" + userId);
												if (sessionList != null) {
													sessionList.remove(pf);
												}
												iterator.remove();
												log.info("AAAAAAAAAAAAAAA UserId" + userId + " platform " + pf
														+ " has been kicked off VVVVVVVVVV");
											}
										}
									}
								}
								mapSession.get("UserId" + userId).put(platform, session);
							}
						}
					} else {
						// 当前IP的取得
						String serverIP = SystemCfg.getConfig("http.service.ip");
						String serverPort = SystemCfg.getConfig("http.service.port");
						StringBuffer strBuffer = new StringBuffer();
						strBuffer.append(serverIP);
						strBuffer.append(":");
						strBuffer.append(serverPort);
						if (! strBuffer.toString().equals(rConn.STRINGS.get("LCUserId" + userId))) {
							// 如果用户上次登陆的IP不是当前服务器的IP的情况下
							// 不是TV的情况下
							if ((! "2".equals(platform)) && ((String) rConn.STRINGS.get("LCUserId" + userId) != null)) {
								JSONObject sendJson = new JSONObject();
								sendJson.put("UserId", userId);
								sendJson.put("HomeId", homeId);
								strBuffer = new StringBuffer();
								strBuffer.append("http://");
								strBuffer.append(rConn.STRINGS.get("LCUserId" + userId));
								strBuffer.append(SystemCfg.getConfig("app.service"));
								String response = HttpClientUtil.doPostForJson(
										strBuffer.toString(), sendJson.toJSONString());
								log.debug("异地登陆推送 : " + response);
							}
						}
						Hashtable<String, IoSession> newSession = new Hashtable<String, IoSession>();
						newSession.put(platform, session);
						mapSession.put("UserId" + userId, newSession);
						log.debug("UserId " + userId
								+ " relogin XXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
					
				}
				// 命令字
				// 命令返回 device_key_res 0x800e
				returnHeader.setCmd((short) 0x800e);
				
				jsonLen = (short) result800d.getBytes().length;
				// 消息体总数据长度
				returnHeader.setLen(jsonLen);
				
				byte[] cmd800dBytes = result800d.getBytes();
				byte[] encrybytes = RSAUtils.encryptByPrivateKey(cmd800dBytes, base64AuthKey);
				
				returnProtocolData.setCmData(encrybytes);
				sendData = encode(returnProtocolData);
				session.write(sendData);
				log.debug(sendData);
				break;*/
			case (short) 0xA103://app获取服务器证书cmd(明文)
				Map<String, Object> A104Map = new HashMap<String,Object>();
				//获取证书返回cmd
				returnHeader.setCmd((short) 0xA104);
				try {
					A104Map = appMinaService.runCmdA103(session, data);
				}catch (Exception e) {
					log.error("runCmdA103 异常：",e);
					A104Map.put("status", "2");
				}
				String cmdDataStr = JSONObject.toJSONString(A104Map);
				appMinaService.encodeAndSendMessage(cmdDataStr, returnProtocolData, ctrl, session);
				/*AppMinaHandler.removeSession(session,"sessionClosed", true);*/
				break;
			// PRE_GWAP_Authentication	0xA105	App申请鉴权(app->服务器（），app-网关，网关到服务器)
			case (short) 0xA105:
				Map<String, Object> resultA105 = appMinaService.runCmdA105(session, data);
				
				final String platform = (String) session.getAttribute("SessionPlatform");
				final Integer homeId = (Integer) session.getAttribute("SessionHomeId");
				// 如果用户在一个新设备上再次登陆的情况下，把原来的登陆踢掉
				ReentrantLock lock = new ReentrantLock();
				lock.lock();
				try {
					if (mapSession.containsKey("UserId" + userId)) {
						// TV的情况下
						if ("2".equals(platform)) {
							mapSession.get("UserId" + userId).remove(platform);
							mapSession.get("UserId" + userId).put(platform, session);
						} else {
							// 不是TV的情况下
							// 如果mapsession里面只有TV的情况下
							if (mapSession.get("UserId" + userId).containsKey("2") && mapSession.get("UserId" + userId).size() == 1) {
								mapSession.get("UserId" + userId).put(platform, session);
							} else {
								// 当前IP的取得
								String serverIP = SystemCfg.getConfig("http.service.ip");
								String serverPort = SystemCfg.getConfig("http.service.port");
								StringBuffer strBuffer = new StringBuffer();
								strBuffer.append(serverIP);
								strBuffer.append(":");
								strBuffer.append(serverPort);
								// 如果IP跟当前IP不一致的情况下
								if (! strBuffer.toString().equals(rConn.STRINGS.get("LCUserId" + userId)) && ((String) rConn.STRINGS.get("LCUserId" + userId) != null)) {
									new Thread() {
										public void run () {
											JSONObject sendJson = new JSONObject();
											sendJson.put("UserId", userId);
											sendJson.put("HomeId", homeId);
											StringBuffer strBuffer = new StringBuffer();
											strBuffer.append("http://");
											strBuffer.append(rConn.STRINGS.get("LCUserId" + userId));
											strBuffer.append(SystemCfg.getConfig("app.service"));
											String response = HttpClientUtil.doPostForJson(
													strBuffer.toString(), sendJson.toJSONString());
											log.debug("异地登陆推送 : " + response);
										}
									}.start();
								} else {
									Set<String> platformList = mapSession.get("UserId" + userId).keySet();
									Iterator iterator = platformList.iterator();
									while(iterator.hasNext()) {
										String pf = (String) iterator.next();
									/*for (String pf : platformList) {*/
										if (! "2".equals(pf)) {
											IoSession oldSession = mapSession.get("UserId"
													+ userId).get(pf);
											log.info("oldSession.isConnected()" + oldSession.isConnected());
											if (oldSession.isConnected()) {
												// 消息推送
												returnHeader.setCtrl((short) (((short) 2 & 0xF0FF) << 12 | ((1 & 0xF) << 8)));
												returnHeader.setCmd((short) 0xB002);
												appMinaService.encodeAndSendMessage("{}", returnProtocolData, ctrl, oldSession);
												removeSession(mapSession.get("UserId" + userId).get(pf),
														"sessionClosed", true);
												/*Hashtable<String, IoSession> sessionList = mapSession.get("UserId" + userId);
												if (sessionList != null) {
													sessionList.remove(pf);
												}*/
												iterator.remove();
												log.info("AAAAAAAAAAAAAAA UserId" + userId + " platform " + pf
														+ " has been kicked off VVVVVVVVVV");
											}
										}
									}
								}
								mapSession.get("UserId" + userId).put(platform, session);
							}
						}
					} else {
						new Thread() {
							public void run () {
								// 不是TV的情况下
								if (! "2".equals(platform) ){
									
									JSONObject jsonForAppLc = new JSONObject();
									// 消息推送
									JSONObject sendProtocolData = new JSONObject();
									sendProtocolData.put("informationType", (short) 0x8000);
									sendProtocolData.put("cmd", (short) 0xB002);
									sendProtocolData.put("cmdData", new byte[0]);
									sendProtocolData.put("cmdDataLen", 0);
									// 信息包发送发NetID
									sendProtocolData.put("sourceID", 0x00000001);
									Integer destinationID = Integer.valueOf(rConn.STRINGS.get("GetNetIdByUserIdHomeId"
										+ userId + homeId));
									if (destinationID == null) {
										destinationID = 0x00000000;
									}
									// 信息包接收方NetID
									sendProtocolData.put(
										"destinationID", destinationID);
									jsonForAppLc.put("userId", userId);
									jsonForAppLc.put("protocolData", sendProtocolData);
									// 实现跨家庭跨节点推送
									StringBuffer strBuffer = new StringBuffer();
									strBuffer.append(SystemCfg.getConfig("proxyServer.url"));
									strBuffer.append("/pushAppProtocolData.do");
										
									HttpClientUtil.doPostForJson(strBuffer.toString(),
												jsonForAppLc.toJSONString());
								}
							}
						}.start();
						
						Hashtable<String, IoSession> newSession = new Hashtable<String, IoSession>();
						newSession.put(platform, session);
						mapSession.put("UserId" + userId, newSession);
						log.info("UserId " + userId
								+ " relogin XXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
					
				} finally {
					lock.unlock();
				}
				// PRE_GWAP_Authentication_res	0xA106	App申请鉴权返回
				returnHeader.setCtrl((short) (((short) 2 & 0xF0FF) << 12 | ((2 & 0xF) << 8)));
				returnHeader.setCmd((short) 0xA106);
				String publicKey = resultA105.get("publicKey").toString();
			    resultJson = resultA105.get("resultJson").toString();
			    returnProtocolData.setCmData(resultJson.getBytes("UTF-8"));
				returnHeader.setLen((short)resultJson.getBytes("UTF-8").length);
				resultJson = appMinaService.RsaEncode(returnProtocolData, session, publicKey);
				session.write(resultJson);
				break;
			case (short) 0xA107:
				if (userId == null) {
					removeSession(session, "sessionClosed", true);
					break;
				}
				// 命令字
				// PRE_GWAP_ SignIn_res	0xA108	App登录确认返回
				returnHeader.setCmd((short) 0xA108);
				resultJson = appMinaService.runCmdA107(dataLen, session, data);
				appMinaService.encodeAndSendMessage(resultJson, returnProtocolData, ctrl, session);
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void sessionIdle(IoSession session, IdleStatus status) {
		removeSession(session, "sessionIdle", true);
	}

	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("UserId " + (String) session.getAttribute("SessionUserId")
				+ " offline XXXXXXXXXXXXXXXXXXXXXXX", cause);
		removeSession(session, "exceptionCaught", true);
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		//removeSession(session, "sessionClosed", true);
	};

	public static void removeSession(IoSession session, String cause,
			boolean imme) {
		session.close(imme);
	}

	private static void doResp(IoSession session, String msg) {
		log.debug("msg:"+msg);
		try {
			int packetId = 1;
			if (session.getAttribute("packetId") != null) {
				packetId = (int) session.getAttribute("packetId");
			}
			packetId = packetId++;
			/*IoBuffer buffer = IoBuffer.allocate(2048).setAutoExpand(true);*/

			JSONObject reciveJson = JSON.parseObject(msg, JSONObject.class);
			IoBuffer bufferForSend = IoBuffer.allocate(2048).setAutoExpand(true);
			bufferForSend.mark();
			short ctrl = (short) (((short) 2 & 0xF0FF) << 12 | ((1 & 0xF) << 8));
				log.debug("ctrl:"+ ctrl);
				//协议版本 按照版本2来
				// 控制字
				bufferForSend.putShort(ctrl);
				// 消息体总数据长度
				bufferForSend.putShort(reciveJson.getShortValue("cmdDataLen"));
				// 序列号
				bufferForSend.putInt(packetId);
				// 时间戳
				bufferForSend.putLong(System.currentTimeMillis());
				// 信息包类型
				bufferForSend.putShort(reciveJson.getShortValue("informationType"));
				// 信息包接收方NetID
				bufferForSend.putInt(reciveJson.getIntValue("destinationID"));
				// 信息包发送发NetID
				bufferForSend.putInt(reciveJson.getIntValue("sourceID"));
				// 命令字
				bufferForSend.putShort(reciveJson.getShortValue("cmd"));
				log.debug("cmd:" + reciveJson.getShortValue("cmd"));
				if (!Helper.getJsonText(reciveJson.getBytes("cmdData")).equals("{}")) {
					// 消息体
					bufferForSend.put(reciveJson.getBytes("cmdData"));
				} else {
					// 消息体总数据长度
					bufferForSend.putShort((short) 0x0000);
				}
			//加密的源数据
			byte[] byteForSend = new byte[bufferForSend.position()];
			bufferForSend.reset();
			bufferForSend.get(byteForSend);
			
			//需要加密的部分
			byte [] needEncodeV2 = new byte [byteForSend.length-2];
			byte [] result = null;
			//版本2方式加密
			System.arraycopy(byteForSend, 2, needEncodeV2, 0, needEncodeV2.length);
			// 用对称加密的密钥进行加密
			String skJson = session.getAttribute("SessionSecretKey").toString();
			JSONObject jsonobj = JSONObject.parseObject(skJson);
			byte[] encodeBytes  = XXTeaHelper.encrypt(needEncodeV2,
					jsonobj.getString("xxteaKey").getBytes("UTF-8"));
			result  = new byte [encodeBytes.length+2];
			System.arraycopy(byteForSend, 0, result, 0, 2);
			System.arraycopy(encodeBytes, 0, result, 2, encodeBytes.length);
			String stringForSend = Base64Util.encodeToString(result);
			StringBuffer strBuilder = new StringBuffer();
			strBuilder.append('^');
			strBuilder.append(stringForSend);
			bufferForSend.flip();
			session.write(strBuilder.toString());	
			log.debug("The Message for App: ^" + stringForSend + "&");
			session.setAttribute("packetId", packetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据协议把字节数组转换成数据对象
	 * 
	 * @param buffer
	 * @return
	 * @throws Exception
	 */
	public DeviceAbstractProtocolData createProtocolData(byte[] buffer,String secretKey, String shbcPrivateKey)
			throws Exception {
		DeviceProtocolData deviceProtocolData = new DeviceProtocolData();
		deviceProtocolData.init(buffer, secretKey, shbcPrivateKey);
		return deviceProtocolData;
	}

}
