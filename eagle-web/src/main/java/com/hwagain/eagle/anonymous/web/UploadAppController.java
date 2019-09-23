package com.hwagain.eagle.anonymous.web;


import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hwagain.eagle.annotation.IgnoreUserToken;
import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.UploadUtil;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app/upload")
@CrossOrigin
@Api(tags="APP-文件上传API")
public class UploadAppController {
	@Autowired ILogLoginService logLoginService;
    @Value("${sys.config.upload.basePath}")
    private String UPLOAD_FOLDER;
    @Value("${sys.config.uploadLog.basePath}")
    private String UPLOAD_FOLDER_LOG;
    
    /**
     * 文件上传 
     * 
     * @param file	文件体
     * @param type	文件类型（0车牌，1006app异常日志）
     * @return
     */
    @IgnoreUserToken
    @PostMapping("/singlefile")
    @ApiOperation(value = "文件上传", notes = "文件上传",httpMethod="POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="type",value="type",paramType = "query", required = false, dataType = "String")
    })
    public Response singleFileUpload(MultipartFile file,String type) {
    	String upload_fold="";
    	if ("1006".equals(type)) {
    		upload_fold=UPLOAD_FOLDER_LOG;
		}else {
			upload_fold=UPLOAD_FOLDER;
		}
    	Map<String, String> uploadMap = UploadUtil.upload(file, upload_fold,type);
    	if ("1".equals(uploadMap.get("code"))){
    		Assert.isTrue(false, uploadMap.get("code"));
    	}
    	if (("1006").equals(type)) {
			LogLogin logLogin=new LogLogin();
			logLogin.setFdId(Long.valueOf(IdWorker.getId()));
			logLogin.setTerminalInfo(uploadMap.get("relatedPath"));
			logLogin.setSessionId("eagleBug");
			logLogin.setCreateTime(new Date());
			logLogin.setUserId(Long.valueOf(BaseContextHandler.getUserID()));
			logLoginService.insert(logLogin);
		}
    	return SuccessResponseData.newInstance(uploadMap);
    }
}
