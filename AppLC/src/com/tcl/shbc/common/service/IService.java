/**
 * 
 */
package com.tcl.shbc.common.service;

/**
 * @author zsj
 *
 */
public interface IService {
	
	/**
	 * service主处理
	 * @param param
	 * @return
	 */
	Object execute(Object param);
	
	/**
	 * service所对应的命令
	 * @return
	 */
	String getCmd();
}
