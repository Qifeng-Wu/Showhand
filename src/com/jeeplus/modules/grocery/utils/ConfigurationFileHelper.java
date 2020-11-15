package com.jeeplus.modules.grocery.utils;


import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationFileHelper {

	private static final String SERVICE_URL = "serviceUrl";	//服务器域名
	private static final String SAVEIMAGEPATH = "saveImagePath";	//保存图片的位置
	private static final String APPID = "appid";	//小程序APPID
	private static final String APPSECRET = "appsecret";	//小程序APPSECRET
	private static final String MCHID = "mch_id";	//小程序支付商户号
	private static final String APIKEY = "api_key";	//小程序支付密钥
		
	private static String serviceUrl;
	private static String saveImagePath;
	private static String appid;
	private static String appsecret;
	private static String mch_id;
	private static String api_key;
	/**
	 * 初始化
	 */
	static {
		initDBSource();
	}

	private static final void initDBSource() {
		Properties config = new Properties();
		try {
			// 加载配置文件
			String path = ConfigurationFileHelper.class.getResource("/").getPath();
			String websiteURL = ("/" + path + "configuration.properties").replaceFirst("/", "");
			FileInputStream in = new FileInputStream(websiteURL);
			config.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		serviceUrl = config.getProperty(SERVICE_URL);
		saveImagePath = config.getProperty(SAVEIMAGEPATH);
		appid = config.getProperty(APPID);
		appsecret = config.getProperty(APPSECRET);
		mch_id = config.getProperty(MCHID);
		api_key = config.getProperty(APIKEY);

	}

	public static synchronized String getServiceUrl() {
		return serviceUrl;
	}	
	public static synchronized String getSaveImagePath() {
		return saveImagePath;
	}
	public static synchronized String getAppid() {
		return appid;
	}	
	public static synchronized String getAppsecret() {
		return appsecret;
	}	
	public static synchronized String getMchid() {
		return mch_id;
	}	
	public static synchronized String getPayKey() {
		return api_key;
	}	
}
