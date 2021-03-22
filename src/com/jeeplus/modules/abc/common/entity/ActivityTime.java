package com.jeeplus.modules.abc.common.entity;


import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 活动时间Entity
 * @author stephen
 * @version 2021-2-22
 */
public class ActivityTime extends DataEntity<ActivityTime> {
	
	private static final long serialVersionUID = 1L;
	private String timeId;	    // 主键
	private Integer type;		//活动类型
	private Date startTime; //开始时间
	private Date endTime; //结束时间
	
	public ActivityTime() {
		super();
	}

	public ActivityTime(String id){
		super(id);
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}