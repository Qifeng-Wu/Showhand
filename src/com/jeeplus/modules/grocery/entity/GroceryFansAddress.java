/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 收货地址Entity
 * @author stephen
 * @version 2019-10-25
 */
public class GroceryFansAddress extends DataEntity<GroceryFansAddress> {
	
	private static final long serialVersionUID = 1L;
	private Integer addressId;		// 主键ID
	private String openId;	    	// 粉丝主键ID
	private String name;	    //姓名
	private String sex;    //性别
	private String phone;    //手机号码
	private String province;    //省份
	private String city;    //城市
	private String area;    //区
	private String address;    //详细地址
	private Boolean status;    //是否为默认地址，（1代表默认，0代表不默认）
	private Boolean deleteFlag;   //删除标记，默认1（1代表正常，0代表删除）
	
	public GroceryFansAddress() {
		super();
	}

	public GroceryFansAddress(String id){
		super(id);
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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