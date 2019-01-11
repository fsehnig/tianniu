package com.tianniu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tianniu.common.Constants;
import com.tianniu.common.HelpUtil;
import com.tianniu.pojo.ResponseMessage;
import com.tianniu.service.ILoginService;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService loginServiceImpl;
	
	/**
	 * 
	 * @Title: login  
	 * @Description: 跳转到login.html
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping(value="/tologin", method=RequestMethod.GET)
	public String toLogin() {
		return "login/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseMessage login(String name, String pwd) {
		ResponseMessage msg = null;
		name = name.trim();
		pwd = pwd.trim();
		if(StringUtils.isNotEmpty(name.trim()) && StringUtils.isNotEmpty(pwd.trim())) {
			boolean check = HelpUtil.check(name, Constants.REGEX_NAME);
			if (check) {
				msg = loginServiceImpl.login(name, pwd);
				//正确登录
				if(msg.getStatus() == 1) {
					return msg;
				}
			}
		}
		msg = new ResponseMessage();
		msg.setStatus(0);
		msg.setErrorMsg("请正确填写用户名和密码");
		return msg;
	}
	
}
