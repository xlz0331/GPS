package com.hwagain.eagle.acquisitionIndictor.service;

import com.hwagain.eagle.acquisitionIndictor.dto.AcquisitionIndictorDto;
import com.hwagain.eagle.acquisitionIndictor.entity.AcquisitionIndictor;
import com.hwagain.framework.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 收购指标信息表 服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
public interface IAcquisitionIndictorService extends IService<AcquisitionIndictor> {
    String save(AcquisitionIndictorDto acquisitionIndictorDto);

    String update(AcquisitionIndictorDto acquisitionIndictorDto);

    String delete(String fdId);

    String deleteByIds(String ids);

    List<AcquisitionIndictorDto> findAll();

    AcquisitionIndictor findOne(String fdId);
}
