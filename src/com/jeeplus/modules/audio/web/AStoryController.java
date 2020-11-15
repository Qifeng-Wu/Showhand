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
import com.jeeplus.modules.audio.entity.AFans;
import com.jeeplus.modules.audio.entity.AOrderItem;
import com.jeeplus.modules.audio.entity.AStory;
import com.jeeplus.modules.audio.service.AFansService;
import com.jeeplus.modules.audio.service.AOrderItemService;
import com.jeeplus.modules.audio.service.AStoryService;
import com.jeeplus.common.utils.StringUtils;

/**
 * 故事Controller
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/story")
public class AStoryController extends BaseController {

	@Autowired
	private AStoryService aStoryService;
	@Autowired
	private AFansService aFansService;
	@Autowired
	private AOrderItemService aOrderItemService;
	
	@ModelAttribute
	public AStory get(@RequestParam(required=false) String id) {
		AStory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aStoryService.get(id);
		}
		if (entity == null){
			entity = new AStory();
		}
		return entity;
	}
	
	/**
	 * 列表页面
	 */
	@RequiresPermissions("audio:story:list")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("aStory")AStory aStory, HttpServletRequest request, HttpServletResponse response, Model model) {
		aStory.setDeleteFlag(false);
		Page<AStory> page = aStoryService.findPage(new Page<AStory>(request, response), aStory); 
		model.addAttribute("page", page);
		return "modules/audio/storyList";
	}

	/**
	 * 故事分配时获取故事列表
	 */
	@RequiresPermissions("audio:story:anotherList")
	@RequestMapping(value = "anotherList")
	public String anotherList(@ModelAttribute("aStory")AStory aStory, HttpServletRequest request, HttpServletResponse response, Model model) {
		aStory.setOpenId(request.getParameter("openId"));
		aStory.setDeleteFlag(false);
		Page<AStory> page = aStoryService.findAnotherPage(new Page<AStory>(request, response), aStory); 
		model.addAttribute("page", page);
		model.addAttribute("openId", request.getParameter("openId"));
		return "modules/audio/storyAnotherList";
	}
	
	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"audio:story:view","audio:story:add","audio:story:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AStory aStory, Model model) {
		model.addAttribute("aStory", aStory);
		return "modules/audio/storyForm";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save")
	public String save(AStory aStory, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aStory)){
			return form(aStory, model);
		}
		aStory.setTitle(request.getParameter("title"));
		aStory.setRemark(request.getParameter("remark"));
		aStory.setDeleteFlag(false);
		aStoryService.customSave(aStory);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/audio/story/?repage";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("audio:story:delete")
	@RequestMapping(value = "delete")
	public String delete(AStory aStory, RedirectAttributes redirectAttributes) {
		aStory.setDeleteFlag(true);
		aStoryService.delete(aStory);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/story/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("audio:story:delete")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aStoryService.delete(aStoryService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/audio/story/?repage";
	}
	
	/**
	 * 查看该故事下的粉丝用户数量
	 */
	@RequestMapping(value = "fans")
	public String list(@ModelAttribute("aFans")AFans aFans, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AFans> page = aFansService.findPage(new Page<AFans>(request, response), aFans); 
		model.addAttribute("page", page);
		return "modules/audio/storyFansList";
	}
	

	/**
	 * 查看该故事下的交易记录
	 */
	@RequestMapping(value = "order")
	public String list(@ModelAttribute("aOrderItem")AOrderItem aOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AOrderItem> page = aOrderItemService.findPage(new Page<AOrderItem>(request, response), aOrderItem); 
		model.addAttribute("page", page);
		return "modules/audio/storyOrderItemList";
	}
}