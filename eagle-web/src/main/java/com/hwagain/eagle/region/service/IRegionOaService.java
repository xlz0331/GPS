package com.hwagain.eagle.region.service;

import java.util.List;

import com.hwagain.eagle.base.entity.OaAduit;
import com.hwagain.eagle.region.dto.RegionOaDto;
import com.hwagain.eagle.region.entity.RegionOa;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 片区表 服务类
 * </p>
 *
 * @author hanjin
 * @since 2019-05-11
 */
public interface IRegionOaService extends IService<RegionOa> {
	
	public List<RegionOa> findList() throws CustomException;
	
	public String approveRegion(Integer status,String oaCode,String node,String empName,String empNo,String flowDjbh,String flowDjlsh) throws CustomException;

	String sendToOA();

	List<OaAduit> findVersion(String node);

	List<RegionOaDto> findList2(String oaCode) throws CustomException;

	List<RegionOaDto> findList3() throws CustomException;

	boolean insertOne(RegionOa entity);

	List<RegionOa> findListRetail() throws CustomException;

	String approveRegionRetail(Integer status, String oaCode, String node, String empName, String empNo,
			String flowDjbh, String flowDjlsh) throws CustomException;

	String sendToOARetail();

	List<RegionOaDto> findListRetail3() throws CustomException;
}
