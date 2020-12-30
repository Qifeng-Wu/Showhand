package com.jeeplus.modules.audio.utils;

import net.sf.json.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信--公共信息API
 * @author stephen
 * @version 2020-12-23
 * 
 */
public class WxCommonAPI {
	private static Logger logger = LoggerFactory.getLogger(WxCommonAPI.class);
	//获取公众号应用access_token （该access_token用于调用其他接口）
	private static String get_access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
	//获取公众号应用access_token和用户openid（网页授权）
	private static String get_access_token_bycode_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//获取用户信息（使用openid）
	private static String get_user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";  

    /**
	 * 1、获取公众号应用access_token （该access_token用于调用其他接口）
	 * @param appid
	 * @param secret
	 * @return access_token
	 * @throws WexinReqException
	 */
	public static String getAccessToken(String appid,String secret) throws WexinReqException{
		String access_token = "";
		String requestUrl = get_access_token_url.replace("APPID", appid).replace("SECRET",secret);
		JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", null);
		if (result.has("errcode") && result.getString("errcode").equals("0") && result.getString("errmsg").equals("ok")) {
			access_token = result.getString("access_token");
		} else {
			logger.error("获取公众号应用access_token！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			throw new WexinReqException("获取公众号应用access_token！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));

		}
		return access_token;
	}
	
    /**
	 * 2、获取公众号应用access_token和用户openid（网页授权）
	 * @param appid
	 * @param secret
	 * @param 
	 * @return access_token
	 * @throws WexinReqException
	 */
	public static JSONObject getAuthAccessToken(String appid,String secret,String code) throws WexinReqException{
		String requestUrl = get_access_token_bycode_url.replace("APPID", appid).replace("SECRET",secret).replace("CODE",code);
		JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", null);
		if (result.has("errcode")) {
			logger.error("公众号应用网页授权！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			throw new WexinReqException("公众号应用网页授权！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));

		}
		return result;
	}
	
	/**
	 * 3、使用openid获取用户
	 * @param access_token
	 * @param openid
	 * @throws WexinReqException
	 */
	public static JSONObject getUserInfo(String access_token,String openid) throws WexinReqException{
		String requestUrl = get_user_info_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET",null);		
		if (result.has("errcode")){
			logger.error("获取用户信息！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			throw new WexinReqException("获取用户信息！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
		}
		return result;
	}

	public static void main(String[] args) throws WexinReqException {
		
	}
}
