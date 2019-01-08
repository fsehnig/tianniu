package com.tianniu.mapper;
 
import java.util.List;

import com.tianniu.pojo.MemberOrder;

 
public interface MemberOrderMapper {
 
      
    public List<MemberOrder> list();

	public MemberOrder get(String openid);

	public List<MemberOrder> list(String openid);
    
}