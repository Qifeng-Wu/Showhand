/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.grocery.entity.GroceryOrder;
import com.jeeplus.modules.grocery.entity.GroceryOrderItem;
import com.jeeplus.modules.grocery.service.GroceryOrderItemService;
import com.jeeplus.modules.grocery.service.GroceryOrderService;

/**
 * 粉丝订单APIController
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "g-api/order")
public class OrderAPI extends BaseController {
		
	@Autowired
	private GroceryOrderService groceryOrderService;
	@Autowired
	private GroceryOrderItemService groceryOrderItemService;
	
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
	 * 粉丝订单集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(GroceryOrder groceryOrder,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		String orderStatus = request.getParameter("orderStatus");
		if("0".equals(orderStatus)){
			groceryOrder.setStatus(null);
		}else if("1".equals(orderStatus)) {
			groceryOrder.setStatus(1);
		}else if("2".equals(orderStatus)){
			groceryOrder.setStatus(2);
		}else if("3".equals(orderStatus)){
			groceryOrder.setStatus(3);
		}
		groceryOrder.setDeleteFlag(true);
		List<GroceryOrder> list = groceryOrderService.findList(groceryOrder);
		List<GroceryOrderItem> items;
		GroceryOrderItem item = new GroceryOrderItem();
		Integer buyCount = 0;
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				item.setOrderId(list.get(i).getOrderId());
				items = groceryOrderItemService.findList(item);
				list.get(i).setOrderItemList(items);
				for(int j=0; j<items.size(); j++) {
					buyCount += items.get(j).getQuantity();
				}
				list.get(i).setBuyCount(buyCount);
			}
		}		
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取订单详情信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public synchronized AjaxJson info(GroceryOrder groceryOrder, GroceryOrderItem groceryOrderItem) {
		AjaxJson ajax = new AjaxJson();	
		groceryOrder = groceryOrderService.get(groceryOrder);
		if(groceryOrder!=null) {
			groceryOrderItem.setOrderId(groceryOrder.getOrderId());
			groceryOrder.setOrderItemList(groceryOrderItemService.findList(groceryOrderItem));
		}
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("order", groceryOrder);			
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 买家确认收货
	 */
	@RequestMapping(value = "confirmGet")
	@ResponseBody
	public synchronized AjaxJson confirmGet(GroceryOrder groceryOrder, RedirectAttributes redirectAttributes) {
		AjaxJson ajax = new AjaxJson();	
		groceryOrder.setReceiveTime(new Date());
		groceryOrderService.customSave(groceryOrder);
		return ajax;
	}
	/**
	 * 卖家确认发货
	 */
	@RequestMapping(value = "confirmSend")
	@ResponseBody
	public synchronized AjaxJson confirmSend(GroceryOrder groceryOrder, RedirectAttributes redirectAttributes) {
		AjaxJson ajax = new AjaxJson();	
		groceryOrder.setSendTime(new Date());
		groceryOrderService.customSave(groceryOrder);
		return ajax;
	}
	
	/**
	 * 订单管理订单搜索
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public AjaxJson orderSearch(GroceryOrder groceryOrder,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		groceryOrder.setDeleteFlag(true);
		List<GroceryOrder> list = groceryOrderService.findList(groceryOrder);
		List<GroceryOrderItem> items;
		GroceryOrderItem item = new GroceryOrderItem();
		Integer buyCount = 0;
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				item.setOrderId(list.get(i).getOrderId());
				items = groceryOrderItemService.findList(item);
				list.get(i).setOrderItemList(items);
				for(int j=0; j<items.size(); j++) {
					buyCount += items.get(j).getQuantity();
				}
				list.get(i).setBuyCount(buyCount);
			}
		}
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public synchronized AjaxJson delete(GroceryOrder groceryOrder, RedirectAttributes redirectAttributes) {
		groceryOrder.setDeleteFlag(false);
		AjaxJson ajax = new AjaxJson();	
		groceryOrderService.delete(groceryOrder);
		return ajax;
	}
}