package com.jeeplus.modules.abc.lantern.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.abc.lantern.dao.LanternDao;
import com.jeeplus.modules.abc.lantern.entity.Lantern;

/**
 * 元宵节猜灯谜Service
 * @author stephen
 * @version 2021-2-22
 */
@Service
@Transactional(readOnly = true)
public class LanternService extends CrudService<LanternDao, Lantern> {

	public Lantern get(String id) {
		return super.get(id);
	}
	
	public List<Lantern> findList(Lantern lantern) {
		return super.findList(lantern);
	}
	
	public Page<Lantern> findPage(Page<Lantern> page, Lantern lantern) {
		return super.findPage(page, lantern);
	}
	
	@Transactional(readOnly = false)
	public void save(Lantern Lantern) {
		super.save(Lantern);
	}
	
	@Transactional(readOnly = false)
	public String customSave(Lantern lantern) {
		boolean isNewRecord = true;
		if (lantern.getOpenId() != null){
			isNewRecord = false;		
		}
		lantern.setIsNewRecord(isNewRecord);
		super.saveCustomId(lantern);
		return lantern.getOpenId();
	}
	
	@Transactional(readOnly = false)
	public void delete(Lantern lantern) {
		super.delete(lantern);
	}
	
}