package com.hwagain.eagle.region.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.base.dto.OaAduitDetailDto;
import com.hwagain.eagle.base.entity.ChinaRegion;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.entity.OaAduit;
import com.hwagain.eagle.base.entity.OaAduitDetail;
import com.hwagain.eagle.base.service.IChinaRegionService;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.base.service.IOaAduitDetailService;
import com.hwagain.eagle.base.service.IOaAduitService;
import com.hwagain.eagle.config.LogAspect;
import com.hwagain.eagle.region.dto.RegionOaDto;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.entity.RegionOa;
import com.hwagain.eagle.region.mapper.RegionOaMapper;
import com.hwagain.eagle.region.service.IRegionHistoryService;
import com.hwagain.eagle.region.service.IRegionOaService;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.SqlDbUtils;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.w;

/**
 * <p>
 * 片区表 服务实现类
 * </p>
 *
 * @author hanjin
 * @since 2019-05-11
 */
@Slf4j
@Service("regionOaService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegionOaServiceImpl extends ServiceImpl<RegionOaMapper, RegionOa> implements IRegionOaService {
	
	@Autowired IRegionHistoryService regionHistoryService;
	@Autowired IRegionService regionService;
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired BaseContextHandler baseContextHandler;
	@Autowired IDictService dictService;;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired IOaAduitService oaAduitService;
	@Autowired IOaAduitDetailService oaAduitDetailService;
	@Autowired IChinaRegionService chinaRegionService;
	static {
		
	}
	// RegionOa转Region
	static MapperFacade RegionOAToRegionMapper;
	
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegionOa.class, Region.class).byDefault().register();
		RegionOAToRegionMapper = factory.getMapperFacade();
		
		MapperFactory factoryOne = new DefaultMapperFactory.Builder().build();
		factoryOne.classMap(RegionOa.class, RegionOaDto.class).byDefault().register();
		entityToDtoMapper = factoryOne.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegionOaDto.class, RegionOa.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//新增
	@Override
	public boolean insertOne(RegionOa entity) {
		entity.setSupplierName("散户");
//		Assert.notBlank(entity.getSupplierName(), "供应商名称不能为空");
		Assert.notBlank(entity.getMaterial(), "原料名称不能为空");
		Assert.notNull(entity.getEffdt(), "生效时间不能为空");
		Assert.notBlank(entity.getProvince(), "省份不能为空");
		Assert.notBlank(entity.getCity(),  "城市不能为空");
//		Assert.notBlank(entity.getRegionName(), "县/区不能为空");
//		Assert.notNull(entity.getRegionDistance(), "运距不能为空");
		Assert.notNull(entity.getSubsidy2(), "运费补助不能为空");
//		if(entity.getRegionDistance()<1){
//			Assert.throwException("运距必须大于0");
//		}
//		String [] aa=entity.getRegionName().split(",");
		List<String> re=new ArrayList<>();
		
//		for(String str:aa){
//			ChinaRegion chinaRegiona=chinaRegionService.selectById(str);
//			re.add(chinaRegiona.getName());
//		}
//		String chinaRegionCoun=StringUtils.join(re,"、");
		ChinaRegion chinaRegionProvince=chinaRegionService.selectById(entity.getProvince());
		ChinaRegion chinaRegionCity=chinaRegionService.selectById(entity.getCity());
		
		entity.setProvince(chinaRegionProvince.getName());
		entity.setCity(chinaRegionCity.getName());
//		entity.setRegionName(chinaRegionCoun);
		Wrapper<RegionOa> wrapper=new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.eq("supplier_name", entity.getSupplierName());
		wrapper.eq("province", entity.getProvince());
		wrapper.eq("city", entity.getCity());
//		wrapper.eq("region_name", entity.getRegionName());
		wrapper.eq("material", entity.getMaterial());
		wrapper.notIn("state", 2);
		RegionOa regionOa=super.selectFirst(wrapper);
		if(regionOa!=null){
			Assert.throwException("该竹木原料运费补助标准已存在");
		}
		entity.setState(0);
		entity.setStatus(0);
		entity.setCreateTime(new Date());
		entity.setCreatorId(UserUtils.getUserInfo().getName());
		Wrapper<RegionOa> wrap=new CriterionWrapper<RegionOa>(RegionOa.class);
		wrap.isNotNull("oa_code");
		wrap.notIn("status", 1);
		wrap.notIn("state", 2);
		RegionOa reg=super.selectFirst(wrap);
		if(reg!=null){
			entity.setOaCode(reg.getOaCode());
		}
		return super.insert(entity);
	}
	
	@Override
	public boolean updateById(RegionOa entity) {
		Assert.notNull(entity.getFdId(), "数据ID不能为空");
//		entity.setState(1);
		entity.setStatus(0);
		entity.setLastAlterTime(new Date());
		entity.setLastAltorId(UserUtils.getUserInfo().getName());
		return super.updateById(entity);
	}
	
	@Override
	public boolean deleteById(Serializable id) {
		Assert.notNull(id, "数据ID不能为空");
		RegionOa regionOa=super.selectById(id);
		Assert.notNull(regionOa, "删除数据不存在");
		Wrapper<Region> wrapper=new CriterionWrapper<Region>(Region.class);
		wrapper.eq("supplier_name", regionOa.getSupplierName());
		wrapper.eq("province", regionOa.getProvince());
		wrapper.eq("city", regionOa.getCity());
		wrapper.eq("region_name", regionOa.getRegionName());
		wrapper.eq("material", regionOa.getMaterial());
		Region region=regionService.selectFirst(wrapper);
		if(region==null){
			regionOa.setState(2);
		}else{
			Assert.throwException("非新增数据，不可删除");
		}	
		return super.updateById(regionOa);
	}

	@Override
	public List<RegionOa> findList() throws CustomException {
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
//		wrapper.eq("supplier_name", "散户");
//		wrapper.orderBy("fd_id");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("effdt");
		return super.selectList(wrapper);
	}
	
	
	@Override
	public List<RegionOa> findListRetail() throws CustomException {
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
		wrapper.eq("supplier_name", "散户");
//		wrapper.orderBy("fd_id");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("effdt");
		return super.selectList(wrapper);
	}
	
	//OA审核审批Region结果回写
	@Override
	public String approveRegion(Integer status,String oaCode,String node,String empName,String empNo,String flowDjbh,String flowDjlsh) throws CustomException {
		Assert.notNull(status, "请指定审核状态");
		String [] sta={"0","2"};
		Assert.isTrue((1==status||-1==status||2==status||3==status||0==status), "审核状态只能为1（审核通过）或者-1（审核不通过）,0(退回初始节点),2(从初始节点进入审核节点),3(从初始节点删除)");
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
//		wrapper.in("status", sta);
		wrapper.eq("oa_code", oaCode);
		List<RegionOa> list = super.selectList(wrapper);
		if(list==null||list.size()<1) {
			Assert.throwException("无待审数据，无法审核！");
		}
		Date doDate = new Date();
		String flowName="竹木原料运费补助标准调整申请表";
		String platform="region";
		//记录OA日志
		
		//修改表为已审核状态
//		RegionOa entity = new RegionOa();
//		entity.setStatus(status);
		//审核通过
		if(1==status) {
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("补助标准审批");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
			for(RegionOa regionOa:list){
				RegionOa ent=new RegionOa();
				ent.setFdId(Long.valueOf(IdWorker.getId()));				
				ent.setSubsidy(regionOa.getSubsidy2());
				ent.setSubsidy2(regionOa.getSubsidy2());
				ent.setCity(regionOa.getCity());
				ent.setProvince(regionOa.getProvince());
				ent.setRegionDistance(regionOa.getRegionDistance());
				ent.setRegionName(regionOa.getRegionName());
				ent.setSupplierName(regionOa.getSupplierName());
				ent.setSupplierId(regionOa.getSupplierId());
				ent.setEffdt(regionOa.getEffdt());
				ent.setMaterial(regionOa.getMaterial());
				ent.setStatus(0);
				ent.setState(1);
				ent.setCreateTime(regionOa.getCreateTime());
				ent.setCreatorId(regionOa.getCreatorId());
				super.insert(ent);
			}
			
			for(RegionOa regionOa:list){
				regionOa.setState(1);
				regionOa.setStatus(status);
				regionOa.setLastAlterTime(doDate);
				regionOa.setLastAltorId(empName);
				super.updateById(regionOa);
				OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
				oaDetail.setFdId(IdWorker.getId());
				oaDetail.setRecFdId(regionOa.getFdId());
				oaAduitDetailService.insert(oaDetail);
			}
//			super.update(entity, wrapper);
			//正在使用数据插入到历史表
			regionHistoryService.insertBatchByRegionTable();
			//全量删除正式数据
			regionService.delete(new CriterionWrapper<Region>(Region.class));
			//审批数据插入到正式表
			List<Region> regionList = RegionOAToRegionMapper.mapAsList(list, Region.class);
			insertRegion(regionList);
		}
		if(status==-1){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("补助标准审批不通过");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(0);
				regionOa.setOaCode("");
				super.updateById(regionOa);
				
			}			
		}
		if(status==2){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("补助标准审批中");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(status);
				super.updateById(regionOa);
				
			}			
		}
		if(status==3){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("从初始节点删除");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(0);
				regionOa.setOaCode("");
				super.updateAllById(regionOa);
				
			}			
		}
		if(status==0){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("退回到初始节点");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(0);
				super.updateById(regionOa);
				
			}			
		}
		return null;
	}
	
	//OA审核审批RegionRetail结果回写
	@Override
	public String approveRegionRetail(Integer status,String oaCode,String node,String empName,String empNo,String flowDjbh,String flowDjlsh) throws CustomException {
		Assert.notNull(status, "请指定审核状态");
		String [] sta={"0","2"};
		Assert.isTrue((1==status||-1==status||2==status||3==status||0==status), "审核状态只能为1（审核通过）或者-1（审核不通过）,0(退回初始节点),2(从初始节点进入审核节点),3(从初始节点删除)");
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
//		wrapper.in("status", sta);
		wrapper.eq("oa_code", oaCode);
		List<RegionOa> list = super.selectList(wrapper);
		if(list==null||list.size()<1) {
			Assert.throwException("无待审数据，无法审核！");
		}
		Date doDate = new Date();
		String flowName="竹木原料运费补助标准调整申请表";
		String platform="region";
		//记录OA日志
		
		//修改表为已审核状态
//		RegionOa entity = new RegionOa();
//		entity.setStatus(status);
		//审核通过
		if(1==status) {
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("补助标准审批");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
			for(RegionOa regionOa:list){
				RegionOa ent=new RegionOa();
				ent.setFdId(Long.valueOf(IdWorker.getId()));				
				ent.setSubsidy(regionOa.getSubsidy2());
				ent.setSubsidy2(regionOa.getSubsidy2());
				ent.setCity(regionOa.getCity());
				ent.setProvince(regionOa.getProvince());
				ent.setRegionDistance(regionOa.getRegionDistance());
				ent.setRegionName(regionOa.getRegionName());
				ent.setSupplierName(regionOa.getSupplierName());
				ent.setSupplierId(regionOa.getSupplierId());
				ent.setEffdt(regionOa.getEffdt());
				ent.setMaterial(regionOa.getMaterial());
				ent.setStatus(0);
				ent.setState(1);
				ent.setCreateTime(regionOa.getCreateTime());
				ent.setCreatorId(regionOa.getCreatorId());
				super.insert(ent);
			}
			
			for(RegionOa regionOa:list){
				regionOa.setState(1);
				regionOa.setStatus(status);
				regionOa.setLastAlterTime(doDate);
				regionOa.setLastAltorId(empName);
				super.updateById(regionOa);
				OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
				oaDetail.setFdId(IdWorker.getId());
				oaDetail.setRecFdId(regionOa.getFdId());
				oaAduitDetailService.insert(oaDetail);
			}
//			super.update(entity, wrapper);
			//正在使用数据插入到历史表
			regionHistoryService.insertBatchByRegionTable();
			//全量删除正式数据
			Wrapper<Region> wraRegion=new CriterionWrapper<Region>(Region.class);
			wrapper.eq("supplier_name", "散户");
			wrapper.eq("state", 1);
			List<Region> lRegions=regionService.selectList(wraRegion);
			log.info("删除散户数据:"+lRegions.size());
			for(Region region:lRegions){
				regionService.deleteById(region.getFdId());
			}
//			regionService.delete(new CriterionWrapper<Region>(Region.class));
			//审批数据插入到正式表
			List<Region> regionList = RegionOAToRegionMapper.mapAsList(list, Region.class);
			insertRegion(regionList);
		}
		if(status==-1){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("补助标准审批不通过");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(0);
				regionOa.setOaCode("");
				super.updateById(regionOa);
				
			}			
		}
		if(status==2){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("补助标准审批中");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(status);
				super.updateById(regionOa);
				
			}			
		}
		if(status==3){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("从初始节点删除");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(0);
				regionOa.setOaCode("");
				super.updateAllById(regionOa);
				
			}			
		}
		if(status==0){		
			OaAduit oa = new OaAduit();
			oa.setFdId(IdWorker.getId());
			oa.setOaCode(oaCode);
			oa.setPlatform(platform);
			oa.setTableName("region");
			oa.setFlowName(flowName);
			oa.setNodeName("退回到初始节点");
			oa.setEmpName(empName);
			oa.setEmpNo(empNo);
			oa.setFlowDjbh(flowDjbh);
			oa.setFlowDjlsh(flowDjlsh);
			oa.setStatus(status);	
			// oa.setCreaterId(UserUtils.getUserId());
			oa.setCreateTime(doDate);
			oaAduitService.insert(oa);
			for(RegionOa regionOa:list){				
				regionOa.setStatus(0);
				super.updateById(regionOa);
				
			}			
		}
		return null;
	}
	
	@Async
	private void insertRegion(List<Region> regionList) {
		regionList.forEach(re->{
			regionService.insert(re);
		});
	}
	
	//提交OA
	@Override
	public String sendToOA(){
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
		wrapper.eq("status", 0);
//		wrapper.eq("supplier_name", "散户");
//		wrapper.isNull("oa_code");
		List<RegionOa> list=super.selectList(wrapper);
		if(list.size()==0){
			return "没有需要提交OA的数据";
		}
		String oaCode = String.valueOf(IdWorker.getId());
		String flowName="竹木原料运费补助标准调整申请表";
		String platform="region";
		Dict dic = dictService.findOaTemId(flowName, platform);
		Assert.notNull(dic, "没有找到[" + flowName + "]的OA流程配置信息");
		Assert.notBlank(dic.getCode(), "没有找到[" + flowName + "]的OA流程配置信息");
		String temid = dic.getCode();
		String title = dic.getText();
		String userName=UserUtils.getUserInfo().getName();
		String userEmpNo=UserUtils.getUserCode();
		Integer iresult = SqlDbUtils.sentOaFlow(temid, oaCode, title, userEmpNo, userName, platform + ";" + flowName);
		Assert.isTrue(iresult == 1, "提交OA失败");
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oaCode);
		oa.setPlatform(platform);
		oa.setTableName("region");
		oa.setFlowName(flowName);
		oa.setNodeName("提交OA");
		oa.setEmpName(userName);
		oa.setEmpNo(userEmpNo);
		oa.setStatus(0);
		oa.setCreaterId(userEmpNo);
		oa.setCreateTime(new Date());
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
		OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
		oaDetail.setFdId(IdWorker.getId());
		oaDetail.setRecFdId(IdWorker.getId());
		oaAduitDetailService.insert(oaDetail);
		for(RegionOa regionOa:list){
			regionOa.setOaCode(oaCode);
			regionOa.setLastAlterTime(new Date());
			regionOa.setLastAltorId(UserUtils.getUserInfo().getName());
			super.updateById(regionOa);
		}
		return "提交OA成功";
	}
	
	//提交OA
	@Override
	public String sendToOARetail(){
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
		wrapper.eq("status", 0);
		wrapper.eq("supplier_name", "散户");
//			wrapper.isNull("oa_code");
		List<RegionOa> list=super.selectList(wrapper);
		if(list.size()==0){
			return "没有需要提交OA的数据";
		}
		String oaCode = String.valueOf(IdWorker.getId());
		String flowName="竹木原料运费补助标准调整申请表";
		String platform="region";
		Dict dic = dictService.findOaTemId(flowName, platform);
		Assert.notNull(dic, "没有找到[" + flowName + "]的OA流程配置信息");
		Assert.notBlank(dic.getCode(), "没有找到[" + flowName + "]的OA流程配置信息");
		String temid = dic.getCode();
		String title = dic.getText();
		String userName=UserUtils.getUserInfo().getName();
		String userEmpNo=UserUtils.getUserCode();
		Integer iresult = SqlDbUtils.sentOaFlow(temid, oaCode, title, userEmpNo, userName, platform + ";" + flowName);
		Assert.isTrue(iresult == 1, "提交OA失败");
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oaCode);
		oa.setPlatform(platform);
		oa.setTableName("region");
		oa.setFlowName(flowName);
		oa.setNodeName("提交OA");
		oa.setEmpName(userName);
		oa.setEmpNo(userEmpNo);
		oa.setStatus(0);
		oa.setCreaterId(userEmpNo);
		oa.setCreateTime(new Date());
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
		OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
		oaDetail.setFdId(IdWorker.getId());
		oaDetail.setRecFdId(IdWorker.getId());
		oaAduitDetailService.insert(oaDetail);
		for(RegionOa regionOa:list){
			regionOa.setOaCode(oaCode);
			regionOa.setLastAlterTime(new Date());
			regionOa.setLastAltorId(UserUtils.getUserInfo().getName());
			super.updateById(regionOa);
		}
		return "提交OA成功";
	}
	
	@Override
	public List<OaAduit> findVersion(String node){
		Wrapper<OaAduit> wrapper=new CriterionWrapper<OaAduit>(OaAduit.class);
		wrapper.eq("node_name", node);
//		wrapper.eq("status", 1);
		List<OaAduit> list=oaAduitService.selectList(wrapper);
		return list;		
	}
	
	
	@Override
	public List<RegionOaDto> findList2(String oaCode) throws CustomException {
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		wrapper.notIn("state", 2);
		wrapper.eq("oa_code", oaCode);
//		wrapper.orderBy("fd_id");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("effdt");
		List<RegionOa> list=super.selectList(wrapper);
		List<RegionOaDto> listA=new ArrayList<>();
		for(RegionOa regionOa:list){
			RegionOaDto regionOaDto=entityToDtoMapper.map(regionOa, RegionOaDto.class);
			if(regionOaDto.getState()==0){
				regionOaDto.setSubsidy(null);
				regionOaDto.setSubsidy3(regionOaDto.getSubsidy2());
				regionOaDto.setSubsidyExt(null);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        Date date = new Date(regionOaDto.getEffdt());
		        regionOaDto.setEffdtText(simpleDateFormat.format(date));
			}else{
//				System.err.println(regionOaDto.getSubsidy2()+"-"+regionOa.getSubsidy());
				if(regionOaDto.getSubsidy2()!=null&&regionOaDto.getSubsidy()!=null){
					if(regionOaDto.getSubsidy2()-regionOa.getSubsidy()!=0){
						regionOaDto.setSubsidy3(regionOaDto.getSubsidy2());
						regionOaDto.setSubsidyExt(regionOaDto.getSubsidy2()-regionOa.getSubsidy());
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				        Date date = new Date(regionOaDto.getEffdt());
				        regionOaDto.setEffdtText(simpleDateFormat.format(date));
					}else{
						regionOaDto.setSubsidy3(null);
						regionOaDto.setReason("");
					}
				}
			}
			
	        listA.add(regionOaDto);
		}
		return listA;
	}
	
	@Override
	public List<RegionOaDto> findList3() throws CustomException {
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		String [] status={"0","2"};
		wrapper.notIn("state", 2);
		wrapper.in("status", status);
//		wrapper.eq("supplier_name", "散户");
//		wrapper.orderBy("fd_id");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("material");
		wrapper.orderBy("effdt");
		List<RegionOa> list=super.selectList(wrapper);
		List<RegionOaDto> listA=new ArrayList<>();
		for(RegionOa regionOa:list){
			RegionOaDto regionOaDto=entityToDtoMapper.map(regionOa, RegionOaDto.class);
			if(regionOaDto.getState()==0){
				regionOaDto.setSubsidy(null);
				regionOaDto.setSubsidy3(regionOaDto.getSubsidy2());
				regionOaDto.setSubsidyExt(null);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        Date date = new Date(regionOaDto.getEffdt());
		        regionOaDto.setEffdtText(simpleDateFormat.format(date));
			}else{
//				System.err.println(regionOaDto.getSubsidy2()+"-"+regionOa.getSubsidy());
				if(regionOaDto.getSubsidy2()!=null&&regionOaDto.getSubsidy()!=null){
					if(regionOaDto.getSubsidy2()-regionOa.getSubsidy()!=0){
						regionOaDto.setSubsidy3(regionOaDto.getSubsidy2());
						regionOaDto.setSubsidyExt(regionOaDto.getSubsidy2()-regionOa.getSubsidy());
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				        Date date = new Date(regionOaDto.getEffdt());
				        regionOaDto.setEffdtText(simpleDateFormat.format(date));
					}else{
						regionOaDto.setSubsidy3(null);
						regionOaDto.setReason("");
					}
				}
			}
			
	        listA.add(regionOaDto);
		}
		return listA;
	}
	
	@Override
	public List<RegionOaDto> findListRetail3() throws CustomException {
		Wrapper<RegionOa> wrapper = new CriterionWrapper<RegionOa>(RegionOa.class);
		String [] status={"0","2"};
		wrapper.notIn("state", 2);
		wrapper.in("status", status);
		wrapper.eq("supplier_name", "散户");
//		wrapper.orderBy("fd_id");
		wrapper.orderBy("supplier_name");
		wrapper.orderBy("province");
		wrapper.orderBy("effdt");
		List<RegionOa> list=super.selectList(wrapper);
		List<RegionOaDto> listA=new ArrayList<>();
		for(RegionOa regionOa:list){
			RegionOaDto regionOaDto=entityToDtoMapper.map(regionOa, RegionOaDto.class);
			if(regionOaDto.getState()==0){
				regionOaDto.setSubsidy(null);
				regionOaDto.setSubsidy3(regionOaDto.getSubsidy2());
				regionOaDto.setSubsidyExt(null);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        Date date = new Date(regionOaDto.getEffdt());
		        regionOaDto.setEffdtText(simpleDateFormat.format(date));
			}else{
//				System.err.println(regionOaDto.getSubsidy2()+"-"+regionOa.getSubsidy());
				if(regionOaDto.getSubsidy2()!=null&&regionOaDto.getSubsidy()!=null){
					if(regionOaDto.getSubsidy2()-regionOa.getSubsidy()!=0){
						regionOaDto.setSubsidy3(regionOaDto.getSubsidy2());
						regionOaDto.setSubsidyExt(regionOaDto.getSubsidy2()-regionOa.getSubsidy());
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				        Date date = new Date(regionOaDto.getEffdt());
				        regionOaDto.setEffdtText(simpleDateFormat.format(date));
					}else{
						regionOaDto.setSubsidy3(null);
						regionOaDto.setReason("");
					}
				}
			}
			
	        listA.add(regionOaDto);
		}
		return listA;
	}
}
