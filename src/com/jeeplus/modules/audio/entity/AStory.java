package com.jeeplus.modules.audio.entity;


import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 故事Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AStory extends DataEntity<AStory> {
	
	private static final long serialVersionUID = 1L;
	private Integer storyId;		// 主键ID
	private String title;	    	// 标题
	private BigDecimal price;	    //价格
	private String remark;	    	// 备注
	private String picture;	    	// 缩略图
	private Integer sort;		 // 排序
	private Boolean status;		 // 是否在前端显示，默认显示（上架、下架，1代表显示，0代表不显示）
	private Boolean recommend; //是否推荐（0：正常，1：推荐）
	private Boolean deleteFlag;   	//删除标记（0：正常，1：删除）	
	
	private Integer fansCount;   	//该故事下粉丝用户数量（后台数据统计传参使用）
	private BigDecimal sumMoney;   	//该故事下用户交易额数量（后台数据统计传参使用）
	private Integer chapterCount;   	//该故事下的章回数量（小程序接口传参使用）
	
	private String openId;   	//后台分配故事该用户时用于过滤已被分配的故事
	
	public AStory() {
		super();
	}

	public AStory(String id){
		super(id);
	}

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getRecommend() {
		return recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public Integer getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(Integer chapterCount) {
		this.chapterCount = chapterCount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}