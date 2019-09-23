package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.dto.DictDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-26
 */
public interface IDictService extends IService<Dict> {

	Dict findOaTemId(String itemName, String groupName);

	List<DictDto> findAll(String typeName);

	DictDto addOne(DictDto dto);

	String deleteByIds(String ids);

	List<Dict> findBytypeName(String typeName);

	Dict updateOneByFdId(Dict dict);
	
}
