package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AOrderItemDao;
import com.jeeplus.modules.audio.entity.AOrderItem;

/**
 * 订单商品详情Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AOrderItemService extends CrudService<AOrderItemDao, AOrderItem> {

	public AOrderItem get(String id) {
		return super.get(id);
	}
	
	public List<AOrderItem> findList(AOrderItem aOrderItem) {
		return super.findList(aOrderItem);
	}
	
	public Page<AOrderItem> findPage(Page<AOrderItem> page, AOrderItem aOrderItem) {
		return super.findPage(page, aOrderItem);
	}
	
	@Transactional(readOnly = false)
	public void save(AOrderItem aOrderItem) {
		super.save(aOrderItem);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AOrderItem aOrderItem) {
		boolean isNewRecord = true;
		if (aOrderItem.getItemId() != null){
			isNewRecord = false;		
		}
		aOrderItem.setIsNewRecord(isNewRecord);
		super.saveCustomId(aOrderItem);
		return aOrderItem.getItemId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AOrderItem aOrderItem) {
		super.delete(aOrderItem);
	}
	
}