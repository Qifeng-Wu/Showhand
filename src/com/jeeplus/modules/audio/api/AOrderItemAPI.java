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
import com.jeeplus.modules.audio.entity.AOrderItem;
import com.jeeplus.modules.audio.service.AOrderItemService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 微信用户订单itemAPIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/order/item")
public class AOrderItemAPI extends BaseController {

	@Autowired
	private AOrderItemService aOrderItemService;
	
	@ModelAttribute
	public AOrderItem get(@RequestParam(required=false) String id) {
		AOrderItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aOrderItemService.get(id);
		}
		if (entity == null){
			entity = new AOrderItem();
		}
		return entity;
	}
	
	/**
	 * 获取用户订单中item集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AOrderItem aOrderItem) {	
		AjaxJson ajax = new AjaxJson();	
		List<AOrderItem> list = aOrderItemService.findList(aOrderItem);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
}