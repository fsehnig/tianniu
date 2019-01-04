package com.tcl.shbc.bussiness.sqlmaps.uc;

import com.tcl.shbc.bussiness.entity.uc.Message;
import com.tcl.shbc.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface MessageMapper {
	public void insertMessage(Message m);
}
