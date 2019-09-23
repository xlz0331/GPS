package com.hwagain.eagle.user.service;

import com.hwagain.eagle.user.dto.UserRoleDto;
import com.hwagain.eagle.user.entity.UserRole;
import com.hwagain.framework.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
public interface IUserRoleService extends IService<UserRole> {
    String save(UserRoleDto userRoleDto);

    String update(UserRoleDto userRoleDto);

    String delete(String fdId);

    String deleteByIds(String ids);

    List<UserRoleDto> findAll();

    UserRole findOne(String fdId);

    List<UserRole> queryParam(UserRoleDto userRoleDto);
}
