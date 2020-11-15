/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.web;

import java.util.Date;

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
import com.jeeplus.modules.grocery.entity.GroceryFreight;
import com.jeeplus.modules.grocery.service.GroceryFreightService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 运费Controller
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/grocery/freight")
public class GroceryFreightController extends BaseController {

	@Autowired
	private GroceryFreightService groceryFreightService;
	
	@ModelAttribute
	public GroceryFreight get(@RequestParam(required=false) String id) {
		GroceryFreight entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groceryFreightService.get(id);
		}
		if (entity == null){
			entity = new GroceryFreight();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GroceryFreight groceryFreight, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroceryFreight> page = groceryFreightService.findPage(new Page<GroceryFreight>(request, response), groceryFreight); 
		model.addAttribute("page", page);
		return "modules/grocery/freightList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GroceryFreight groceryFreight, Model model) {
		model.addAttribute("groceryFreight", groceryFreight);
		return "modules/grocery/freightForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(GroceryFreight groceryFreight, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, groceryFreight)){
			return form(groceryFreight, model);
		}
		GroceryFreight freight = groceryFreightService.findUniqueByProperty("province", request.getParameter("province"));
		
		if(groceryFreight.getFreightId() != null) {
			if(freight.getFreightId() != null && !freight.getFreightId().equals(groceryFreight.getFreightId())) {
				addMessage(redirectAttributes, "该地区运费已设置，保存失败");
				return "redirect:"+Global.getAdminPath()+"/grocery/freight/?repage";
			}
		}else {
			if(freight != null) {
				addMessage(redirectAttributes, "该地区运费已设置，保存失败");
				return "redirect:"+Global.getAdminPath()+"/grocery/freight/?repage";
			}	
		}
		groceryFreight.setProvince(request.getParameter("province"));
		groceryFreight.setCreateTime(new Date());
		groceryFreightService.customSave(groceryFreight);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/freight/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public String delete(GroceryFreight groceryFreight, RedirectAttributes redirectAttributes) {
		groceryFreightService.delete(groceryFreight);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/grocery/freight/?repage";
	}
	
}