package com.tianniu.mapper.maps;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianniu.pojo.User;
@Repository
public interface LoginMapper {

	List<User> getUserByName(String name);
	
}
