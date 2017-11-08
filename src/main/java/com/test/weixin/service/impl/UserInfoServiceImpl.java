package com.test.weixin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.weixin.domain.userInfo.UserInfo;
import com.test.weixin.mapper.UserInfoMapper;
import com.test.weixin.service.UserInfoService;

@Service("userInfoService")  
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public void addUserInfo(UserInfo userInfo) {
		userInfoMapper.insert(userInfo);
	}

	@Override
	public void deleteUserInfo(String openid) {
		userInfoMapper.delete(openid);
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoMapper.update(userInfo);
	}

	@Override
	public List<UserInfo> getUserInfo() {
		return userInfoMapper.list();
	}

	@Override
	public List queryList(Map map) {
		return userInfoMapper.queryList(map);
	}

	@Override
	public UserInfo getUserInfoById(String openid) {
		return userInfoMapper.getById(openid);
	}

}
