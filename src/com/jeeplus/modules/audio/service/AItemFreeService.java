package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AItemFreeDao;
import com.jeeplus.modules.audio.entity.AItemFree;

/**
 * 用户免费项目Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AItemFreeService extends CrudService<AItemFreeDao, AItemFree> {

	public AItemFree get(String id) {
		return super.get(id);
	}
	
	public List<AItemFree> findList(AItemFree aItemFree) {
		return super.findList(aItemFree);
	}
	
	public Page<AItemFree> findPage(Page<AItemFree> page, AItemFree aItemFree) {
		return super.findPage(page, aItemFree);
	}
	
	@Transactional(readOnly = false)
	public void save(AItemFree aItemFree) {
		super.save(aItemFree);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AItemFree aItemFree) {
		boolean isNewRecord = true;
		if (aItemFree.getFreeId() != null){
			isNewRecord = false;		
		}
		aItemFree.setIsNewRecord(isNewRecord);
		super.saveCustomId(aItemFree);
		return aItemFree.getFreeId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AItemFree aItemFree) {
		super.delete(aItemFree);
	}
	
}