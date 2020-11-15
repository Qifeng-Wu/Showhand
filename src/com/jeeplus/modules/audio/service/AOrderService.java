package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.AOrderDao;
import com.jeeplus.modules.audio.entity.AOrder;

/**
 * 订单Service
 * @author stephen
 * @version 2020-5-12
 */
@Service
@Transactional(readOnly = true)
public class AOrderService extends CrudService<AOrderDao, AOrder> {

	public AOrder get(String id) {
		return super.get(id);
	}
	
	public List<AOrder> findList(AOrder aOrder) {
		return super.findList(aOrder);
	}
	
	public Page<AOrder> findPage(Page<AOrder> page, AOrder aOrder) {
		return super.findPage(page, aOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(AOrder aOrder) {
		super.save(aOrder);
	}
	
	@Transactional(readOnly = false)
	public Integer customSave(AOrder aOrder) {
		boolean isNewRecord = true;
		if (aOrder.getOrderId() != null){
			isNewRecord = false;		
		}
		aOrder.setIsNewRecord(isNewRecord);
		super.saveCustomId(aOrder);
		return aOrder.getOrderId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AOrder aOrder) {
		super.delete(aOrder);
	}
	
}