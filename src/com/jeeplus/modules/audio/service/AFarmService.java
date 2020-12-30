package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AFarmDao;
import com.jeeplus.modules.audio.entity.AFarm;

/**
 * 家庭农场Service
 * @author stephen
 * @version 2020-12-25
 */
@Service
@Transactional(readOnly = true)
public class AFarmService extends CrudService<AFarmDao, AFarm> {

	public AFarm get(String id) {
		return super.get(id);
	}
	
	public List<AFarm> findList(AFarm aFarm) {
		return super.findList(aFarm);
	}
	
	public Page<AFarm> findPage(Page<AFarm> page, AFarm aFarm) {
		return super.findPage(page, aFarm);
	}
	
	@Transactional(readOnly = false)
	public void save(AFarm aFarm) {
		super.save(aFarm);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AFarm aFarm) {
		boolean isNewRecord = true;
		if (aFarm.getFarmId() != null){
			isNewRecord = false;		
		}
		aFarm.setIsNewRecord(isNewRecord);
		super.saveCustomId(aFarm);
		return aFarm.getFarmId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AFarm aFarm) {
		super.delete(aFarm);
	}
	
}