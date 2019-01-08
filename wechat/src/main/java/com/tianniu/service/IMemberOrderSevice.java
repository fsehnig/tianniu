package com.tianniu.service;

import java.util.List;

import com.tianniu.pojo.MemberOrder;

public interface IMemberOrderSevice {

	public MemberOrder get(String openid);

	public List<MemberOrder> list(String openid);

}
