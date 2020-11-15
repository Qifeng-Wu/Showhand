/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryPlatformDao;
import com.jeeplus.modules.grocery.entity.GroceryPlatform;

/**
 * 平台信息Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryPlatformService extends CrudService<GroceryPlatformDao, GroceryPlatform> {

	public GroceryPlatform get(String id) {
		return super.get(id);
	}
	
	public List<GroceryPlatform> findList(GroceryPlatform groceryPlatform) {
		return super.findList(groceryPlatform);
	}
	
	public Page<GroceryPlatform> findPage(Page<GroceryPlatform> page, GroceryPlatform groceryPlatform) {
		return super.findPage(page, groceryPlatform);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryPlatform groceryPlatform) {
		super.save(groceryPlatform);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryPlatform groceryPlatform) {
		boolean isNewRecord = true;
		if (groceryPlatform.getPlatformId() != null){
			isNewRecord = false;		
		}
		groceryPlatform.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryPlatform);
		return groceryPlatform.getPlatformId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryPlatform groceryPlatform) {
		super.delete(groceryPlatform);
	}
	
}