package com.jeeplus.modules.audio.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 客户意见反馈Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AFeedback extends DataEntity<AFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		//粉丝用户唯一标识
	private String type;		//类型
	private String description;		//内容
	private String pictures;   	//图片集
	private Date createTime;   	//创建时间	
	
	private AFans fans;   	//用户实体
	
	public AFeedback() {
		super();
	}

	public AFeedback(String id){
		super(id);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public AFans getFans() {
		return fans;
	}

	public void setFans(AFans fans) {
		this.fans = fans;
	}

}