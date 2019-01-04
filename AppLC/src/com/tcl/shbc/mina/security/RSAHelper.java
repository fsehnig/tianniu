/**
 * 
 */
package com.tcl.shbc.mina.security;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.Cipher;

import com.tcl.shbc.mina.common.Helper;

/**
 * @author sumjohn
 * 非对称加密
 */
public class RSAHelper {
	
	private static final int KEY_SIZE = 1024;
	
	private static final String ALG = "RSA";

	public static enum KeyMode {
		PRIVATE_KEY,
		PUBLIC_KEY
	}

	private RSAHelper() {
		
	}
	
	/**
	 * 生成RSA加密算法钥匙对
	 * @return 数组，第一个为秘钥，第二个为公钥
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[][] generateKeyPair() throws GeneralSecurityException {
		
		KeyPairGenerator keyGen =KeyPairGenerator.getInstance(ALG);
		SecureRandom sRandom = new SecureRandom(Helper.longToByteArray((new Date()).getTime()));
		
		keyGen.initialize(KEY_SIZE, sRandom);
	    KeyPair key= keyGen.generateKeyPair();
	    
	    byte[][] ret = new byte[2][];
	    
	    ret[0] = key.getPrivate().getEncoded();
	    ret[1] = key.getPublic().getEncoded();
	
	    return ret;
	}
	
	/**
	 * 加密
	 * @param key 秘钥
	 * @param plainText 原文
	 * @param mode 模式：使用公钥加密/使用密钥加密
	 * @return
	 * @throws GeneralSecurityException 
	 */
	public static byte[] encrypt(byte[] key, byte[] plainText, KeyMode mode) throws GeneralSecurityException {
		return crypt(key, plainText, mode, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 解密
	 * @param key 秘钥
	 * @param cipherText 密文
	 * @param mode 模式：使用公钥解密/使用密钥解密
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(byte[] key, byte[] cipherText, KeyMode mode) throws GeneralSecurityException {
		return decrypt(key, cipherText, mode, false);
	}
	


	/**
	 * 解密
	 * @param key 秘钥
	 * @param cipherText 密文
	 * @param mode 模式：使用公钥解密/使用密钥解密
	 * @param bcMode 模式: BC MODE
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(byte[] key, byte[] cipherText, KeyMode mode, boolean bcMode) throws GeneralSecurityException {
		byte[] ret = crypt(key, cipherText, mode, Cipher.DECRYPT_MODE);
		if (bcMode && ret != null) {
			int idx = -1;
			for (int i = ret.length - 1; i >=0; i--) {
				if (ret[i] == 0) {
					idx = i;
					break;
				}
			}
			
			byte[] bcModeRet = new byte[ret.length - idx - 1];
			
			if (bcModeRet.length > 0) {
				System.arraycopy(ret, idx + 1, bcModeRet, 0, bcModeRet.length);
			}
			
			return bcModeRet;
		} else {
			return ret;
		}
	}

	/**
	 * 
	 * @param key
	 * @param text
	 * @param mode
	 * @param cryptMode
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static byte[] crypt(byte[] key, byte[] text, KeyMode mode, int cryptMode) throws GeneralSecurityException {
		KeyFactory keyFactory = KeyFactory.getInstance(ALG);
		Key keyObject =null;
		
		if (mode == KeyMode.PRIVATE_KEY) {
			keyObject = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
		} else if (mode == KeyMode.PUBLIC_KEY) {
			keyObject = keyFactory.generatePublic(new X509EncodedKeySpec(key));  	
		}
		
		Cipher c1 =Cipher.getInstance(ALG);
	    c1.init(cryptMode, keyObject);
	    byte[] cipherText = c1.doFinal(text);
	    
	    return cipherText;
	}
}
