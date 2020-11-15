/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.grocery.api;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.modules.grocery.utils.ConfigurationFileHelper;

/**
 * 微信小程序上传图片
 * @author Stephen
 * @version 2019-10-25
 */
@Controller
@RequestMapping(value = "g-api/upload")
public class ImageUploadAPI {
	private static String saveImagePath = ConfigurationFileHelper.getSaveImagePath();
	//上传图片
	@RequestMapping(value = "image")
	@ResponseBody
	public String imageUpload( HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		String picturePath = "";
		// 判断文件是否为空  
        if (!file.isEmpty()) {                 
            	Calendar calendar = Calendar.getInstance();
            	String year = String.valueOf(calendar.get(Calendar.YEAR));
            	String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);            
            	if(month.length()<2) month = 0 + month;//将月份变为两位数
            	String day = String.valueOf(calendar.get(Calendar.DATE));
            	if(day.length()<2) day = 0 + day;//将日期变为两位数
            	//创建文件夹
            	String realPath = saveImagePath + "/WXImage/" + year + "/" + month + "/" + day + "/";	
            	FileUtils.createDirectory(realPath);
            	
                // 转存文件  
            	file.transferTo(new File(realPath + file.getOriginalFilename()));  
            	//文件保存路径  
            	picturePath = "/WXImage/" + year + "/" + month + "/" + day + "/" + file.getOriginalFilename();

        }  
		return picturePath;
	}
}
