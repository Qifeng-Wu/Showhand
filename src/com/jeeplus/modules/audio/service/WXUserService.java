package com.jeeplus.modules.audio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.audio.dao.WXUserDao;
import com.jeeplus.modules.audio.entity.WXUser;

/**
 * 微信公众号网页授权用户信息Service
 * @author stephen
 * @version 2020-12-23
 */
@Service
@Transactional(readOnly = true)
public class WXUserService extends CrudService<WXUserDao, WXUser> {

	public WXUser get(String id) {
		return super.get(id);
	}
	
	public List<WXUser> findList(WXUser wxUser) {
		return super.findList(wxUser);
	}
	
	public Page<WXUser> findPage(Page<WXUser> page, WXUser wxUser) {
		return super.findPage(page, wxUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WXUser wxUser) {
		super.save(wxUser);
	}
	
	@Transactional(readOnly = false)
	public String customSave(WXUser wxUser) {
		boolean isNewRecord = true;
		if (wxUser.getOpenId() != null){
			isNewRecord = false;		
		}
		wxUser.setIsNewRecord(isNewRecord);
		super.saveCustomId(wxUser);
		return wxUser.getOpenId();
	}
	
	@Transactional(readOnly = false)
	public void delete(WXUser wxUser) {
		super.delete(wxUser);
	}
}