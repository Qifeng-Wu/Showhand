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
import com.jeeplus.modules.audio.entity.AOrder;
import com.jeeplus.modules.audio.service.AOrderService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 订单Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/order")
public class AOrderController extends BaseController {

	@Autowired
	private AOrderService aOrderService;
	
	@ModelAttribute
	public AOrder get(@RequestParam(required=false) String id) {
		AOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aOrderService.get(id);
		}
		if (entity == null){
			entity = new AOrder();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:order:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aOrder")AOrder aOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		aOrder.setDeleteFlag(false);
		Page<AOrder> page = aOrderService.findPage(new Page<AOrder>(request, response), aOrder); 
		model.addAttribute("page", page);
		return "modules/audio/orderList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:order:view","audio:order:add","audio:order:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AOrder aOrder, Model model) {
		model.addAttribute("aOrder", aOrder);
		return "modules/audio/orderForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AOrder aOrder, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aOrder)){
			return form(aOrder, model);
		}
		aOrder.setDeleteFlag(false);
		aOrderService.customSave(aOrder);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/order/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:order:delete")
	@RequestMapping(value = "delete")
	public String delete(AOrder aOrder, RedirectAttributes redirectAttributes) {
		aOrder.setDeleteFlag(true);
		aOrderService.delete(aOrder);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/order/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:order:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aOrderService.delete(aOrderService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/order/?repage";
	}
}