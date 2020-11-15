package com.jeeplus.modules.audio.dao;

import java.util.Date;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.AChapter;

/**
 * 章DAO接口
 * @author stephen
 * @version 2020-5-12
 */
@MyBatisDao
public interface AChapterDao extends CrudDao<AChapter> {
	//获取该故事下最近章回跟新时间
	Date findLastUpdateTime(Integer storyId);
}