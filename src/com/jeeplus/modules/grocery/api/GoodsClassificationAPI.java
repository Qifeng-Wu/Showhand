/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.grocery.entity.GroceryGoods;
import com.jeeplus.modules.grocery.entity.GroceryGoodsClassification;
import com.jeeplus.modules.grocery.service.GroceryGoodsClassificationService;
import com.jeeplus.modules.grocery.service.GroceryGoodsService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 商品分类APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/classification")
public class GoodsClassificationAPI extends BaseController {
		
	@Autowired
	private GroceryGoodsClassificationService groceryGoodsClassificationService;
	@Autowired
	private GroceryGoodsService groceryGoodsService;
	
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
	 * 获取显示的分类集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(GroceryGoodsClassification groceryGoodsClassification,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		groceryGoodsClassification.setStatus(true);
		groceryGoodsClassification.setDeleteFlag(true);

		List<GroceryGoodsClassification> list = groceryGoodsClassificationService.findList(groceryGoodsClassification);		
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);						
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取分类下的商品集合
	 */
	@RequestMapping(value = "goods")
	@ResponseBody
	public AjaxJson findGoods(GroceryGoods groceryGoods,HttpServletRequest request) {	
		String classificationId = request.getParameter("classificationId");
		AjaxJson ajax = new AjaxJson();	
		groceryGoods.setStatus(true);
		groceryGoods.setDeleteFlag(true);
		groceryGoods.setClassificationId(Integer.parseInt(classificationId));
		
		List<GroceryGoods> list = groceryGoodsService.findList(groceryGoods);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);						
		ajax.setBody(body);
		return ajax;
	}
}