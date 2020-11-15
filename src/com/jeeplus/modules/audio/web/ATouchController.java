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
import com.jeeplus.modules.audio.entity.ATouch;
import com.jeeplus.modules.audio.service.ATouchService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 触屏操作Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/touch")
public class ATouchController extends BaseController {

	@Autowired
	private ATouchService aTouchService;
	
	@ModelAttribute
	public ATouch get(@RequestParam(required=false) String id) {
		ATouch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aTouchService.get(id);
		}
		if (entity == null){
			entity = new ATouch();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:touch:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aTouch")ATouch aTouch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ATouch> page = aTouchService.findPage(new Page<ATouch>(request, response), aTouch); 
		model.addAttribute("page", page);
		return "modules/audio/touchList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:touch:view","audio:touch:add","audio:touch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ATouch aTouch, Model model) {
		model.addAttribute("aTouch", aTouch);
		return "modules/audio/touchForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(ATouch aTouch, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aTouch)){
			return form(aTouch, model);
		}
		aTouch.setName(request.getParameter("name"));
		aTouch.setAudio(request.getParameter("audio"));
		aTouchService.customSave(aTouch);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/touch/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:touch:delete")
	@RequestMapping(value = "delete")
	public String delete(ATouch aTouch, RedirectAttributes redirectAttributes) {
		aTouchService.delete(aTouch);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/touch/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:touch:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aTouchService.delete(aTouchService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/touch/?repage";
	}
}