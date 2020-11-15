/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 运费Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryFreight extends DataEntity<GroceryFreight> {
	
	private static final long serialVersionUID = 1L;
	private Integer freightId;  //自增益主键
	private String province;		// 省份名称
	private Integer freight;		// 运费
	private Date createTime;		// 创建时间
	
	public GroceryFreight() {
		super();
	}

	public GroceryFreight(String id){
		super(id);
	}

	public Integer getFreightId() {
		return freightId;
	}

	public void setFreightId(Integer freightId) {
		this.freightId = freightId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
}