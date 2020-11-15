package com.jeeplus.modules.audio.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.ASlide;

/**
 * 幻灯片DAO接口
 * @author stephen
 * @version 2020-5-12
 */
@MyBatisDao
public interface ASlideDao extends CrudDao<ASlide> {
	
}