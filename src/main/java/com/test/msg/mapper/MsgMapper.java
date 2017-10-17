package com.test.msg.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.msg.domain.Msg;

@Repository
public interface MsgMapper  {
	void insert(Msg msg);
	void delete(String id);
	void update(Msg msg);
	List<Msg> list();
	Msg getById(String id);
}
