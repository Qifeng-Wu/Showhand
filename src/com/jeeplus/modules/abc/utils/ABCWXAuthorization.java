package com.jeeplus.modules.abc.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Hex;
import org.jeewx.api.mp.aes.AesException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;
import net.sf.json.JSONObject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

@Controller
@RequestMapping(value = "abc-api/wx")
public class ABCWXAuthorization extends BaseController {
	
	public static final String TOKEN = "stephen1234567";
    public static final String APPID = "wxe112e7675a5d5f98"; // 微信appid---微信公众平台
    public static final String APPSECRET = "2b52a387b7af83e753114eecf4e7c966"; // 微信AppSecret---微信公众平台

    @RequestMapping("/getSignature")
    @ResponseBody
    public AjaxJson getSignature() {
    	AjaxJson ajax = new AjaxJson();	
        String appid = APPID;
        String url="https://wu.stephen7.top/Showhand/webpage/modules/abc/questionnaire/xixigongguan.html";//需要显示文字图片的地址
        String access_token, ticket, nonceStr, timestamp, signature;
		try {
			access_token = getAccessToken();//需要调用微信开放接口获取
	        ticket = getTicket(access_token);//需要调用微信开放接口获取
	        nonceStr = getRandomStr();//随机数
	        timestamp = getTimestamp();//获取时间戳-秒
			signature = getSHA1(ticket,timestamp,nonceStr,url);
		} catch (Exception e) {
			ajax.setSuccess(false);
			ajax.setMsg("提交参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}
        
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();	
		body.put("nonceStr",nonceStr);
		body.put("timestamp",timestamp);
	    body.put("signature",signature);
		body.put("appid",appid);		
		ajax.setBody(body);
		return ajax;
	}

    
	/**
    *
    * @param map
    * @return 这个接口就是验证基本配置-->服务器配置-->修改配置，然而这个接口只需要返回入参的随机数echostr即可，没有其他操作可以不管
    * @throws AesException
    * @throws ParserConfigurationException
    * @throws IOException
    * @throws SAXException
    */
   @RequestMapping(value = "check")
   public void checkWxDomainUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   try {
	     // 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数 
	     String signature = request.getParameter("signature");// 微信加密签名（token、timestamp、nonce。） 
	     String timestamp = request.getParameter("timestamp");// 时间戳 
	     String nonce = request.getParameter("nonce");// 随机数 
	     String echostr = request.getParameter("echostr");// 随机字符串 
	     // 将token、timestamp、nonce三个参数进行字典序排序 
	     String[] params = new String[] { TOKEN, timestamp, nonce };
	     Arrays.sort(params);
	     // 将三个参数字符串拼接成一个字符串进行sha1加密 
	     String clearText = params[0] + params[1] + params[2];
	     String algorithm = "SHA-1";
	     String sign = new String(Hex.encodeHex(
	         MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
	     // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信 
	     if (signature.equals(sign)) {
	       response.getWriter().print(echostr);
	     }
	   } catch (Exception e) {
	     e.printStackTrace();
	   }
	 }
   
   
   /**
    * 获取access_token
    *
    * @return
    */
   public static String getAccessToken() {
       String access_token = "";
       String grant_type = "client_credential";//获取access_token填写client_credential
       String AppId = APPID;//第三方用户唯一凭证
       String secret = APPSECRET;//第三方用户唯一凭证密钥，即appsecret
       //这个url链接地址和参数皆不能变
       String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + AppId + "&secret=" + secret;

       try {
           URL urlGet = new URL(url);
           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
           http.setRequestMethod("GET"); // 必须是get方式请求
           http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           http.setDoOutput(true);
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
           http.connect();
           InputStream is = http.getInputStream();
           int size = is.available();
           byte[] jsonBytes = new byte[size];
           is.read(jsonBytes);
           String message = new String(jsonBytes, "UTF-8");
           JSONObject demoJson = JSONObject.fromObject(message);
           System.out.println("JSON字符串：" + demoJson);
           access_token = demoJson.getString("access_token");
           is.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return access_token;
   }


   /**
    * 获取jsapi_ticket
    *
    * @param access_token
    * @return
    */
   public static String getTicket(String access_token) {
       String ticket = null;
       String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";//这个url链接和参数不能变
       try {
           URL urlGet = new URL(url);
           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
           http.setRequestMethod("GET"); // 必须是get方式请求
           http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           http.setDoOutput(true);
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
           http.connect();
           InputStream is = http.getInputStream();
           int size = is.available();
           byte[] jsonBytes = new byte[size];
           is.read(jsonBytes);
           String message = new String(jsonBytes, "UTF-8");
           JSONObject demoJson = JSONObject.fromObject(message);
           System.out.println("JSON字符串：" + demoJson);
           ticket = demoJson.getString("ticket");
           is.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return ticket;
   }


   /**
    * 进行sha1签名
    * @param jsapi_ticket
    * @param timestamp
    * @param noncestr
    * @param url
    * @return 返回   signature
    * @throws AesException
    */
   public static String getSHA1(String jsapi_ticket, String timestamp, String noncestr, String url) throws AesException{
       try {
           StringBuffer sb = new StringBuffer();
           sb.append("jsapi_ticket=").append(jsapi_ticket).append("&noncestr=").append(noncestr).append("&timestamp=").append(timestamp).append("&url=").append(url);
           String str = sb.toString();
           System.err.println(str);
           // SHA1签名生成
           MessageDigest md = MessageDigest.getInstance("SHA-1");
           md.update(str.getBytes());
           byte[] digest = md.digest();

           StringBuffer hexstr = new StringBuffer();
           String shaHex = "";
           for (int i = 0; i < digest.length; i++) {
               shaHex = Integer.toHexString(digest[i] & 0xFF);
               if (shaHex.length() < 2) {
                   hexstr.append(0);
               }
               hexstr.append(shaHex);
           }
           return hexstr.toString();
       } catch (Exception e) {
           e.printStackTrace();
           return "";
       }
   }
   
	// 随机生成16位字符串
   public static String getRandomStr() {
       String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
       Random random = new Random();
       StringBuffer sb = new StringBuffer();
       for (int i = 0; i < 16; i++) {
           int number = random.nextInt(base.length());
           sb.append(base.charAt(number));
       }
       return sb.toString();
   }

   //获取时间戳-秒
   public static String getTimestamp() {
       String startTs = String.valueOf(System.currentTimeMillis());
       return startTs.substring(0,10);
   }
}