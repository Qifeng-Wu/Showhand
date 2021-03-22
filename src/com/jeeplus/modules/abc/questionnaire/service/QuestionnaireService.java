package com.jeeplus.modules.abc.questionnaire.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.abc.questionnaire.dao.QuestionnaireDao;
import com.jeeplus.modules.abc.questionnaire.entity.Questionnaire;

/**
 * 问卷调查Service
 * @author stephen
 * @version 2020-11-28
 */
@Service
@Transactional(readOnly = true)
public class QuestionnaireService extends CrudService<QuestionnaireDao, Questionnaire> {

	public Questionnaire get(String id) {
		return super.get(id);
	}
	
	public List<Questionnaire> findList(Questionnaire questionnaire) {
		return super.findList(questionnaire);
	}
	
	public Page<Questionnaire> findPage(Page<Questionnaire> page, Questionnaire questionnaire) {
		return super.findPage(page, questionnaire);
	}
	
	@Transactional(readOnly = false)
	public void save(Questionnaire questionnaire) {
		super.save(questionnaire);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(Questionnaire questionnaire) {
		boolean isNewRecord = true;
		if (questionnaire.getQuestionnaireId() != null){
			isNewRecord = false;		
		}
		questionnaire.setIsNewRecord(isNewRecord);
		super.saveCustomId(questionnaire);
		return questionnaire.getQuestionnaireId();
	}
	
	@Transactional(readOnly = false)
	public void delete(Questionnaire questionnaire) {
		super.delete(questionnaire);
	}
	
}