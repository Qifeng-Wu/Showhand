package com.jeeplus.modules.grocery.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.grocery.entity.GroceryFans;

/**
 * 微信信息DAO接口
 * @author stephen
 * @version 2019-10-25
 */
@MyBatisDao
public interface GroceryFansDao extends CrudDao<GroceryFans> {
	
}