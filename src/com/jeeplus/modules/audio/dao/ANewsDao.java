package com.jeeplus.modules.audio.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.ANews;

/**
 * 新闻资讯DAO接口
 * @author stephen
 * @version 2020-5-12
 */
@MyBatisDao
public interface ANewsDao extends CrudDao<ANews> {
	
}