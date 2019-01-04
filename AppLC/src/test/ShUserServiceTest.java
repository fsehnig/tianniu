package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tcl.shbc.bussiness.service.IShUserService;
import com.tcl.shbc.common.service.impl.AppMinaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:SpringContext.xml")
@TransactionConfiguration(defaultRollback = true)
public class ShUserServiceTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private IShUserService shUserService;
	@Autowired
	private AppMinaService appMinaService;
	
	@Test
	public void test() throws Exception {
		/*Map<String,Object> userInfo = new HashMap<String,Object>();
		userInfo.put("userId", "10090");
		userInfo.put("thirdUserId", "1142939725");
		userInfo.put("createTime", "1409712934000");
		//userInfo.put("email", "xingqiang.man@tcl.com");
		userInfo.put("nickname", "中国");
		userInfo.put("username", "15201068300");
		userInfo.put("phone", "15201068300");
		userInfo.put("status", "2");
		userInfo.put("type", "3");
		userInfo.put("headpic","http://dl.tclclouds.com/swift/v1/fzl_container/usercenter/20150811/13/57/55/RCiHtnLb4Q.jpg");
		userInfo.put("updateTime", "1409712934000");
		userInfo.put("accountType", "1");
		userInfo.put("sex", "男");
		Long result = shUserService.saveUserInfo(userInfo);
		System.out.println(result);*/
	}
	
	@Test
	public void auth() throws Exception {
		/*String result = appMinaService.ssoValidateAuth("184976888", "3b3643b0773cb0bbe8dee7a3756776cb", "1", "1");
		System.out.println(result);*/
	}
}
