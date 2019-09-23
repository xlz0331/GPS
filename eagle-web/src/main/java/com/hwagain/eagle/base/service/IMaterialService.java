package com.hwagain.eagle.base.service;

import java.util.List;

import com.hwagain.eagle.base.dto.MaterialDto;
import com.hwagain.eagle.base.entity.Material;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-04-02
 */
public interface IMaterialService extends IService<Material> {

	MaterialDto addOne(MaterialDto dto);

	List<MaterialDto> findAll(String name,String level);

	String deleteByIds(String ids);

	List<MaterialDto> getMaterial();

	Material findOneByName(String name);

	String syncMaterial();

	Material updateMaterial(Long fdId, String code);
	
}
