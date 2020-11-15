/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.web;

import java.util.List;

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
import com.jeeplus.modules.grocery.entity.GroceryPlatform;
import com.jeeplus.modules.grocery.service.GroceryPlatformService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 平台信息Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/platform")
public class GroceryPlatformController extends BaseController {

	@Autowired
	private GroceryPlatformService groceryPlatformService;
	
	@ModelAttribute
	public GroceryPlatform get(@RequestParam(required=false) String id) {
		GroceryPlatform entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryPlatformService.get(id);
		}
		if (entity == null){
			entity = new GroceryPlatform();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryPlatform groceryPlatform, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryPlatform> page = groceryPlatformService.findPage(new Page<GroceryPlatform>(request, response), groceryPlatform); 
		model.addAttribute("page", page);
		return "modules/grocery/platformList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryPlatform groceryPlatform, Model model) {
		List<GroceryPlatform> list = groceryPlatformService.findList(groceryPlatform);
		if(list!=null && list.size()>0){
			model.addAttribute("groceryPlatform", list.get(0));
		}
		return "modules/grocery/platformForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,GroceryPlatform groceryPlatform, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryPlatform)){
			return form(groceryPlatform, model);
		}
		String name = request.getParameter("name");
		String picture = request.getParameter("picture");
		String address = request.getParameter("address");
		String description = request.getParameter("description");
		String notice = request.getParameter("notice");
		String photos = request.getParameter("photos");
		groceryPlatform.setName(name);
		groceryPlatform.setPicture(picture);
		groceryPlatform.setAddress(address);
		groceryPlatform.setDescription(description);
		groceryPlatform.setNotice(notice);
		groceryPlatform.setPhotos(photos);
		groceryPlatformService.customSave(groceryPlatform);
		addMessage(redirectAttributes, "保存平台信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/platform/form";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryPlatform groceryPlatform, RedirectAttributes redirectAttributes) {
		groceryPlatformService.delete(groceryPlatform);
		addMessage(redirectAttributes, "删除平台信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/platform/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			groceryPlatformService.delete(groceryPlatformService.get(id));
		}
		addMessage(redirectAttributes, "删除平台信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/platform/?repage";
	}
}