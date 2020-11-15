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
import com.jeeplus.modules.grocery.entity.GroceryFansAddress;
import com.jeeplus.modules.grocery.service.GroceryFansAddressService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 收货地址Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/fansAddress")
public class GroceryFansAddressController extends BaseController {

	@Autowired
	private GroceryFansAddressService groceryFansAddressService;
	
	@ModelAttribute
	public GroceryFansAddress get(@RequestParam(required=false) String id) {
		GroceryFansAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFansAddressService.get(id);
		}
		if (entity == null){
			entity = new GroceryFansAddress();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryFansAddress groceryFansAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryFansAddress> page = groceryFansAddressService.findPage(new Page<GroceryFansAddress>(request, response), groceryFansAddress); 
		model.addAttribute("page", page);
		return "modules/grocery/fansAddressList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryFansAddress groceryFansAddress, Model model) {
		model.addAttribute("groceryFansAddress", groceryFansAddress);
		return "modules/grocery/fansAddressForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GroceryFansAddress groceryFansAddress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryFansAddress)){
			return form(groceryFansAddress, model);
		}
		groceryFansAddressService.customSave(groceryFansAddress);
		addMessage(redirectAttributes, "保存收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fansAddress/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryFansAddress groceryFansAddress, RedirectAttributes redirectAttributes) {
		groceryFansAddressService.delete(groceryFansAddress);
		addMessage(redirectAttributes, "删除收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fansAddress/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			groceryFansAddressService.delete(groceryFansAddressService.get(id));
		}
		addMessage(redirectAttributes, "删除收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/fansAddress/?repage";
	}
}