package com.hwagain.eagle.region.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.eagle.region.service.ITaskRouteService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-08-28
 */
@RestController
@RequestMapping(value="/region/taskRoute",method={RequestMethod.GET,RequestMethod.POST})
public class TaskRouteController extends BaseController{
	
	@Autowired
	ITaskRouteService taskRouteService;
	
}
