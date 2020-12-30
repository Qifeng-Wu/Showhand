package com.jeeplus.modules.audio.entity;


import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 家庭农场Entity
 * @author stephen
 * @version 2020-12-25
 */
public class AFarm extends DataEntity<AFarm> {
	
	private static final long serialVersionUID = 1L;
	private Integer farmId;		// 主键ID
	private String openId;	    // 用户OPNEID
	private String name;	    // 姓名
	private String phone;	    // 手机号码
	private String room;	    // 房间号
	private String farm;	    // 菜园编号
	private Date getTime;	    // 领取菜园时间
	private Date createTime;	// 创建时间
	
	private Integer filter;	    // 传参使用（sql findList接口过滤使用）
	
	public AFarm() {
		super();
	}

	public AFarm(String id){
		super(id);
	}

	public Integer getFarmId() {
		return farmId;
	}

	public void setFarmId(Integer farmId) {
		this.farmId = farmId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=50, message="姓名必须介于 0 和 50 之间")
	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=50, message="手机号必须介于 0 和 50 之间")
	@ExcelField(title="手机号", align=2, sort=2)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=50, message="房屋号必须介于 0 和 50 之间")
	@ExcelField(title="房屋号", align=2, sort=3)
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Length(min=0, max=50, message="菜园编号必须介于 0 和 50 之间")
	@ExcelField(title="菜园编号", align=2, sort=4)
	public String getFarm() {
		return farm;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="秒杀时间", type=1, align=2, sort=5)
	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFilter() {
		return filter;
	}

	public void setFilter(Integer filter) {
		this.filter = filter;
	}
}