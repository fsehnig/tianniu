package com.tianniu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianniu.common.Constants;
import com.tianniu.mapper.maps.MemberUserMapper;
import com.tianniu.pojo.MemberConsumer;
import com.tianniu.pojo.MemberUser;
import com.tianniu.pojo.ResponseMessage;
import com.tianniu.service.IMemberUserService;
import com.tianniu.vo.MemberUserVO;
@Service
public class MemberUserServiceImpl implements IMemberUserService {
	private static Logger log = Logger.getLogger(MemberUserServiceImpl.class);
	
	@Autowired
	private MemberUserMapper memberUserMapper;

	//分页
	@Override
	public ResponseMessage getUserListByFlag(MemberUser user, int pnum) {
		ResponseMessage rm = new ResponseMessage();
		
		int start = (pnum-1)*Constants.NUM;
		int end = pnum*Constants.NUM + 1;
		
		List<MemberUserVO> list = memberUserMapper.getUserListByFlag(user, start, end);
		if (list != null && !list.isEmpty()) {
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("sum", list.size());
			rm.setStatus(1);
			rm.setMap(map);
		}else {
			rm.setStatus(0);
			rm.setErrorMsg("没有查到相关记录");
		}
		return rm;
	}

	@Override
	public ResponseMessage getPage(String cardFlag) {
		ResponseMessage rm = new ResponseMessage();
		Map<String, Object> map = new HashMap<>();
		//总数
		int count = memberUserMapper.getUserCount(cardFlag);
		if (count == 0) {
			map.put("sum", 0);
		}else {
			map.put("sum", count/Constants.NUM + (count%Constants.NUM > 0 ? 1 : 0));
			MemberUser user = new MemberUser();
			user.setCardflag(cardFlag);
			
			int start = 0;
			int end = Constants.NUM + 1;
			List<MemberUserVO> list = memberUserMapper.getUserListByFlag(user, start, end);
			map.put("list", list);
			rm.setStatus(1);
		}
		rm.setMap(map);
		return rm;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResponseMessage updateUser(MemberUser user) {
		ResponseMessage rm = new ResponseMessage();
		Integer result = memberUserMapper.updateByPrimaryKeySelective(user);
		if (result == 1) {
			rm.setStatus(1);
			log.debug("updateuser user:" + user.toString());
		}
		return rm;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResponseMessage deleteUser(MemberUser user) {
		ResponseMessage rm = new ResponseMessage();
		Integer result = memberUserMapper.deleteByOpenid(user.getOpenid());
		if (result == 1) {
			rm.setStatus(1);
			log.debug("deleteuser :" + user.toString());
		}
		return rm;
	} 


}
