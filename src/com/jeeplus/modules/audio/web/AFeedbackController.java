package com.jeeplus.modules.audio.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AFeedback;
import com.jeeplus.modules.audio.service.AFeedbackService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 客户问题反馈Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/feedback")
public class AFeedbackController extends BaseController {

	@Autowired
	private AFeedbackService aFeedbackService;
	
	@ModelAttribute
	public AFeedback get(@RequestParam(required=false) String id) {
		AFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aFeedbackService.get(id);
		}
		if (entity == null){
			entity = new AFeedback();
		}
		return entity;
	}
	
	/**
	 * 意见反馈列表页面
	 */
	//@RequiresPermissions("qis:AFeedback:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aFeedback")AFeedback aFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AFeedback> page = aFeedbackService.findPage(new Page<AFeedback>(request, response), aFeedback); 
		model.addAttribute("page", page);
		return "modules/audio/feedbackList";
	}

	/**
	 * 查看，增加，编辑意见反馈表单页面
	 */
	//@RequiresPermissions(value={"qis:AFeedback:view","qis:AFeedback:add","qis:AFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AFeedback aFeedback, Model model) {
		model.addAttribute("aFeedback", aFeedback);
		return "modules/audio/feedbackForm";
	}

	/**
	 * 保存意见反馈
	 */
	//@RequiresPermissions(value={"qis:AFeedback:add","qis:AFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(AFeedback aFeedback, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aFeedback)){
			return form(aFeedback, model);
		}
		
		aFeedbackService.save(aFeedback);
		addMessage(redirectAttributes, "保存意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/audio/feedback/?repage";
	}
	
	/**
	 * 删除意见反馈
	 */
	//@RequiresPermissions("qis:AFeedback:del")
	@RequestMapping(value = "delete")
	public String delete(AFeedback aFeedback, RedirectAttributes redirectAttributes) {
		aFeedbackService.delete(aFeedback);
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/audio/feedback/?repage";
	}
	
	/**
	 * 批量删除意见反馈
	 */
	//@RequiresPermissions("qis:AFeedback:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids,AFeedback aFeedback,RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aFeedback = aFeedbackService.get(id);
			aFeedbackService.delete(aFeedback);
		}
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/audio/feedback/?repage";
	}

}