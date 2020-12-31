package com.jeeplus.modules.audio.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
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
import com.jeeplus.modules.audio.entity.AFarm;
import com.jeeplus.modules.audio.service.AFarmService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;

/**
 * 家庭菜园农场Controller
 * @author stephen
 * @version 2020-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/farm")
public class AFarmController extends BaseController {

	@Autowired
	private AFarmService aFarmService;
	
	@ModelAttribute
	public AFarm get(@RequestParam(required=false) String id) {
		AFarm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aFarmService.get(id);
		}
		if (entity == null){
			entity = new AFarm();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:farm:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aFarm")AFarm aFarm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AFarm> page = aFarmService.findPage(new Page<AFarm>(request, response), aFarm); 
		model.addAttribute("page", page);
		return "modules/audio/farm/farmList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:farm:view","audio:farm:add","audio:farm:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AFarm aFarm, Model model) {
		model.addAttribute("aFarm", aFarm);
		return "modules/audio/farm/farmForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AFarm aFarm, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aFarm)){
			return form(aFarm, model);
		}
		if(aFarm.getFarmId()==null) {
			aFarm.setCreateTime(new Date());
		}
		aFarmService.customSave(aFarm);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/farm/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:farm:delete")
	@RequestMapping(value = "delete")
	public String delete(AFarm aFarm, RedirectAttributes redirectAttributes) {
		aFarmService.delete(aFarm);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/farm/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:farm:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aFarmService.delete(aFarmService.get(id));
		}
		addMessage(redirectAttributes, "批量删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/farm/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("audio:farm:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exporQisile(AFarm aFarm, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, String ids) {
		try {
            String fileName = "一米菜园信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AFarm> page = new Page<>();
            if (StringUtils.isNotBlank(ids)) {
                String idArray[] = ids.split(",");
                List<AFarm> list = new ArrayList<>();
                for (String id : idArray) {
                	AFarm Farm = aFarmService.get(id);
                    list.add(Farm);
                }
                page.setList(list);
            } else {
            	aFarm.setFilter(1);//过滤没有秒杀的用户
                page = aFarmService.findPage(new Page<AFarm>(request, response, -1), aFarm);
            }
            new ExportExcel("一米菜园信息表", AFarm.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出一米菜园信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/audio/farm/?repage";
    }
	
	/**
	 * 查看剩余菜园编号
	 */
	@RequestMapping(value = "remain")
	public String remain() {
		return "modules/audio/farm/remainFarmList";
	}
}