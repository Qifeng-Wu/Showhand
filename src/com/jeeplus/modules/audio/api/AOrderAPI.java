/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.audio.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.jeeplus.modules.audio.entity.AChapter;
import com.jeeplus.modules.audio.entity.AOrder;
import com.jeeplus.modules.audio.entity.AOrderItem;
import com.jeeplus.modules.audio.service.AChapterService;
import com.jeeplus.modules.audio.service.AOrderItemService;
import com.jeeplus.modules.audio.service.AOrderService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 微信用户订单APIController
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/order")
public class AOrderAPI extends BaseController {
		
	@Autowired
	private AOrderService aOrderService;
	@Autowired
	private AOrderItemService aOrderItemService;
	@Autowired
	private AChapterService aChapterService;
	
	@ModelAttribute
	public AOrder get(@RequestParam(required=false) String id) {
		AOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aOrderService.get(id);
		}
		if (entity == null){
			entity = new AOrder();
		}
		return entity;
	}
	
	/**
	 * 粉丝订单集合
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson findList(AOrder aOrder) {	
		AjaxJson ajax = new AjaxJson();	
		aOrder.setDeleteFlag(false);
		List<AOrder> list = aOrderService.findList(aOrder);
		List<AOrderItem> items;
		AOrderItem item = new AOrderItem();
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				item.setOrderId(list.get(i).getOrderId());
				items = aOrderItemService.findList(item);
				list.get(i).setOrderItemList(items);
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
	public synchronized AjaxJson info(AOrder aOrder, AOrderItem aOrderItem) {
		AjaxJson ajax = new AjaxJson();	
		aOrder = aOrderService.get(aOrder);
		if(aOrder!=null) {
			aOrderItem.setOrderId(aOrder.getOrderId());
			aOrder.setOrderItemList(aOrderItemService.findList(aOrderItem));
		}
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("order", aOrder);			
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 订单管理订单搜索
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public AjaxJson orderSearch(AOrder aOrder, HttpServletRequest request) {	
		String searchWords = request.getParameter("searchWords");
		AjaxJson ajax = new AjaxJson();	
		aOrder.setOrderNo(searchWords);
		aOrder.setDeleteFlag(false);
		List<AOrder> list = aOrderService.findList(aOrder);
		List<AOrderItem> items;
		AOrderItem item = new AOrderItem();
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				item.setOrderId(list.get(i).getOrderId());
				items = aOrderItemService.findList(item);
				list.get(i).setOrderItemList(items);
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
	@ResponseBody
	public synchronized AjaxJson delete(AOrder aOrder) {
		aOrder.setDeleteFlag(true);
		AjaxJson ajax = new AjaxJson();	
		aOrderService.delete(aOrder);
		return ajax;
	}
	
	/**
	 * 订单保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(AOrder aOrder, HttpServletRequest request) {
		String openId = request.getParameter("openId");
		String type = request.getParameter("type");		
		String storyId = request.getParameter("storyId");
		String title = request.getParameter("title");
		String chapterIds = request.getParameter("chapterIds");
		String payMoney = request.getParameter("payMoney");
		aOrder.setOpenId(openId);
		aOrder.setDeleteFlag(false);
		aOrder.setOrderNo(createOrderNumber());
		aOrder.setPayType("微信支付");
		aOrder.setPayTime(new Date());
		AjaxJson ajax = new AjaxJson();	
		aOrderService.customSave(aOrder);	
		if("0".equals(type)) {//购买故事
			AOrderItem aOrderItem = new AOrderItem();
			aOrderItem.setOrderId(aOrder.getOrderId());
			aOrderItem.setStoryId(Integer.parseInt(storyId));
			aOrderItem.setOpenId(openId);
			aOrderItem.setType(0);
			aOrderItem.setGoodsId(Integer.parseInt(storyId));
			aOrderItem.setTitle(title);
			aOrderItem.setPrice(new BigDecimal(payMoney));
			aOrderItem.setCreateTime(new Date());
			aOrderItemService.customSave(aOrderItem);
		}else {
			String chapterIdArray[] = chapterIds.split(",");
			AChapter chapter = new AChapter();
			for(String chapterId : chapterIdArray){
				AOrderItem aOrderItem = new AOrderItem();
				aOrderItem.setOrderId(aOrder.getOrderId());
				chapter = aChapterService.get(chapterId);
				if(chapter != null) {
					aOrderItem.setType(1);
					aOrderItem.setStoryId(Integer.parseInt(storyId));
					aOrderItem.setOpenId(openId);
					aOrderItem.setGoodsId(chapter.getChapterId());
					aOrderItem.setTitle(chapter.getTitle());
					aOrderItem.setPrice(chapter.getPrice());
					aOrderItem.setCreateTime(new Date());
					aOrderItemService.customSave(aOrderItem);
				}				
			}
		}			
		return ajax;
	}
	//生成订单编号
	public static String createOrderNumber(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String serialnumber = sdf.format(date);
		String time = String.valueOf(System.currentTimeMillis());
		return "SH"+serialnumber+time.substring(5);
	}
	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String serialnumber = sdf.format(date);
		System.out.println(serialnumber);
		System.out.println(createOrderNumber().length());
		System.out.println(createOrderNumber());		
	}
}