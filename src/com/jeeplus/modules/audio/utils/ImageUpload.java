package com.jeeplus.modules.audio.utils;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.FileUtils;


/**
 * 上传图片
 * @author Stephen
 * @version 2020-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/audio/pictures")
public class ImageUpload {
	
	//上传图片
	@RequestMapping(value = "upload")
	@ResponseBody
	public String imageUpload( HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		String picturePath = "";
		String pictureName = "";
		// 判断文件是否为空  
        if (!file.isEmpty()) {  
                // 文件保存路径  
        		String realPath = Global.USERFILES_BASE_URL+"pictures/" ;
                // 转存文件  
            	FileUtils.createDirectory(Global.getUserfilesBaseDir()+realPath);
            	file.transferTo(new File( Global.getUserfilesBaseDir() +realPath +  file.getOriginalFilename())); 
            	pictureName = file.getOriginalFilename();
            	picturePath = request.getContextPath()+realPath + file.getOriginalFilename();

        } 
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();
        map.put("code",0);//0表示成功，1失败
        map.put("msg","上传成功");//提示消息
        map.put("data",map2);
        map2.put("src",picturePath);//图片url
        map2.put("title",pictureName);//图片名称，这个会显示在输入框里
        String result = new JSONObject(map).toString();
        return result;
	}

}
