package com.hwagain.eagle.base.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.eagle.base.service.ILogOperatorService;

/**
 * <p>
 * 操作日志表。
注意：以后开发平台的日志服务上线后，需要做功能迁移 前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@RestController
@RequestMapping(value="/base/logOperator",method={RequestMethod.GET,RequestMethod.POST})
public class LogOperatorController extends BaseController{
	
	@Autowired
	ILogOperatorService logOperatorService;
	
}
