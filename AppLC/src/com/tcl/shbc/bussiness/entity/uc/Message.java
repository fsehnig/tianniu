package com.tcl.shbc.bussiness.entity.uc;

import java.util.Date;

public class Message {
	private Integer id;
	private Long userId;
	private String title;
	private String content;
	private String kind;
	private String type;
	private String deviceType;
	private String iconUrl;
	private Date startTime;
	private Date endTime;
	private String isRead;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", userId=" + userId + ", title=" + title
				+ ", content=" + content + ", kind=" + kind + ", type=" + type
				+ ", deviceType=" + deviceType + ", iconUrl=" + iconUrl
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", isRead=" + isRead + "]";
	}
	public Message(Integer id, Long userId, String title, String content,
			String kind, String type, String deviceType, String iconUrl,
			Date startTime, Date endTime, String isRead) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.kind = kind;
		this.type = type;
		this.deviceType = deviceType;
		this.iconUrl = iconUrl;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isRead = isRead;
	}
	public Message() {
		super();
	}
	
}
