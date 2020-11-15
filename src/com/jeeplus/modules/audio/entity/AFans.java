package com.jeeplus.modules.audio.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 微信信息Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AFans extends DataEntity<AFans> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 主键
	private String cid;			//身份编号
	private String nickname;		// 昵称
	private String avatar;		// 头像
	private String sex;		// 性别（0：没有设置；1：男；2：女）
	private Date addtime;		// 添加时间
	private Date updatetime;		// 跟新时间
	private String status;		// 状态（默认0；0正常，1禁用）
	private String remark;		// 备注
	
	private Integer storyId;		// 故事主键 后台故事用户数据统计筛选使用
	
	public AFans() {
		super();
	}

	public AFans(String id){
		super(id);
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}		

}