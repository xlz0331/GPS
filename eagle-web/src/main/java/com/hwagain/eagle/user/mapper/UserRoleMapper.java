package com.hwagain.eagle.user.mapper;

import com.hwagain.eagle.user.dto.UserRoleDto;
import com.hwagain.eagle.user.entity.UserRole;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 用户角色表 Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    void deleteByIds(@Param("list")String[] ids);
    List<UserRole> queryParam(UserRoleDto userRoleDto);
}