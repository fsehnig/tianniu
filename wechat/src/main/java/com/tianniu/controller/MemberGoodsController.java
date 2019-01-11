package com.tianniu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class MemberGoodsController {
	
	private static Logger log = Logger.getLogger(MemberGoodsController.class);
	
	@Autowired
	private IMemberGoodsService goodsImpl;
	
	public void togoodspage(){
		
	}

}
