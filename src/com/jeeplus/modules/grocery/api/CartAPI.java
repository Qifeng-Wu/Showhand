/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.grocery.entity.GroceryCart;
import com.jeeplus.modules.grocery.entity.GroceryGoods;
import com.jeeplus.modules.grocery.service.GroceryCartService;
import com.jeeplus.modules.grocery.service.GroceryGoodsService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 购物车APIController
 * @author stephen
 * @version 2019-10-23
 */
@Controller
@RequestMapping(value = "g-api/cart")
public class CartAPI extends BaseController {
		
	@Autowired
	private GroceryCartService groceryCartService;
	
	@Autowired
	private GroceryGoodsService groceryGoodsService;
	
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
	 * 获取该用户下的购物车列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(GroceryCart groceryCart,HttpServletRequest request) {	
		String openId = request.getParameter("openId");
		AjaxJson ajax = new AjaxJson();	
		groceryCart.setOpenId(openId);
		groceryCart.setPayFlag(false);
		List<GroceryCart> list = groceryCartService.findList(groceryCart);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 加入购物车
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(GroceryCart groceryCart,HttpServletRequest request) {	
		String openId = request.getParameter("openId");
		String goodsId = request.getParameter("goodsId");
		String quantity = request.getParameter("quantity");
		AjaxJson ajax = new AjaxJson();	
		if(openId==null||openId.isEmpty()){
			ajax.setSuccess(false);
			ajax.setMsg("提交参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		
		groceryCart.setOpenId(openId);
		groceryCart.setGoodsId(Integer.parseInt(goodsId));
		groceryCart.setQuantity(Integer.parseInt(quantity));
		groceryCart.setCreateTime(new Date());
		groceryCart.setPayFlag(false);
		groceryCartService.save(groceryCart);
		return ajax;
	}
	
	/**
	 * 跟新购物车数量信息
	 */
	@RequestMapping(value = "updateQuantity")
	@ResponseBody
	public synchronized AjaxJson updateQuantity(GroceryCart groceryCart) {	
		AjaxJson ajax = new AjaxJson();
		groceryCartService.customSave(groceryCart);
		return ajax;
	}
	
	/**
	 * 删除购物车商品
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public synchronized AjaxJson delete(GroceryCart groceryCart) {	
		AjaxJson ajax = new AjaxJson();
		groceryCartService.delete(groceryCart);
		return ajax;
	}
	
	/**
	 * 通过catId获取商品信息（购物车checkout使用）
	 */
	@RequestMapping(value = "checkout")
	@ResponseBody
	public AjaxJson checkout(String cartIds, GroceryCart groceryCart, GroceryGoods groceryGoods) {	
		AjaxJson ajax = new AjaxJson();
		String idArray[] = cartIds.split(",");
		List<GroceryGoods> list = new ArrayList<GroceryGoods>();
		BigDecimal goodsMoney = new BigDecimal(0);
		for(String cartId : idArray){
			groceryCart = groceryCartService.get(cartId);
			groceryGoods = groceryGoodsService.get(groceryCart.getGoodsId().toString());
			groceryGoods.setBuyCount(groceryCart.getQuantity());
			list.add(groceryGoods);
			goodsMoney = goodsMoney.add(groceryCart.getGoods().getCprice().multiply(new BigDecimal(groceryCart.getQuantity())));
			if(!groceryCart.getGoods().getStatus() ||!groceryCart.getGoods().getDeleteFlag()) {
				ajax.setSuccess(false);
				ajax.setMsg("购物车有商品已下架");	
				break;
			}
		}
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);	
		body.put("goodsMoney", goodsMoney);	
		ajax.setBody(body);
		return ajax;
	}
}