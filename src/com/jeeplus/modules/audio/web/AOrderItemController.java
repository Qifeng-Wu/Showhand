/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.audio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AOrderItem;
import com.jeeplus.modules.audio.service.AOrderItemService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 订单商品详情Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/order/item")
public class AOrderItemController extends BaseController {

	@Autowired
	private AOrderItemService aOrderItemService;
	
	@ModelAttribute
	public AOrderItem get(@RequestParam(required=false) String id) {
		AOrderItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aOrderItemService.get(id);
		}
		if (entity == null){
			entity = new AOrderItem();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:order:item:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aOrderItem")AOrderItem aOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AOrderItem> page = aOrderItemService.findPage(new Page<AOrderItem>(request, response), aOrderItem); 
		model.addAttribute("page", page);
		return "modules/audio/orderItemList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(AOrderItem aOrderItem, Model model) {
		model.addAttribute("aOrderItem", aOrderItem);
		return "modules/audio/orderItemForm";
	}
	
}