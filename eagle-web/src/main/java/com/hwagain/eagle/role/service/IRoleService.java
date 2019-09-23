package com.hwagain.eagle.role.service;

import java.util.List;

import com.hwagain.eagle.role.dto.RoleDto;
import com.hwagain.eagle.role.entity.Role;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.util.PageDto;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
public interface IRoleService extends IService<Role> {

	public RoleDto addOne(RoleDto dto) throws CustomException;

	public String deleteByIds(String ids) throws CustomException;

	public List<RoleDto> findAll() throws CustomException;

	PageDto<RoleDto> findByPage(int pageNum, int pageSize) throws CustomException;
	
}
