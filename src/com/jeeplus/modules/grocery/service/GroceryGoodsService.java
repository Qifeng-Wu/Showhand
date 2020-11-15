/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryGoodsDao;
import com.jeeplus.modules.grocery.entity.GroceryGoods;

/**
 * 商品Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryGoodsService extends CrudService<GroceryGoodsDao, GroceryGoods> {

	public GroceryGoods get(String id) {
		return super.get(id);
	}
	
	public List<GroceryGoods> findList(GroceryGoods groceryGoods) {
		return super.findList(groceryGoods);
	}
	
	public Page<GroceryGoods> findPage(Page<GroceryGoods> page, GroceryGoods groceryGoods) {
		return super.findPage(page, groceryGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryGoods groceryGoods) {
		super.save(groceryGoods);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryGoods groceryGoods) {
		boolean isNewRecord = true;
		if (groceryGoods.getGoodsId() != null){
			isNewRecord = false;		
		}
		groceryGoods.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryGoods);
		return groceryGoods.getGoodsId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryGoods groceryGoods) {
		super.delete(groceryGoods);
	}
	
}