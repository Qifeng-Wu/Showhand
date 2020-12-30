package com.jeeplus.modules.audio.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.AFarm;

/**
 * 家庭农场DAO接口
 * @author stephen
 * @version 2020-12-25
 */
@MyBatisDao
public interface AFarmDao extends CrudDao<AFarm> {
	
}