package com.hwagain.eagle.comparison.service.impl;

import com.hwagain.eagle.comparison.entity.Comparison;
import com.hwagain.eagle.comparison.dto.ComparisonDto;
import com.hwagain.eagle.comparison.mapper.ComparisonMapper;
import com.hwagain.eagle.comparison.service.IComparisonService;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.util.syncYuanLZhu;
import com.hwagain.esb.common.annotation.ExecuteConfig;
import com.hwagain.esb.mobile.api.ICommonService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
 * @since 2019-05-26
 */
@Slf4j
@Configurable
@EnableScheduling
@Component
@Service("comparisonService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ComparisonServiceImpl extends ServiceImpl<ComparisonMapper, Comparison> implements IComparisonService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired ICommonService commonService;
	@Autowired syncYuanLZhu syncylz;
	@Autowired ITaskService taskService;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired ITaskPhotoService taskPhotoService;
	@Autowired ITrackInfoService trackInfoService;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Comparison.class, ComparisonDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(ComparisonDto.class, Comparison.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	//生成新旧系统对照表
	@Scheduled(cron = "0 0 0/1 * * ?") // 每小时执行一次
	@Override
	public List<ComparisonDto> findAll(){
		System.err.println("测试1个小时");
		Wrapper<Task> wrapTask=new CriterionWrapper<Task>(Task.class);
		String [] status={"10","13"};
		wrapTask.eq("state", 1);
		wrapTask.in("status", status);
		List<Task> tasks=taskService.selectList(wrapTask);
		for(Task task:tasks){
			Wrapper<Comparison> wrapper=new CriterionWrapper<Comparison>(Comparison.class);
			wrapper.eq("pound_id", task.getPoundId());
			wrapper.eq("task_id", task.getFdId());
			wrapper.eq("state", 1);
			Comparison comparison=super.selectFirst(wrapper);
			if(comparison==null){
				Comparison entity=new Comparison();
				entity.setFdId(IdWorker.getId());
				entity.setTaskId(task.getFdId());
				entity.setMaterial(task.getMaterial());
				entity.setPlateNumber(task.getUserPlateNumber());
				entity.setSupplierName(supplierInfoService.selectById(task.getSupplierId()).getName());
				entity.setPoundId(task.getPoundId());
				List<TaskPhotoVo> taskPhotos= taskPhotoService.findByParam(task.getFdId(), "");
				entity.setNewAuditPhoto1("合格");
				entity.setNewAuditPhoto2("合格");
				entity.setNewAuditPhoto3("合格");
				for(TaskPhotoVo tPhotoVo:taskPhotos){
					if(tPhotoVo.getTaskPhotoDto().getAudit1()==2){
						entity.setNewAuditPhoto1("不合格");
					}
					if(tPhotoVo.getTaskPhotoDto().getAudit2()==2){
						entity.setNewAuditPhoto2("不合格");
					}
					if(tPhotoVo.getTaskPhotoDto().getAudit3()==2){
						entity.setNewAuditPhoto3("不合格");
					}
				}
				Wrapper<TrackInfo> wrapTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
				wrapTrack.eq("task_id", task.getFdId());
				List<TrackInfo> trackInfos=trackInfoService.selectList(wrapTrack);
				if(trackInfos.get(0).getAudit2()==1){
					entity.setNewAuditTrack2("合格");
				}else{
					entity.setNewAuditTrack2("不合格");
				}
				if(trackInfos.get(0).getAudit3()==1){
					entity.setNewAuditTrack3("合格");
				}else{
					entity.setNewAuditTrack3("不合格");
				}
				if(entity.getNewAuditPhoto1().equals("不合格")||entity.getNewAuditPhoto2().equals("不合格")||entity.getNewAuditPhoto3().equals("不合格")
						||entity.getNewAuditTrack2().equals("不合格")||entity.getNewAuditTrack3().equals("不合格")){
					entity.setNewResult("不合格");
				}else{
					entity.setNewResult("合格");
				}
				List<Map<String, Object>> listMap=syncylz.getYLZDatas(task.getPoundId());
				System.err.println(listMap);
				if(listMap.size()>0){
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						entity.setTransportTime(sdf.parse(String.valueOf(listMap.get(0).get("guoBtime"))));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(String.valueOf(listMap.get(0).get("PhotoR"))!=null&&!String.valueOf(listMap.get(0).get("PhotoR")).isEmpty()){
						entity.setOldAuditPhoto1(String.valueOf(listMap.get(0).get("PhotoR")));
						entity.setOldAuditPhoto2(String.valueOf("合格"));
						if(String.valueOf(listMap.get(0).get("RouteResult"))!=null&&!String.valueOf(listMap.get(0).get("RouteResult")).isEmpty()){
							entity.setOldAuditTrack2(String.valueOf(listMap.get(0).get("RouteResult")));
						}
						if(entity.getOldAuditPhoto1().equals(entity.getOldAuditPhoto2())){
							entity.setOldResult(entity.getOldAuditPhoto1());
						}else{
							entity.setOldResult("不合格");
						}
					}else{
						entity.setOldAuditPhoto2(String.valueOf("不合格"));
						entity.setOldResult("不合格");
					}
					entity.setCreateTime(new Date());
					super.insert(entity);
				}
				
			}else{
				if(comparison.getOldResult()!=null&&!comparison.getOldResult().isEmpty()){
					
				}else{
					List<Map<String, Object>> listMap=syncylz.getYLZDatas(comparison.getPoundId());
					System.err.println(listMap.size());
					if(listMap.size()>0){
						DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							comparison.setTransportTime(sdf.parse(String.valueOf(listMap.get(0).get("guoBtime"))));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(listMap.get(0).get("PhotoR") != null){
							comparison.setOldAuditPhoto1(String.valueOf(listMap.get(0).get("PhotoR")));
							comparison.setOldAuditPhoto2(String.valueOf("合格"));
							System.err.println(listMap.get(0).get("RouteResult"));
							if(listMap.get(0).get("RouteResult")!=null){
								comparison.setOldAuditTrack2(String.valueOf(listMap.get(0).get("RouteResult")));
								if(comparison.getOldAuditPhoto1().equals(comparison.getOldAuditPhoto2())){
									comparison.setOldResult(comparison.getOldAuditPhoto1());
								}else{
									comparison.setOldResult("不合格");
								}
							}
							
						}else{
							comparison.setOldAuditPhoto2(String.valueOf("不合格"));
							comparison.setOldResult("不合格");
						}
						super.updateById(comparison);
					}
				}
			}
		}
		Wrapper<Comparison> wraCom=new CriterionWrapper<Comparison>(Comparison.class);
		wraCom.eq("state", 1);
		wraCom.orderBy("transport_time",false);
		List<Comparison> list=super.selectList(wraCom);
		return entityToDtoMapper.mapAsList(list, ComparisonDto.class);		
	}
	
	//更新新旧系统对照表
	@Override
	public String updateAllNew(){
		Wrapper<Task> wrapTask=new CriterionWrapper<Task>(Task.class);
		String [] status={"10","13"};
		wrapTask.eq("state", 1);
		wrapTask.in("status", status);
		List<Task> tasks=taskService.selectList(wrapTask);
		for(Task task:tasks){
			Wrapper<Comparison> wrapper=new CriterionWrapper<Comparison>(Comparison.class);
			wrapper.eq("pound_id", task.getPoundId());
			wrapper.eq("task_id", task.getFdId());
			wrapper.eq("state", 1);
			Comparison comparison=super.selectFirst(wrapper);
			if(comparison==null){
				Comparison entity=new Comparison();
				entity.setFdId(IdWorker.getId());
				entity.setTaskId(task.getFdId());
				entity.setMaterial(task.getMaterial());
				entity.setPlateNumber(task.getUserPlateNumber());
				entity.setSupplierName(supplierInfoService.selectById(task.getSupplierId()).getName());
				entity.setPoundId(task.getPoundId());
				List<TaskPhotoVo> taskPhotos= taskPhotoService.findByParam(task.getFdId(), "");
				entity.setNewAuditPhoto1("合格");
				entity.setNewAuditPhoto2("合格");
				entity.setNewAuditPhoto3("合格");
				for(TaskPhotoVo tPhotoVo:taskPhotos){
					if(tPhotoVo.getTaskPhotoDto().getAudit1()==2){
						entity.setNewAuditPhoto1("不合格");
					}
					if(tPhotoVo.getTaskPhotoDto().getAudit2()==2){
						entity.setNewAuditPhoto2("不合格");
					}
					if(tPhotoVo.getTaskPhotoDto().getAudit3()==2){
						entity.setNewAuditPhoto3("不合格");
					}
				}
				Wrapper<TrackInfo> wrapTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
				wrapTrack.eq("task_id", task.getFdId());
				List<TrackInfo> trackInfos=trackInfoService.selectList(wrapTrack);
				if(trackInfos.get(0).getAudit2()==1){
					entity.setNewAuditTrack2("合格");
				}else{
					entity.setNewAuditTrack2("不合格");
				}
				if(trackInfos.get(0).getAudit3()==1){
					entity.setNewAuditTrack3("合格");
				}else{
					entity.setNewAuditTrack3("不合格");
				}
				if(entity.getNewAuditPhoto1().equals("不合格")||entity.getNewAuditPhoto2().equals("不合格")||entity.getNewAuditPhoto3().equals("不合格")
						||entity.getNewAuditTrack2().equals("不合格")||entity.getNewAuditTrack3().equals("不合格")){
					entity.setNewResult("不合格");
				}else{
					entity.setNewResult("合格");
				}
				entity.setState(1);
				entity.setCreateTime(new Date());
				entity.setTransportTime(task.getLastAlterTime());
				super.insert(entity);
//				ExecuteConfig ec=new ExecuteConfig();
//				ec.setEnv("SIT");
//				ec.setResultType(Types.VARCHAR);
//				ec.setSystemCode("SYSTEM_YLZSG");
//				ec.setSql("select cheh,zhumc,jingzhong,cyrMC,gzsMC,PhotoR,RouteResult from bdinf where id='"+task.getPoundId()+"'");
//				commonService.executeSql(ec);
//				List<Map<String, Object>> listMap=syncylz.getYLZDatas(task.getPoundId());
//				System.err.println(listMap);
//				if(listMap.size()>0){
//					DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					try {
//						entity.setTransportTime(sdf.parse(String.valueOf(listMap.get(0).get("guoBtime"))));
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if(String.valueOf(listMap.get(0).get("PhotoR"))!=null&&!String.valueOf(listMap.get(0).get("PhotoR")).isEmpty()){
//						entity.setOldAuditPhoto1(String.valueOf(listMap.get(0).get("PhotoR")));
//						entity.setOldAuditPhoto2(String.valueOf("合格"));
//						entity.setOldAuditTrack2(String.valueOf(listMap.get(0).get("RouteResult")));
//						if(entity.getOldAuditPhoto1().equals(entity.getOldAuditPhoto2())){
//							entity.setOldResult(entity.getOldAuditPhoto1());
//						}else{
//							entity.setOldResult("不合格");
//						}
//					}else{
//						entity.setOldAuditPhoto2(String.valueOf("不合格"));
//					}	
					
//				}
				
			}
		}
		return "success";		
	}
	
	//更新旧系统未审核完毕信息
	@Scheduled(cron = "0 0 1 ? * *") // 每天1点执行-首先执行
//	@Scheduled(cron = "0 */30 * * * ?") // 30分钟一次【 测试用】
	@Override
	public String updateAllOld(){
		Wrapper<Comparison> wraCom=new CriterionWrapper<Comparison>(Comparison.class);
		wraCom.eq("state", 1);
		wraCom.orderBy("transport_time",false);
		List<Comparison> list=super.selectList(wraCom);
		for(Comparison comparison:list){
			if(comparison.getOldResult()!=null&&!comparison.getOldResult().isEmpty()){
				
			}else {
				log.info(comparison.getPoundId()+":"+comparison.getPlateNumber());
				List<Map<String, Object>> listMap=syncylz.getYLZDatas(comparison.getPoundId());
				System.err.println(listMap);
				if(listMap.size()>0){
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						comparison.setTransportTime(sdf.parse(String.valueOf(listMap.get(0).get("guoBtime"))));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(listMap.get(0).get("PhotoR")!=null){
						comparison.setOldAuditPhoto1(String.valueOf(listMap.get(0).get("PhotoR")));
						comparison.setOldAuditPhoto2(String.valueOf("合格"));
						
					}
					if(listMap.get(0).get("RouteResult")!=null){
						comparison.setOldAuditTrack2(String.valueOf(listMap.get(0).get("RouteResult")));
						if(comparison.getOldAuditPhoto1().equals(comparison.getOldAuditTrack2())){
							comparison.setOldResult(comparison.getOldAuditPhoto1());
						}else{
							comparison.setOldResult("不合格");
						}
					}									
				}else{
					comparison.setOldAuditPhoto2(String.valueOf("不合格"));
				}
				comparison.setLastAlterTime(new Date());
				super.updateById(comparison);
			}
		}
		return "更新成功";
		
	}
	
	
	@Override
	public List<Comparison> findAllResult(){
//		updateAllNew();
		Wrapper<Comparison> wraCom=new CriterionWrapper<Comparison>(Comparison.class);
		wraCom.eq("state", 1);
		wraCom.orderBy("transport_time",false);
		List<Comparison> list=super.selectList(wraCom);
		return list;		
	}
	
	//
	@Override
	public Comparison updateByFdId(Long fdId,String reason){
		Comparison entity=super.selectById(fdId);
		if(entity!=null){
			entity.setReason(reason);
			entity.setLastAlterTime(new Date());
			entity.setLastAltorId(Long.valueOf(UserUtils.getUserInfo().getFdId()));
			super.updateById(entity);
		}
		return entity;		
	}
}
