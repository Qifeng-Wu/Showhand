package com.jeeplus.modules.audio.api;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AChapter;
import com.jeeplus.modules.audio.service.AChapterService;

/**
 * 章回APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/chapter")
public class AChapterAPI extends BaseController {
		
	@Autowired
	private AChapterService aChapterService;
	
	/**
	 * 获取显示的章回集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AChapter aChapter) {	
		AjaxJson ajax = new AjaxJson();	
		aChapter.setDeleteFlag(false);
		aChapter.setStatus(true);
		List<AChapter> list = aChapterService.findList(aChapter);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取章回信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson info(AChapter aChapter) {	
		AjaxJson ajax = new AjaxJson();	
		aChapter = aChapterService.get(aChapter);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("chapter", aChapter);
		ajax.setBody(body);
		return ajax;
	}
}