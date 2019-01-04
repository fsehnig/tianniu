/**
 * 
 */
package com.tcl.shbc.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zsj
 *
 */
@Component
@Order(100)
public class ServiceMap extends HashMap<String, IService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5535125159841696191L;
	@Autowired
	ApplicationContext act;
	
	@PostConstruct
	public void PostConstruct() {
		Map<String, IService> serviceMap = act.getBeansOfType(IService.class);
		for (Map.Entry<String, IService> entry : serviceMap.entrySet()) {
			IService service = entry.getValue();
			this.put(service.getCmd(), service);
		}
	}
}
