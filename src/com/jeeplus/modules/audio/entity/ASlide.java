package com.jeeplus.modules.audio.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 幻灯片Entity
 * @author stephen
 * @version 2020-5-12
 */
public class ASlide extends DataEntity<ASlide> {
	
	private static final long serialVersionUID = 1L;
	private Integer slideId;		// 主键ID
	private String title;	    	// 标题
	private String picture;	    //图片
	private String description;	    //幻灯片描述
	private Integer sort;    //排序
	private Boolean status;   	 	//是否启用，默认不启用（0：不启用，1：启用）	
	
	public ASlide() {
		super();
	}

	public ASlide(String id){
		super(id);
	}

	public Integer getSlideId() {
		return slideId;
	}

	public void setSlideId(Integer slideId) {
		this.slideId = slideId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	
}