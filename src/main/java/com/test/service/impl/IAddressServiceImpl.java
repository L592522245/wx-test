package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.mapper.IAddressMapper;
import com.test.pojo.Address;
import com.test.service.IAddressService;

@Service("addressService")  // @Service(@Component)指定该bean的id
@Transactional              // 事务管理，当对数据库执行语句发生错误时，事物将回滚，所有语句都将不执行
public class IAddressServiceImpl implements IAddressService {
	@Autowired  
	private IAddressMapper mapper;

	public void addAddress(Address address) {
		mapper.insert(address);
	}

	@Override
	public List<Address> queryList() {
		// TODO Auto-generated method stub
		return mapper.list();
	}

	@Override
	public List<Address> getCountry() {
		// TODO Auto-generated method stub
		return mapper.country();
	}

	@Override
	public List<Address> getArea(int parentId) {
		// TODO Auto-generated method stub
		return mapper.area(parentId);
	}

	
}
