package com.test.weixin.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.test.weixin.domain.userInfo.UserInfo;

@Repository
public interface UserInfoMapper {
	void insert(UserInfo userInfo);
	void delete(String openid);
	void update(UserInfo userInfo);
	List<UserInfo> list();
	UserInfo getById(String openid);
	List queryList(Map map);
}
