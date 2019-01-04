package com.tcl.shbc.mina.common;

public class SecretKey {
	/**
	 * 秘钥版本号
	 */
	private Short version;
	
	/**
	 * 前版本加密失效时间(T1)
	 */
	private Long t1;
	
	/**
	 * 前版本解密失效时间(T2)
	 */
	private Long t2;
	
	/**
	 * 秘钥
	 */
	private String key;
	
	public Short getVersion() {
		return version;
	}

	public void setVersion(Short version) {
		this.version = version;
	}

	public Long getT1() {
		return t1;
	}

	public void setT1(Long t1) {
		this.t1 = t1;
	}

	public Long getT2() {
		return t2;
	}

	public void setT2(Long t2) {
		this.t2 = t2;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
