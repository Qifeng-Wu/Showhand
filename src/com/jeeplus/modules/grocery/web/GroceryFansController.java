/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.jeeplus.modules.grocery.entity.GroceryFans;
import com.jeeplus.modules.grocery.service.GroceryFansService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 粉丝用户Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/fans")
public class GroceryFansController extends BaseController {

	@Autowired
	private GroceryFansService groceryFansService;
	
	@ModelAttribute
	public GroceryFans get(@RequestParam(required=false) String id) {
		GroceryFans entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFansService.get(id);
		}
		if (entity == null){
			entity = new GroceryFans();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryFans groceryFans, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryFans> page = groceryFansService.findPage(new Page<GroceryFans>(request, response), groceryFans); 
		model.addAttribute("page", page);
		return "modules/grocery/fansList";
	}
	/**
	 * 订单管理员列表页面
	 */
	@RequestMapping(value = "clerkList")
	public String clerkList(GroceryFans GroceryFans, HttpServletRequest request, HttpServletResponse response, Model model) {
		GroceryFans.setClerk(true);
		Page<GroceryFans> page = groceryFansService.findPage(new Page<GroceryFans>(request, response), GroceryFans); 
		model.addAttribute("page", page);
		return "modules/grocery/clerkList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryFans groceryFans, Model model) {
		model.addAttribute("groceryFans", groceryFans);
		return "modules/grocery/fansForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GroceryFans groceryFans, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryFans)){
			return form(groceryFans, model);
		}
		groceryFansService.customSave(groceryFans);
		addMessage(redirectAttributes, "保存订单管理员成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fans/?repage";
	}
	
	/**
	 * 把粉丝设置为订单管理员
	 */
	@RequestMapping(value = "settingClerk")
	public String settingClerk(GroceryFans groceryFans, RedirectAttributes redirectAttributes) {
		groceryFans.setClerk(true);
		groceryFansService.customSave(groceryFans);
		addMessage(redirectAttributes, "设置订单管理员成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fans/?repage";
	}
	/**
	 * 删除订单管理员
	 */
	@RequestMapping(value = "deleteClerk")
	public String deleteClerk(GroceryFans groceryFans, RedirectAttributes redirectAttributes) {
		groceryFans.setClerk(false);
		groceryFansService.customSave(groceryFans);
		addMessage(redirectAttributes, "删除订单管理员成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fans/clerkList?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryFans groceryFans, RedirectAttributes redirectAttributes) {
		groceryFansService.delete(groceryFans);
		addMessage(redirectAttributes, "删除订单管理员成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fans/?repage";
	}
	
}