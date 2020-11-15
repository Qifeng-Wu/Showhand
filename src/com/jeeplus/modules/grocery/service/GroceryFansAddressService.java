/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryFansAddressDao;
import com.jeeplus.modules.grocery.entity.GroceryFansAddress;

/**
 * 收货地址Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryFansAddressService extends CrudService<GroceryFansAddressDao, GroceryFansAddress> {
	@Autowired
	private GroceryFansAddressDao groceryFansAddressDao;
	
	public GroceryFansAddress get(String id) {
		return super.get(id);
	}
	
	public List<GroceryFansAddress> findList(GroceryFansAddress groceryFansAddress) {
		return super.findList(groceryFansAddress);
	}
	
	public Page<GroceryFansAddress> findPage(Page<GroceryFansAddress> page, GroceryFansAddress groceryFansAddress) {
		return super.findPage(page, groceryFansAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryFansAddress groceryFansAddress) {
		super.save(groceryFansAddress);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryFansAddress groceryFansAddress) {
		boolean isNewRecord = true;
		if (groceryFansAddress.getAddressId() != null){
			isNewRecord = false;		
		}
		groceryFansAddress.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryFansAddress);
		return groceryFansAddress.getAddressId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryFansAddress groceryFansAddress) {
		super.delete(groceryFansAddress);
	}
	
	@Transactional(readOnly = false)
	public void settingAddressStatus(GroceryFansAddress groceryFansAddress) {
		groceryFansAddressDao.settingAddressStatus(groceryFansAddress);
	}
}