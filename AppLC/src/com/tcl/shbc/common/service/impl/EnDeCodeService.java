package com.tcl.shbc.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tcl.shbc.common.cache.RedisConn;
import com.tcl.shbc.common.cache.RedisConn.Strings;
import com.tcl.shbc.mina.common.Base64Util;
import com.tcl.shbc.mina.common.SecretKey;
import com.tcl.shbc.mina.security.XXTeaHelper;
@Service
public class EnDeCodeService{
	private static Logger logger = LoggerFactory.getLogger(EnDeCodeService.class);
			
	@Autowired
	private RedisConn redisConn;

 	/**
	 * 加密
	 * @param source 明文
	 * @param homeId 
 	 */
	public Map<String, Object> encode(String source,String key)
			throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCode = 1;

		Strings rStr = redisConn.STRINGS;
		String skJson = rStr.get(key);
		
		SecretKey rSecretKey = JSON.parseObject(skJson,
					SecretKey.class);

		byte[] result = XXTeaHelper.encrypt(source.getBytes("UTF-8"),
					rSecretKey.getKey().getBytes("UTF-8"));
		returnMap.put("result", result);
		returnMap.put("version", rSecretKey.getVersion());
		logger.debug("密文:" + Base64Util.encodeToString(result));
		
		returnMap.put("resultCode", resultCode);
		return returnMap;
	}
	
	/**
	 * 加密
	 * @param source 明文
	 * @param homeId 
 	 */
	public Map<String, Object> encode(byte[] sourceByte,String key)
			throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCode = 1; 

		Strings rStr = redisConn.STRINGS;
		String skJson = rStr.get(key);
		 
		// 只有一个版本,用此版本加密 
		SecretKey rSecretKey = JSON.parseObject(skJson,
					SecretKey.class);

		byte[] result = XXTeaHelper.encrypt(sourceByte,
					rSecretKey.getKey().getBytes("UTF-8"));
		returnMap.put("result", result);
		returnMap.put("version", rSecretKey.getVersion());
		logger.debug("密文:" + Base64Util.encodeToString(result));
		
		returnMap.put("resultCode", resultCode);
		return returnMap;
	}
	
	/**
	 * 解密
	 * @param source 密文
	 * @param homeId
	 * @param version 版本号
	 * @param len 明文长度
	 */
	public Map<String, Object> decode(byte[] sourcebyte,String key,
			Integer len) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCode = 1;
		
		Strings rStr = redisConn.STRINGS;
		String skJson = rStr.get(key);

		// 只有一个版本,用此版本解密 
		SecretKey rSecretKey = JSON.parseObject(skJson,
					SecretKey.class);

		byte[] result = XXTeaHelper.decrypt(sourcebyte, rSecretKey.getKey()
					.getBytes("UTF-8"), len);
		returnMap.put("result", result);
		logger.debug("明文:" + new String(result, "UTF-8"));
	
		returnMap.put("resultCode", resultCode);
		return returnMap;
	}

	/**
	 * 秘钥保存到redis
	 * 
	 * @param h
	 * @param priKey
	 * @param secretType
	 *            :0 全网公用 1home秘钥
	 */
	public SecretKey saveSecret2Reids(String key){
		long t1 = 120000;// 前版本加密失效时间2分钟
		long t2 = 600000;// 前版本解密失效时间10分钟
		short version = 1;
		String sKey = XXTeaHelper.getRandomKey(16);
		SecretKey secretKey = new SecretKey();
		secretKey.setVersion(version);
		secretKey.setT1(t1);
		secretKey.setT2(t2);
		secretKey.setKey(sKey);
		String skJson = JSON.toJSONString(secretKey);
		Strings rStr = redisConn.STRINGS;
		rStr.set(key, skJson);
		return secretKey;
	}
}
