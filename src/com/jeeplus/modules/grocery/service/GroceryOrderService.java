/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryOrderDao;
import com.jeeplus.modules.grocery.entity.GroceryOrder;

/**
 * 订单Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryOrderService extends CrudService<GroceryOrderDao, GroceryOrder> {

	public GroceryOrder get(String id) {
		return super.get(id);
	}
	
	public List<GroceryOrder> findList(GroceryOrder groceryOrder) {
		return super.findList(groceryOrder);
	}
	
	public Page<GroceryOrder> findPage(Page<GroceryOrder> page, GroceryOrder groceryOrder) {
		return super.findPage(page, groceryOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryOrder groceryOrder) {
		super.save(groceryOrder);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryOrder groceryOrder) {
		boolean isNewRecord = true;
		if (groceryOrder.getOrderId() != null){
			isNewRecord = false;		
		}
		groceryOrder.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryOrder);
		return groceryOrder.getOrderId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryOrder groceryOrder) {
		super.delete(groceryOrder);
	}
	
}