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
		String community = request.getParameter("community");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String room = request.getParameter("room");
		String farm = request.getParameter("farm");
		AjaxJson ajax = new AjaxJson();	

		if(aFarm.getFarmId().equals(0)) {
			//判断房间是否存在
			AFarm farmer1 = new AFarm();
			farmer1.setRoom(room);
			farmer1.setCommunity(community);
			List<AFarm> list1 = aFarmService.findList(farmer1);
			if(list1!=null && list1.size()>0) {
				ajax.setMsg("该房号已被使用啦");
				ajax.setErrorCode("1");
				return ajax;
			}
			//判断当前用户是否已经提交
			AFarm farmer2 = new AFarm();
			farmer2.setOpenId(openId);
			farmer2.setCommunity(community);
			List<AFarm> list2 = aFarmService.findList(farmer2);
			if(list2!=null && list2.size()>0) {
				ajax.setMsg("您已经提交过信息啦");
				ajax.setErrorCode("2");
				return ajax;
			}
			aFarm.setFarmId(null);
			aFarm.setOpenId(openId);
			aFarm.setCommunity(community);
			aFarm.setName(name);
			aFarm.setPhone(phone);
			aFarm.setRoom(room);
			aFarm.setFarm(null);
			aFarm.setCreateTime(new Date());
		}else {
			//判断这个菜园是否被秒杀
			aFarm.setFarm(farm);
			aFarm.setCommunity(community);
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
			}else if("3".equals(community) && "23".equals(farm) && (!"邹菊芬".equals(farmer3.getName()) || !"18857177289".equals(farmer3.getPhone()))) {
				//判断23号菜园是否被指定人抢的(23号菜园指定给 邹菊芬 18857177289)
				ajax.setMsg("该菜园已被秒杀啦");
				ajax.setErrorCode("0");
				return ajax;
			}else if("4".equals(community) && "32".equals(farm) && (!"史长安".equals(farmer3.getName()) || !"13666686321".equals(farmer3.getPhone()))) {
				//判断32号菜园是否被指定人抢的(32号菜园指定给 史长安 13666686321)
				ajax.setMsg("该菜园已被秒杀啦");
				ajax.setErrorCode("0");
				return ajax;
			}
			
			//判断是否全部被抢完
			AFarm farmer4 = new AFarm();
			farmer4.setCommunity(community);
			farmer4.setFilter(1);
			List<AFarm> list4 = aFarmService.findList(farmer4);
			if(community.equals("1")){
				if(list4!=null && list4.size()>=42) {//判断大河宸章是否被抢完 预留四个 
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}
			}if(community.equals("2")){
				if(list4!=null && list4.size()>=32) {//判断上河
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}
			}else if(community.equals("3")) {//判断上塘宸章是否被抢完 预留四个 指定一个
				AFarm farmer5 = new AFarm();
				farmer5.setCommunity(community);
				farmer5.setFarm("23");
				List<AFarm> list5 = aFarmService.findList(farmer5);
				if(list4!=null && list5!=null && list5.size()>0 && list4.size()>=34) {
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}else if(list4!=null && (list5!=null && list5.size()<=0) && list4.size()>=33 && 
						(!"邹菊芬".equals(farmer3.getName()) || !"18857177289".equals(farmer3.getPhone()))) {
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}
			}else if(community.equals("4")) {//判断远洋香奈是否被抢完指定一个
				AFarm farmer6 = new AFarm();
				farmer6.setCommunity(community);
				farmer6.setFarm("32");
				List<AFarm> list6 = aFarmService.findList(farmer6);
				if(list4!=null && list6!=null && list6.size()>0 && list4.size()>=34) {
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}else if(list4!=null && (list6!=null && list6.size()<=0) && list4.size()>=33 && 
						(!"史长安".equals(farmer3.getName()) || !"13666686321".equals(farmer3.getPhone()))) {
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}
			}else {
				if(list4!=null && list4.size()>=46) {
					ajax.setMsg("菜园已被秒杀完啦");
					ajax.setErrorCode("4");
					return ajax;
				}
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