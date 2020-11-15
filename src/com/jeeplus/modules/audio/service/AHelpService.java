package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AHelpDao;
import com.jeeplus.modules.audio.entity.AHelp;

/**
 * 帮助中心Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AHelpService extends CrudService<AHelpDao, AHelp> {
	
	public AHelp get(String id) {
		return super.get(id);
	}
	
	public List<AHelp> findList(AHelp aHelp) {
		return super.findList(aHelp);
	}
	
	public Page<AHelp> findPage(Page<AHelp> page, AHelp aHelp) {
		return super.findPage(page, aHelp);
	}
	
	@Transactional(readOnly = false)
	public void save(AHelp aHelp) {
		super.save(aHelp);
	}
	
	@Transactional(readOnly = false)
	public void delete(AHelp aHelp) {
		super.delete(aHelp);
	}
	
}