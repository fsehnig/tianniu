/**
 * 
 */
package com.tcl.shbc.mina.security;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author zhengsj
 *
 */
public class XXTeaHelper {

    /**
     * Encrypt data with key.
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                encrypt(toIntArray(data), toIntArray(key)));
    }
 
    /**
     * Decrypt data with key.
     *
     * @param data
     * @param key
     * @param realLen
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key, int realLen) {
        if (data.length == 0) {
            return data;
        }
        byte[] decText = toByteArray(decrypt(toIntArray(data), toIntArray(key)));
        byte[] ret = new byte[realLen];
        
        System.arraycopy(decText, 0, ret, 0, realLen);
        
        return ret;
    }
 
    /**
     * Encrypt data with key.
     *
     * @param v
     * @param k
     * @return
     */
    public static int[] encrypt(int[] v, int[] k) {
        int n = v.length;
        
        if (n <= 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];
 
            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n - 1], y = v[0], delta = 0x9E3779B9, sum = 0, e;
        int p, q = 6 + 52 / (n);
 
        while (q-- > 0) {
            sum = sum + delta;
            e = sum >>> 2 & 3;
            for (p = 0; p < n - 1; p++) {
                y = v[p + 1];
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                        ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            }
            y = v[0];
            z = v[n - 1] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                    ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
        }
        return v;
    }
 
    /**
     * Decrypt data with key.
     *
     * @param v
     * @param k
     * @return
     */
    public static int[] decrypt(int[] v, int[] k) {
        int n = v.length;
        
        if (n <= 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];
 
            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n - 1], y = v[0], delta = 0x9E3779B9, sum, e;
        int p, q = 6 + 52 / (n);
 
        sum = q * delta;
        while (sum != 0) {
            e = sum >>> 2 & 3;
            for (p = n - 1; p > 0; p--) {
                z = v[p - 1];
                y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                        ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            }
            z = v[n - 1];
            y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                    ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            sum = sum - delta;
        }
        return v;
    }
 
    /**
     * Convert byte array to int array.
     *
     * @param data
     * @return
     */
    private static int[] toIntArray(byte[] data) {
        int n = (((data.length & 3) == 0)
                ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;

        result = new int[n];
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }
 
    /**
     * Convert int array to byte array.
     *
     * @param data
     * @return
     */
    private static byte[] toByteArray(int[] data) {
        int n = data.length << 2;
 
//        if (includeLength) {
//            int m = data[data.length - 1];
// 
//            if (m > n) {
//                return null;
//            } else {
//                n = m;
//            }
//        }
        byte[] result = new byte[n];
 
        for (int i = 0; i < n; i++) {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return result;
    }
    
    public static String getRandomKey(int len){
		return RandomStringUtils.randomAlphanumeric(len);
	}
}
