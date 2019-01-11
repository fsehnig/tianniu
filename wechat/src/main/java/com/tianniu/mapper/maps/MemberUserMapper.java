package com.tianniu.mapper.maps;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tianniu.pojo.MemberUser;
import com.tianniu.vo.MemberUserVO;

@Repository
public interface MemberUserMapper {

	int getUserCount(String cardFlag);

	List<MemberUserVO> getUserListByFlag(@Param(value = "user") MemberUser user, 
			@Param(value = "start") int start, @Param(value = "end") int end);
	
	Integer updateByPrimaryKeySelective(MemberUser user);
	
	
	Integer deleteByPrimaryKey(String openid);

	Integer deleteByOpenid(String openid);

	int getUserNoDelete();

	int getUserWithCard();

	int getUserNoCard();

	int getUserDelete();

	int getNoLoad();
}
