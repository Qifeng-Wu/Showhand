package com.jeeplus.modules.grocery.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 微信信息Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryFans extends DataEntity<GroceryFans> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 主键
	private Boolean clerk;		// 订单管理员标识，默认是0（0代表不是，1代表是）
	private String nickname;		// 昵称
	private String avatar;		// 头像
	private String sex;		// 性别（0：没有设置；1：男；2：女）
	private Date addtime;		// 添加时间
	private Date updatetime;		// 跟新时间
	
	public GroceryFans() {
		super();
	}

	public GroceryFans(String id){
		super(id);
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Boolean getClerk() {
		return clerk;
	}

	public void setClerk(Boolean clerk) {
		this.clerk = clerk;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}		

}