package com.jeeplus.modules.audio.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.AFeedback;

/**
 * 客户意见反馈DAO接口
 * @author stephen
 * @version 2020-5-12
 */
@MyBatisDao
public interface AFeedbackDao extends CrudDao<AFeedback> {
	
}