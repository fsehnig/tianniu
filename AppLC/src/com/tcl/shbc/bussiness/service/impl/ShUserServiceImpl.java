package com.tcl.shbc.bussiness.service.impl;

import org.springframework.stereotype.Service;

import com.tcl.shbc.bussiness.service.IShUserService;

@Service
public class ShUserServiceImpl implements IShUserService {
	/*@Autowired
	private ShUserMapper shUserMapper;
	
	private Logger logger = LoggerFactory.getLogger(ShUserServiceImpl.class);
	
	public Long saveUserInfo(Map<String,Object> userInfo){
		Long userId = Long.valueOf(userInfo.get("userId").toString());
		ShUser shUser = shUserMapper.getShUserByUserId(userId);
		if(shUser == null){
			shUser = this.getUserData(userInfo);
			shUserMapper.insertSelective(shUser);
			logger.info("保存用户信息成功:userId="+userId);
		}
		return userId;
	}
	
	private ShUser getUserData(Map<String, Object> userInfo){
		ShUser user = new ShUser();
		Long userId = Long.valueOf(userInfo.get("userId").toString());
		user.setUserId(userId);
		
		Integer thirdUserId = Integer.valueOf(userInfo.get("thirdUserId").toString());
        user.setThirdUserId(thirdUserId);
        
        String userName = userInfo.get("userName").toString();//账户
        user.setName(userName);
        
        String nickName = userInfo.get("nickName").toString();
        user.setNickName(nickName);
        //app保存到mie用utf-8之后的值,这里要解码
        if(null != userInfo.get("nickname")){
        	String nickName = userInfo.get("nickname").toString();
        	if(!StringUtils.isEmpty(nickName)){
        		try {
					nickName = URLDecoder.decode(nickName,"UTF-8");
					user.setNickName(nickName);
				} catch (Exception e) {
					logger.error("saveOrUpdateUserInfo异常：", e);
				}
        	}
        }
        String mobile = null;
        if(null != userInfo.get("mobile")){
        	mobile = userInfo.get("mobile").toString();
        	user.setMobile(mobile);
        }
        String email = null;
        if(null != userInfo.get("email")){
        	email = userInfo.get("email").toString();
        	user.setEmail(email);
        }
        String accountStatus = null;
        if(null != userInfo.get("accountStatus")){
        	accountStatus = userInfo.get("accountStatus").toString();
        	user.setAccountStatus(accountStatus);
        }
          
        String registerType = null;
        if(null != userInfo.get("registerType")){
        	registerType = userInfo.get("registerType").toString();
        	user.setRegisterType(registerType);
        }
        
        if(null != userInfo.get("registerTime")){
        	 Date registerTime = new Date(Long.valueOf(userInfo.get("registerTime").toString()));
             user.setRegisterTime(registerTime);
        }
        
        if(null != userInfo.get("updateTime")){
        	Date updateTime = new Date(Long.valueOf(userInfo.get("updateTime").toString()));
            user.setUpdateTime(updateTime);
        }
        
        String headpic = null;
        if(null != userInfo.get("headpic")){
        	headpic = userInfo.get("headpic").toString();
            user.setHeadpic(headpic);
        }
        String sex = null;
        if(null != userInfo.get("sex")){
        	sex = userInfo.get("sex").toString();
        	user.setSex(sex);
        }
        String birthday = null;
        if(null != userInfo.get("birthday")){
        	birthday = userInfo.get("birthday").toString();
        	user.setBirthday(birthday);
        }
        String province = null;
        if(null != userInfo.get("province")){
        	province = userInfo.get("province").toString();
        	user.setProvince(province);
        }
        String city = null;
        if(null != userInfo.get("city")){
        	city = userInfo.get("city").toString();
        	user.setCity(city);
        }
        String region = null;
        if(null != userInfo.get("region")){
        	region = userInfo.get("region").toString();
        	user.setRegion(region);
        }
        
        String accountType = userInfo.get("accountType").toString();
        user.setAccountType(accountType);
        return user;
	}*/
	
	/*@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public int saveOrUpdateUserInfo(Map<String, Object> userInfo) {
		ShUser user = new ShUser();
        Integer userId = Integer.valueOf(userInfo.get("id").toString());
        user.setUserId(userId);
        String userName = userInfo.get("username").toString();//账户
        user.setName(userName);
        
        //app保存到mie用utf-8之后的值,这里要解码
        if(null != userInfo.get("nickname")){
        	String nickName = userInfo.get("nickname").toString();
        	if(!StringUtils.isEmpty(nickName)){
        		try {
					nickName = URLDecoder.decode(nickName,"UTF-8");
					user.setNickName(nickName);
				} catch (Exception e) {
					logger.error("saveOrUpdateUserInfo异常：", e);
				}
        	}
        }
        
        String mobile = null;
        if(null != userInfo.get("phone")){
        	mobile = userInfo.get("phone").toString();
        	user.setMobile(mobile);
        }
        String email = null;
        if(null != userInfo.get("email")){
        	email = userInfo.get("email").toString();
        	user.setEmail(email);
        }
        String accountStatus = userInfo.get("status").toString();
        user.setAccountStatus(accountStatus);
          
        String registerType = userInfo.get("type").toString();
        user.setRegisterType(registerType); 
        
        Date createTime = new Date(Long.valueOf(userInfo.get("createTime").toString()));
        user.setRegisterTime(createTime);
        
        Date updateTime = new Date(Long.valueOf(userInfo.get("updateTime").toString()));
        user.setUpdateTime(updateTime);
        
        String headpic = userInfo.get("headpic").toString();
        user.setHeadpic(headpic);
        
        Hash redisHash = redis.HASH;
        Map userRedisMap = redisHash.hgetAll("shUser:userId:"+userId);
        if(null != userRedisMap && !userRedisMap.isEmpty()){
        	if(this.compareUser(user, userRedisMap)){
        		shUserMapper.updateByUserIdSelective(user);
            	this.saveUser2Redis(user,redisHash);
            	logger.debug("更新用户信息成功:userId="+userId);
        	}
        }else{
            shUserMapper.insertSelective(user);
            this.saveUser2Redis(user,redisHash);
        	logger.debug("保存用户信息成功:userId="+userId);
        }
		return 0;
	}*/
	
	/**
	 * 比较用户数据是否发生变化
	 * @param user
	 * @param userRedisMap
	 * @return
	 */
	/*private boolean compareUser(ShUser user,Map userRedisMap){
		boolean flag = false;
		String nickName = user.getNickName();
		if(!StringUtils.isEmpty(nickName) && !nickName.equals(userRedisMap.get("nickName"))){
			flag = true;
			return flag;
		}
		String email = user.getEmail();
		if(!StringUtils.isEmpty(email) && !email.equals(userRedisMap.get("email"))){
			flag = true;
			return flag;
		}
		String mobile = user.getMobile();
		if(!StringUtils.isEmpty(mobile) && !mobile.equals(userRedisMap.get("mobile"))){
			flag = true;
			return flag;
		}
		String headPic = user.getHeadpic();
		if(!StringUtils.isEmpty(headPic) && !headPic.equals(userRedisMap.get("headpic"))){
			flag = true;
			return flag;
		}
		return false;
	}*/
	/**
	 * 将用户信息nickName name email mobile保存到redis
	 * @param user
	 */
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	private void saveUser2Redis(ShUser user,Hash redisHash){
		Map map = new HashMap();
		
		String nickName = "";
		if(!StringUtils.isEmpty(user.getNickName())){
			nickName = user.getNickName();
		}
		map.put("nickName", nickName);
		
		map.put("name", user.getName());
		
		String email = "";
		if(!StringUtils.isEmpty(user.getEmail())){
			email = user.getEmail();
		}
		map.put("email", email);
		
		String mobile = "";
		if(!StringUtils.isEmpty(user.getMobile())){
			mobile = user.getMobile();
		}
		map.put("mobile", mobile);
		
		String headPic = "";
		if(!StringUtils.isEmpty(user.getHeadpic())){
			headPic = user.getHeadpic();
		}
		map.put("headpic", headPic);
		
		redisHash.hmset("shUser:userId:"+user.getUserId(), map);
	}*/
}
