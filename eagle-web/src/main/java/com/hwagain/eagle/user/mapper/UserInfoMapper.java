package com.hwagain.eagle.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 用户信息表 Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    void deleteByIds(@Param("list")String[] ids);

	List<UserInfo> queryByUserAccount(@Param("userAccount") String userAccount);

}