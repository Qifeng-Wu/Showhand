/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.audio.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AFarm;
import com.jeeplus.modules.audio.service.AFarmService;

/**
 * 家庭菜园农场APIController
 * @author stephen
 * @version 2020-12-25
 */
@Controller
@RequestMapping(value = "a-api/farm")
public class AFarmAPI extends BaseController {

	@Autowired
	private AFarmService aFarmService;
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public synchronized AjaxJson save(AFarm aFarm, HttpServletRequest request) {	
		
		String openId = request.getParameter("openId");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String room = request.getParameter("room");
		String farm = request.getParameter("farm");
		AjaxJson ajax = new AjaxJson();	

		if(aFarm.getFarmId().equals(0)) {
			//判断房间是否存在
			AFarm farmer1 = new AFarm();
			farmer1.setRoom(room);
			List<AFarm> list1 = aFarmService.findList(farmer1);
			if(list1!=null && list1.size()>0) {
				ajax.setMsg("该房号已被使用啦");
				ajax.setErrorCode("1");
				return ajax;
			}
			//判断当前用户是否已经提交
			AFarm farmer2 = new AFarm();
			farmer2.setOpenId(openId);
			List<AFarm> list2 = aFarmService.findList(farmer2);
			if(list2!=null && list2.size()>0) {
				ajax.setMsg("您已经提交过信息啦");
				ajax.setErrorCode("2");
				return ajax;
			}
			aFarm.setFarmId(null);
			aFarm.setOpenId(openId);
			aFarm.setName(name);
			aFarm.setPhone(phone);
			aFarm.setRoom(room);
			aFarm.setFarm(null);
			aFarm.setCreateTime(new Date());
		}else {
			//判断这个菜园是否被秒杀
			aFarm.setFarm(farm);
			List<AFarm> list = aFarmService.findList(aFarm);
			if(list!=null && list.size()>0) {
				ajax.setMsg("该菜园已被秒杀啦");
				ajax.setErrorCode("0");
				return ajax;
			}
			//判断当前用户是否已秒杀过
			AFarm farmer3 = aFarmService.get(String.valueOf(aFarm.getFarmId()));
			if(farmer3!=null && farmer3.getFarm()!=null && !farmer3.getFarm().isEmpty() && !farmer3.getFarm().equals("null")){
				ajax.setMsg("你已经秒杀过啦");
				ajax.setErrorCode("3");
				return ajax;
			}
			aFarm.setFarm(farm);
			aFarm.setGetTime(new Date());
		}
		aFarmService.customSave(aFarm);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("farmId", aFarm.getFarmId());	
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 获取菜园信息记录
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson list(AFarm aFarm, HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();	
		List<AFarm> list = aFarmService.findList(aFarm);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("list", list);			
		ajax.setBody(body);
		return ajax;
	}
	
}