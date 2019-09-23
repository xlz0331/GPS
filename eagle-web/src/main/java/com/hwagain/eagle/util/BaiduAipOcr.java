package com.hwagain.eagle.util;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class BaiduAipOcr {
	//设置APPID/AK/SK
    public static final String APP_ID = "17018550";
    public static final String API_KEY = "AKPlSO3sfH1x4jv98wxBPBLN";
    public static final String SECRET_KEY = "wxZ7UwybCHOejnEm3meu1xazi0bTnOua";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
//        String path = "test.jpg";
//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//        System.out.println(res.toString(2));
        sample(client);
    }
    
    public static void sample(AipOcr client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "true");
        
        
        // 参数为本地路径
        String image = "D:/xlz/2.jpeg";
        JSONObject res = client.plateLicense(image, options);
        System.out.println(res.toString(2));

//        // 参数为二进制数组
//        byte[] file = readFile("test.jpg");
//        res = client.plateLicense(file, options);
//        System.out.println(res.toString(2));
    }
}
