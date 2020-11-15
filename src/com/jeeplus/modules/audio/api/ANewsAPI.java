package com.jeeplus.modules.audio.api;


import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.ANews;
import com.jeeplus.modules.audio.service.ANewsService;

/**
 * 新闻资讯APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/news")
public class ANewsAPI extends BaseController {

	@Autowired
	private ANewsService aNewsService;
	
	/**
	 * 获取新闻资讯集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(ANews aNews,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		aNews.setState(true);
		List<ANews> list = aNewsService.findList(aNews);	
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);		
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取新闻资讯信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson info(ANews aNews,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		aNews = aNewsService.get(aNews);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("news", aNews);			
		ajax.setBody(body);
		return ajax;
	}
}