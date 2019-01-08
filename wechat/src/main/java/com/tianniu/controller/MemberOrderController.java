package com.tianniu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianniu.pojo.MemberOrder;
import com.tianniu.service.IMemberOrderSevice;


@Controller
@RequestMapping("/mem")
public class MemberOrderController {
	
	private static Logger logger = Logger.getLogger(MemberOrderController.class);
	
	@Autowired
	private IMemberOrderSevice memberOrderService;
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(){
		System.out.println("index");
		System.err.println("error info");
		try {
			int i = 9/0;
		} catch (Exception e) {
			logger.error("mem/index", e);
		}
		return "index";
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public String get(@RequestParam String openid){
		System.out.println("openid:"+openid);
		if(openid != null && !openid.isEmpty()){
			List<MemberOrder> list = memberOrderService.list(openid);
			System.out.println(list.size());
			for (MemberOrder memberOrder : list) {
				System.out.println(memberOrder);
			}
		}
		return "ok";
	}
	
	@RequestMapping("/post")
	public String post(@RequestBody String post) {
		System.out.println("post:" + post);
		return "file";
	}
	
	
	
	

}
