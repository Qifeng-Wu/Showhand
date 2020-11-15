package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AFeedbackDao;
import com.jeeplus.modules.audio.entity.AFeedback;

/**
 * 客户意见反馈Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AFeedbackService extends CrudService<AFeedbackDao, AFeedback> {
		
	public AFeedback get(String id) {
		return super.get(id);
	}
	
	public List<AFeedback> findList(AFeedback aFeedback) {
		return super.findList(aFeedback);
	}
	
	public Page<AFeedback> findPage(Page<AFeedback> page, AFeedback aFeedback) {
		return super.findPage(page, aFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(AFeedback aFeedback) {
		super.save(aFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(AFeedback aFeedback) {
		super.delete(aFeedback);
	}

}