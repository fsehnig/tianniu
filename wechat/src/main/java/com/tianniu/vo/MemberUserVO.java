package com.tianniu.vo;

public class MemberUserVO {
	
	private String openid;
	private String cardNumber;
	private String name;
	private String sex;
	private String address;
	private Short phone;
	//0:有卡；1：无卡
	private String cardflag;
	//车牌
	private String carCd;
	//加油卡余额
	private String money;
	//1.普通会员，2.白金会员，3.铂金会员
	private Short memberlevel;
	//积分
	private Short score;
	
	
	public String getCardflag() {
		return cardflag;
	}
	public void setCardflag(String cardflag) {
		this.cardflag = cardflag;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Short getPhone() {
		return phone;
	}
	public void setPhone(Short phone) {
		this.phone = phone;
	}
	public String getCarCd() {
		return carCd;
	}
	public void setCarCd(String carCd) {
		this.carCd = carCd;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Short getMemberlevel() {
		return memberlevel;
	}
	public void setMemberlevel(Short memberlevel) {
		this.memberlevel = memberlevel;
	}
	public Short getScore() {
		return score;
	}
	public void setScore(Short score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "MemberUserVO [openid=" + openid + ", cardNumber=" + cardNumber + ", name=" + name + ", sex=" + sex
				+ ", address=" + address + ", phone=" + phone + ", cardflag=" + cardflag + ", carCd=" + carCd
				+ ", money=" + money + ", memberlevel=" + memberlevel + ", score=" + score + "]";
	}
	
	

}
