package com.hwagain.eagle.supplier.mapper;

import com.hwagain.eagle.supplier.entity.SupplierInfo;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 供应商信息表 Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-22
 */
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

	void deleteByIds(String[] id);

}