/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.abc.questionnaire.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.abc.questionnaire.entity.Questionnaire;
import com.jeeplus.modules.abc.questionnaire.service.QuestionnaireService;

/**
 * 回访问卷APIController
 * @author stephen
 * @version 2020-11-28
 */
@Controller
@RequestMapping(value = "abc-api/questionnaire")
public class QuestionnaireAPI extends BaseController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(Questionnaire questionnaire,HttpServletRequest request) {	
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
		Questionnaire que = new Questionnaire();
		que.setRoom(room);
		List<Questionnaire> list = questionnaireService.findList(que);
		if(list!=null && list.size()>0) {
			questionnaire.setQuestionnaireId(list.get(0).getQuestionnaireId());
		}
		
		questionnaire.setRoom(room);
		questionnaire.setName(name);
		questionnaire.setInspector(inspector);
		questionnaire.setQuestion1(question1);
		questionnaire.setQuestion2(question2);
		questionnaire.setQuestion3(question3);
		questionnaire.setQuestion4(question4);
		questionnaire.setQuestion5(question5);
		questionnaire.setQuestion6(question6);
		questionnaire.setRemark(remark);
		questionnaire.setCreateTime(new Date());
		questionnaireService.customSave(questionnaire);
		return ajax;
	}
}