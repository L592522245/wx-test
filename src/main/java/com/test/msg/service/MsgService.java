package com.test.msg.service;

import java.util.List;

import com.test.msg.domain.Msg;

public interface MsgService {
	void addMsg(Msg msg);
	void deleteMsg(String id);
	void updateMsg(Msg msg);
	List<Msg> getMsg();
	Msg getMsgById(String id);
}
