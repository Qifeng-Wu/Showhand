/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 平台信息Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryPlatform extends DataEntity<GroceryPlatform> {
	
	private static final long serialVersionUID = 1L;
	private Integer platformId;  //自增益主键
	private String name;		// 名称
	private String picture;  //图片
	private String photos;  //图片集合
	private String description;  //描述
	private String phone;  //联系电话
	private String startTime;  //营业开始时间
	private String endTime;  //营业结束时间
	private String address;  //地址
	private String notice;  //公告
	private String leftPic;  //首页左边图
	private String rightTopPic;  //首页右边上方图片
	private String rightBottomPic;  //首页右边下方图片
	
		
	public GroceryPlatform() {
		super();
	}

	public GroceryPlatform(String id){
		super(id);
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getLeftPic() {
		return leftPic;
	}

	public void setLeftPic(String leftPic) {
		this.leftPic = leftPic;
	}

	public String getRightTopPic() {
		return rightTopPic;
	}

	public void setRightTopPic(String rightTopPic) {
		this.rightTopPic = rightTopPic;
	}

	public String getRightBottomPic() {
		return rightBottomPic;
	}

	public void setRightBottomPic(String rightBottomPic) {
		this.rightBottomPic = rightBottomPic;
	}
	
}