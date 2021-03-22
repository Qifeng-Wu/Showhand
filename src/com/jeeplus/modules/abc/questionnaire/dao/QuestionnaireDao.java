package com.jeeplus.modules.abc.questionnaire.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.abc.questionnaire.entity.Questionnaire;

/**
 * 问卷调查DAO接口
 * @author stephen
 * @version 2020-11-28
 */
@MyBatisDao
public interface QuestionnaireDao extends CrudDao<Questionnaire> {
	
}