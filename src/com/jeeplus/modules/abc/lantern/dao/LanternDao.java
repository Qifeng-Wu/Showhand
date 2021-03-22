package com.jeeplus.modules.abc.lantern.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.abc.lantern.entity.Lantern;

/**
 * 元宵节猜灯谜DAO接口
 * @author stephen
 * @version 2021-2-22
 */
@MyBatisDao
public interface LanternDao extends CrudDao<Lantern> {
	
}