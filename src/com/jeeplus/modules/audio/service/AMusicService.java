package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AMusicDao;
import com.jeeplus.modules.audio.entity.AMusic;

/**
 * 音乐Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AMusicService extends CrudService<AMusicDao, AMusic> {

	public AMusic get(String id) {
		return super.get(id);
	}
	
	public List<AMusic> findList(AMusic aMusic) {
		return super.findList(aMusic);
	}
	
	public Page<AMusic> findPage(Page<AMusic> page, AMusic aMusic) {
		return super.findPage(page, aMusic);
	}
	
	@Transactional(readOnly = false)
	public void save(AMusic aMusic) {
		super.save(aMusic);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AMusic aMusic) {
		boolean isNewRecord = true;
		if (aMusic.getMusicId() != null){
			isNewRecord = false;		
		}
		aMusic.setIsNewRecord(isNewRecord);
		super.saveCustomId(aMusic);
		return aMusic.getMusicId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AMusic aMusic) {
		super.delete(aMusic);
	}
	
}