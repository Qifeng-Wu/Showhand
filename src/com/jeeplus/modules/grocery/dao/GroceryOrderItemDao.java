/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.grocery.entity.GroceryOrderItem;

/**
 * 订单商品详情DAO接口
 * @author stephen
 * @version 2019-10-25
 */
@MyBatisDao
public interface GroceryOrderItemDao extends CrudDao<GroceryOrderItem> {
	
}