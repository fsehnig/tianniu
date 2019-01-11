package com.tianniu.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianniu.mapper.maps.MemberUserMapper;
import com.tianniu.pojo.ResponseMessage;
import com.tianniu.service.IWorkbenchService;
@Service
public class WorkbenchServiceImpl implements IWorkbenchService{
	
	@Autowired
	private MemberUserMapper mapper;

	/*
a)	会员总数：汇总数据库MEMBER_USER表中会员绑卡标识符为0数据总数：DELETEFLAG=”0”
b)	有卡会员数：汇总数据库MEMBER_USER表中会员卡标识为0的数据总数（a前提）：DELETEFLAG=”0” & CARDFLAG=”0”
c)	电子会员数：汇总数据库MEMBER_USER表中会员卡标识为1的数据总数（a前提）：DELETEFLAG=”0” & CARDFLAG=”1”
d)	预充值未圈存人数：汇总数据库MEMBER_USER表中加油卡预充值金额不为0的数据总数（a前提）：DELETEFLAG=”0” & RECHARGE！=0
e)	会员流失人数：汇总数据库MEMBER_USER表中会员绑卡标识符为1数据总数：DELETEFLAG=”1”
*/
	@Override
	public ResponseMessage getUser() {
		ResponseMessage rm = new ResponseMessage();
		//会员总数：汇总数据库MEMBER_USER表中会员绑卡标识符为0数据总数：DELETEFLAG=”0”
		int userSum = mapper.getUserNoDelete();
		//有卡会员数：汇总数据库MEMBER_USER表中会员卡标识为0的数据总数（a前提）：DELETEFLAG=”0” & CARDFLAG=”0”
		int withCardSum = mapper.getUserWithCard();
		//电子会员数：汇总数据库MEMBER_USER表中会员卡标识为1的数据总数（a前提）：DELETEFLAG=”0” & CARDFLAG=”1”
		int noCardSum = mapper.getUserNoCard();
		//预充值未圈存人数：汇总数据库MEMBER_USER表中加油卡预充值金额不为0的数据总数（a前提）：DELETEFLAG=”0” & RECHARGE！=0
		int noLoad = mapper.getNoLoad();
		//会员流失人数：汇总数据库MEMBER_USER表中会员绑卡标识符为1数据总数：DELETEFLAG=”1”
		int deleSum = mapper.getUserDelete();
		
		Map<String, Object> map = new HashMap<>();
		map.put("userSum", userSum);
		map.put("withCardSum", withCardSum);
		map.put("noCardSum", noCardSum);
		map.put("deleSum", deleSum);
		rm.setMap(map);
		rm.setStatus(1);
		
		return rm;
	}
	
}
