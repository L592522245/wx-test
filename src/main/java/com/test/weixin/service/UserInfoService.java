package com.test.weixin.service;

import java.util.List;
import java.util.Map;

import com.test.weixin.domain.userInfo.UserInfo;

public interface UserInfoService {
	void addUserInfo(UserInfo userInfo);
	void deleteUserInfo(String openid);
	void updateUserInfo(UserInfo userInfo);
	List<UserInfo> getUserInfo();
	UserInfo getUserInfoById(String openid);
	List queryList(Map map);
}
