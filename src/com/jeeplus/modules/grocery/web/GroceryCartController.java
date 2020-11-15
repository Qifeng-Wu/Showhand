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

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.grocery.entity.GroceryCart;
import com.jeeplus.modules.grocery.service.GroceryCartService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 客户问题反馈Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/cart")
public class GroceryCartController extends BaseController {

	@Autowired
	private GroceryCartService groceryCartService;
	
	@ModelAttribute
	public GroceryCart get(@RequestParam(required=false) String id) {
		GroceryCart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryCartService.get(id);
		}
		if (entity == null){
			entity = new GroceryCart();
		}
		return entity;
	}
	
	/**
	 * 意见反馈列表页面
	 */
	//@RequiresPermissions("qis:GroceryCart:list")
	@RequestMapping(value = {"list", ""})
	public String list(GroceryCart groceryCart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryCart> page = groceryCartService.findPage(new Page<GroceryCart>(request, response), groceryCart); 
		model.addAttribute("page", page);
		return "modules/grocery/cartList";
	}

	/**
	 * 查看，增加，编辑意见反馈表单页面
	 */
	//@RequiresPermissions(value={"qis:GroceryCart:view","qis:GroceryCart:add","qis:GroceryCart:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GroceryCart groceryCart, Model model) {
		model.addAttribute("GroceryCart", groceryCart);
		return "modules/grocery/cartForm";
	}
}