/**
 * 
 */
package com.tcl.shbc.mina.security;

import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author sumjohn
 * 对称加密
 */
public class DESHelper {
	
	private static final String ALG = "DES";
	
	private DESHelper() {
		
	}
	
	/**
	 * 根据密码生成秘钥
	 * @param password
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static Key generateKey(String password) throws GeneralSecurityException {
//        KeyGenerator gen = KeyGenerator.getInstance(ALG);
//        SecureRandom random = new SecureRandom();
        
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALG);
        SecretKey securekey = keyFactory.generateSecret(desKey);
        
//        gen.init(new SecureRandom(password.getBytes()));
//        Key key = gen.generateKey();
        return securekey;
	}
	
	/**
	 * 加密
	 * @param password 密码
	 * @param plainText 原文
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(String password, byte[] plainText) throws GeneralSecurityException {
		return crypt(password, plainText, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 解密
	 * @param password 密码
	 * @param cipherText 密文
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(String password, byte[] cipherText) throws GeneralSecurityException {
		return crypt(password, cipherText, Cipher.DECRYPT_MODE);
	}
	
	/**
	 * 
	 * @param password
	 * @param text
	 * @param cryptMode
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static byte[] crypt(String password, byte[] text, int cryptMode) throws GeneralSecurityException {
		Key key = generateKey(password);

		Cipher c1 =Cipher.getInstance(ALG);
	    c1.init(cryptMode, key);
	    byte[] cipherText = c1.doFinal(text);
	    
	    return cipherText;
	}
}
