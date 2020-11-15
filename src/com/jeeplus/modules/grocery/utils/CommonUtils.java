/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.jeewx.api.core.common.MyX509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;

/**
 * 用户工具类
 * @author jeeplus
 * @version 2018-6-30
 */
public class CommonUtils {
	private static Logger log = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 向指定URL发送POST请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return 响应结果
	 */
	public static String sendPost(String url, Map<String, String> paramMap) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());

			String param = "";
			if (paramMap != null && paramMap.size() > 0) {
				Iterator<String> ite = paramMap.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();
					String value = paramMap.get(key);
					param += key + "=" + value + "&";
				}
				param = param.substring(0, param.length() - 1);
			}

			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 数据流post请求
	 * 
	 * @param urlStr
	 * @param xmlInfo
	 */
	public static String doPost(String urlStr, String strInfo) {
		String reStr = "";
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write(new String(strInfo.getBytes("utf-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				reStr += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	public static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
		String url = "http://" + request.getServerName();
		if (null2Int(Integer.valueOf(request.getServerPort())) != 80) {
			url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
		} else {
			url = url + contextPath;
		}
		return url;
	}

	/**
	 * 将null值转化为0
	 * 
	 * @param s
	 * @return
	 */
	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 读取流返回json
	 * 
	 * @param is
	 * @return
	 */
	public static JSONObject loadString(InputStream is) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				stringBuilder.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject paramJson = JSONObject.fromObject(stringBuilder.toString());
		return paramJson;
	}

	/**
	 * 根据搜索特殊字符串
	 * @param id
	 * @return 取不到返回null
	 */
	public static String specialStr(String str){
		Integer index=str.indexOf("%");
		Integer index1=str.indexOf("_");
		Integer index2=str.indexOf("\\");
		StringBuffer stringBuffer = new StringBuffer(str);
		if(index!=-1) {
			 stringBuffer.insert(index, "\\");
		}
		if(index1!=-1) {
			 stringBuffer.insert(index1, "\\");
		}
		if(index2!=-1) {
			 stringBuffer.insert(index2, "\\");
		}
		return stringBuffer.toString();
		
	}
	
	
	/** 
	 * 判断字符串是否为数字 
	 */  
	public static boolean isNumeric(String str){  		
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    if(str==null||"".equals(str)){
	    	return false;
	    }
	    Matcher isNum = pattern.matcher(str);  
	    return isNum.matches();    
	 } 
	
	/**
	 * 去除字符串中的所有特殊字符和空格
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String removeSpecialchar(String str) throws PatternSyntaxException {
		if (str != null) {
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim().replace(" ", "");
		}
		return null;
	}
	
	/**
	 * API
	 * 
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return jsonObject;
	}
	
	
	//处理Double类型保留两位小数
	public static Double TwoDecimal(Double decimal){
		Double result = 0.00;
		if(decimal!=null){
			BigDecimal b = new BigDecimal(decimal);  
			result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		}
		return result;
	}
	
}
