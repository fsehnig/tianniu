package com.tcl.shbc.bussiness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.shbc.common.service.impl.AppService;

/**
 * 
 * @author mengke.xu
 *
 */
@Controller
@RequestMapping("/appControll")
public class AppController {

	@Autowired
	private AppService appService;

	/**
	 * 用户控制
	 * 将用户发送的控制指令转发到实体网关或单品设备
	 * @param jsonText 设备ID、控制命令
	 * @return 执行结果
	 * @throws Exception 
	 */
	@RequestMapping("/doControll")
	@ResponseBody
	public String doControll(@RequestBody String jsonText) throws Exception {
		return appService.doControll(jsonText);
	}
	
	/**
	 * 判断用户异地登陆时要不要被踢
	 * @param jsonText
	 * @throws Exception
	 */
	@RequestMapping("/checkRelogin")
	@ResponseBody
	public void checkRelogin(@RequestBody String jsonText) throws Exception {
		appService.checkRelogin(jsonText);
	}

}
