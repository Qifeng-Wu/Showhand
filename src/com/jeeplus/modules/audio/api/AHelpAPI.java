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
import com.jeeplus.modules.audio.entity.AHelp;
import com.jeeplus.modules.audio.service.AHelpService;

/**
 * 帮助中心APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/help")
public class AHelpAPI extends BaseController {

	@Autowired
	private AHelpService aHelpService;
	
	/**
	 * 获取帮助中心集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AHelp aHelp,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		aHelp.setState(true);
		List<AHelp> helpList = aHelpService.findList(aHelp);	
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("helpList", helpList);		
		ajax.setBody(body);
		return ajax;
	}
}