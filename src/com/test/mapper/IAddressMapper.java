package com.test.mapper;

import java.util.List;

import com.test.pojo.Address;

public interface IAddressMapper {
	
	void insert(Address address);
	void delete(int id);
	Address getById(int id);
	void update(Address address);
	List<Address> list();
	List<Address> country();
	List<Address> area(int parentId);
}
