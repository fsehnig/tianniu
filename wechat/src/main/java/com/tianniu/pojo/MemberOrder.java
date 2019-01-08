package com.tianniu.pojo;

public class MemberOrder {
	private String ordernumber;
	private String ordertime;
	private String openid;
	private Integer state;
	private Integer recharge;
	private String transactionid;
	private String mchid;
	private String cardnumber;
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getRecharge() {
		return recharge;
	}
	public void setRecharge(Integer recharge) {
		this.recharge = recharge;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	@Override
	public String toString() {
		return "MemberOrder [ordernumber=" + ordernumber + ", ordertime=" + ordertime + ", openid=" + openid
				+ ", state=" + state + ", recharge=" + recharge + ", transactionid=" + transactionid + ", mchid="
				+ mchid + ", cardnumber=" + cardnumber + "]";
	}
	
	

}
