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
import com.jeeplus.modules.grocery.entity.GroceryOrderItem;
import com.jeeplus.modules.grocery.service.GroceryOrderItemService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 订单商品详情Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/order/item")
public class GroceryOrderItemController extends BaseController {

	@Autowired
	private GroceryOrderItemService groceryOrderItemService;
	
	@ModelAttribute
	public GroceryOrderItem get(@RequestParam(required=false) String id) {
		GroceryOrderItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryOrderItemService.get(id);
		}
		if (entity == null){
			entity = new GroceryOrderItem();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryOrderItem groceryOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryOrderItem> page = groceryOrderItemService.findPage(new Page<GroceryOrderItem>(request, response), groceryOrderItem); 
		model.addAttribute("page", page);
		return "modules/grocery/orderItemList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryOrderItem groceryOrderItem, Model model) {
		model.addAttribute("groceryOrderItem", groceryOrderItem);
		return "modules/grocery/orderItemForm";
	}
	
}