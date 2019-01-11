package com.tianniu.service;

import com.tianniu.pojo.ResponseMessage;

public interface ILoginService {

	ResponseMessage login(String name, String pwd);

}
