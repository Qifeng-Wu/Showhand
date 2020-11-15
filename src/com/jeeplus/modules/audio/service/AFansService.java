package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AFansDao;
import com.jeeplus.modules.audio.entity.AFans;

/**
 * 微信粉丝Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AFansService extends CrudService<AFansDao, AFans> {

	public AFans get(String id) {
		return super.get(id);
	}
	
	public List<AFans> findList(AFans aFans) {
		return super.findList(aFans);
	}
	
	public Page<AFans> findPage(Page<AFans> page, AFans aFans) {
		return super.findPage(page, aFans);
	}
	
	@Transactional(readOnly = false)
	public void save(AFans aFans) {
		super.save(aFans);
	}
	
	@Transactional(readOnly = false)
	public String customSave(AFans aFans) {
		boolean isNewRecord = true;
		if (aFans.getOpenId() != null){
			isNewRecord = false;		
		}
		aFans.setIsNewRecord(isNewRecord);
		super.saveCustomId(aFans);
		return aFans.getOpenId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AFans aFans) {
		super.delete(aFans);
	}
}