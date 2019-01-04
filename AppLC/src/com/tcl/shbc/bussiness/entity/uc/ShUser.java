package com.tcl.shbc.bussiness.entity.uc;

import java.util.Date;

import com.tcl.shbc.common.persistence.BaseEntity;

public class ShUser extends BaseEntity<ShUser>{
	private static final long serialVersionUID = -3341883744044898372L;

	private Long userId;
	/**
	 * 第三方账户系统的userId
	 */
	private Integer thirdUserId;
	
    private String userName;
    
    private String nickName;

    private String password;

    private String name;

    private String idCardNo;

    private String email;

    private String mobile;

    private String registerType;

    private Date registerTime;

    private Date updateTime;
    
    /**
     * 1：id注册没有绑定邮箱和手机，2：id注册绑定了邮箱，3：id注册绑定了手机，4：id注册绑定了邮箱和手机，
     * 5：邮箱注册未激活，6：邮箱注册激活但没绑定手机，7：邮箱注册激活且绑定了手机
     * 8：手机注册未激活，9：手机注册激活但没绑定邮箱，10：手机注册激活且绑定了邮箱
     */
    private String accountStatus;
    
    private String headpic;

    private String sex;//性别
    private String birthday;//出生日期
    private String province;//常居地
    private String city;
    private String region;
    
    /**
     * 账户系统类型
     */
    private String accountType;
    
	
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
	public Integer getThirdUserId() {
		return thirdUserId;
	}
	public void setThirdUserId(Integer thirdUserId) {
		this.thirdUserId = thirdUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public String getSex() {
		return sex;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
    
}