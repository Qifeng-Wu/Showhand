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
import com.jeeplus.modules.grocery.entity.GroceryPlatform;
import com.jeeplus.modules.grocery.service.GroceryPlatformService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 平台信息APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/platform")
public class PlatformAPI extends BaseController {
		
	@Autowired
	private GroceryPlatformService groceryPlatformService;
	
	@ModelAttribute
	public GroceryPlatform get(@RequestParam(required=false) String id) {
		GroceryPlatform entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryPlatformService.get(id);
		}
		if (entity == null){
			entity = new GroceryPlatform();
		}
		return entity;
	}
	
	/**
	 * 获取平台信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson information(GroceryPlatform groceryPlatform,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		List<GroceryPlatform> list = groceryPlatformService.findList(groceryPlatform);
		if(list!=null && list.size()>0){
			groceryPlatform = list.get(0);			
		}
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("platform", groceryPlatform);			
		ajax.setBody(body);
		return ajax;
	}
}