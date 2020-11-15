/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryFreightDao;
import com.jeeplus.modules.grocery.entity.GroceryFreight;

/**
 * 运费Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryFreightService extends CrudService<GroceryFreightDao, GroceryFreight> {

	public GroceryFreight get(String id) {
		return super.get(id);
	}
	
	public List<GroceryFreight> findList(GroceryFreight groceryFreight) {
		return super.findList(groceryFreight);
	}
	
	public Page<GroceryFreight> findPage(Page<GroceryFreight> page, GroceryFreight groceryFreight) {
		return super.findPage(page, groceryFreight);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryFreight groceryFreight) {
		super.save(groceryFreight);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryFreight groceryFreight) {
		boolean isNewRecord = true;
		if (groceryFreight.getFreightId() != null){
			isNewRecord = false;		
		}
		groceryFreight.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryFreight);
		return groceryFreight.getFreightId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryFreight groceryFreight) {
		super.delete(groceryFreight);
	}
	
}