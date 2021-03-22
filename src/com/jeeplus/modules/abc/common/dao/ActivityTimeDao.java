package com.jeeplus.modules.abc.common.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.abc.common.entity.ActivityTime;

/**
 * 活动时间DAO接口
 * @author stephen
 * @version 2021-2-22
 */
@MyBatisDao
public interface ActivityTimeDao extends CrudDao<ActivityTime> {
	
}