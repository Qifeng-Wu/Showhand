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
import com.jeeplus.modules.grocery.entity.GrocerySlide;
import com.jeeplus.modules.grocery.service.GrocerySlideService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 幻灯片Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/slide")
public class GrocerySlideController extends BaseController {

	@Autowired
	private GrocerySlideService grocerySlideService;
	
	@ModelAttribute
	public GrocerySlide get(@RequestParam(required=false) String id) {
		GrocerySlide entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = grocerySlideService.get(id);
		}
		if (entity == null){
			entity = new GrocerySlide();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GrocerySlide grocerySlide, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GrocerySlide> page = grocerySlideService.findPage(new Page<GrocerySlide>(request, response), grocerySlide); 
		model.addAttribute("page", page);
		return "modules/grocery/slideList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GrocerySlide grocerySlide, Model model) {
		model.addAttribute("grocerySlide", grocerySlide);
		return "modules/grocery/slideForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GrocerySlide grocerySlide, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, grocerySlide)){
			return form(grocerySlide, model);
		}
		grocerySlide.setTitle(request.getParameter("title"));
		grocerySlide.setDescription(request.getParameter("description"));
		grocerySlideService.customSave(grocerySlide);
		addMessage(redirectAttributes, "保存幻灯片成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/slide/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GrocerySlide grocerySlide, RedirectAttributes redirectAttributes) {
		grocerySlideService.delete(grocerySlide);
		addMessage(redirectAttributes, "删除幻灯片成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/slide/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			grocerySlideService.delete(grocerySlideService.get(id));
		}
		addMessage(redirectAttributes, "删除幻灯片成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/slide/?repage";
	}
}