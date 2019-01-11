package com.tianniu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianniu.pojo.MemberUser;
import com.tianniu.service.IMemberOrderService;

@Controller
@RequestMapping("/recharge")
public class RechargeController {
	
	private static Logger log = Logger.getLogger(RechargeController.class);
	
	@Autowired
	private IMemberOrderService memberOrderImpl;
	
	@RequestMapping("/2recharge")
	@ResponseBody
	public ModelAndView torecharge(MemberUser user){
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("recharge");
		return mv;
	}
	

}
