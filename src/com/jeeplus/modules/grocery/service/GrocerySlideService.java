/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GrocerySlideDao;
import com.jeeplus.modules.grocery.entity.GrocerySlide;

/**
 * 幻灯片Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GrocerySlideService extends CrudService<GrocerySlideDao, GrocerySlide> {

	public GrocerySlide get(String id) {
		return super.get(id);
	}
	
	public List<GrocerySlide> findList(GrocerySlide grocerySlide) {
		return super.findList(grocerySlide);
	}
	
	public Page<GrocerySlide> findPage(Page<GrocerySlide> page, GrocerySlide grocerySlide) {
		return super.findPage(page, grocerySlide);
	}
	
	@Transactional(readOnly = false)
	public void save(GrocerySlide grocerySlide) {
		super.save(grocerySlide);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(GrocerySlide grocerySlide) {
		boolean isNewRecord = true;
		if (grocerySlide.getSlideId() != null){
			isNewRecord = false;		
		}
		grocerySlide.setIsNewRecord(isNewRecord);
		super.saveCustomId(grocerySlide);
		return grocerySlide.getSlideId();
	}
	
	@Transactional(readOnly = false)
	public void delete(GrocerySlide grocerySlide) {
		super.delete(grocerySlide);
	}
	
}