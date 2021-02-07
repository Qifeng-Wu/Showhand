/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.audio.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AQuestionnaire;
import com.jeeplus.modules.audio.service.AQuestionnaireService;

/**
 * 回访问卷APIController
 * @author stephen
 * @version 2020-11-28
 */
@Controller
@RequestMapping(value = "a-api/questionnaire")
public class AQuestionnaireAPI extends BaseController {

	@Autowired
	private AQuestionnaireService aQuestionnaireService;
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(AQuestionnaire aQuestionnaire,HttpServletRequest request) {	
		String room = request.getParameter("room");
		String name = request.getParameter("name");
		String inspector = request.getParameter("inspector");
		String question1 = request.getParameter("question1");
		String question2 = request.getParameter("question2");
		String question3 = request.getParameter("question3");
		String question4 = request.getParameter("question4");
		String question5 = request.getParameter("question5");
		String question6 = request.getParameter("question6");
		String remark = request.getParameter("remark");
		AjaxJson ajax = new AjaxJson();	
		if(room==null||room.isEmpty()||name==null||name.isEmpty()||inspector==null||inspector.isEmpty()||
		   question1==null||question1.isEmpty()||question2==null||question2.isEmpty()||
		   question3==null||question3.isEmpty()||question4==null||question4.isEmpty()||
		   question5==null||question5.isEmpty()||question6==null||question6.isEmpty()){
			ajax.setSuccess(false);
			ajax.setMsg("提交参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		AQuestionnaire questionnaire = new AQuestionnaire();
		questionnaire.setRoom(room);
		List<AQuestionnaire> list = aQuestionnaireService.findList(questionnaire);
		if(list!=null && list.size()>0) {
			aQuestionnaire.setQuestionnaireId(list.get(0).getQuestionnaireId());
		}
		
		aQuestionnaire.setRoom(room);
		aQuestionnaire.setName(name);
		aQuestionnaire.setInspector(inspector);
		aQuestionnaire.setQuestion1(question1);
		aQuestionnaire.setQuestion2(question2);
		aQuestionnaire.setQuestion3(question3);
		aQuestionnaire.setQuestion4(question4);
		aQuestionnaire.setQuestion5(question5);
		aQuestionnaire.setQuestion6(question6);
		aQuestionnaire.setRemark(remark);
		aQuestionnaire.setCreateTime(new Date());
		aQuestionnaireService.customSave(aQuestionnaire);
		return ajax;
	}
}