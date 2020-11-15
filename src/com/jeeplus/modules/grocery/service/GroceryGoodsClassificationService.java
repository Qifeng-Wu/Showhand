/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryGoodsClassificationDao;
import com.jeeplus.modules.grocery.entity.GroceryGoodsClassification;

/**
 * 商品Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryGoodsClassificationService extends CrudService<GroceryGoodsClassificationDao, GroceryGoodsClassification> {

	public GroceryGoodsClassification get(String id) {
		return super.get(id);
	}
	
	public List<GroceryGoodsClassification> findList(GroceryGoodsClassification groceryGoodsClassification) {
		return super.findList(groceryGoodsClassification);
	}
	
	public Page<GroceryGoodsClassification> findPage(Page<GroceryGoodsClassification> page, GroceryGoodsClassification groceryGoodsClassification) {
		return super.findPage(page, groceryGoodsClassification);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryGoodsClassification groceryGoodsClassification) {
		super.save(groceryGoodsClassification);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GroceryGoodsClassification groceryGoodsClassification) {
		boolean isNewRecord = true;
		if (groceryGoodsClassification.getClassificationId() != null){
			isNewRecord = false;		
		}
		groceryGoodsClassification.setIsNewRecord(isNewRecord);
		super.saveCustomId(groceryGoodsClassification);
		return groceryGoodsClassification.getClassificationId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryGoodsClassification groceryGoodsClassification) {
		super.delete(groceryGoodsClassification);
	}
	
}