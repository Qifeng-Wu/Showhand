/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryFansDao;
import com.jeeplus.modules.grocery.entity.GroceryFans;

/**
 * 微信粉丝Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryFansService extends CrudService<GroceryFansDao, GroceryFans> {

	public GroceryFans get(String id) {
		return super.get(id);
	}
	
	public List<GroceryFans> findList(GroceryFans groceryFans) {
		return super.findList(groceryFans);
	}
	
	public Page<GroceryFans> findPage(Page<GroceryFans> page, GroceryFans groceryFans) {
		return super.findPage(page, groceryFans);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryFans groceryFans) {
		super.save(groceryFans);
	}
	
	@Transactional(readOnly = false)
	public String customSave(GroceryFans groceryFans) {
		boolean isNewRecord = true;
		if (groceryFans.getOpenId() != null){
			isNewRecord = false;		
		}
		groceryFans.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryFans);
		return groceryFans.getOpenId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryFans groceryFans) {
		super.delete(groceryFans);
	}
	
}