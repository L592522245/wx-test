package com.test.msg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.msg.domain.Msg;
import com.test.msg.mapper.MsgMapper;
import com.test.msg.service.MsgService;

@Service("msgService")  
public class MsgServiceImpl implements MsgService {
	@Autowired
	private MsgMapper msgMapper;
	
	@Override
	public void addMsg(Msg msg) {
		msgMapper.insert(msg);
	}

	@Override
	public void deleteMsg(String id) {
		msgMapper.delete(id);
	}

	@Override
	public void updateMsg(Msg msg) {
		msgMapper.update(msg);
	}

	@Override
	public List<Msg> getMsg() {
		return msgMapper.list();
	}

	@Override
	public Msg getMsgById(String id) {
		return msgMapper.getById(id);
	}

}
