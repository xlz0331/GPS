package com.hwagain.eagle.supplier.service;

import java.util.List;
import java.util.Map;

import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.entity.SupplierInfo;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.util.PageDto;

/**
 * <p>
 * 供应商信息表 服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-22
 */
public interface ISupplierInfoService extends IService<SupplierInfo> {

	public String save(SupplierInfoDto supplierInfoDto);

	public String update(SupplierInfoDto supplierInfoDto);

	public String delete(String fdId);

	public String deleteByIds(String ids);

	public List<SupplierInfoDto> findAll();

	public SupplierInfo findOne(String fdId);
	
	public PageDto<SupplierInfoDto> findByPage(int pageNum, int pageSize,String name,String mobile);

	SupplierInfoDto addOne(SupplierInfoDto dto);

	public String updateUserState();

	public SupplierInfoDto findByName(String supplierName);

	public List<SupplierInfoDto> findAllByName(String name,String mobile);

	public String addSupplier(SupplierInfo entity);

	public List<SupplierInfoDto> findAllByCode(String code);

	public List<Map<String, String>> findAllSupplierForOA();
}
