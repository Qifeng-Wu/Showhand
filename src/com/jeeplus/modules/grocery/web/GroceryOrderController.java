/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.web;

import java.util.Date;

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
import com.jeeplus.modules.grocery.entity.GroceryOrder;
import com.jeeplus.modules.grocery.service.GroceryOrderService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 订单信息信息Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/order")
public class GroceryOrderController extends BaseController {

	@Autowired
	private GroceryOrderService groceryOrderService;
	
	@ModelAttribute
	public GroceryOrder get(@RequestParam(required=false) String id) {
		GroceryOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryOrderService.get(id);
		}
		if (entity == null){
			entity = new GroceryOrder();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryOrder groceryOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryOrder> page = groceryOrderService.findPage(new Page<GroceryOrder>(request, response), groceryOrder); 
		model.addAttribute("page", page);
		System.out.println("======"+page.getList().get(0));
		return "modules/grocery/orderList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryOrder groceryOrder, Model model) {
		model.addAttribute("groceryOrder", groceryOrder);
		return "modules/grocery/orderForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GroceryOrder groceryOrder, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryOrder)){
			return form(groceryOrder, model);
		}
		groceryOrderService.customSave(groceryOrder);
		addMessage(redirectAttributes, "保存订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/order/list?repage";
	}
	
	/**
	 * 确认发货
	 */
	@RequestMapping(value = "fahuo")
	public String fahuo(GroceryOrder groceryOrder,RedirectAttributes redirectAttributes) {		
		groceryOrder.setStatus(2);
		groceryOrder.setSendTime(new Date());
		groceryOrderService.customSave(groceryOrder);
		addMessage(redirectAttributes, "确认发货成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/order/list?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryOrder groceryOrder, RedirectAttributes redirectAttributes) {
		groceryOrder.setDeleteFlag(false);
		groceryOrderService.delete(groceryOrder);
		addMessage(redirectAttributes, "删除订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/order/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			groceryOrderService.delete(groceryOrderService.get(id));
		}
		addMessage(redirectAttributes, "删除订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/order/?repage";
	}
}