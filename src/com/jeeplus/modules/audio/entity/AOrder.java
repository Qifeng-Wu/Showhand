package com.jeeplus.modules.audio.entity;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;

/**
 * 订单Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AOrder extends DataEntity<AOrder> {
	
	private static final long serialVersionUID = 1L;
	private Integer orderId;		// 主键ID
	private String openId;		    // 微信用户主键
	private String orderNo;	    	//订单编号
	private BigDecimal payMoney;	//支付金额
	private String payType;		//支付方式
	private Date payTime;		//支付时间
	private Boolean deleteFlag;   	//删除标记（0：正常，1：删除）	
	
	private List<AOrderItem> orderItemList = Lists.newArrayList();//订单商品列表
	private AFans fans;//粉丝用户信息
	
	public AOrder() {
		super();
	}

	public AOrder(String id){
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

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<AOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<AOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public AFans getFans() {
		return fans;
	}

	public void setFans(AFans fans) {
		this.fans = fans;
	}

}