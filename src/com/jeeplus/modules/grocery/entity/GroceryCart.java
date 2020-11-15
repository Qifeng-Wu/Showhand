/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 购物车Entity
 * @author stephen
 * @version 2019-10-20
 */
public class GroceryCart extends DataEntity<GroceryCart> {
	
	private static final long serialVersionUID = 1L;
	private Integer cartId;  //自增益主键
	private String openId;		//粉丝用户唯一标识
	private Integer goodsId;  //商品主键
	private Integer quantity; //数量
	private Date createTime;   	//创建时间	
	private Boolean payFlag;   	//是否已结算（已支付购买）
	private Date payTime;   	//购买时间
	
	private GroceryFans fans;   	//用户实体
	private GroceryGoods goods;   	//商品实体
	
	public GroceryCart() {
		super();
	}

	public GroceryCart(String id){
		super(id);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(Boolean payFlag) {
		this.payFlag = payFlag;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public GroceryFans getFans() {
		return fans;
	}

	public void setFans(GroceryFans fans) {
		this.fans = fans;
	}

	public GroceryGoods getGoods() {
		return goods;
	}

	public void setGoods(GroceryGoods goods) {
		this.goods = goods;
	}
}