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
import com.jeeplus.modules.grocery.entity.GroceryGoods;
import com.jeeplus.modules.grocery.entity.GroceryGoodsClassification;
import com.jeeplus.modules.grocery.service.GroceryGoodsClassificationService;
import com.jeeplus.modules.grocery.service.GroceryGoodsService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 商品信息Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/goods")
public class GroceryGoodsController extends BaseController {

	@Autowired
	private GroceryGoodsService groceryGoodsService;
	@Autowired
	private GroceryGoodsClassificationService groceryGoodsClassificationService;
	
	@ModelAttribute
	public GroceryGoods get(@RequestParam(required=false) String id) {
		GroceryGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryGoodsService.get(id);
		}
		if (entity == null){
			entity = new GroceryGoods();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryGoods groceryGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		groceryGoods.setDeleteFlag(true);
		Page<GroceryGoods> page = groceryGoodsService.findPage(new Page<GroceryGoods>(request, response), groceryGoods); 
		model.addAttribute("page", page);
		return "modules/grocery/goodsList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryGoods groceryGoods, Model model) {
		GroceryGoodsClassification classification = new GroceryGoodsClassification();
		classification.setDeleteFlag(true);
		List<GroceryGoodsClassification> classificationList = groceryGoodsClassificationService.findList(classification);
		model.addAttribute("classificationList", classificationList);
		model.addAttribute("groceryGoods", groceryGoods);
		return "modules/grocery/goodsForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GroceryGoods groceryGoods, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryGoods)){
			return form(groceryGoods, model);
		}
		groceryGoods.setDeleteFlag(true);
		groceryGoods.setName(request.getParameter("name"));
		groceryGoods.setDescription(request.getParameter("description"));
		groceryGoodsService.customSave(groceryGoods);
		addMessage(redirectAttributes, "保存商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/goods/list?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryGoods groceryGoods, RedirectAttributes redirectAttributes) {
		groceryGoods.setDeleteFlag(false);
		groceryGoodsService.delete(groceryGoods);
		addMessage(redirectAttributes, "删除商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/goods/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			groceryGoodsService.delete(groceryGoodsService.get(id));
		}
		addMessage(redirectAttributes, "删除商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/goods/?repage";
	}
}