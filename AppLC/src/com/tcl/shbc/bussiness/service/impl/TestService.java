/**
 * 
 */
package com.tcl.shbc.bussiness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcl.shbc.bussiness.service.ITestService;
import com.tcl.shbc.common.cache.RedisConn;

/**
 * @author zsj
 *
 */
@Service
public class TestService implements ITestService {
	
	@Autowired
	private RedisConn rConn;

	/* (non-Javadoc)
	 * @see com.tcl.shbc.common.service.IService#execute(java.lang.Object)
	 */
	@Override
	public Object execute(Object param) {
		// TODO Auto-generated method stub
		String v = rConn.STRINGS.get("testkey");
		
		System.out.println(v);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tcl.shbc.common.service.IService#getCmd()
	 */
	@Override
	public String getCmd() {
		// TODO Auto-generated method stub
		return "test";
	}

}
