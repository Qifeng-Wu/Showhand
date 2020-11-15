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
import com.jeeplus.modules.audio.entity.AFans;
import com.jeeplus.modules.audio.service.AFansService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 粉丝用户Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/fans")
public class AFansController extends BaseController {

	@Autowired
	private AFansService aFansService;
	
	@ModelAttribute
	public AFans get(@RequestParam(required=false) String id) {
		AFans entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aFansService.get(id);
		}
		if (entity == null){
			entity = new AFans();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:fans:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aFans")AFans aFans, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AFans> page = aFansService.findPage(new Page<AFans>(request, response), aFans); 
		model.addAttribute("page", page);
		return "modules/audio/fansList";
	}
	
	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:fans:view","audio:fans:add","audio:fans:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AFans aFans, Model model) {
		model.addAttribute("aFans", aFans);
		return "modules/audio/fansForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AFans aFans, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aFans)){
			return form(aFans, model);
		}
		aFansService.customSave(aFans);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/fans/?repage";
	}
	
	/**
	 * 禁用
	 */
	@RequiresPermissions("audio:fans:remark")
	@RequestMapping(value = "remark")
	public String remark(AFans aFans, RedirectAttributes redirectAttributes) {
		aFansService.customSave(aFans);
		addMessage(redirectAttributes, "备注成功");
		return "redirect:"+Global.getAdminPath()+"/audio/fans/?repage";
	}
	
	/**
	 * 禁用
	 */
	@RequiresPermissions("audio:fans:forbid")
	@RequestMapping(value = "forbid")
	public String forbid(AFans aFans, RedirectAttributes redirectAttributes) {
		aFansService.customSave(aFans);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/audio/fans/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:fans:delete")
	@RequestMapping(value = "delete")
	public String delete(AFans aFans, RedirectAttributes redirectAttributes) {
		aFansService.delete(aFans);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/fans/?repage";
	}
	
}