/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.grocery.entity.GroceryFreight;
import com.jeeplus.modules.grocery.service.GroceryFreightService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 物流运费APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/freight")
public class FreightAPI extends BaseController {
		
	@Autowired
	private GroceryFreightService groceryFreightService;
	
	@ModelAttribute
	public GroceryFreight get(@RequestParam(required=false) String id) {
		GroceryFreight entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFreightService.get(id);
		}
		if (entity == null){
			entity = new GroceryFreight();
		}
		return entity;
	}
	
	/**
	 * 根据省份名称获取运费
	 */
	@RequestMapping(value = "province/freight")
	@ResponseBody
	public AjaxJson findList(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		GroceryFreight groceryFreight = 
				groceryFreightService.findUniqueByProperty("province", request.getParameter("province"));		
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("freight", groceryFreight);			
		ajax.setBody(body);
		return ajax;
	}
}