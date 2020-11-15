package com.jeeplus.modules.audio.entity;


import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 订单详情Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AOrderItem extends DataEntity<AOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private Integer itemId;		// 主键ID
	private Integer orderId;	// 订单主键（关联订单表）
	private Integer goodsId;	// 商品主键（故事或章回主键）
	private Integer storyId;	// 所属故事主键
	private String openId;	// 粉丝用户主键
	private Integer type;	    //购买方式（0：购买整个故事，1：购买单个章回）
	private String title;		//标题
	private BigDecimal price;	//单价
	private Date createTime;	//创建时间
	
	private AOrder order;//订单信息
	private AStory story;//故事信息
	private AFans fans;//微信用户信息
	
	public AOrderItem() {
		super();
	}

	public AOrderItem(String id){
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

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public AOrder getOrder() {
		return order;
	}

	public void setOrder(AOrder order) {
		this.order = order;
	}

	public AStory getStory() {
		return story;
	}

	public void setStory(AStory story) {
		this.story = story;
	}

	public AFans getFans() {
		return fans;
	}

	public void setFans(AFans fans) {
		this.fans = fans;
	}

}