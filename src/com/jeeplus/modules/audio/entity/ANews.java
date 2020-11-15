package com.jeeplus.modules.audio.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 新闻资讯Entity
 * @author stephen
 * @version 2020-5-12
 */
public class ANews extends DataEntity<ANews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String label;		// 标签
	private String summary;		// 摘要
	private String picture;		// 缩略图
	private String content;		// 内容
	private Boolean state;		// 是否显示，默认1（1代表显示，0代表不显示）
	private Integer sort;		// 显示顺序
	
	public ANews() {
		super();
	}

	public ANews(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}