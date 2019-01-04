package com.tcl.shbc.common.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcl.shbc.bussiness.sqlmaps.uc.ShUserHomeRefMapper;
import com.tcl.shbc.common.cache.RedisConn;
import com.tcl.shbc.common.service.IAppService;
import com.tcl.shbc.mina.common.HttpClientUtil;
import com.tcl.shbc.mina.common.SystemConfig;
import com.tcl.shbc.mina.handler.AppMinaHandler;

/**
 * 
 * @author mengke.xu
 *
 */
@Service
public class AppService implements IAppService {
	static Logger log = LoggerFactory.getLogger(AppService.class);

	@Autowired
	private RedisConn rConn;
	
	@Autowired 
	private ShUserHomeRefMapper ShUserHomeRefMapper;
	
	@Autowired
	private EnDeCodeService enDeCodeService;
	
	@Autowired
	private SystemConfig SystemCfg;

	public String doControll(String jsonText) throws Exception {
		JSONObject ackJson = new JSONObject();
		
		// 发送Flg
		boolean sendFlg = false;
		JSONObject reciveJson = JSON.parseObject(jsonText, JSONObject.class);
		String pushType = reciveJson.getString("PushType");
		
		// 消息推送的情况下
		if (pushType != null) {

			String userId = reciveJson.get("userId").toString();
			String kind = reciveJson.getString("Kind");
			
			JSONObject sendJson = new JSONObject();
			sendJson.put("informationType", (short) 0xB000);
			sendJson.put("cmd", (short) 0xB001);
			sendJson.put("SourceID", (int) reciveJson.get("SourceID"));
			sendJson.put("DestinationID", (int) reciveJson.get("DestinationID"));

			// 消息体的设置
			JSONObject cmdData = new JSONObject();
			cmdData.put("Kind", kind);
			cmdData.put("PushType", pushType);
			cmdData.put("UserId", Long.valueOf(userId));
			String content = "";
			if(reciveJson.get("Content") != null){
				content = reciveJson.get("Content").toString();
			}
			cmdData.put("Content", content);
			
			/*String secretKey = "LCUser:secretKey:" + userId;
			Map<String, Object> encodeMap = enDeCodeService.encode(cmdData.toString(), secretKey);
			int resultCode = (int) encodeMap.get("resultCode");
			if (resultCode == 1) {
				byte[] encodeBytes = (byte[]) encodeMap.get("result");*/
				sendJson.put("cmdData", cmdData.toString().getBytes("UTF-8"));
				sendJson.put("cmdDataLen", cmdData.toString().getBytes("UTF-8").length);
				
				// TODO
				/*short ctrl = 0;
				short encodeVersion = (short) encodeMap.get("version");
				short encodeCtrl = (short) ((ctrl & 0xF0FF) | ((encodeVersion & 0xF) << 8));
				sendJson.put("ctrl", encodeCtrl);*/
			/*} else {
				// 向App推送失败
				ackJson.put("Status", "0");
				return ackJson.toString();
			}*/
			
			boolean writeFlg = AppMinaHandler.writeMessage(userId, null,
					sendJson.toString());
			if (writeFlg) {
				// 只要有一个推送成功就证明接受结果是OK的
				sendFlg = true;
			} 
			
			// ------------------------------------------------------
			/*else {
				// 推送失败的情况下, ios的情况下,用极光推送
				String platform = rConn.STRINGS.get("GetPlatformByUserId" + userId);
				// 如果是ios的情况下,且是报警的情况下
				if ("1".endsWith(platform) && cmd == (short) 0x8005) {
					JSONObject sendIosMsg = new JSONObject();
					sendIosMsg.put("UserId", userId);
					sendIosMsg.put("Alert", "您有一条新的消息");
					sendIosMsg.put("PushType", pushType);
					sendIosMsg(sendIosMsg.toString());
					sendFlg = true;
				}
			}*/
			// ------------------------------------------------------
		} else {
			String userId = reciveJson.get("userId").toString();
			JSONObject sendJson = JSONObject.parseObject(reciveJson.get("protocolData").toString());
			short cmd = sendJson.getShortValue("cmd");
			// 如果是异地登陆的消息推送时
			if (cmd == (short) 0xB002) {
				if (AppMinaHandler.mapSession.containsKey("UserId" + userId)) {
					log.info("AppMinaHandler.mapSession.containsKey(userId)" + AppMinaHandler.mapSession.containsKey("UserId" + userId));
					// 不是只有TV的情况下
					if (! (AppMinaHandler.mapSession.get("UserId" + userId).containsKey("2") && AppMinaHandler.mapSession.get("UserId" + userId).size() == 1)) {
						log.info("不是只有TV的情况下");
						Set<String> platformList = AppMinaHandler.mapSession.get("UserId" + userId).keySet();
						for (String pf : platformList) {
							log.info("pf " + pf);
							if (! "2".equals(pf)) {
								AppMinaHandler.writeMessage(userId, pf, sendJson.toString());
								AppMinaHandler.removeSession(AppMinaHandler.mapSession.get("UserId" + userId).get(pf),
										"sessionClosed", true);
							}
						}
					}
				}
			} else {
				boolean writeFlg = AppMinaHandler.writeMessage(userId, null,
						sendJson.toString());
				if (writeFlg) {
					// 只要有一个推送成功就证明接受结果是OK的
					sendFlg = true;
				} else {
					// 推送失败的情况下, ios的情况下,用极光推送
					/*String platform = rConn.STRINGS.get("GetPlatformByUserId" + userId);*/
					/*short cmd = sendJson.getShortValue("cmd");*/
					// 如果是ios的情况下,且是报警的情况下
					if (AppMinaHandler.mapSession.get("UserId" + userId).containsKey("1") && cmd == (short) 0x8005) {
						JSONObject sendIosMsg = new JSONObject();
						sendIosMsg.put("UserId", userId);
						sendIosMsg.put("Alert", "您有一条新的消息");
						/*sendIosMsg(sendIosMsg.toString());*/
						HttpClientUtil.doPostForJson(SystemCfg.getConfig("jpush.url"), sendIosMsg.toString());
						sendFlg = true;
					}
				}
			}
			
		}
		
		if (sendFlg) {
			// 向App推送成功
			ackJson.put("Status", "1");
		} else {
			// 向App推送失败
			ackJson.put("Status", "0");
		}
		return ackJson.toString();
	}

	/**
	 * 用户对应服务器关系存储
	 * 
	 * @param userId
	 *            用户ID
	 * @param serverIP
	 *            服务器IP
	 */
	public void insertUserIP(String userId, String serverIP) {
		rConn.STRINGS.set(userId, serverIP);
	}

	/**
	 * 向ios推送离线消息
	 * 
	 * @param jsonText
	 */
	/*public void sendIosMsg(String jsonText) {
		JSONObject jsonObj = JSONObject.parseObject(jsonText);
		// Portal上注册应用时生成的 masterSecret
		String masterSecret = SystemCfg.getConfig("jpush.masterSecret");
		// Portal上注册应用时生成的 appKey
		String appKey = SystemCfg.getConfig("jpush.appKey");
		// jpushClient的生成
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		String alert = jsonObj.getString("Alert");
		String userId = jsonObj.getString("UserId");
		
		PushPayload payload = buildPushPayload(alert, userId);
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

	*//**
	 * push pay load的建立
	 * 
	 * @param notification
	 * @param userId
	 * @return
	 * @throws Exception 
	 *//*
	private PushPayload buildPushPayload(String alert, 
			String userId) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.alias(userId))
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setAlert(alert).build())
								.build())
				.setOptions(
						Options.newBuilder().setApnsProduction(true).build())
				.build();
	}*/
	
	/**
	 * 判断用户异地登陆时要不要被踢
	 * @param jsonText
	 * @throws Exception
	 */
	public void checkRelogin(String jsonText) throws Exception {
		log.info("入力参数" + jsonText);
		JSONObject reciveJson = JSON.parseObject(jsonText, JSONObject.class);
		String userId = reciveJson.get("UserId").toString();
		Integer homeId = reciveJson.getInteger("HomeId");
		if (AppMinaHandler.mapSession.containsKey("UserId" + userId)) {
			log.info("AppMinaHandler.mapSession.containsKey(userId)" + AppMinaHandler.mapSession.containsKey("UserId" + userId));
			// 不是只有TV的情况下
			if (! (AppMinaHandler.mapSession.get("UserId" + userId).containsKey("2") && AppMinaHandler.mapSession.get("UserId" + userId).size() == 1)) {
				log.info("不是只有TV的情况下");
				Set<String> platformList = AppMinaHandler.mapSession.get("UserId" + userId).keySet();
				for (String pf : platformList) {
					log.info("pf " + pf);
					if (! "2".equals(pf)) {
						// 消息推送
						JSONObject sendProtocolData = new JSONObject();
						sendProtocolData.put("informationType", (short) 0x8000);
						sendProtocolData.put("cmd", (short) 0xB002);
						
						// 通过用户Id取得HomeId
						/*ShUserHomeRef shUserHomeRef = ShUserHomeRefMapper.getUserHomeByUserId(Integer.valueOf(userId));
						Integer homeId = null;
						if (shUserHomeRef != null) {
							// homeId的取得
							homeId = shUserHomeRef.getHomeId();
						}
						log.info("homeId " + homeId);*/
						/*String secretKey = "LCUser:secretKey:" + userId;
						Map<String, Object> encodeMap = enDeCodeService.encode("", secretKey);
						int resultCode = (int) encodeMap.get("resultCode");
						log.info("resultCode " + resultCode);
						if (resultCode == 1) {*/
							sendProtocolData.put("cmdData", new byte[0]);
							sendProtocolData.put("cmdDataLen", 0);
							
							/*// TODO
							short ctrl = 0;
							short encodeVersion = (short) encodeMap.get("version");
							short encodeCtrl = (short) ((ctrl & 0xF0FF) | ((encodeVersion & 0xF) << 8));
							sendProtocolData.put("ctrl", encodeCtrl);
						}*/
						
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
						log.info("sendProtocolData " + sendProtocolData.toString());
						AppMinaHandler.writeMessage(userId, pf, sendProtocolData.toString());
						AppMinaHandler.removeSession(AppMinaHandler.mapSession.get("UserId" + userId).get(pf),
								"sessionClosed", true);
					}
				}
			}
		}
	}
}
