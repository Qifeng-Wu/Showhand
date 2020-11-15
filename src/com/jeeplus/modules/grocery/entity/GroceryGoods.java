/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 商品信息Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryGoods extends DataEntity<GroceryGoods> {
	
	private static final long serialVersionUID = 1L;
	private Integer goodsId;  //自增益主键
	private Integer classificationId;  //关联的商品分类ID
	private String name;		// 分类名称
	private BigDecimal cprice;  //现价
	private BigDecimal oprice;  //原价
	private String picture;  //图片
	private String photos;  //图片集合
	private String description;  //描述
	private Integer sold;  //已售
	private String lable;  //标签
	private Integer sort;  //排序
	private Boolean isRecommend;// 是否推荐，默认不推荐（1代表推荐，0代表不推荐）
	private Boolean status;		// 是否在前端显示，默认显示（上架、下架，1代表显示，0代表不显示）
	private Boolean deleteFlag;	// 删除标记，默认0（0代表正常，1代表删除）
	
	private GroceryGoodsClassification goodsClassification;//关联的商品分类
	private Integer sortType;//排序类型 供接口调用使用
	private Integer buyCount;//小程序接口chekout时传参使用
	
	public GroceryGoods() {
		super();
	}

	public GroceryGoods(String id){
		super(id);
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
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

	public BigDecimal getCprice() {
		return cprice;
	}

	public void setCprice(BigDecimal cprice) {
		this.cprice = cprice;
	}

	public BigDecimal getOprice() {
		return oprice;
	}

	public void setOprice(BigDecimal oprice) {
		this.oprice = oprice;
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
	
	public Integer getSold() {
		return sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
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

	public GroceryGoodsClassification getGoodsClassification() {
		return goodsClassification;
	}

	public void setGoodsClassification(GroceryGoodsClassification goodsClassification) {
		this.goodsClassification = goodsClassification;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}		
}