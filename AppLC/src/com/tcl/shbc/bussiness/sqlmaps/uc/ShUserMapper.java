package com.tcl.shbc.bussiness.sqlmaps.uc;

import com.tcl.shbc.bussiness.entity.uc.ShUser;
import com.tcl.shbc.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ShUserMapper{
    int insertSelective(ShUser record);
    ShUser getShUserByUserId(Long userId);
    int updateByUserIdSelective(ShUser record);

}