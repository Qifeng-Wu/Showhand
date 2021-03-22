package com.jeeplus.modules.abc.lantern.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.abc.common.entity.ActivityTime;
import com.jeeplus.modules.abc.common.service.ActivityTimeService;
import com.jeeplus.modules.abc.lantern.entity.Lantern;
import com.jeeplus.modules.abc.lantern.service.LanternService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;

/**
 * 元宵节猜灯谜Controller
 * @author stephen
 * @version 2021-2-22
 */
@Controller
@RequestMapping(value = "${adminPath}/abc/lantern")
public class LanternController extends BaseController {

	@Autowired
	private LanternService lanternService;
	
	@Autowired
	private ActivityTimeService activityTimeService;
	
	@ModelAttribute
	public Lantern get(@RequestParam(required=false) String id) {
		Lantern entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lanternService.get(id);
		}
		if (entity == null){
			entity = new Lantern();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("abc:lantern:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("lantern")Lantern lantern, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Lantern> page = lanternService.findPage(new Page<Lantern>(request, response), lantern); 
		model.addAttribute("page", page);
		return "modules/abc/lantern/lanternList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(Lantern lantern, Model model) {
		model.addAttribute("lantern", lantern);
		return "modules/abc/lantern/lanternForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(Lantern lantern, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lantern)){
			return form(lantern, model);
		}
		if(lantern.getOpenId()==null) {
			lantern.setCreateTime(new Date());
		}
		lantern.setUpdateTime(new Date());
		lanternService.customSave(lantern);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/abc/lantern/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(Lantern lantern, RedirectAttributes redirectAttributes) {
		lanternService.delete(lantern);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/abc/lantern/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lanternService.delete(lanternService.get(id));
		}
		addMessage(redirectAttributes, "批量删除成功");
		return "redirect:"+Global.getAdminPath()+"/abc/lantern/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exporQisile(Lantern lantern, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, String ids) {
		try {
            String fileName = "元宵节猜灯谜"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Lantern> page = new Page<>();
            if (StringUtils.isNotBlank(ids)) {
                String idArray[] = ids.split(",");
                List<Lantern> list = new ArrayList<>();
                for (String id : idArray) {
                	Lantern Farm = lanternService.get(id);
                    list.add(Farm);
                }
                page.setList(list);
            } else {
            	lantern.setStatus(2);
            	lantern.setFilter(1);
                page = lanternService.findPage(new Page<Lantern>(request, response, -1), lantern);
            }
            new ExportExcel("元宵节猜灯谜", Lantern.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出元宵节猜灯谜信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/abc/lantern/?repage";
    }
    
	/**
	 * 跳转活动时间设置页面
	 */
	@RequestMapping(value = "activityTime")
	public String activityTime(ActivityTime activityTime, Model model) {
		activityTime.setType(1);
		List<ActivityTime> list = activityTimeService.findList(activityTime);
		if(list!=null && list.size()>0) {
			activityTime = list.get(0);
		}
		model.addAttribute("activityTime", activityTime);
		return "modules/abc/lantern/activityTimeForm";
	}
	/**
	 * 保存活动时间
	 */
	@RequestMapping(value = "activityTimeSave")
	public String activityTimeSave(ActivityTime activityTime, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		activityTime.setType(1);
		activityTimeService.customSave(activityTime);
		addMessage(redirectAttributes, "活动时间设置成功");
		return "redirect:"+Global.getAdminPath()+"/abc/lantern/?repage";
	}
}