package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AQuestionnaireDao;
import com.jeeplus.modules.audio.entity.AQuestionnaire;

/**
 * 问卷调查Service
 * @author stephen
 * @version 2020-11-28
 */
@Service
@Transactional(readOnly = true)
public class AQuestionnaireService extends CrudService<AQuestionnaireDao, AQuestionnaire> {

	public AQuestionnaire get(String id) {
		return super.get(id);
	}
	
	public List<AQuestionnaire> findList(AQuestionnaire aQuestionnaire) {
		return super.findList(aQuestionnaire);
	}
	
	public Page<AQuestionnaire> findPage(Page<AQuestionnaire> page, AQuestionnaire aQuestionnaire) {
		return super.findPage(page, aQuestionnaire);
	}
	
	@Transactional(readOnly = false)
	public void save(AQuestionnaire aQuestionnaire) {
		super.save(aQuestionnaire);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AQuestionnaire aQuestionnaire) {
		boolean isNewRecord = true;
		if (aQuestionnaire.getQuestionnaireId() != null){
			isNewRecord = false;		
		}
		aQuestionnaire.setIsNewRecord(isNewRecord);
		super.saveCustomId(aQuestionnaire);
		return aQuestionnaire.getQuestionnaireId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AQuestionnaire aQuestionnaire) {
		super.delete(aQuestionnaire);
	}
	
}