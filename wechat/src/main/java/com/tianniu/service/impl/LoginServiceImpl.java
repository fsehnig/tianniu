package com.tianniu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianniu.common.Constants;
import com.tianniu.common.HelpUtil;
import com.tianniu.mapper.maps.LoginMapper;
import com.tianniu.pojo.ResponseMessage;
import com.tianniu.pojo.User;
import com.tianniu.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired
	private LoginMapper loginMapper;


	@Override
	public ResponseMessage login(String name, String pwd) {
		ResponseMessage rm = new ResponseMessage();
		
		List<User> list = loginMapper.getUserByName(name);
		if (list.isEmpty() || list.size() != 1) {
			rm.setStatus(0);
			return rm;
		}
		
		User u = list.get(0);
		String encrypt = HelpUtil.encrypt(pwd, Constants.SALT, "MD5");
		if (u.getPassword().equals(encrypt)) {
			rm.setStatus(1);
			User user = new User();
			user.setUserId(u.getUserId());
			user.setUserName(u.getUserName());
			user.setFlag(u.getFlag());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			rm.setMap(map);
			return rm;
		}
		return rm;
	}

}
