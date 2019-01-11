package com.tianniu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianniu.pojo.ResponseMessage;
import com.tianniu.service.IWorkbenchService;

@Controller
@RequestMapping("/workbench")
public class WorkbenchController {
	
	private static Logger log = Logger.getLogger(WorkbenchController.class);
	
	@Autowired
	private IWorkbenchService workbenchImpl;
	
	@RequestMapping("/2chart")
	@ResponseBody
	public ModelAndView tochart(){
		ResponseMessage rm = new ResponseMessage();
		rm = workbenchImpl.getUser();
		ModelAndView mv = new ModelAndView();
		mv.addObject("data", rm);
		mv.setViewName("chart");
		return mv;
	} 

}
