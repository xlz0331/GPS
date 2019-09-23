package com.hwagain.eagle.supplier.service;

import java.util.Date;
import java.util.List;

import com.hwagain.eagle.supplier.dto.MaterialPurchasePlanDto;
import com.hwagain.eagle.supplier.dto.MaterialPurchasePlanRptDto;
import com.hwagain.eagle.supplier.entity.MaterialPurchasePlan;
import com.hwagain.eagle.supplier.entity.MaterialRpt;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-22
 */
public interface IMaterialPurchasePlanService extends IService<MaterialPurchasePlan> {

	MaterialPurchasePlanDto addOne(MaterialPurchasePlanDto dto);

	List<MaterialPurchasePlanDto> findBySupplier(Long supplierId, Integer state);

	List<MaterialPurchasePlanDto> findBySupplierId();

	String deleteByIds(String ids);

	List<MaterialPurchasePlanDto> findMaterialHistory(String supplierName, String material, Date planStartTime,
			Date planEndTime);

	List<MaterialPurchasePlanDto> inputSendPurchasePlanToOa(String ids);

	List<MaterialPurchasePlanDto> inputOaAduitFlow(String oACode, Integer status, String nodeName, String empName,
			String empNo, String flowDjbh, String flowDjlsh);

	List<MaterialPurchasePlanDto> inputQueryByOaCode(String oACode);

	List<MaterialRpt> getNowPlan();

	List<MaterialPurchasePlanRptDto> getPurchasePlan();
	
}
