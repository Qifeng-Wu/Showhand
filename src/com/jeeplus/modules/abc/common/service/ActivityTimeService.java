package com.jeeplus.modules.abc.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.abc.common.dao.ActivityTimeDao;
import com.jeeplus.modules.abc.common.entity.ActivityTime;

/**
 * 活动时间Service
 * @author stephen
 * @version 2021-2-22
 */
@Service
@Transactional(readOnly = true)
public class ActivityTimeService extends CrudService<ActivityTimeDao, ActivityTime> {

	public ActivityTime get(String id) {
		return super.get(id);
	}
	
	public List<ActivityTime> findList(ActivityTime activityTime) {
		return super.findList(activityTime);
	}
	
	public Page<ActivityTime> findPage(Page<ActivityTime> page, ActivityTime activityTime) {
		return super.findPage(page, activityTime);
	}
	
	@Transactional(readOnly = false)
	public void save(ActivityTime activityTime) {
		super.save(activityTime);
	}
	
	@Transactional(readOnly = false)
	public String customSave(ActivityTime activityTime) {
		boolean isNewRecord = true;
		if (activityTime.getTimeId() != null){
			isNewRecord = false;		
		}
		activityTime.setIsNewRecord(isNewRecord);
		super.saveCustomId(activityTime);
		return activityTime.getTimeId();
	}
	
	@Transactional(readOnly = false)
	public void delete(ActivityTime activityTime) {
		super.delete(activityTime);
	}
	
}