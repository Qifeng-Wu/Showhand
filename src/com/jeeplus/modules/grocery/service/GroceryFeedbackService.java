/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.grocery.dao.GroceryFeedbackDao;
import com.jeeplus.modules.grocery.entity.GroceryFeedback;


/**
 * 客户意见反馈Service
 * @author stephen
 * @version 2019-10-25
 */
@Service
@Transactional(readOnly = true)
public class GroceryFeedbackService extends CrudService<GroceryFeedbackDao, GroceryFeedback> {
		
	public GroceryFeedback get(String id) {
		return super.get(id);
	}
	
	public List<GroceryFeedback> findList(GroceryFeedback groceryFeedback) {
		return super.findList(groceryFeedback);
	}
	
	public Page<GroceryFeedback> findPage(Page<GroceryFeedback> page, GroceryFeedback groceryFeedback) {
		return super.findPage(page, groceryFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(GroceryFeedback groceryFeedback) {
		super.save(groceryFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(GroceryFeedback groceryFeedback) {
		super.delete(groceryFeedback);
	}

}