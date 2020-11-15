package com.jeeplus.modules.audio.web;


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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.ANews;
import com.jeeplus.modules.audio.service.ANewsService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 新闻资讯Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/news")
public class ANewsController extends BaseController {

	@Autowired
	private ANewsService aNewsService;
	
	@ModelAttribute
	public ANews get(@RequestParam(required=false) String id) {
		ANews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aNewsService.get(id);
		}
		if (entity == null){
			entity = new ANews();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:news:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aNews")ANews aNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ANews> page = aNewsService.findPage(new Page<ANews>(request, response), aNews); 
		model.addAttribute("page", page);
		return "modules/audio/newsList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:news:view","audio:news:add","audio:news:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ANews aNews, Model model) {
		model.addAttribute("aNews", aNews);
		return "modules/audio/newsForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(ANews aNews, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aNews)){
			return form(aNews, model);
		}
		aNews.setTitle(request.getParameter("title"));
		aNews.setSummary(request.getParameter("summary"));
		aNews.setPicture(request.getParameter("picture"));
		aNews.setContent(request.getParameter("content"));
		aNewsService.save(aNews);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/news/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:news:delete")
	@RequestMapping(value = "delete")
	public String delete(ANews aNews, RedirectAttributes redirectAttributes) {
		aNewsService.delete(aNews);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/news/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:news:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids,ANews aNews,RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aNewsService.delete(aNewsService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/news/?repage";
	}

}