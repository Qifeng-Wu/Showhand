/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.audio.api;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AFeedback;
import com.jeeplus.modules.audio.service.AFeedbackService;

/**
 * 客户意见反馈APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/feedback")
public class AFeedbackAPI extends BaseController {

	@Autowired
	private AFeedbackService aFeedbackService;
	
	/**
	 * 客户意见反馈信息保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(AFeedback aFeedback,HttpServletRequest request) {	
		String openId = request.getParameter("openId");
		String type = request.getParameter("type");
		String description = request.getParameter("description");
		String pictures = request.getParameter("pictures");
		AjaxJson ajax = new AjaxJson();	
		if(openId==null||openId.isEmpty()){
			ajax.setSuccess(false);
			ajax.setMsg("提交参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		
		aFeedback.setOpenId(openId);
		aFeedback.setType(type);
		aFeedback.setDescription(description);
		aFeedback.setPictures(pictures);
		aFeedback.setCreateTime(new Date());

		aFeedbackService.save(aFeedback);
		return ajax;
	}
	
	/**
	 * 客户获取自己提交的反馈集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public synchronized AjaxJson findList(AFeedback aFeedback,HttpServletRequest request) {	
		String openId = request.getParameter("openId");
		AjaxJson ajax = new AjaxJson();	
		if(openId==null||openId.isEmpty()){
			ajax.setSuccess(false);
			ajax.setMsg("提交参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}	
		aFeedback.setOpenId(openId);
		List<AFeedback> list = aFeedbackService.findList(aFeedback);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);		
		ajax.setBody(body);
		return ajax;
	}
}