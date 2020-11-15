/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;

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
import com.jeeplus.modules.grocery.entity.GrocerySlide;
import com.jeeplus.modules.grocery.service.GrocerySlideService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 幻灯片APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/slide")
public class SlideAPI extends BaseController {
		
	@Autowired
	private GrocerySlideService grocerySlideService;
	
	@ModelAttribute
	public GrocerySlide get(@RequestParam(required=false) String id) {
		GrocerySlide entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = grocerySlideService.get(id);
		}
		if (entity == null){
			entity = new GrocerySlide();
		}
		return entity;
	}
	
	/**
	 * 获取显示的幻灯片集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(GrocerySlide grocerySlide,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		grocerySlide.setStatus(true);
		List<GrocerySlide> list = grocerySlideService.findList(grocerySlide);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取幻灯片信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson information(GrocerySlide grocerySlide,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		grocerySlide = grocerySlideService.get(grocerySlide);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("slide", grocerySlide);			
		ajax.setBody(body);
		return ajax;
	}
}