package com.jeeplus.modules.audio.web;

import java.util.Date;

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
import com.jeeplus.modules.audio.entity.AQuestionnaire;
import com.jeeplus.modules.audio.service.AQuestionnaireService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;

/**
 * 问卷调查Controller
 * @author stephen
 * @version 2020-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/questionnaire")
public class AQuestionnaireController extends BaseController {

	@Autowired
	private AQuestionnaireService aQuestionnaireService;
	
	@ModelAttribute
	public AQuestionnaire get(@RequestParam(required=false) String id) {
		AQuestionnaire entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aQuestionnaireService.get(id);
		}
		if (entity == null){
			entity = new AQuestionnaire();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:questionnaire:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aQuestionnaire")AQuestionnaire aQuestionnaire, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AQuestionnaire> page = aQuestionnaireService.findPage(new Page<AQuestionnaire>(request, response), aQuestionnaire); 
		model.addAttribute("page", page);
		return "modules/audio/questionnaireList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:questionnaire:view","audio:questionnaire:add","audio:questionnaire:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AQuestionnaire aQuestionnaire, Model model) {
		model.addAttribute("aQuestionnaire", aQuestionnaire);
		return "modules/audio/questionnaireForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AQuestionnaire aQuestionnaire, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aQuestionnaire)){
			return form(aQuestionnaire, model);
		}
		aQuestionnaire.setRemark(request.getParameter("remark"));
		aQuestionnaire.setCreateTime(new Date());
		aQuestionnaireService.customSave(aQuestionnaire);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/questionnaire/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:questionnaire:delete")
	@RequestMapping(value = "delete")
	public String delete(AQuestionnaire aQuestionnaire, RedirectAttributes redirectAttributes) {
		aQuestionnaireService.delete(aQuestionnaire);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/questionnaire/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:questionnaire:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aQuestionnaireService.delete(aQuestionnaireService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/questionnaire/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("audio:questionnaire:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exporQisile(AQuestionnaire aQuestionnaire, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "回访问卷记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AQuestionnaire> page = aQuestionnaireService.findPage(new Page<AQuestionnaire>(request, response, -1), aQuestionnaire);
    		new ExportExcel("回访问卷记录", AQuestionnaire.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出回访问卷记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/audio/questionnaire/?repage";
    }
}