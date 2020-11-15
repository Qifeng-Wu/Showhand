package com.jeeplus.modules.audio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AChapterDao;
import com.jeeplus.modules.audio.entity.AChapter;

/**
 * 章Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AChapterService extends CrudService<AChapterDao, AChapter> {
	@Autowired
	private AChapterDao aChapterDao;

	public AChapter get(String id) {
		return super.get(id);
	}
	
	public List<AChapter> findList(AChapter aChapter) {
		return super.findList(aChapter);
	}
	
	public Page<AChapter> findPage(Page<AChapter> page, AChapter aChapter) {
		return super.findPage(page, aChapter);
	}
	
	@Transactional(readOnly = false)
	public void save(AChapter aChapter) {
		super.save(aChapter);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AChapter aChapter) {
		boolean isNewRecord = true;
		if (aChapter.getChapterId() != null){
			isNewRecord = false;		
		}
		aChapter.setIsNewRecord(isNewRecord);
		super.saveCustomId(aChapter);
		return aChapter.getChapterId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AChapter aChapter) {
		super.delete(aChapter);
	}
	
	//获取该故事下最近章回跟新时间
	public Date findLastUpdateTime(Integer storyId) {
		return aChapterDao.findLastUpdateTime(storyId);
	}
}