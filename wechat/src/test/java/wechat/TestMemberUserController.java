package wechat;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tianniu.pojo.MemberUser;
import com.tianniu.pojo.ResponseMessage;
import com.tianniu.service.IMemberUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMemberUserController {
	
	static Logger log = Logger.getLogger(TestMemberUserController.class);
	
	@Autowired
	private IMemberUserService memberUserImpl;
	
	@Test
	public void testpage(){
		ResponseMessage rm = null;
		String cardFlag = "0";//有
		String cardFlag1 = "1";//有
		rm = memberUserImpl.getPage(cardFlag);
		System.out.println(JSON.toJSONString(rm));
		ResponseMessage rm1 = memberUserImpl.getPage(cardFlag1);
		System.out.println(JSON.toJSONString(rm1));
	}
	
	@Test
	public void testBytiaojian(){
		MemberUser user = new MemberUser();
		user.setCardflag("1");
		user.setName("9999");
		ResponseMessage rm = memberUserImpl.getUserListByFlag(user, 1);
		System.out.println(JSON.toJSONString(rm));
		/*MemberUser user1 = new MemberUser();
		user1.setName("1234");
		System.out.println("test user:" + user.toString());
		ResponseMessage rm1 = memberUserImpl.getUserListByFlag(user1, 1);
		System.out.println(JSON.toJSONString(rm1));*/
	}
	
	@Test
	public void testUpdate(){
		MemberUser user = new MemberUser();
		user.setOpenid("oM6Zy56kfnjtEiKpZgkOIJ22tU_g");
		user.setMemberid((short) 30);
		ResponseMessage rm = memberUserImpl.updateUser(user);
		System.out.println(JSON.toJSONString(rm));
	}
	
	
}
