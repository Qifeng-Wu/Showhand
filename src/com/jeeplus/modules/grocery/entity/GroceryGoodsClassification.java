/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 商品分类Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryGoodsClassification extends DataEntity<GroceryGoodsClassification> {
	
	private static final long serialVersionUID = 1L;
	private Integer classificationId;  //自增益主键
	private String name;		// 分类名称
	private Integer sort;		// 排序
	private Boolean status;		// 是否显示，默认0（0代表显示，1代表不显示）
	private Boolean deleteFlag;		// 删除标记，默认0（0代表正常，1代表删除）
	
	public GroceryGoodsClassification() {
		super();
	}

	public GroceryGoodsClassification(String id){
		super(id);
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}		
}