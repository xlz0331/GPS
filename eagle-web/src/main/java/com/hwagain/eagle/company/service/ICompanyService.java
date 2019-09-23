package com.hwagain.eagle.company.service;

import com.hwagain.eagle.company.dto.CompanyDto;
import com.hwagain.eagle.company.entity.Company;
import com.hwagain.framework.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 公司信息表 服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-20
 */
public interface ICompanyService extends IService<Company> {
    String save(CompanyDto companyDto);

    String update(CompanyDto companyDto);

    String delete(String fdId);

    String deleteByIds(String ids);

    List<CompanyDto> findAll();

    Company findOne(String fdId);
}
