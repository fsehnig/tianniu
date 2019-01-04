package com.tcl.shbc.mina.common;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 将system.properties注入到SystemConfig类
 * @author lenovo
 *
 */
@Component
public class SystemConfig {
	@Value("#{systemCfg}")  
	private Properties properties;
	
	/**
	 * 根据key获取system.properties对应的value
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		String value = properties.getProperty(key);
		return value;
	}
}
