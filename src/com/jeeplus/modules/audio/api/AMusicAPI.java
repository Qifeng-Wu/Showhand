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
import com.jeeplus.modules.audio.entity.AMusic;
import com.jeeplus.modules.audio.service.AMusicService;

/**
 * 背景音乐APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/music")
public class AMusicAPI extends BaseController {
		
	@Autowired
	private AMusicService aMusicService;
	
	/**
	 * 获取音乐集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AMusic aMusic,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		List<AMusic> list = aMusicService.findList(aMusic);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 搜索音乐
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public AjaxJson search(AMusic aMusic,HttpServletRequest request) {	
		String searchword = request.getParameter("searchword");
		AjaxJson ajax = new AjaxJson();	
		aMusic.setTitle(searchword);
		List<AMusic> list = aMusicService.findList(aMusic);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取音乐信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson info(AMusic aMusic,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		aMusic = aMusicService.get(aMusic);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("music", aMusic);			
		ajax.setBody(body);
		return ajax;
	}
}