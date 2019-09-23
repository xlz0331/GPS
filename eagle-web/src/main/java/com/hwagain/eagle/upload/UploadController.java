package com.hwagain.eagle.upload;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.UploadUtil;
import com.hwagain.framework.api.category.dto.languageDto;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/upload")
@CrossOrigin
@Api(tags="文件上传API")
public class UploadController {
	
    @Value("${sys.config.upload.basePath}")
    private String UPLOAD_FOLDER;
    @Autowired ILogLoginService logLoginService;
    
    /**
     * 
     * @param file 文件体
     * @param type 文件类型（0 车牌，1车头，2车身，3车尾，1006app异常log）
     * @return Map<String, String>
     */
    @PostMapping("/singlefile")
    @ApiOperation(value = "文件上传", notes = "文件上传",httpMethod="POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="type",value="type",paramType = "query", required = false, dataType = "String")
    })
    public Response singleFileUpload(MultipartFile file,String type) {
    	System.err.println(file);
    	Map<String, String> uploadMap = UploadUtil.upload(file, UPLOAD_FOLDER,type);
    	
//    	if (type.equals("1006")) {
//			LogLogin logLogin=new LogLogin();
//			logLogin.setFdId(Long.valueOf(IdWorker.getId()));
//			logLogin.setTerminalInfo(uploadMap.get("relatedPath"));
//			logLogin.setSessionId("eagleBug");
//			logLogin.setCreateTime(new Date());
//			logLogin.setUserId(Long.valueOf(BaseContextHandler.getUserID()));
//			System.err.println(logLogin.getFdId());
//			loginService.insert(logLogin);
//		}
    	return SuccessResponseData.newInstance(uploadMap);
    }
}
