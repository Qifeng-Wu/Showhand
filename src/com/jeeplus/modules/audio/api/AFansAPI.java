package com.jeeplus.modules.audio.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.jeeplus.modules.audio.entity.AFans;
import com.jeeplus.modules.audio.service.AFansService;
import com.jeeplus.modules.audio.utils.CommonUtils;
import com.jeeplus.modules.audio.utils.ConfigurationFileHelper;

import net.sf.json.JSONObject;

/**
 * 获取用户微信信息API
 * 
 * @author stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "a-api/fans")
public class AFansAPI extends BaseController {
	 private static String APPID = ConfigurationFileHelper.getAppid();
	 private static String APPSECRET = ConfigurationFileHelper.getAppsecret();
	 @Autowired
	 private AFansService aFansService;
	 
	/**
	 * 获取用户信息(openid,session_key)
	 * @return
	 * @throws WexinReqException 
	 */
	@RequestMapping(value = "/authorize")
	@ResponseBody
	public AjaxJson getFindList(AFans aFans,HttpServletRequest request) throws WexinReqException {			
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
				String status = "0";//用户状态
				if(openId !=null && !openId.isEmpty() && !openId.equals("null")){
					//存储用户信息
					aFans.setOpenId(openId);
					aFans.setNickname(nickName);
					aFans.setSex(gender);
					aFans.setAvatar(avatarUrl);
					AFans fan = aFansService.get(openId);
					if(fan!=null && fan.getOpenId()!=null) {
						aFans.setUpdatetime(new Date());
						aFansService.customSave(aFans);//跟新
						status = fan.getStatus();
					}else {
						aFans.setStatus("0");
						aFans.setCid(createCidNumber());
						aFans.setAddtime(new Date());
						aFans.setUpdatetime(new Date());
						aFansService.save(aFans);//新增
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
				body.put("status", status);
				body.put("fans", aFans);
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
	public synchronized AjaxJson info(AFans aFans, RedirectAttributes redirectAttributes) {
		AjaxJson ajax = new AjaxJson();	
		aFans = aFansService.get(aFans);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("fans", aFans);			
		ajax.setBody(body);
		return ajax;
	}
	
	//生成身份编号
		public static String createCidNumber(){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy");
			String year = sdf.format(date);
			String num = String.valueOf((int)((Math.random()*9+1)*10000));
			return "SH"+year+num;
		}
		public static void main(String[] args) throws ParseException {
			System.out.println(createCidNumber().length());
			System.out.println(createCidNumber());		
		}
}