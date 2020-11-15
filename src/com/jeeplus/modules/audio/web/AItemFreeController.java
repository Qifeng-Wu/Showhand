package com.jeeplus.modules.audio.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.audio.entity.AItemFree;
import com.jeeplus.modules.audio.service.AItemFreeService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 用户免费项目Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/item/free")
public class AItemFreeController extends BaseController {

	@Autowired
	private AItemFreeService aItemFreeService;
	
	@ModelAttribute
	public AItemFree get(@RequestParam(required=false) String id) {
		AItemFree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aItemFreeService.get(id);
		}
		if (entity == null){
			entity = new AItemFree();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:order:item:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aItemFree")AItemFree aItemFree, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AItemFree> page = aItemFreeService.findPage(new Page<AItemFree>(request, response), aItemFree); 
		model.addAttribute("page", page);
		return "modules/audio/itemFreeList";
	}

	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequestMapping(value = "form")
	public String form(AItemFree aItemFree, Model model) {
		model.addAttribute("aItemFree", aItemFree);
		return "modules/audio/itemFreeForm";
	}
	
	/**
	 * 保存分配给用户故事信息
	 */
	@RequestMapping(value = "sendStoryToFan")
	@Transactional
	public String sendStoryToFan(AItemFree aItemFree,String ids,String openId, Model model, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aItemFree.setOpenId(openId);
			aItemFree.setType(0);
			aItemFree.setStoryId(Integer.parseInt(id));
			aItemFree.setGoodsId(Integer.parseInt(id));
			aItemFree.setCreateTime(new Date());
			aItemFreeService.customSave(aItemFree);
		}
				
		addMessage(redirectAttributes, "故事分配成功");
		return "redirect:"+Global.getAdminPath()+"/audio/fans/list?repage";
	}
}