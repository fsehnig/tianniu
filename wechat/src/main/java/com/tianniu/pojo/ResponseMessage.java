package com.tianniu.pojo;

import java.util.Map;

public class ResponseMessage {
	private int status;
	private String info;
	private String errorMsg;
	private Map<String, Object> map;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "ResponseMessage [status=" + status + ", info=" + info + ", errorMsg=" + errorMsg + ", map=" + map + "]";
	}
	
}
