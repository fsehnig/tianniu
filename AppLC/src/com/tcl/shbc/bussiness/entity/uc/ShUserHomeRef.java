package com.tcl.shbc.bussiness.entity.uc;

import java.io.Serializable;
import java.util.Date;


public class ShUserHomeRef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8257516802730679019L;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 家庭ID
	 */
	private int homeId;
	
	/**
	 * NetID
	 */
	private int netId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the homeId
	 */
	public int getHomeId() {
		return homeId;
	}

	/**
	 * @param homeId the homeId to set
	 */
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}

	/**
	 * @return the netId
	 */
	public int getNetId() {
		return netId;
	}

	/**
	 * @param netId the netId to set
	 */
	public void setNetId(int netId) {
		this.netId = netId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}