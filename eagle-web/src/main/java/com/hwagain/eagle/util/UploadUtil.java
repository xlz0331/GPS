package com.hwagain.eagle.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.aip.ocr.AipOcr;
import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.config.LogAspect;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UploadUtil {
	public static Map<String, String> upload(MultipartFile file, String UPLOAD_FOLDER,String type){
		System.err.println(file);
		Map<String, String> result = new HashMap<>();
		if (Objects.isNull(file) || file.isEmpty()) {
			result.put("code", "1");
			result.put("msg", "文件为空，请重新上传");
		}
		
		try {
			byte[] bytes = file.getBytes();
			String datePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String relatedPath = datePath + "/" + IdWorker.getId()+"_" +file.getOriginalFilename();
			Path path = Paths.get(UPLOAD_FOLDER + relatedPath);
			//如果没有files文件夹，创建
			if (!Files.isWritable(path)) {
				Files.createDirectories(Paths.get(UPLOAD_FOLDER + datePath));
			}
			//文件写入指定路径
			Files.write(path, bytes);
			result.put("type", type);
			result.put("code", "0");
			result.put("relatedPath", relatedPath);
			result.put("size", file.getSize()/1024+"");
			result.put("ext", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1));
			String plateNumber="";
			//车牌识别
			try {
				if ("0".equals(type)) {
					 // 初始化一个AipOcr
			        AipOcr client = new AipOcr("17018550", "AKPlSO3sfH1x4jv98wxBPBLN", "wxZ7UwybCHOejnEm3meu1xazi0bTnOua");
			        // 可选：设置网络连接参数
			        client.setConnectionTimeoutInMillis(2000);
			        client.setSocketTimeoutInMillis(60000);

			        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//			        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//			        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

			        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
			        // 也可以直接通过jvm启动参数设置此环境变量
			        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

			        // 调用接口
//			        String path = "test.jpg";
//			        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//			        System.out.println(res.toString(2));
			        // 传入可选参数调用接口
			        HashMap<String, String> options = new HashMap<String, String>();
			        options.put("multi_detect", "true");
			        
			        
//			        // 参数为本地路径
//			        String image = "D:/xlz/2.jpeg";
//			        JSONObject res = client.plateLicense(image, options);
//			        System.out.println(res.toString(2));

//			        // 参数为二进制数组
			        JSONObject res = client.plateLicense(bytes, options);			       
			        log.info(res.toString());
			        JSONArray words_result=res.getJSONArray("words_result");
			        for (Object object : words_result) {
			        	JSONObject word_result = (JSONObject) object;
			        	plateNumber=word_result.getString("number");
			        	System.err.println(plateNumber);
			        }
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			result.put("plateNumber", plateNumber);
			
		} catch (IOException e) {
			result.put("code", "1");
			result.put("msg", e.getMessage());
		}
		
		return result;
	}
}
