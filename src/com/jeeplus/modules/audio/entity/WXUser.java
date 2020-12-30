package com.jeeplus.modules.audio.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 微信公众号网页授权用户信息Entity
 * @author stephen
 * @version 2020-12-23
 */
public class WXUser extends DataEntity<WXUser> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 主键（用户的唯一标识）
	private String nickname;		// 用户昵称
	private String sex;		// 性别（0：没有设置；1：男；2：女）
	private String province;		// 用户个人资料填写的省份
	private String city;		// 普通用户个人资料填写的城市
	private String country;		// 国家，如中国为CN
	private String headimgurl;		// 头像链接
	private Date createtime;		// 添加时间
	private Date updatetime;		// 跟新时间
	
	
	public WXUser() {
		super();
	}

	public WXUser(String id){
		super(id);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}