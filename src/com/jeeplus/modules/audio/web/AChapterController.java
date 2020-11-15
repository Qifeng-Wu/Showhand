package com.jeeplus.modules.audio.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AChapter;
import com.jeeplus.modules.audio.entity.AMusic;
import com.jeeplus.modules.audio.entity.AStory;
import com.jeeplus.modules.audio.service.AChapterService;
import com.jeeplus.modules.audio.service.AMusicService;
import com.jeeplus.modules.audio.service.AStoryService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 章Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/chapter")
public class AChapterController extends BaseController {

	@Autowired
	private AChapterService aChapterService;
	@Autowired
	private AStoryService aStoryService;
	@Autowired
	private AMusicService aMusicService;
	
	@ModelAttribute
	public AChapter get(@RequestParam(required=false) String id) {
		AChapter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aChapterService.get(id);
		}
		if (entity == null){
			entity = new AChapter();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:chapter:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aChapter")AChapter aChapter, HttpServletRequest request, HttpServletResponse response, Model model) {
		aChapter.setDeleteFlag(false);
		Page<AChapter> page = aChapterService.findPage(new Page<AChapter>(request, response), aChapter); 
		//获取故事列表
		AStory astory = new AStory();
		astory.setDeleteFlag(false);
		List<AStory> storyList = aStoryService.findList(astory);
		model.addAttribute("storyList", storyList);
		model.addAttribute("page", page);
		return "modules/audio/chapterList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:chapter:view","audio:chapter:add","audio:chapter:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AChapter aChapter, Model model) {
		AStory astory = new AStory();
		astory.setDeleteFlag(false);
		List<AStory> storyList = aStoryService.findList(astory);
		model.addAttribute("storyList", storyList);
		model.addAttribute("aChapter", aChapter);
		return "modules/audio/chapterForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AChapter aChapter, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aChapter)){
			return form(aChapter, model);
		}
		aChapter.setTitle(request.getParameter("title"));
		aChapter.setUpdateTime(new Date());
		aChapter.setDeleteFlag(false);
		aChapterService.customSave(aChapter);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/chapter/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:chapter:delete")
	@RequestMapping(value = "delete")
	public String delete(AChapter aChapter, RedirectAttributes redirectAttributes) {
		aChapter.setDeleteFlag(true);
		aChapterService.delete(aChapter);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/chapter/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:chapter:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aChapterService.delete(aChapterService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/chapter/?repage";
	}
	
	/**
	 * 跳转到设置页面
	 */
	@RequiresPermissions("audio:chapter:set")
	@RequestMapping(value = "set")
	public String set(AChapter aChapter, AMusic aMusic, Model model) {
		List<AMusic> musicList = aMusicService.findList(aMusic);
 		model.addAttribute("aChapter", aChapter);
		model.addAttribute("musicList", musicList);
		return "modules/audio/chapterSetForm";
	}
	
	/**
	 * 节点设置保存
	 */
	@RequestMapping(value = "nodesSet")
	@ResponseBody
	public AjaxJson nodesSet(AChapter aChapter, HttpServletRequest request) {
		AjaxJson ajax = new AjaxJson();	
		aChapter.setNodes(request.getParameter("nodes"));
		aChapter.setUpdateTime(new Date());
		aChapterService.customSave(aChapter);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("aChapter", aChapter);		
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 背景音乐设置保存
	 */
	@RequestMapping(value = "musicSet")
	@ResponseBody
	public AjaxJson musicSet(AChapter aChapter, HttpServletRequest request) {
		AjaxJson ajax = new AjaxJson();	
		aChapter.setMusics(request.getParameter("musics"));
		aChapter.setUpdateTime(new Date());
		aChapterService.customSave(aChapter);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("aChapter", aChapter);		
		ajax.setBody(body);
		return ajax;
	}
}