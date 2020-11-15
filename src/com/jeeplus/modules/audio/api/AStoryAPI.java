package com.jeeplus.modules.audio.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AChapter;
import com.jeeplus.modules.audio.entity.AStory;
import com.jeeplus.modules.audio.service.AChapterService;
import com.jeeplus.modules.audio.service.AStoryService;

/**
 * 故事APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/story")
public class AStoryAPI extends BaseController {
		
	@Autowired
	private AStoryService aStoryService;
	@Autowired
	private AChapterService aChapterService;
	
	/**
	 * 获取显示的故事集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AStory aStory) {	
		AjaxJson ajax = new AjaxJson();	
		aStory.setDeleteFlag(false);
		aStory.setStatus(true);
		List<AStory> list = aStoryService.findList(aStory);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取N条推荐故事集合
	 */
	@RequestMapping(value = "recommend")
	@ResponseBody
	public AjaxJson recommend(String num) {	
		AjaxJson ajax = new AjaxJson();
		List<AStory> list = aStoryService.findRecommendList(Integer.parseInt(num));
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取故事信息(同时获取该故事下的章回)
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson info(AStory aStory, AChapter aChapter) {	
		AjaxJson ajax = new AjaxJson();	
		aStory = aStoryService.get(aStory);
		//获取该故事下的章回
		aChapter.setDeleteFlag(false);
		aChapter.setStatus(true);
		List<AChapter> chapterList = aChapterService.findList(aChapter);
		//获取该故事下最近章回跟新时间
		Date lastUpdateTime = aChapterService.findLastUpdateTime(aStory.getStoryId());
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("story", aStory);
		body.put("chapterList", chapterList);
		body.put("lastUpdateTime", lastUpdateTime);
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 搜索音乐
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public AjaxJson search(AStory aStory,HttpServletRequest request) {	
		String searchword = request.getParameter("searchword");
		AjaxJson ajax = new AjaxJson();	
		aStory.setTitle(searchword);
		aStory.setDeleteFlag(false);
		aStory.setStatus(true);
		List<AStory> list = aStoryService.findList(aStory);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
}