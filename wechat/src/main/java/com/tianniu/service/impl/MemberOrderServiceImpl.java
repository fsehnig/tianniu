package com.tianniu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianniu.mapper.MemberOrderMapper;
import com.tianniu.pojo.MemberOrder;
import com.tianniu.service.IMemberOrderSevice;
@Service
public class MemberOrderServiceImpl implements IMemberOrderSevice {
	
	@Autowired
	private MemberOrderMapper mapper;

	@Override
	public MemberOrder get(String openid) {
		MemberOrder item = mapper.get(openid);
		return item;
	}

	@Override
	public List<MemberOrder> list(String openid) {
		List<MemberOrder> order =  mapper.list(openid);
		return order;
	}

}
