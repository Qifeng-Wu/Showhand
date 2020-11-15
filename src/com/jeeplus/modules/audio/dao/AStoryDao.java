package com.jeeplus.modules.audio.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.AStory;

/**
 * 故事DAO接口
 * @author stephen
 * @version 2020-5-12
 */
@MyBatisDao
public interface AStoryDao extends CrudDao<AStory> {
	List<AStory> findAnotherList(AStory aStory);
	//获取N条推荐故事集合
	List<AStory> findRecommendList(Integer num);
	
}