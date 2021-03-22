package com.jeeplus.modules.abc.questionnaire.web;

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
import com.jeeplus.modules.abc.questionnaire.entity.Questionnaire;
import com.jeeplus.modules.abc.questionnaire.service.QuestionnaireService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;

/**
 * 问卷调查Controller
 * @author stephen
 * @version 2020-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/abc/questionnaire")
public class QuestionnaireController extends BaseController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@ModelAttribute
	public Questionnaire get(@RequestParam(required=false) String id) {
		Questionnaire entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionnaireService.get(id);
		}
		if (entity == null){
			entity = new Questionnaire();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("abc:questionnaire:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("questionnaire")Questionnaire questionnaire, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Questionnaire> page = questionnaireService.findPage(new Page<Questionnaire>(request, response), questionnaire); 
		model.addAttribute("page", page);
		return "modules/abc/questionnaire/list";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(Questionnaire questionnaire, Model model) {
		model.addAttribute("questionnaire", questionnaire);
		return "modules/abc/questionnaire/form";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(Questionnaire questionnaire, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, questionnaire)){
			return form(questionnaire, model);
		}
		questionnaire.setRemark(request.getParameter("remark"));
		questionnaire.setCreateTime(new Date());
		questionnaireService.customSave(questionnaire);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/abc/questionnaire/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(Questionnaire questionnaire, RedirectAttributes redirectAttributes) {
		questionnaireService.delete(questionnaire);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/abc/questionnaire/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			questionnaireService.delete(questionnaireService.get(id));
		}
		addMessage(redirectAttributes, "批量删除成功");
		return "redirect:"+Global.getAdminPath()+"/abc/questionnaire/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exporQisile(Questionnaire Questionnaire, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, String ids) {
		try {
            String fileName = "回访问卷记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Questionnaire> page = new Page<>();
            if (StringUtils.isNotBlank(ids)) {
                String idArray[] = ids.split(",");
                List<Questionnaire> list = new ArrayList<>();
                for (String id : idArray) {
                	Questionnaire questionnaire = questionnaireService.get(id);
                    list.add(questionnaire);
                }
                page.setList(list);
            } else {
                page = questionnaireService.findPage(new Page<Questionnaire>(request, response, -1), Questionnaire);
            }
            new ExportExcel("回访问卷记录", Questionnaire.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出回访问卷记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/abc/questionnaire/?repage";
    }
}