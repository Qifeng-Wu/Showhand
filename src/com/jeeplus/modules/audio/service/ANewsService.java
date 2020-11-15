package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.ANewsDao;
import com.jeeplus.modules.audio.entity.ANews;

/**
 * 新闻资讯Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class ANewsService extends CrudService<ANewsDao, ANews> {
	
	public ANews get(String id) {
		return super.get(id);
	}
	
	public List<ANews> findList(ANews aNews) {
		return super.findList(aNews);
	}
	
	public Page<ANews> findPage(Page<ANews> page, ANews aNews) {
		return super.findPage(page, aNews);
	}
	
	@Transactional(readOnly = false)
	public void save(ANews aNews) {
		super.save(aNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(ANews aNews) {
		super.delete(aNews);
	}
	
}