package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AStoryDao;
import com.jeeplus.modules.audio.entity.AStory;

/**
 * 故事Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AStoryService extends CrudService<AStoryDao, AStory> {
	@Autowired
	private AStoryDao aStoryDao;

	public AStory get(String id) {
		return super.get(id);
	}
	
	public List<AStory> findList(AStory aStory) {
		return super.findList(aStory);
	}
	
	public Page<AStory> findPage(Page<AStory> page, AStory aStory) {
		return super.findPage(page, aStory);
	}
	
	//获取未分配的故事信息集合
	public Page<AStory> findAnotherPage(Page<AStory> page, AStory aStory) {
		aStory.setPage(page);
		page.setList(aStoryDao.findAnotherList(aStory));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(AStory aStory) {
		super.save(aStory);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AStory aStory) {
		boolean isNewRecord = true;
		if (aStory.getStoryId() != null){
			isNewRecord = false;		
		}
		aStory.setIsNewRecord(isNewRecord);
		super.saveCustomId(aStory);
		return aStory.getStoryId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AStory aStory) {
		super.delete(aStory);
	}
	
	/**
	 * 获取N条推荐故事集合
	 */
	public List<AStory> findRecommendList(Integer num) {	
		return aStoryDao.findRecommendList(num);
	}
	
}