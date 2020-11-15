package com.jeeplus.modules.audio.api;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.ATouch;
import com.jeeplus.modules.audio.service.ATouchService;

/**
 * 触屏动作APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/touch")
public class ATouchAPI extends BaseController {
		
	@Autowired
	private ATouchService aTouchService;
	
	/**
	 * 获取集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(ATouch aTouch) {	
		AjaxJson ajax = new AjaxJson();	
		List<ATouch> list = aTouchService.findList(aTouch);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson info(ATouch aTouch) {	
		AjaxJson ajax = new AjaxJson();	
		aTouch = aTouchService.get(aTouch);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("touch", aTouch);			
		ajax.setBody(body);
		return ajax;
	}
}