/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 订单商品信息Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryOrderItem extends DataEntity<GroceryOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private Integer itemId;  //自增益主键
	private Integer orderId;  //订单主键（关联订单表）
	private Integer goodsId;		// 商品主键（关联商品表）
	private String name;		// 商品名称
	private BigDecimal price;	// 商品单价	
	private Integer quantity;		// 购买数量	
	private String picture;		// 商品图片
	private Date createTime;		// 创建时间
		
	private GroceryOrder order;//订单信息
	
	public GroceryOrderItem() {
		super();
	}

	public GroceryOrderItem(String id){
		super(id);
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public GroceryOrder getOrder() {
		return order;
	}

	public void setOrder(GroceryOrder order) {
		this.order = order;
	}

}