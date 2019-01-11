package com.tianniu.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianniu.common.Constants;
import com.tianniu.pojo.MemberConsumer;
import com.tianniu.pojo.MemberUser;
import com.tianniu.pojo.ResponseMessage;
import com.tianniu.service.IMemberUserService;

@Controller
@RequestMapping("/memberUser")
public class MemberUserController {
	
	private Logger log = Logger.getLogger(MemberUserController.class);
	
	
	@Autowired
	private IMemberUserService memberUserImpl;
	
	/**
	 * 跳转会员展示jsp
	 * @Title: toMemberUser  
	 * @Description: 
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/toMemberUser")
	public ModelAndView toMemberUser(String cardFlag){
		ResponseMessage rm = null;
		//获取第一页
		rm = memberUserImpl.getPage(cardFlag);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("data", rm);
		mv.setViewName("memberUser/list");
		return mv;
	}
	
	/**
	 * 
	 * @Title: memberUserList  
	 * @Description: 
	 * @param @param flag  会员卡标识
	 * @param @param pnum  请求页数
	 * @param @param sum   总页数 
	 * @param @return    
	 * @return ResponseMessage    
	 * @throws
	 */
	@RequestMapping("/memberUserList")
	@ResponseBody
	public ResponseMessage memberUserList(MemberUser user, int pnum){
		ResponseMessage rm = null;
		rm = memberUserImpl.getUserListByFlag(user, pnum);
		return rm;
	}
	//用form.html
	/**
	 * 跳转到update.jsp
	 * @Title: toUpdatePage  
	 * @Description: 
	 * @param @param user
	 * @param @return    
	 * @return ModelAndView    
	 * @throws
	 */
	@RequestMapping("/2update")
	@ResponseBody
	public ModelAndView toUpdatePage(MemberUser user) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("memberUser/update");
		return mv;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ResponseMessage memberUserUpdate(MemberUser user) {
		ResponseMessage rm = memberUserImpl.updateUser(user);
		return rm;
	}
	
	public ResponseMessage deleteUser(MemberUser user){
		ResponseMessage rm = memberUserImpl.deleteUser(user);
		return rm;
	}
	

}
