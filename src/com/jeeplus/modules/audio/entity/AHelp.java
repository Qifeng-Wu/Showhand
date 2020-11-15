package com.jeeplus.modules.audio.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 帮助提示中心Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AHelp extends DataEntity<AHelp> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private Boolean state;		// 是否显示，默认1（1代表显示，0代表不显示）
	private Integer sort;		// 显示顺序
	
	public AHelp() {
		super();
	}

	public AHelp(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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