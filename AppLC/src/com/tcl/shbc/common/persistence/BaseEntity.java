/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tcl.shbc.common.persistence;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Entity支持类
 * @author zsj
 * @version 2013-01-15
 */
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	
}
