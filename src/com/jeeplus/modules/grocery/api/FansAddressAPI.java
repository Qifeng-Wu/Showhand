/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.grocery.entity.GroceryFansAddress;
import com.jeeplus.modules.grocery.service.GroceryFansAddressService;

/**
 * 收货地址APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/address")
public class FansAddressAPI extends BaseController {
		
	@Autowired
	private GroceryFansAddressService groceryFansAddressService;
	
	@ModelAttribute
	public GroceryFansAddress get(@RequestParam(required=false) String id) {
		GroceryFansAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFansAddressService.get(id);
		}
		if (entity == null){
			entity = new GroceryFansAddress();
		}
		return entity;
	}
	
	/**
	 * 收货地址提交保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(GroceryFansAddress groceryFansAddress,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		groceryFansAddress.setStatus(false);
		groceryFansAddress.setDeleteFlag(true);
		groceryFansAddressService.customSave(groceryFansAddress);
		return ajax;
	}
	
	/**
	 * 粉丝收货地址集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(GroceryFansAddress groceryFansAddress,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		groceryFansAddress.setDeleteFlag(true);
		List<GroceryFansAddress> list = groceryFansAddressService.findList(groceryFansAddress);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 设置默认地址
	 */
	@RequestMapping(value = "setDefault")
	@ResponseBody
	@Transactional
	public synchronized AjaxJson settingAddress(GroceryFansAddress groceryFansAddress,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		groceryFansAddress.setStatus(false);
		groceryFansAddressService.settingAddressStatus(groceryFansAddress);//将所有地址改为非默认状态
		groceryFansAddress.setStatus(true);
		groceryFansAddressService.customSave(groceryFansAddress);//将这个地址改为默认地址
		return ajax;
	}
	
	/**
	 * 获取粉丝默认收货地址
	 */
	@RequestMapping(value = "default")
	@ResponseBody
	public AjaxJson findDefault(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		String addressId = request.getParameter("addressId");
		GroceryFansAddress groceryFansAddress = new GroceryFansAddress();
		if(addressId!=null && !addressId.isEmpty() && !"undefined".equals(addressId)) {
			groceryFansAddress =  groceryFansAddressService.get(addressId);
		}else {
			groceryFansAddress.setDeleteFlag(true);
			groceryFansAddress.setStatus(true);
			List<GroceryFansAddress> list = groceryFansAddressService.findList(groceryFansAddress);
			if(list!=null&&list.size()>0) {
				groceryFansAddress = list.get(0);
			}
		}
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("address", groceryFansAddress);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public synchronized AjaxJson delete(GroceryFansAddress groceryFansAddress, RedirectAttributes redirectAttributes) {
		groceryFansAddress.setDeleteFlag(false);
		AjaxJson ajax = new AjaxJson();	
		groceryFansAddressService.delete(groceryFansAddress);
		return ajax;
	}
}