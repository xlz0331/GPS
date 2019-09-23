package com.hwagain.eagle.acquisitionIndictor.mapper;

import com.hwagain.eagle.acquisitionIndictor.entity.AcquisitionIndictor;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 收购指标信息表 Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
public interface AcquisitionIndictorMapper extends BaseMapper<AcquisitionIndictor> {
    void deleteByIds(@Param("list")String[] ids);
}