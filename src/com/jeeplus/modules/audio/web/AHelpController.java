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
import com.jeeplus.modules.audio.entity.AHelp;
import com.jeeplus.modules.audio.service.AHelpService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 帮助中心Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/help")
public class AHelpController extends BaseController {

	@Autowired
	private AHelpService aHelpService;
	
	@ModelAttribute
	public AHelp get(@RequestParam(required=false) String id) {
		AHelp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aHelpService.get(id);
		}
		if (entity == null){
			entity = new AHelp();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:help:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aHelp")AHelp aHelp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AHelp> page = aHelpService.findPage(new Page<AHelp>(request, response), aHelp); 
		model.addAttribute("page", page);
		return "modules/audio/helpList";
	}

	/**
	 * 查看，增加，编辑帮助中心表单页面
	 */
	@RequiresPermissions(value={"audio:help:view","audio:help:add","audio:help:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AHelp aHelp, Model model) {
		model.addAttribute("aHelp", aHelp);
		return "modules/audio/helpForm";
	}

	/**
	 * 保存帮助中心
	 */
	@RequestMapping(value = "save")
	public String save(AHelp aHelp, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aHelp)){
			return form(aHelp, model);
		}
		aHelp.setTitle(request.getParameter("title"));
		aHelp.setContent(request.getParameter("content"));
		aHelpService.save(aHelp);
		addMessage(redirectAttributes, "保存帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/audio/help/?repage";
	}
	
	/**
	 * 删除帮助中心
	 */
	@RequiresPermissions("audio:help:delete")
	@RequestMapping(value = "delete")
	public String delete(AHelp aHelp, RedirectAttributes redirectAttributes) {
		aHelpService.delete(aHelp);
		addMessage(redirectAttributes, "删除帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/audio/help/?repage";
	}
	
	/**
	 * 批量删除帮助中心
	 */
	@RequiresPermissions("audio:help:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids,AHelp aHelp,RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aHelpService.delete(aHelpService.get(id));
		}
		addMessage(redirectAttributes, "删除帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/audio/help/?repage";
	}

}