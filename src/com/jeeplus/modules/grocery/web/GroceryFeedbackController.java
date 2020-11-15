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
import com.jeeplus.modules.grocery.entity.GroceryFeedback;
import com.jeeplus.modules.grocery.service.GroceryFeedbackService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 客户问题反馈Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/feedback")
public class GroceryFeedbackController extends BaseController {

	@Autowired
	private GroceryFeedbackService groceryFeedbackService;
	
	@ModelAttribute
	public GroceryFeedback get(@RequestParam(required=false) String id) {
		GroceryFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFeedbackService.get(id);
		}
		if (entity == null){
			entity = new GroceryFeedback();
		}
		return entity;
	}
	
	/**
	 * 意见反馈列表页面
	 */
	//@RequiresPermissions("qis:GroceryFeedback:list")
	@RequestMapping(value = {"list", ""})
	public String list(GroceryFeedback groceryFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryFeedback> page = groceryFeedbackService.findPage(new Page<GroceryFeedback>(request, response), groceryFeedback); 
		model.addAttribute("page", page);
		return "modules/grocery/feedbackList";
	}

	/**
	 * 查看，增加，编辑意见反馈表单页面
	 */
	//@RequiresPermissions(value={"qis:GroceryFeedback:view","qis:GroceryFeedback:add","qis:GroceryFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GroceryFeedback groceryFeedback, Model model) {
		model.addAttribute("GroceryFeedback", groceryFeedback);
		return "modules/grocery/feedbackForm";
	}

	/**
	 * 保存意见反馈
	 */
	//@RequiresPermissions(value={"qis:GroceryFeedback:add","qis:GroceryFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(GroceryFeedback groceryFeedback, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryFeedback)){
			return form(groceryFeedback, model);
		}
		
		groceryFeedbackService.save(groceryFeedback);
		addMessage(redirectAttributes, "保存意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/feedback/?repage";
	}
	
	/**
	 * 删除意见反馈
	 */
	//@RequiresPermissions("qis:GroceryFeedback:del")
	@RequestMapping(value = "delete")
	public String delete(GroceryFeedback groceryFeedback, RedirectAttributes redirectAttributes) {
		groceryFeedbackService.delete(groceryFeedback);
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/feedback/?repage";
	}
	
	/**
	 * 批量删除意见反馈
	 */
	//@RequiresPermissions("qis:GroceryFeedback:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids,GroceryFeedback groceryFeedback,RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			groceryFeedback = groceryFeedbackService.get(id);
			groceryFeedbackService.delete(groceryFeedback);
		}
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/feedback/?repage";
	}

}