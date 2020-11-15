/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;

/**
 * 订单信息Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryOrder extends DataEntity<GroceryOrder> {
	
	private static final long serialVersionUID = 1L;
	private Integer orderId;  //自增益主键
	private String openId;  //粉丝用户openId
	private Integer addressId;  //收货地址Id
	private String orderNo;		// 订单编号
	private BigDecimal payMoney;	// 支付金额
	private String payType;		// 购买数量支付方式
	private Date payTime;		// 支付时间	
	private Date sendTime;		// 发货时间
	private Date receiveTime;		// 确认收货时间
	private String remark;		// 买家留言
	private Integer status;		// 订单状态（1代表已支付(待发货)，2代表已发货，3代表已收货）
	private Boolean deleteFlag;	// 删除标记，默认1（1代表正常，0代表删除）
	
	private List<GroceryOrderItem> orderItemList = Lists.newArrayList();//订单商品列表
	private GroceryFans fans;//粉丝用户信息
	private GroceryFansAddress fansAddress;//粉丝用户收货地址
	private Integer buyCount;//共多少件商品，小程序接口订单列表时传参使用
	
	public GroceryOrder() {
		super();
	}

	public GroceryOrder(String id){
		super(id);
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<GroceryOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<GroceryOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public GroceryFans getFans() {
		return fans;
	}

	public void setFans(GroceryFans fans) {
		this.fans = fans;
	}

	public GroceryFansAddress getFansAddress() {
		return fansAddress;
	}

	public void setFansAddress(GroceryFansAddress fansAddress) {
		this.fansAddress = fansAddress;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

}