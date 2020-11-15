package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.ATouchDao;
import com.jeeplus.modules.audio.entity.ATouch;

/**
 * 触屏操作Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class ATouchService extends CrudService<ATouchDao, ATouch> {

	public ATouch get(String id) {
		return super.get(id);
	}
	
	public List<ATouch> findList(ATouch aTouch) {
		return super.findList(aTouch);
	}
	
	public Page<ATouch> findPage(Page<ATouch> page, ATouch aTouch) {
		return super.findPage(page, aTouch);
	}
	
	@Transactional(readOnly = false)
	public void save(ATouch aTouch) {
		super.save(aTouch);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(ATouch aTouch) {
		boolean isNewRecord = true;
		if (aTouch.getTouchId() != null){
			isNewRecord = false;		
		}
		aTouch.setIsNewRecord(isNewRecord);
		super.saveCustomId(aTouch);
		return aTouch.getTouchId();
	}
	
	@Transactional(readOnly = false)
	public void delete(ATouch aTouch) {
		super.delete(aTouch);
	}
	
}