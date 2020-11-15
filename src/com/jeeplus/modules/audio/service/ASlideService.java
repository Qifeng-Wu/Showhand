package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.ASlideDao;
import com.jeeplus.modules.audio.entity.ASlide;

/**
 * 幻灯片Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class ASlideService extends CrudService<ASlideDao, ASlide> {

	public ASlide get(String id) {
		return super.get(id);
	}
	
	public List<ASlide> findList(ASlide aSlide) {
		return super.findList(aSlide);
	}
	
	public Page<ASlide> findPage(Page<ASlide> page, ASlide aSlide) {
		return super.findPage(page, aSlide);
	}
	
	@Transactional(readOnly = false)
	public void save(ASlide aSlide) {
		super.save(aSlide);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(ASlide aSlide) {
		boolean isNewRecord = true;
		if (aSlide.getSlideId() != null){
			isNewRecord = false;		
		}
		aSlide.setIsNewRecord(isNewRecord);
		super.saveCustomId(aSlide);
		return aSlide.getSlideId();
	}
	
	@Transactional(readOnly = false)
	public void delete(ASlide aSlide) {
		super.delete(aSlide);
	}
	
}