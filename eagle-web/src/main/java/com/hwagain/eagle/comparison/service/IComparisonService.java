package com.hwagain.eagle.comparison.service;

import java.util.List;

import com.hwagain.eagle.comparison.dto.ComparisonDto;
import com.hwagain.eagle.comparison.entity.Comparison;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-26
 */
public interface IComparisonService extends IService<Comparison> {

	List<ComparisonDto> findAll();

	Comparison updateByFdId(Long fdId, String reason);

	List<Comparison> findAllResult();

	String updateAllNew();

	String updateAllOld();
	
}
