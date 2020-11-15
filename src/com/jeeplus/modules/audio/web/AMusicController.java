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
import com.jeeplus.modules.audio.entity.AMusic;
import com.jeeplus.modules.audio.service.AMusicService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 音乐Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/music")
public class AMusicController extends BaseController {

	@Autowired
	private AMusicService aMusicService;
	
	@ModelAttribute
	public AMusic get(@RequestParam(required=false) String id) {
		AMusic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aMusicService.get(id);
		}
		if (entity == null){
			entity = new AMusic();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:music:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aMusic")AMusic aMusic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AMusic> page = aMusicService.findPage(new Page<AMusic>(request, response), aMusic); 
		model.addAttribute("page", page);
		return "modules/audio/musicList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:music:view","audio:music:add","audio:music:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AMusic aMusic, Model model) {
		model.addAttribute("aMusic", aMusic);
		return "modules/audio/musicForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AMusic aMusic, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aMusic)){
			return form(aMusic, model);
		}
		aMusic.setTitle(request.getParameter("title"));
		aMusic.setPicture(request.getParameter("picture"));
		aMusicService.customSave(aMusic);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/music/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:music:delete")
	@RequestMapping(value = "delete")
	public String delete(AMusic aMusic, RedirectAttributes redirectAttributes) {
		aMusicService.delete(aMusic);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/music/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:music:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aMusicService.delete(aMusicService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/music/?repage";
	}
}