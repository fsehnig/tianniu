package com.tianniu.pojo;

public class User extends BasePojo<User>{
	
	private String userId;
	private String userName;
	private String sex;
	private int age;
	//身份证号
	private String sfzh;
	//1.普通用户；2.站长管理员
	private int flag;
	//用户被锁定的mac地址
	private String lockMacIp;
	private String password;
	//扩展字段
	private String tianniu03;
	//扩展字段
	private String tianniu04;
	private String remark;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getLockMacIp() {
		return lockMacIp;
	}
	public void setLockMacIp(String lockMacIp) {
		this.lockMacIp = lockMacIp;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTianniu03() {
		return tianniu03;
	}
	public void setTianniu03(String tianniu03) {
		this.tianniu03 = tianniu03;
	}
	public String getTianniu04() {
		return tianniu04;
	}
	public void setTianniu04(String tianniu04) {
		this.tianniu04 = tianniu04;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", sex=" + sex + ", age=" + age + ", sfzh=" + sfzh
				+ ", flag=" + flag + ", lockMacIp=" + lockMacIp + ", password=" + password + ", tianniu03=" + tianniu03
				+ ", tianniu04=" + tianniu04 + ", remark=" + remark + "]";
	}
	
}
