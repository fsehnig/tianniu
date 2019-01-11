package com.tianniu.pojo;

public class MemberUser extends BasePojo<MemberUser>{
	
	private static final long serialVersionUID = -4649096335655847336L;
	
	private String openid;
    private Short memberid;
    private String name;
    private String sex;
    private Short age;
    private String sfzh;
    private String cardNumber;
    private String address;
    private Short phone;
    private String carCd;
    private String cardflag;
    private String tianniu02;
    private String tianniu03;
    private String tianniu04;
    private String remarks;
    private String money;
    private String deleteflag;
    private Short recharge;
    private String cardbindingtime;
    
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Short getMemberid() {
        return memberid;
    }

    public void setMemberid(Short memberid) {
        this.memberid = memberid;
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

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getCardflag() {
        return cardflag;
    }

    public void setCardflag(String cardflag) {
        this.cardflag = cardflag;
    }

    public String getTianniu02() {
        return tianniu02;
    }

    public void setTianniu02(String tianniu02) {
        this.tianniu02 = tianniu02;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    public Short getRecharge() {
        return recharge;
    }

    public void setRecharge(Short recharge) {
        this.recharge = recharge;
    }

    public String getCardbindingtime() {
        return cardbindingtime;
    }

    public void setCardbindingtime(String cardbindingtime) {
        this.cardbindingtime = cardbindingtime;
    }

	@Override
	public String toString() {
		return "MemberUser [openid=" + openid + ", memberid=" + memberid + ", name=" + name + ", sex=" + sex + ", age="
				+ age + ", sfzh=" + sfzh + ", cardNumber=" + cardNumber + ", address=" + address + ", phone=" + phone
				+ ", carCd=" + carCd + ", cardflag=" + cardflag + ", tianniu02=" + tianniu02 + ", tianniu03="
				+ tianniu03 + ", tianniu04=" + tianniu04 + ", remarks=" + remarks + ", money=" + money + ", deleteflag="
				+ deleteflag + ", recharge=" + recharge + ", cardbindingtime=" + cardbindingtime + "]";
	}
    
    
}