package com.hwagain.eagle.company.mapper;

import com.hwagain.eagle.company.entity.Company;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 公司信息表 Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
public interface CompanyMapper extends BaseMapper<Company> {
    void deleteByIds(@Param("list")String[] ids);
}