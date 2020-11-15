/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryCartDao;
import com.jeeplus.modules.grocery.entity.GroceryCart;

/**
 * 购物车Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryCartService extends CrudService<GroceryCartDao, GroceryCart> {

	public GroceryCart get(String id) {
		return super.get(id);
	}
	
	public List<GroceryCart> findList(GroceryCart groceryCart) {
		return super.findList(groceryCart);
	}
	
	public Page<GroceryCart> findPage(Page<GroceryCart> page, GroceryCart groceryCart) {
		return super.findPage(page, groceryCart);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryCart groceryCart) {
		super.save(groceryCart);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryCart groceryCart) {
		boolean isNewRecord = true;
		if (groceryCart.getCartId() != null){
			isNewRecord = false;		
		}
		groceryCart.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryCart);
		return groceryCart.getCartId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryCart groceryCart) {
		super.delete(groceryCart);
	}
	
}