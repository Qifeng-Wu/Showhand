package com.jeeplus.modules.audio.entity;


import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 章Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AChapter extends DataEntity<AChapter> {
	
	private static final long serialVersionUID = 1L;
	private Integer chapterId;		// 主键ID
	private Integer storyId;		// 关联故事表主键
	private String title;	    	// 标题
	private BigDecimal price;	    //价格
	private Integer sort;		// 排序
	private Boolean isFree;		// 是否免费，默认不免费（1代表免费，0代表不免费）
	private Boolean status;		// 是否在前端显示，默认显示（上架、下架，1代表显示，0代表不显示）
	private Boolean deleteFlag;   	//删除标记（0：正常，1：删除）	
	private String nodes;	    	// 节点设置字符串
	private String musics;	    	// 背景设置字符串
	private Date updateTime;   //跟新时间
	
	private AStory story;//关联的商品分类
	public AChapter() {
		super();
	}

	public AChapter(String id){
		super(id);
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public String getMusics() {
		return musics;
	}

	public void setMusics(String musics) {
		this.musics = musics;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public AStory getStory() {
		return story;
	}

	public void setStory(AStory story) {
		this.story = story;
	}

}