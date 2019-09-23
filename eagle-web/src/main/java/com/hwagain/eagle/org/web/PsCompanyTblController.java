package com.hwagain.eagle.org.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.eagle.org.service.IPsCompanyTblService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xionglz
 * @since 2019-04-17
 */
@RestController
@RequestMapping(value="/org/psCompanyTbl",method={RequestMethod.GET,RequestMethod.POST})
public class PsCompanyTblController extends BaseController{
	
	@Autowired
	IPsCompanyTblService psCompanyTblService;
	
}
