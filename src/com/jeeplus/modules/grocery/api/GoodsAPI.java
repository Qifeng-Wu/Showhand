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
import com.jeeplus.modules.grocery.service.GroceryGoodsService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 商品信息APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/goods")
public class GoodsAPI extends BaseController {
	
	@Autowired
	private GroceryGoodsService groceryGoodsService;
	
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
	 * 获取显示的推荐的商品信息集合
	 */
	@RequestMapping(value = "recommend")
	@ResponseBody
	public AjaxJson findRecommendList(GroceryGoods groceryGoods,HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		groceryGoods.setStatus(true);
		groceryGoods.setIsRecommend(true);
		groceryGoods.setDeleteFlag(true);
		
		//1.获取综合排序数据
		groceryGoods.setSortType(1);
		List<GroceryGoods> sortList = groceryGoodsService.findList(groceryGoods);
		
		//2.获取销量排序数据
		groceryGoods.setSortType(2);
		List<GroceryGoods> soldList = groceryGoodsService.findList(groceryGoods);

		//3.获取价格升序排序数据
		groceryGoods.setSortType(3);
		List<GroceryGoods> priceAscList = groceryGoodsService.findList(groceryGoods);
		
		//4.获取价格降序排序数据
		groceryGoods.setSortType(4);
		List<GroceryGoods> priceDescList = groceryGoodsService.findList(groceryGoods);
		
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("sortList", sortList);			
		body.put("soldList", soldList);			
		body.put("priceAscList", priceAscList);			
		body.put("priceDescList", priceDescList);			
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 商品信息搜索
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public AjaxJson goodsSearch(GroceryGoods groceryGoods,HttpServletRequest request) {
		String searchword = request.getParameter("searchword");
		AjaxJson ajax = new AjaxJson();	
		groceryGoods.setStatus(true);
		groceryGoods.setDeleteFlag(true);
		groceryGoods.setName(searchword);
		//1.获取综合排序数据
		groceryGoods.setSortType(1);
		List<GroceryGoods> sortList = groceryGoodsService.findList(groceryGoods);

		//2.获取销量排序数据
		groceryGoods.setSortType(2);
		List<GroceryGoods> soldList = groceryGoodsService.findList(groceryGoods);
	
		//3.获取价格升序排序数据
		groceryGoods.setSortType(3);
		List<GroceryGoods> priceAscList = groceryGoodsService.findList(groceryGoods);
		
		//4.获取价格降序排序数据
		groceryGoods.setSortType(4);
		List<GroceryGoods> priceDescList = groceryGoodsService.findList(groceryGoods);
			
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("sortList", sortList);			
		body.put("soldList", soldList);			
		body.put("priceAscList", priceAscList);			
		body.put("priceDescList", priceDescList);			
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 通过主键获取商品信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public AjaxJson goodsInfo(GroceryGoods groceryGoods,HttpServletRequest request) {
		String goodsId = request.getParameter("goodsId");
		AjaxJson ajax = new AjaxJson();	
		groceryGoods = groceryGoodsService.get(goodsId);	
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();		
		body.put("goods", groceryGoods);			
		ajax.setBody(body);
		return ajax;
	}
}