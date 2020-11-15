package com.jeeplus.modules.audio.api;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.ASlide;
import com.jeeplus.modules.audio.service.ASlideService;

/**
 * 幻灯片APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/slide")
public class ASlideAPI extends BaseController {
		
	@Autowired
	private ASlideService aSlideService;
	
	/**
	 * 获取显示的幻灯片集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(ASlide aSlide) {	
		AjaxJson ajax = new AjaxJson();	
		aSlide.setStatus(true);
		List<ASlide> list = aSlideService.findList(aSlide);
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
	public AjaxJson info(ASlide aSlide) {	
		AjaxJson ajax = new AjaxJson();	
		aSlide = aSlideService.get(aSlide);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("slide", aSlide);			
		ajax.setBody(body);
		return ajax;
	}
}