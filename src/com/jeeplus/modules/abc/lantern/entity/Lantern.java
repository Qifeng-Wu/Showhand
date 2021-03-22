package com.jeeplus.modules.abc.lantern.entity;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 元宵节猜灯谜Entity
 * @author stephen
 * @version 2021-2-22
 */
public class Lantern extends DataEntity<Lantern> {
	
	private static final long serialVersionUID = 1L;
	private String openId;	    // 主键 用户OPNEID
	private Integer status;		//状态（0：初始状态；1：已答题结束；2：已提交信息）
	private String score;		//分数
	private Date answerStartTime; //答题开始时间
	private Date answerEndTime; //答题结束时间
	private String spendTime;	//答题用时
	private String project;		//项目名称
	private String room;	    // 房间号
	private String name;	    // 姓名
	private String phone;	    // 手机号码
	private String address;	    // 地址
	private Date infoTime;	    // 提交信息时间
	private Date createTime;	// 创建时间
	private Date updateTime;	// 跟新时间
	
	private Integer filter;	    // 传参使用（sql findList接口过滤使用）(搜索前100名过滤使用)
	
	public Lantern() {
		super();
	}

	public Lantern(String id){
		super(id);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="分数", align=2, sort=6)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	public Date getAnswerStartTime() {
		return answerStartTime;
	}

	public void setAnswerStartTime(Date answerStartTime) {
		this.answerStartTime = answerStartTime;
	}

	public Date getAnswerEndTime() {
		return answerEndTime;
	}
	
	@ExcelField(title="用时", align=2, sort=7)
	public String getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}

	public void setAnswerEndTime(Date answerEndTime) {
		this.answerEndTime = answerEndTime;
	}
	
	@ExcelField(title="项目名称", align=2, sort=1)
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	@ExcelField(title="房号", align=2, sort=2)
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	@ExcelField(title="业主姓名", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="手机号码", align=2, sort=4)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="收货地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(Date infoTime) {
		this.infoTime = infoTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getFilter() {
		return filter;
	}

	public void setFilter(Integer filter) {
		this.filter = filter;
	}
	
}