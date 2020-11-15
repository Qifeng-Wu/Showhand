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
import com.jeeplus.modules.grocery.entity.GroceryGoodsClassification;
import com.jeeplus.modules.grocery.service.GroceryGoodsClassificationService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 商品分类Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/goodsClassification")
public class GroceryGoodsClassificationController extends BaseController {

	@Autowired
	private GroceryGoodsClassificationService groceryGoodsClassificationService;
	
	@ModelAttribute
	public GroceryGoodsClassification get(@RequestParam(required=false) String id) {
		GroceryGoodsClassification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryGoodsClassificationService.get(id);
		}
		if (entity == null){
			entity = new GroceryGoodsClassification();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryGoodsClassification groceryGoodsClassification, HttpServletRequest request, HttpServletResponse response, Model model) {
		groceryGoodsClassification.setDeleteFlag(true);
		Page<GroceryGoodsClassification> page = groceryGoodsClassificationService.findPage(new Page<GroceryGoodsClassification>(request, response), groceryGoodsClassification); 
		model.addAttribute("page", page);
		return "modules/grocery/goodsClassificationList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryGoodsClassification groceryGoodsClassification, Model model) {
		model.addAttribute("groceryGoodsClassification", groceryGoodsClassification);
		return "modules/grocery/goodsClassificationForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GroceryGoodsClassification groceryGoodsClassification, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryGoodsClassification)){
			return form(groceryGoodsClassification, model);
		}
		groceryGoodsClassification.setDeleteFlag(true);
		groceryGoodsClassification.setName(request.getParameter("name"));
		groceryGoodsClassificationService.customSave(groceryGoodsClassification);
		addMessage(redirectAttributes, "保存商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/goodsClassification/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryGoodsClassification groceryGoodsClassification, RedirectAttributes redirectAttributes) {
		groceryGoodsClassification.setDeleteFlag(false);
		groceryGoodsClassificationService.delete(groceryGoodsClassification);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/goodsClassification/?repage";
	}
	
}