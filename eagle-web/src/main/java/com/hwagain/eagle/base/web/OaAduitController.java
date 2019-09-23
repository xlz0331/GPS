package com.hwagain.eagle.base.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.eagle.base.service.IOaAduitService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-03-26
 */
@RestController
@RequestMapping(value="/base/oaAduit",method={RequestMethod.GET,RequestMethod.POST})
public class OaAduitController extends BaseController{
	
	@Autowired
	IOaAduitService oaAduitService;
	
}
