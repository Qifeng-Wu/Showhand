/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.audio.api;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AItemFree;
import com.jeeplus.modules.audio.service.AItemFreeService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 微信用户免费itemAPIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/item/free")
public class AItemFreeAPI extends BaseController {

	@Autowired
	private AItemFreeService aItemFreeService;
	
	@ModelAttribute
	public AItemFree get(@RequestParam(required=false) String id) {
		AItemFree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aItemFreeService.get(id);
		}
		if (entity == null){
			entity = new AItemFree();
		}
		return entity;
	}
	
	/**
	 * 获取用户免费item集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AItemFree aItemFree) {	
		AjaxJson ajax = new AjaxJson();	
		List<AItemFree> list = aItemFreeService.findList(aItemFree);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
}