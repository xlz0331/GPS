package com.hwagain.eagle.supplier.service.impl;

import com.hwagain.eagle.supplier.entity.MaterialPurchase;
import com.hwagain.eagle.supplier.entity.MaterialPurchasePlan;
import com.hwagain.eagle.supplier.entity.MaterialRpt;
import com.hwagain.eagle.base.dto.MaterialDto;
import com.hwagain.eagle.base.dto.OaAduitDetailDto;
import com.hwagain.eagle.base.dto.OaAduitDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.entity.OaAduit;
import com.hwagain.eagle.base.entity.OaAduitDetail;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.base.service.IOaAduitDetailService;
import com.hwagain.eagle.base.service.IOaAduitService;
import com.hwagain.eagle.supplier.dto.MaterialPurchaseBoundDto;
import com.hwagain.eagle.supplier.dto.MaterialPurchasePlanDto;
import com.hwagain.eagle.supplier.dto.MaterialPurchasePlanRptDto;
import com.hwagain.eagle.supplier.mapper.MaterialPurchasePlanMapper;
import com.hwagain.eagle.supplier.service.IMaterialPurchaseBoundService;
import com.hwagain.eagle.supplier.service.IMaterialPurchasePlanService;
import com.hwagain.eagle.supplier.service.IMaterialPurchaseService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.SqlDbUtils;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.framework.core.util.Assert;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-03-22
 */
@Service("materialPurchasePlanService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MaterialPurchasePlanServiceImpl extends ServiceImpl<MaterialPurchasePlanMapper, MaterialPurchasePlan> implements IMaterialPurchasePlanService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired BaseContextHandler baseContextHandler;
	@Autowired IDictService dictService;
	@Autowired IOaAduitService oaAduitService;
	@Autowired IOaAduitDetailService oaAduitDetailService;
	@Autowired IMaterialPurchaseBoundService materialPurchaseBoundService;
	@Autowired IMaterialService materialService;
	@Autowired IMaterialPurchaseService materialPurchaseService;
	@Autowired MaterialPurchasePlanMapper materialPurchasePlanMapper;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(MaterialPurchasePlan.class, MaterialPurchasePlanDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(MaterialPurchasePlanDto.class, MaterialPurchasePlan.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	private void validate(MaterialPurchasePlanDto dto){
		Assert.notBlank(dto.getSupplierName(), "供应商名称不能为空");
		Assert.notBlank(dto.getMaterial(), "原料名称不能为空");
//		Assert.notNull(dto.getAveragePercarNum(), "平均每车总量不能为空");
//		Assert.notNull(dto.getMaxPurchaseDayNum(), "日均收购量上限不能为空");
//		Assert.notNull(dto.getMinPurchaseDayNum(), "日均收购量下限不能为空");
//		Assert.notNull(dto.getMaxCarNum(), "日均上限车数不能为空");
//		Assert.notNull(dto.getMinCarNum(), "日均上限车数不能为空");
		Assert.notNull(dto.getPlanStarttime(), "计划开始时间不能为空");
		Assert.notNull(dto.getPlanEndtime(), "计划结束时间不能为空");
		Assert.notNull(dto.getPurchasePlanNum(), "计划收购总量不能为空");
	}
	
	@Override
	public MaterialPurchasePlanDto addOne(MaterialPurchasePlanDto dto){
		BigDecimal bignum1 = new BigDecimal("100");  
		MaterialPurchaseBoundDto bound=materialPurchaseBoundService.findBound();
		validate(dto);
		Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
		Date createDate=new Date();
		dto.setFdId(Long.valueOf(IdWorker.getId()));
		if(bound!=null){
			dto.setMaxPurchaseDayNum(dto.getPurchasePlanNum().multiply(bound.getMax().add(bignum1)).divide(bignum1));
			dto.setMinPurchaseDayNum(dto.getPurchasePlanNum().multiply(bignum1.subtract(bound.getMin())).divide(bignum1));
		}else{
			dto.setMaxPurchaseDayNum(dto.getPurchasePlanNum());
			dto.setMinPurchaseDayNum(dto.getPurchasePlanNum());
		}
		dto.setState(1);
		dto.setCreatorId(creator);
		dto.setCreateTime(createDate);
		super.insert(dtoToEntityMapper.map(dto, MaterialPurchasePlan.class));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		String date=df.format(dto.getPlanStarttime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date2=null;
		try {
			date2=sdf.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		Wrapper<MaterialPurchase> wraMater=new CriterionWrapper<MaterialPurchase>(MaterialPurchase.class);
//		wraMater.eq("create_time", date2);
//		wraMater.eq("material", dto.getMaterial());
//		MaterialPurchase mater=materialPurchaseService.selectFirst(wraMater);
//		if(mater!=null){
//			mater.setPurchaseNum(dto.getPurchasePlanNum().add(mater.getPurchaseNum()));
//			mater.setMaxPurchaseNum(dto.getMaxPurchaseDayNum().add(mater.getMaxPurchaseNum()));
//			mater.setMinPurchaseNum(dto.getMinPurchaseDayNum().add(mater.getMinPurchaseNum()));
//			materialPurchaseService.updateById(mater);
//		}else{
//			MaterialPurchase mater2=new MaterialPurchase();
//			mater2.setFdId(Long.valueOf(IdWorker.getId()));
//			mater2.setPurchaseNum(dto.getPurchasePlanNum());
//			mater2.setMaxPurchaseNum(dto.getMaxPurchaseDayNum());
//			mater2.setMinPurchaseNum(dto.getMinPurchaseDayNum());
//			mater2.setMaterial(dto.getMaterial());
////			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//			mater2.setCreatorId(creator);
////	        System.out.println();			
//			mater2.setCreateTime(date2);			
//			materialPurchaseService.insert(mater2);
//		}
		return dto;
	}
	
	@Override
	public List<MaterialPurchasePlanDto> findBySupplier(Long supplierId,Integer state){
		
		Wrapper<MaterialPurchasePlan> wrapper=new CriterionWrapper<MaterialPurchasePlan>(MaterialPurchasePlan.class);
		String [] state1={"5"};
		wrapper.eq("supplier_id", supplierId);
		wrapper.eq("state", state);
		wrapper.notIn("state", state1);
		List<MaterialPurchasePlan> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, MaterialPurchasePlanDto.class);
	}
	
	@Override
	public List<MaterialRpt> getNowPlan(){
		String name="";
		String level="";
		List<MaterialDto> material=materialService.findAll(name, level);
		MaterialRpt mat=new MaterialRpt();
		for(MaterialDto mDto:material){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
			String date=df.format(new Date());
			String MM=date.substring(4, 6);//截取系统月份
			int i=1;
//			mat.setRowi(1);
			System.err.println(MM);
		}
		return null;
		
	}
	
	//app
	@Override
	public List<MaterialPurchasePlanDto> findBySupplierId(){
		String supplierName=baseContextHandler.getUsername();		
		List<MaterialPurchasePlan> list1=new ArrayList<>();
		Wrapper<MaterialPurchasePlan> wrapper=new CriterionWrapper<MaterialPurchasePlan>(MaterialPurchasePlan.class);
		String [] state={"3"};
		wrapper.eq("supplier_name", supplierName);
		wrapper.in("state", state);
		List<MaterialPurchasePlan> list=super.selectList(wrapper);
		Date dt1=null;
		Date dt2=null;
		Date dt3=null;
		if(list.size()>0){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			for(MaterialPurchasePlan dto:list){
				String startTime = df.format(dto.getPlanStarttime());
				String endTime = df.format(dto.getPlanEndtime());
				String nowTime=df.format(new Date());
				try {
					dt1 = df.parse(startTime);
					dt2 = df.parse(endTime);
					dt3 = df.parse(nowTime);
					if(dt3.after(dt1)&&dt3.before(dt2)){
						list1.add(dto);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return entityToDtoMapper.mapAsList(list1, MaterialPurchasePlanDto.class);
	}

	@Override
	public String deleteByIds(String ids){
		if(ids.isEmpty()){
			Assert.throwException("未选中要删除的行");
		}
		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
		String[] id = ids.split(";");
		for(String fdId:id){
			System.err.println(fdId);
			MaterialPurchasePlan material=super.selectById(fdId);
			System.err.println(material);
			if(material==null){
				Assert.throwException("记录不存在");
			}
			if(material!=null&&material.getState()==1){
				material.setState(5);
				material.setLastAltorId(creatorId);
				material.setLastAlterTime(new Date());
				super.updateById(material);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
				String date=df.format(material.getPlanStarttime());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				Date date2=null;
				try {
					date2=sdf.parse(date);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				Wrapper<MaterialPurchase> wraMater=new CriterionWrapper<MaterialPurchase>(MaterialPurchase.class);
//				wraMater.eq("create_time", date2);
//				wraMater.eq("material", material.getMaterial());
//				MaterialPurchase mater=materialPurchaseService.selectFirst(wraMater);
//				if(mater!=null){
//					mater.setPurchaseNum(mater.getPurchaseNum().subtract(material.getPurchasePlanNum()));
//					mater.setMaxPurchaseNum(mater.getMaxPurchaseNum().subtract(material.getMaxPurchaseDayNum()));
//					mater.setMinPurchaseNum(mater.getMinPurchaseNum().subtract(material.getMinPurchaseDayNum()));
//					materialPurchaseService.updateById(mater);
//				}
				return ids;
			}
			if(material!=null&&material.getState()!=1){
				MaterialPurchasePlanDto dto=entityToDtoMapper.map(material, MaterialPurchasePlanDto.class);
				Assert.throwException("【"+dto.getStateText()+"】状态记录不可删除，请重新选择需要删除的行");
			}
		}
		return ids;
	}
	
	//历史收购计划查询
	@Override
	public List<MaterialPurchasePlanDto> findMaterialHistory(String supplierName,String material,Date planStartTime,Date planEndTime){
		System.err.println(planStartTime);
		Wrapper<MaterialPurchasePlan> wrapper=new CriterionWrapper<MaterialPurchasePlan>(MaterialPurchasePlan.class);
		String [] state1={"3","4"};
		wrapper.like("supplier_name", "%"+supplierName+"%", SqlLike.DEFAULT);
		wrapper.like("material", "%"+material+"%", SqlLike.DEFAULT);
		wrapper.in("state", state1);
		List<MaterialPurchasePlan> list=super.selectList(wrapper);
		Date dt1=null;
		Date dt2=null;
		Date dt3=null;
		Date dt4=null;
		Date dt5=null;
		List<MaterialPurchasePlan> lista=new ArrayList<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(list.size()>0){
			for(MaterialPurchasePlan dto:list){
				String startTime = df.format(dto.getPlanStarttime());
				String endTime = df.format(dto.getPlanEndtime());
				String nowTime=df.format(new Date());
				try {
					dt1=df.parse(startTime);
					dt2=df.parse(endTime);
					dt5=df.parse(nowTime);
					if(dt2.before(dt5)){
						lista.add(dto);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(list.size()>0&&planStartTime!=null&&planEndTime!=null){
			for(MaterialPurchasePlan dto:list){
				String startTime = df.format(dto.getPlanStarttime());
				String endTime = df.format(dto.getPlanEndtime());
				String startDate=df.format(planStartTime);
				String endDate=df.format(planEndTime);
				try {
					dt1=df.parse(startTime);
					dt2=df.parse(endTime);
					dt3=df.parse(startDate);
					dt4=df.parse(endDate);
					if(dt1.after(dt3)&&dt2.before(dt4)){
						lista.add(dto);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return entityToDtoMapper.mapAsList(lista, MaterialPurchasePlanDto.class);
	}
	
	//提交OA审批
	@Override 
	public List<MaterialPurchasePlanDto> inputSendPurchasePlanToOa(String ids){
		if(ids.isEmpty()){
			Assert.throwException("请选择需要提交OA的行");
		}
		List<MaterialPurchasePlan> list=new ArrayList<>();
		String[] id = ids.split(";");
		for(String fdId:id){
			MaterialPurchasePlan plan=super.selectById(fdId);
			if(plan!=null){
				list.add(plan);
			}
			
		}
		if(list.size()==0){
			Assert.throwException( "没有找到需要推送OA的录入记录");
		}
		
		
		int i = 1;
		for (MaterialPurchasePlan entity : list) {
			if (entity.getState()!=1)
				Assert.throwException("第" + String.valueOf(i) + "条纪录,不是未提交状态,无法提交OA");
			if (null != entity.getOaCode() && !entity.getOaCode().isEmpty())
				Assert.throwException("第" + String.valueOf(i) + "条纪录,OACode不为空,无法提交OA");

			i++;
		}
		Date doDate = new Date();
		String oACode = String.valueOf(IdWorker.getId());
		String flowName="原料收购计划录入";
		String platform="materialPurchasePlan";
		Dict dic = dictService.findOaTemId(flowName, platform);
		Assert.notNull(dic, "没有找到[" + flowName + "]的OA流程配置信息");
		Assert.notBlank(dic.getCode(), "没有找到[" + flowName + "]的OA流程配置信息");
		String temid = dic.getCode();
		String title = dic.getText();
		String userEmpNo = UserUtils.getUserInfo().getFdEmployeeNumber();
		String userName = UserUtils.getUserInfo().getName();
		Integer iresult = SqlDbUtils.sentOaFlow(temid, oACode, title, userEmpNo, userName, platform + ";" + flowName);
		Assert.isTrue(iresult == 1, "提交OA失败");
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oACode);
		oa.setPlatform(platform);
		oa.setTableName("product_base");
		oa.setFlowName(flowName);
		oa.setNodeName("提交OA");
		oa.setEmpName(userName);
		oa.setEmpNo(userEmpNo);
		oa.setStatus(11);
		oa.setCreaterId(UserUtils.getUserId());
		oa.setCreateTime(doDate);
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);

		for (MaterialPurchasePlan dto : list) {
			dto.setOaCode(oACode);
			
			dto.setState(2);

			dto.setLastAlterTime(doDate);
			dto.setLastAltorId(Long.parseLong(UserUtils.getUserInfo().getFdId()));

			super.updateById(dto);

			OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
			oaDetail.setFdId(IdWorker.getId());
			oaDetail.setRecFdId(dto.getFdId());
			oaAduitDetailService.insert(oaDetail);
		}
		return entityToDtoMapper.mapAsList(list, MaterialPurchasePlanDto.class);
	}
	
	@Override
	//String oACode, Integer status, String nodeName, String empName,String empNo, String flowDjbh, String flowDjlsh
	public List<MaterialPurchasePlanDto> inputOaAduitFlow(String oACode,Integer status,String nodeName,
			String empName,String empNo,String flowDjbh,String flowDjlsh){
		Assert.isTrue(status==3||status==4,"状态无效（只能是3：已审核,4：审核不通过）");
		System.err.println(oACode);
		Wrapper<MaterialPurchasePlan> wrapper=new CriterionWrapper<MaterialPurchasePlan>(MaterialPurchasePlan.class);
		wrapper.eq("oa_code", oACode);
		List<MaterialPurchasePlan> list=super.selectList(wrapper);
		Date doDate = new Date();
		String flowName="原料收购计划录入";
		String platform="materialPurchasePlan";
		// 记录日志
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oACode);
		oa.setPlatform(platform);
		oa.setTableName("material_purchase_plan");
		oa.setFlowName(flowName);
		oa.setNodeName(nodeName);
		oa.setEmpName(empName);
		oa.setEmpNo(empNo);
		oa.setFlowDjbh(flowDjbh);
		oa.setFlowDjlsh(flowDjlsh);
		oa.setStatus(status);
//		oa.setCreaterId(UserUtils.getUserCode());
		oa.setCreateTime(doDate);
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
		for(MaterialPurchasePlan dto:list){
			dto.setOaCode(oACode);
			dto.setState(status);
			dto.setLastAltorId(Long.parseLong(UserUtils.getUserInfo().getFdId()));
			dto.setLastAlterTime(doDate);
			super.updateById(dto);
			OaAduitDetail oaDetail=dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
			oaDetail.setFdId(Long.valueOf(IdWorker.getId()));
			oaDetail.setRecFdId(dto.getFdId());
			oaAduitDetailService.insert(oaDetail);
		}
		return entityToDtoMapper.mapAsList(list, MaterialPurchasePlanDto.class);
	}
	
	@Override
	public List<MaterialPurchasePlanDto> inputQueryByOaCode(String oACode) {
		Wrapper<MaterialPurchasePlan> wrapper=new CriterionWrapper<MaterialPurchasePlan>(MaterialPurchasePlan.class);
		wrapper.eq("oa_code", oACode);
		List<MaterialPurchasePlan> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, MaterialPurchasePlanDto.class);
	}
	
	@Override
	public List<MaterialPurchasePlanRptDto> getPurchasePlan(){
		
		List<MaterialPurchasePlanRptDto> listRpt =new ArrayList<>();	
		List<MaterialPurchasePlanRptDto> listDeptSum = materialPurchasePlanMapper.getNowPlan();
		System.err.println(listDeptSum.get(0).getCreateTime());
		if(null!=listDeptSum && !listDeptSum.isEmpty())
		{
			for(int i=0;i<listDeptSum.size();i++)
			{
				MaterialPurchasePlanRptDto s =listDeptSum.get(i);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");//设置日期格式
				String date=df.format(s.getPlanStarttime());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				
				MaterialPurchasePlanRptDto row_1 = new MaterialPurchasePlanRptDto();
				row_1.setRowType(1);
				row_1.setSeqText(date+"计划");
				row_1.setState(10);
//				row_1.setCreateTime(s.getCreateTime());
				if(i>0&&df.format(listDeptSum.get(i-1).getPlanStarttime()).equals(df.format(listDeptSum.get(i).getPlanStarttime()))){
					System.err.println(listDeptSum.get(i-1).getPlanStarttime());
				}else{
					listRpt.add(row_1);
					List<MaterialPurchasePlanRptDto> listDetail = queryList(date);
					listRpt.addAll(listDetail);	
				}			
			}		
		}
		return listRpt;
		
	}
	
	public List<MaterialPurchasePlanRptDto> queryList(String date)
	{
//		Wrapper< MaterialPurchasePlan> wrapper = new CriterionWrapper< MaterialPurchasePlan>( MaterialPurchasePlan.class);
//		wrapper.eq("state", 3);
		List<MaterialPurchasePlan> list = materialPurchasePlanMapper.getNowPlan2(date);
		
		List<MaterialPurchasePlanRptDto> listDto  = entityToDtoMapper.mapAsList(list, MaterialPurchasePlanRptDto.class);
		
		if(null!=listDto && !listDto.isEmpty())
		{
			int iSeq=1;
			for(MaterialPurchasePlanRptDto d : listDto)
			{
				d.setSeq(iSeq);
				d.setSeqText(String.valueOf(iSeq));
				d.setRowType(0);
				iSeq=iSeq+1;
			}
		}
		
		return listDto;
	}
	
//	@Override
//	public  List<MaterialPurchasePlanRptDto> quer
}
