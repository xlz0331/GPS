package com.hwagain.eagle.correct.service.impl;

import com.hwagain.eagle.correct.entity.Correct;
import com.hwagain.eagle.correct.entity.CorrectVo;
import com.hwagain.eagle.base.dto.MaterialDto;
import com.hwagain.eagle.base.dto.OaAduitDetailDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.entity.Material;
import com.hwagain.eagle.base.entity.OaAduit;
import com.hwagain.eagle.base.entity.OaAduitDetail;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.base.service.IMaterialService;
import com.hwagain.eagle.base.service.IOaAduitDetailService;
import com.hwagain.eagle.base.service.IOaAduitService;
import com.hwagain.eagle.correct.dto.CorrectDto;
import com.hwagain.eagle.correct.mapper.CorrectMapper;
import com.hwagain.eagle.correct.service.ICorrectService;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.eagle.supplier.entity.MaterialPurchasePlan;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.task.dto.TaskDto;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.entity.TaskPhotosVo;
import com.hwagain.eagle.task.entity.TaskResultVo;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.util.SqlDbUtils;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;


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
import oracle.net.aso.i;
import oracle.net.aso.n;
import oracle.net.aso.w;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
@Service("correctService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CorrectServiceImpl extends ServiceImpl<CorrectMapper, Correct> implements ICorrectService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired IDictService dictService;
	@Autowired IOaAduitService oaAduitService;
	@Autowired IOaAduitDetailService oaAduitDetailService;
	@Autowired ITaskService taskService;
	@Autowired IRegionService regionService;
	@Autowired ITrackInfoService trackInfoService;
	@Autowired ITaskPhotoService taskPhotoService;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired IMaterialService materialService;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Correct.class, CorrectDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(CorrectDto.class, Correct.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//竹木原料运费补助补正申请
	@Override
	public List<CorrectDto> subsidyCorrect(String ids){
		String[] id = ids.split(";");
		for(String fdId:id){
			Long taskId=Long.valueOf(fdId);
			String [] state={"1","2","3","4","5"};
			Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
			wrapper.eq("task_id", taskId);
			wrapper.in("state", state);
			Correct corr=super.selectFirst(wrapper);
			if(corr==null){
				Correct correct=new Correct();
				correct.setFdId(Long.valueOf(IdWorker.getId()));
				correct.setTaskId(taskId);
				correct.setCreateTime(new Date());
				correct.setCreatorId(UserUtils.getUserInfo().getFdId());
				correct.setState(4);
				super.insert(correct);
				Task task=taskService.selectById(taskId);
				task.setCorrections(4);
//				task.setLastAlterTime(new Date());
				task.setLastAltorId(Long.valueOf(UserUtils.getUserInfo().getFdId()));
				taskService.updateById(task);
			}else{
				Assert.throwException("存在申请中的数据");
			}
		}
		Wrapper<Correct> wra=new CriterionWrapper<Correct>(Correct.class);
		wra.eq("state", 1);
		List<Correct> list=super.selectList(wra);
		return entityToDtoMapper.mapAsList(list, CorrectDto.class);		
	}
	
	//提交OA
	@Override
	public List<CorrectDto> sentToOA(List<CorrectDto> dtos){
		Date doDate=new Date();
		if(dtos.size()==0){
			Assert.throwException( "没有找到需要推送OA的记录");
		}
		if(dtos.get(0).getOaCode()!=null&&!dtos.get(0).getOaCode().isEmpty()){
			Assert.throwException( "当前申请已提交OA，请勿重复提交");
		}
		String oACode = String.valueOf(IdWorker.getId());
		String flowName="竹木原料运费补助补正申请审核审批记录明细";
		String platform="correct";
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
		oa.setTableName("correct");
		oa.setFlowName(flowName);
		oa.setNodeName("提交OA");
		oa.setEmpName(userName);
		oa.setEmpNo(userEmpNo);
		oa.setStatus(11);
		oa.setCreaterId(UserUtils.getUserId());
		oa.setCreateTime(doDate);
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);

		for (CorrectDto dto : dtos) {
			
			dto.setOaCode(oACode);
			
			dto.setState(5);
			dto.setLastAlterTime(doDate);
			dto.setLastAltorId(UserUtils.getUserInfo().getFdId());
			super.updateById(dtoToEntityMapper.map(dto, Correct.class));

			OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
			oaDetail.setFdId(IdWorker.getId());
			oaDetail.setRecFdId(dto.getFdId());
			oaAduitDetailService.insert(oaDetail);
		}
		return dtos;		
	}
	
	//所有待提交OA数据
	@Override
	public List<TaskResultVo> findAllSentOA(){
		List<MaterialDto> materials=materialService.findAll("", "D");
		Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
		wrapper.eq("state", 4);
		List<Correct> correctList=super.selectList(wrapper);
		List<TaskResultVo> taskResultVos=new ArrayList<>();
		for(Correct correct:correctList){
			Task task=taskService.selectById(correct.getTaskId());
			for(MaterialDto material:materials){
				if(material.getCode().equals(task.getMaterial())){
					task.setMaterial(material.getName());
				}
			}
			Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
			wraTrack.eq("task_id", correct.getTaskId());
			List<TrackInfo> trackInfos=trackInfoService.selectList(wraTrack);
			
			List<TaskPhotoVo> taskPhotoVos=taskPhotoService.findByParam(correct.getTaskId(), "");
			
			TaskResultVo tVo=new TaskResultVo();
			if(trackInfos.size()>0){
				tVo.setAuditTrack2("合格");
				tVo.setAuditTrack3("合格");
				tVo.setAuditReason1("");
				tVo.setAuditReasonTrack2("");
				tVo.setAuditReasonTrack3("");
				for(TrackInfo trackInfo:trackInfos){
					if(trackInfo.getAudit2()!=null&&trackInfo.getAudit3()!=null){
						if(trackInfo.getAudit2()==2){
							tVo.setAuditTrack2("不合格");
							tVo.setAuditReasonTrack2(trackInfo.getAuditReason2());
						}
						if(trackInfo.getAudit3()==2){
							tVo.setAuditTrack3("不合格");
							tVo.setAuditReasonTrack3(trackInfo.getAuditReason3());
						}
						break;
					}
				}
			}
			if(taskPhotoVos!=null){
				tVo.setAudit1("合格");
				tVo.setAudit2("合格");
				tVo.setAudit3("合格");
				tVo.setAuditReason1("");
				tVo.setAuditReason2("");
				tVo.setAuditReason3("");
				String reason1="";
				String reason2="";
				String reason3="";
				List<String> photoPath=new ArrayList<>();
				for(TaskPhotoVo taskPhotoVo:taskPhotoVos){
					photoPath.add(taskPhotoVo.getAttachmentDto().getRelatedPath());
					
					if(taskPhotoVo.getTaskPhotoDto().getAudit1()!=null&&taskPhotoVo.getTaskPhotoDto().getAudit2()!=null&&taskPhotoVo.getTaskPhotoDto().getAudit3()!=null){
						if(taskPhotoVo.getTaskPhotoDto().getAudit1()==2){
							tVo.setAudit1("不合格");
							reason1=reason1+taskPhotoVo.getTaskPhotoDto().getAuditReason1()+";";
							tVo.setAuditReason1(reason1);
						}
						if(taskPhotoVo.getTaskPhotoDto().getAudit2()==2){
							tVo.setAudit2("不合格");
							reason2=reason2+taskPhotoVo.getTaskPhotoDto().getAuditReason2()+";";
							tVo.setAuditReason2(reason2);
						}
						if(taskPhotoVo.getTaskPhotoDto().getAudit3()==2){
							tVo.setAudit3("不合格");
							reason3=reason3+taskPhotoVo.getTaskPhotoDto().getAuditReason3()+";";
							tVo.setAuditReason3(reason3);
						}
						tVo.setAuditor1(taskPhotoVo.getTaskPhotoDto().getAuditor1());
						tVo.setAuditTime1(taskPhotoVo.getTaskPhotoDto().getAuditTime1());
						tVo.setAuditor2(taskPhotoVo.getTaskPhotoDto().getAuditor2());
						tVo.setAuditTime2(taskPhotoVo.getTaskPhotoDto().getAuditTime2());
						tVo.setAuditor3(taskPhotoVo.getTaskPhotoDto().getAuditor3());
						tVo.setAuditTime3(taskPhotoVo.getTaskPhotoDto().getAuditTime3());
					}
				}
				tVo.setPhotoPath(photoPath);
			}
			tVo.setCorrectDto(entityToDtoMapper.map(correct, CorrectDto.class));
			tVo.setTaskDto(entityToDtoMapper.map(task, TaskDto.class));
//			tVo.setTrackInfos(trackInfos);
			taskResultVos.add(tVo);
		}
		return taskResultVos;
	}
	
	@Override
	public List<Region> findRegionByParam(String supplierName,String province,String material,String city){
		Wrapper<Region> wrapper=new CriterionWrapper<Region>(Region.class);
		wrapper.eq("supplier_name", supplierName);
		wrapper.eq("province", province);
		wrapper.eq("material", material);
		wrapper.eq("city", city);
		List<Region> cList=regionService.selectList(wrapper);
		return cList;
		
	}
	
	//查询
	@Override
	public List<CorrectVo> findByOaCode(String oaCode){
		List<MaterialDto> materials=materialService.findAll("", "D");
		Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
		wrapper.eq("oa_code", oaCode);
		List<Correct> correctList=super.selectList(wrapper);
		List<CorrectVo> correctVos=new ArrayList<>();
		for(Correct correct:correctList){
			Task task=taskService.selectById(correct.getTaskId());
			for(MaterialDto material:materials){
				if(material.getCode().equals(task.getMaterial())){
					task.setMaterial(material.getName());
				}
			}
			TaskDto taskDto=entityToDtoMapper.map(task, TaskDto.class);
			taskDto.setSupplierName(supplierInfoService.selectById(task.getSupplierId()).getName());
			Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
			wraTrack.eq("task_id", correct.getTaskId());
			wraTrack.notIn("audit2", "");
			TrackInfo trackInfo=trackInfoService.selectFirst(wraTrack);
			Wrapper<TaskPhoto> wraPhoto=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
//			wraPhoto.eq("task_id", correct.getTaskId());
//			wraPhoto.notIn("photo_type", 5);
//			List<TaskPhoto> taskPhotos=taskPhotoService.selectList(wraPhoto);
			List<TaskPhotoVo> taskPhotoVos=taskPhotoService.findByParam(correct.getTaskId(), "");
			CorrectVo cVo=new CorrectVo();
			cVo.setCorrectDto(entityToDtoMapper.map(correct, CorrectDto.class));
			cVo.setTaskDto(taskDto);
//			cVo.setTrackInfoDto(entityToDtoMapper.map(trackInfo, TrackInfoDto.class));
//			cVo.setTaskPhotoDto(entityToDtoMapper.mapAsList(taskPhotos, TaskPhotoDto.class));
			cVo.setTaskPhotoVos(taskPhotoVos);
			correctVos.add(cVo);
		}
		return correctVos;	
	}
	
	//查询运输任务审核结果
	@Override
	public List<TaskResultVo> queryTaskResultByOaCode(String oaCode){
		Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
		wrapper.eq("oa_code", oaCode);
		List<Correct> correctList=super.selectList(wrapper);
		List<TaskResultVo> taskResultVos=new ArrayList<>();
		for(Correct correct:correctList){
			Task task=taskService.selectById(correct.getTaskId());
			Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
			wraTrack.eq("task_id", correct.getTaskId());
			List<TrackInfo> trackInfos=trackInfoService.selectList(wraTrack);
			
			List<TaskPhotoVo> taskPhotoVos=taskPhotoService.findByParam(correct.getTaskId(), "");
			
			TaskResultVo tVo=new TaskResultVo();
			if(trackInfos.size()>0){
				tVo.setAuditTrack2("合格");
				tVo.setAuditTrack3("合格");
				tVo.setAuditReason1("");
				tVo.setAuditReasonTrack2("");
				tVo.setAuditReasonTrack3("");
				for(TrackInfo trackInfo:trackInfos){
					if(trackInfo.getAudit2()!=null&&trackInfo.getAudit3()!=null){
						if(trackInfo.getAudit2()==2){
							tVo.setAuditTrack2("不合格");
							tVo.setAuditReasonTrack2(trackInfo.getAuditReason2());
						}
						if(trackInfo.getAudit3()==2){
							tVo.setAuditTrack3("不合格");
							tVo.setAuditReasonTrack3(trackInfo.getAuditReason3());
						}
						break;
					}
				}
			}
			if(taskPhotoVos!=null){
				tVo.setAudit1("合格");
				tVo.setAudit2("合格");
				tVo.setAudit3("合格");
				tVo.setAuditReason1("");
				tVo.setAuditReason2("");
				tVo.setAuditReason3("");
				String reason1="";
				String reason2="";
				String reason3="";
				List<String> photoPath=new ArrayList<>();
				for(TaskPhotoVo taskPhotoVo:taskPhotoVos){
					photoPath.add(taskPhotoVo.getAttachmentDto().getRelatedPath());
					
					if(taskPhotoVo.getTaskPhotoDto().getAudit1()!=null&&taskPhotoVo.getTaskPhotoDto().getAudit2()!=null&&taskPhotoVo.getTaskPhotoDto().getAudit3()!=null){
						if(taskPhotoVo.getTaskPhotoDto().getAudit1()==2){
							tVo.setAudit1("不合格");
							reason1=reason1+taskPhotoVo.getTaskPhotoDto().getAuditReason1()+";";
							tVo.setAuditReason1(reason1);
						}
						if(taskPhotoVo.getTaskPhotoDto().getAudit2()==2){
							tVo.setAudit2("不合格");
							reason2=reason2+taskPhotoVo.getTaskPhotoDto().getAuditReason2()+";";
							tVo.setAuditReason2(reason2);
						}
						if(taskPhotoVo.getTaskPhotoDto().getAudit3()==2){
							tVo.setAudit3("不合格");
							reason3=reason3+taskPhotoVo.getTaskPhotoDto().getAuditReason3()+";";
							tVo.setAuditReason3(reason3);
						}
						tVo.setAuditor1(taskPhotoVo.getTaskPhotoDto().getAuditor1());
						tVo.setAuditTime1(taskPhotoVo.getTaskPhotoDto().getAuditTime1());
						tVo.setAuditor2(taskPhotoVo.getTaskPhotoDto().getAuditor2());
						tVo.setAuditTime2(taskPhotoVo.getTaskPhotoDto().getAuditTime2());
						tVo.setAuditor3(taskPhotoVo.getTaskPhotoDto().getAuditor3());
						tVo.setAuditTime3(taskPhotoVo.getTaskPhotoDto().getAuditTime3());
					}
				}
				tVo.setPhotoPath(photoPath);
			}
			tVo.setCorrectDto(entityToDtoMapper.map(correct, CorrectDto.class));
			tVo.setTaskDto(entityToDtoMapper.map(task, TaskDto.class));
			tVo.setTrackInfos(trackInfos);
			taskResultVos.add(tVo);
		}
		return taskResultVos;
		
	}
	
	//财务部总经理审核意见
	@Override
	public String audit(Integer state,String reason,String oaCode,Long correntId,String node,String empName,String empNo,String flowDjbh,String flowDjlsh){
		Assert.isTrue(state==1 || state==2,"状态无效（只能是1或2）");
		String stateText="";
		if(state==1){
			stateText="同意";
		}
		if(state==2){
			stateText="不同意";
		}
		Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
		wrapper.eq("oa_code", oaCode);
		wrapper.eq("fd_id", correntId);
		List<Correct> correctList=super.selectList(wrapper);
		Assert.notNull(correctList, "没有找到oACode对应的运费补助补正记录");
		Assert.isTrue(!correctList.isEmpty(), "没有找到oACode对应的运费补助补正记录");
		for(Correct correct:correctList){
			if(node.endsWith("1")){
				if(!correct.getAudit1().equals("0")){
					Assert.throwException("部分记录已经审批");
				}
			}
			if(node.endsWith("2")){
				if(!correct.getAudit2().equals("0")){
					Assert.throwException("部分记录已经审批");
				}
			}
			if(node.endsWith("3")){
				if(!correct.getAudit3().equals("0")){
					Assert.throwException("部分记录已经审批");
				}
			}
		}
		Date doDate = new Date();
		String flowName="竹木原料运费补助补正申请审核审批记录明细";
		String platform="correct";
		//记录OA日志
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oaCode);
		oa.setPlatform(platform);
		oa.setTableName("corrent");
		oa.setFlowName(flowName);
		if(node.equals("1")){
			oa.setNodeName("原料收购部主管审核");
		}
		if(node.equals("2")){
			oa.setNodeName("运营总经理审批");
		}
		if(node.equals("3")){
			oa.setNodeName("生产支持副总裁审批");
		}
		oa.setEmpName(empName);
		oa.setEmpNo(empNo);
		oa.setFlowDjbh(flowDjbh);
		oa.setFlowDjlsh(flowDjlsh);
		if(state.equals("同意")){
			Integer status=1;
			oa.setStatus(status);
		}else{
			oa.setStatus(2);
		}		
		// oa.setCreaterId(UserUtils.getUserId());
		oa.setCreateTime(doDate);
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
		for(Correct correct:correctList){
			Task task=taskService.selectById(correct.getTaskId());
			if(node.equals("1")){
				correct.setAudit1(stateText);
				correct.setAuditor1(empName);
				correct.setAuditReason1(reason);
				correct.setAuditTime1(new Date());
				correct.setState(6);
			}
			if(node.equals("2")){
				correct.setAudit2(stateText);
				correct.setAuditor2(empName);
				correct.setAuditReason2(reason);
				correct.setAuditTime2(new Date());
				if(correct.getAudit1().equals(correct.getAudit2())){
					if(correct.getAudit1().equals("同意")){
						correct.setState(1);
						task.setCorrections(1);
//						task.setStatus(10);
//						task.setLastAlterTime(doDate);
						taskService.updateById(task);
					}else {
						correct.setState(2);
						task.setCorrections(2);
//						task.setStatus(13);
//						task.setLastAlterTime(doDate);
						taskService.updateById(task);
					}
				}else {
					correct.setState(7);
				}
			}
			if(node.equals("3")){
				correct.setAudit3(stateText);
				correct.setAuditor3(empName);
				correct.setAuditReason3(reason);
				correct.setAuditTime3(new Date());
				if(state==1){
					correct.setState(1);
					task.setCorrections(1);
//					task.setStatus(10);
//					task.setLastAlterTime(doDate);
					taskService.updateById(task);
				}else {
					correct.setState(2);
					task.setCorrections(2);
//					task.setStatus(9);
//					task.setLastAlterTime(doDate);
					taskService.updateById(task);
				}
				
			}
			correct.setLastAlterTime(doDate);
			correct.setLastAltorId(empName);
			super.updateById(correct);
			OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
			oaDetail.setFdId(IdWorker.getId());
			oaDetail.setRecFdId(correct.getFdId());
			oaAduitDetailService.insert(oaDetail);
		}
		return "审核完毕";		
	}
	
	
	@Override
	public List<CorrectDto> findAllAudited(){
		String [] state={"9","10"};
		Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
		wrapper.in("state", state);
		List<Correct> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, CorrectDto.class);
		
	}
	
	@Override
	public List<TaskResultVo> findAllAudited1(){
		String [] state={"1","2"};
		Wrapper<Correct> wrapper=new CriterionWrapper<Correct>(Correct.class);
		wrapper.in("state", state);
		List<Correct> correctList=super.selectList(wrapper);
		List<TaskResultVo> taskResultVos=new ArrayList<>();
		for(Correct correct:correctList){
			Task task=taskService.selectById(correct.getTaskId());
			Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
			wraTrack.eq("task_id", correct.getTaskId());
			List<TrackInfo> trackInfos=trackInfoService.selectList(wraTrack);
			
			List<TaskPhotoVo> taskPhotoVos=taskPhotoService.findByParam(correct.getTaskId(), "");
			
			TaskResultVo tVo=new TaskResultVo();
			if(trackInfos.size()>0){
				tVo.setAuditTrack2("合格");
				tVo.setAuditTrack3("合格");
				tVo.setAuditReason1("");
				tVo.setAuditReasonTrack2("");
				tVo.setAuditReasonTrack3("");
				for(TrackInfo trackInfo:trackInfos){
					if(trackInfo.getAudit2()!=null&&trackInfo.getAudit3()!=null){
						if(trackInfo.getAudit2()==2){
							tVo.setAuditTrack2("不合格");
							tVo.setAuditReasonTrack2(trackInfo.getAuditReason2());
						}
						if(trackInfo.getAudit3()==2){
							tVo.setAuditTrack3("不合格");
							tVo.setAuditReasonTrack3(trackInfo.getAuditReason3());
						}
						break;
					}
				}
			}
			if(taskPhotoVos!=null){
				tVo.setAudit1("合格");
				tVo.setAudit2("合格");
				tVo.setAudit3("合格");
				tVo.setAuditReason1("");
				tVo.setAuditReason2("");
				tVo.setAuditReason3("");
				String reason1="";
				String reason2="";
				String reason3="";
				List<String> photoPath=new ArrayList<>();
				for(TaskPhotoVo taskPhotoVo:taskPhotoVos){
					photoPath.add(taskPhotoVo.getAttachmentDto().getRelatedPath());
					
					if(taskPhotoVo.getTaskPhotoDto().getAudit1()!=null&&taskPhotoVo.getTaskPhotoDto().getAudit2()!=null&&taskPhotoVo.getTaskPhotoDto().getAudit3()!=null){
						if(taskPhotoVo.getTaskPhotoDto().getAudit1()==2){
							tVo.setAudit1("不合格");
							reason1=reason1+taskPhotoVo.getTaskPhotoDto().getAuditReason1()+";";
							tVo.setAuditReason1(reason1);
						}
						if(taskPhotoVo.getTaskPhotoDto().getAudit2()==2){
							tVo.setAudit2("不合格");
							reason2=reason2+taskPhotoVo.getTaskPhotoDto().getAuditReason2()+";";
							tVo.setAuditReason2(reason2);
						}
						if(taskPhotoVo.getTaskPhotoDto().getAudit3()==2){
							tVo.setAudit3("不合格");
							reason3=reason3+taskPhotoVo.getTaskPhotoDto().getAuditReason3()+";";
							tVo.setAuditReason3(reason3);
						}
						tVo.setAuditor1(taskPhotoVo.getTaskPhotoDto().getAuditor1());
						tVo.setAuditTime1(taskPhotoVo.getTaskPhotoDto().getAuditTime1());
						tVo.setAuditor2(taskPhotoVo.getTaskPhotoDto().getAuditor2());
						tVo.setAuditTime2(taskPhotoVo.getTaskPhotoDto().getAuditTime2());
						tVo.setAuditor3(taskPhotoVo.getTaskPhotoDto().getAuditor3());
						tVo.setAuditTime3(taskPhotoVo.getTaskPhotoDto().getAuditTime3());
					}
				}
				tVo.setPhotoPath(photoPath);
			}
			tVo.setCorrectDto(entityToDtoMapper.map(correct, CorrectDto.class));
			tVo.setTaskDto(entityToDtoMapper.map(task, TaskDto.class));
//				tVo.setTrackInfos(trackInfos);
			taskResultVos.add(tVo);
		}
		return taskResultVos;
	}	
}
