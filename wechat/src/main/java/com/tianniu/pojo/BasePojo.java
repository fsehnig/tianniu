package com.tianniu.pojo;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePojo<T> implements Serializable{
	
	private static long serialVersion = 1L;

}
