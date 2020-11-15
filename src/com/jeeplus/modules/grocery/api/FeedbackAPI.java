/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.grocery.entity.GroceryFeedback;
import com.jeeplus.modules.grocery.service.GroceryFeedbackService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 客户意见反馈APIController
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "g-api/feedback")
public class FeedbackAPI extends BaseController {

	@Autowired
	private GroceryFeedbackService groceryFeedbackService;
	
	@ModelAttribute
	public GroceryFeedback get(@RequestParam(required=false) String id) {
		GroceryFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFeedbackService.get(id);
		}
		if (entity == null){
			entity = new GroceryFeedback();
		}
		return entity;
	}
	
	/**
	 * 客户意见反馈信息保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(GroceryFeedback groceryFeedback,HttpServletRequest request) {	
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
		
		groceryFeedback.setOpenId(openId);
		groceryFeedback.setType(type);
		groceryFeedback.setDescription(description);
		groceryFeedback.setPictures(pictures);
		groceryFeedback.setCreateTime(new Date());

		groceryFeedbackService.save(groceryFeedback);
		return ajax;
	}
	
	/**
	 * 客户获取自己提交的反馈集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public synchronized AjaxJson findList(GroceryFeedback groceryFeedback,HttpServletRequest request) {	
		String openId = request.getParameter("openId");
		AjaxJson ajax = new AjaxJson();	
		if(openId==null||openId.isEmpty()){
			ajax.setSuccess(false);
			ajax.setMsg("提交参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}	
		groceryFeedback.setOpenId(openId);
		List<GroceryFeedback> list = groceryFeedbackService.findList(groceryFeedback);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);		
		ajax.setBody(body);
		return ajax;
	}
}