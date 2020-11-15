package com.jeeplus.modules.audio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jeeplus.modules.audio.entity.ASlide;
import com.jeeplus.modules.audio.service.ASlideService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 幻灯片Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/slide")
public class ASlideController extends BaseController {

	@Autowired
	private ASlideService aSlideService;
	
	@ModelAttribute
	public ASlide get(@RequestParam(required=false) String id) {
		ASlide entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aSlideService.get(id);
		}
		if (entity == null){
			entity = new ASlide();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:slide:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aSlide")ASlide aSlide, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ASlide> page = aSlideService.findPage(new Page<ASlide>(request, response), aSlide); 
		model.addAttribute("page", page);
		return "modules/audio/slideList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:slide:view","audio:slide:add","audio:slide:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ASlide aSlide, Model model) {
		model.addAttribute("aSlide", aSlide);
		return "modules/audio/slideForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(ASlide aSlide, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aSlide)){
			return form(aSlide, model);
		}
		aSlide.setTitle(request.getParameter("title"));
		aSlide.setDescription(request.getParameter("description"));
		aSlideService.customSave(aSlide);
		addMessage(redirectAttributes, "保存幻灯片成功");
		return "redirect:"+Global.getAdminPath()+"/audio/slide/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:slide:delete")
	@RequestMapping(value = "delete")
	public String delete(ASlide aSlide, RedirectAttributes redirectAttributes) {
		aSlideService.delete(aSlide);
		addMessage(redirectAttributes, "删除幻灯片成功");
		return "redirect:"+Global.getAdminPath()+"/audio/slide/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aSlideService.delete(aSlideService.get(id));
		}
		addMessage(redirectAttributes, "删除幻灯片成功");
		return "redirect:"+Global.getAdminPath()+"/audio/slide/?repage";
	}
}