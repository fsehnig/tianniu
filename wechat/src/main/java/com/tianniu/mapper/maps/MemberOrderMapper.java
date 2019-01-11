package com.tianniu.mapper.maps;
 
import java.util.List;

import com.tianniu.pojo.MemberOrder;

 
public interface MemberOrderMapper {
 
      
	public MemberOrder get(String openid);

	public List<MemberOrder> list(String openid);
    
}