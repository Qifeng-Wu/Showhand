package com.jeeplus.modules.grocery.api;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.jeewx.api.core.exception.WexinReqException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.grocery.entity.GroceryFans;
import com.jeeplus.modules.grocery.service.GroceryFansService;
import com.jeeplus.modules.grocery.utils.CommonUtils;
import com.jeeplus.modules.grocery.utils.ConfigurationFileHelper;

import net.sf.json.JSONObject;

/**
 * 获取用户信息API
 * 
 * @author stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "g-api/fans")
public class FansAPI extends BaseController {
	 private static String APPID = ConfigurationFileHelper.getAppid();
	 private static String APPSECRET = ConfigurationFileHelper.getAppsecret();
	 @Autowired
	 private GroceryFansService groceryFansService;
	 
	/**
	 * 获取用户信息(openid,session_key)
	 * @return
	 * @throws WexinReqException 
	 */
	@RequestMapping(value = "/authorize")
	@ResponseBody
	public AjaxJson getFindList(GroceryFans groceryFans,HttpServletRequest request) throws WexinReqException {			
		String code = request.getParameter("code").trim();
		String nickName = request.getParameter("nickName");
		String gender = request.getParameter("gender");
		String avatarUrl = request.getParameter("avatarUrl");
		AjaxJson ajax = new AjaxJson();	
		if (code != null && !code.isEmpty()) {// 小程序ID不为0
			if(APPID !=null && !APPID.isEmpty() && APPSECRET !=null && !APPSECRET.isEmpty()){								
				String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+APPID
						+"&secret="+APPSECRET+"&js_code="+code+"&grant_type=authorization_code";
				
				JSONObject json_object = CommonUtils.httpsRequest(url, "GET", null);
				//得到微信返回的两个值(openid,session_key)
				String openId = json_object.getString("openid");				
				//String session_key = json_object.getString("session_key");//暂时用不到（解密时要用）			
				
				if(openId !=null && !openId.isEmpty() && !openId.equals("null")){
					//存储用户信息
					groceryFans.setOpenId(openId);
					groceryFans.setNickname(nickName);
					groceryFans.setSex(gender);
					groceryFans.setAvatar(avatarUrl);
					GroceryFans fan = groceryFansService.get(openId);
					if(fan!=null && fan.getOpenId()!=null) {
						groceryFans.setUpdatetime(new Date());
						groceryFansService.customSave(groceryFans);//跟新
					}else {
						groceryFans.setAddtime(new Date());
						groceryFans.setUpdatetime(new Date());
						groceryFansService.save(groceryFans);//新增
					}
					
					if(openId ==null || openId.isEmpty()){
						ajax.setSuccess(false);
						ajax.setMsg("SAVE INFO ERROR !");
						ajax.setErrorCode("0");
						return ajax;
					}
				}else {
					ajax.setSuccess(false);
					ajax.setMsg("GET OPENID ERROR !");
					ajax.setErrorCode("0");
					return ajax;
				}
				LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
				body.put("openId", openId);		
				ajax.setBody(body);
				return ajax;
				
			}else{
				ajax.setSuccess(false);
				ajax.setMsg("GET MINA_APPSECRET ERROR !");
				ajax.setErrorCode("0");
				return ajax;
			}
		}
		ajax.setSuccess(false);
		ajax.setMsg("CODE IS NONE !");
		ajax.setErrorCode("0");
		return ajax;
	}		

	/**
	 * 获取粉丝详情信息
	 */
	@RequestMapping(value = "info")
	@ResponseBody
	public synchronized AjaxJson info(GroceryFans groceryFans, RedirectAttributes redirectAttributes) {
		AjaxJson ajax = new AjaxJson();	
		groceryFans = groceryFansService.get(groceryFans);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("fans", groceryFans);			
		ajax.setBody(body);
		return ajax;
	}
}