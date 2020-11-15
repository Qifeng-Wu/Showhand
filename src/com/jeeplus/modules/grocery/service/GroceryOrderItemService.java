/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryOrderItemDao;
import com.jeeplus.modules.grocery.entity.GroceryOrderItem;

/**
 * 订单商品详情Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryOrderItemService extends CrudService<GroceryOrderItemDao, GroceryOrderItem> {

	public GroceryOrderItem get(String id) {
		return super.get(id);
	}
	
	public List<GroceryOrderItem> findList(GroceryOrderItem groceryOrderItem) {
		return super.findList(groceryOrderItem);
	}
	
	public Page<GroceryOrderItem> findPage(Page<GroceryOrderItem> page, GroceryOrderItem groceryOrderItem) {
		return super.findPage(page, groceryOrderItem);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryOrderItem groceryOrderItem) {
		super.save(groceryOrderItem);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryOrderItem groceryOrderItem) {
		boolean isNewRecord = true;
		if (groceryOrderItem.getItemId() != null){
			isNewRecord = false;		
		}
		groceryOrderItem.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryOrderItem);
		return groceryOrderItem.getItemId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryOrderItem groceryOrderItem) {
		super.delete(groceryOrderItem);
	}
	
}