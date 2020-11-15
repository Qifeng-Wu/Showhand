package com.jeeplus.modules.audio.entity;


import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 用户免费项目Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AItemFree extends DataEntity<AItemFree> {
	
	private static final long serialVersionUID = 1L;
	private Integer freeId;		// 主键ID
	private String openId;	// 粉丝用户主键
	private Integer type;	    //购买方式（0：购买整个故事，1：购买单个章回）
	private Integer goodsId;	// 商品主键（故事或章回主键）
	private Integer storyId;	// 所属故事主键
	private Date createTime;	//创建时间

	private String title;//后台查询时传参使用
	private AStory story;//故事信息
	private AFans fans;//微信用户信息
	
	public AItemFree() {
		super();
	}

	public AItemFree(String id){
		super(id);
	}

	public Integer getFreeId() {
		return freeId;
	}

	public void setFreeId(Integer freeId) {
		this.freeId = freeId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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