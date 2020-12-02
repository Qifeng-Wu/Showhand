package com.jeeplus.modules.audio.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.audio.entity.AQuestionnaire;

/**
 * 问卷调查DAO接口
 * @author stephen
 * @version 2020-11-28
 */
@MyBatisDao
public interface AQuestionnaireDao extends CrudDao<AQuestionnaire> {
	
}