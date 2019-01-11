package com.tianniu.service;

import com.tianniu.pojo.MemberConsumer;
import com.tianniu.pojo.MemberUser;
import com.tianniu.pojo.ResponseMessage;

public interface IMemberUserService {

	ResponseMessage getUserListByFlag(MemberUser user, int pnum);

	ResponseMessage getPage(String cardFlag);

	ResponseMessage updateUser(MemberUser user);

	ResponseMessage deleteUser(MemberUser user);

}
