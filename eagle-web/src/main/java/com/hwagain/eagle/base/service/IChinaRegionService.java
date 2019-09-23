package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.entity.ChinaRegion;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-20
 */
public interface IChinaRegionService extends IService<ChinaRegion> {

	List<ChinaRegion> findAll(Long parentId);
	
}
