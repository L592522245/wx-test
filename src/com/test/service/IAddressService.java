package com.test.service;

import java.util.List;

import com.test.pojo.Address;

public interface IAddressService {
	
	void addAddress(Address address);
	List<Address> queryList();
	List<Address> getCountry();
	List<Address> getArea(int parentId);
}
