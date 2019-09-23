package com.hwagain.eagle.task.service.impl;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.search.DateTerm;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.attachment.dto.AttachmentDto;
import com.hwagain.eagle.attachment.entity.Attachment;
import com.hwagain.eagle.attachment.mapper.AttachmentMapper;
import com.hwagain.eagle.attachment.service.impl.AttachmentServiceImpl;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.region.service.IRegionSupplierService;
import com.hwagain.eagle.task.dto.RptTask;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.entity.TaskPhotoVo;
import com.hwagain.eagle.task.mapper.TaskPhotoMapper;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.eagle.util.JwtUtil;
import com.hwagain.esb.common.annotation.FunctionConfig;
import com.hwagain.esb.mobile.api.ICommonService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.w;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-02-25
 */
@Configurable
@EnableScheduling
@Component
@Service("taskPhotoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TaskPhotoServiceImpl extends ServiceImpl<TaskPhotoMapper, TaskPhoto> implements ITaskPhotoService {

	@Autowired ICommonService commonService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;

	// dto转entity
	static MapperFacade dtoToEntityMapper;

	@Autowired
	private AttachmentServiceImpl attachmentServiceImpl;
	@Autowired
	TaskPhotoMapper taskPhotoMapper;
	@Autowired ITaskService taskService;
	@Autowired ITrackInfoService trackInfoService;
	@Autowired AttachmentMapper attachmentMapper;
	@Autowired IDictService dictService;
	@Autowired IUserInfoService userInfoService;
	@Autowired IRegionDetailService regionDetailService;
	

    @Value("${sys.config.download.basePath}")
    private String DOWNLOAD_FOLDER;
    
    @Value("${sys.config.downloadOut.basePath}")
    private String DOWNLOAD_FOLDER_OUT;
    @Value("${sys.config.EXB.Env}")
    private String Env;
    @Autowired
    private JwtUtil jwtUtil;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(TaskPhoto.class, TaskPhotoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();

		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(TaskPhotoDto.class, TaskPhoto.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	@Transactional
	public String save(TaskPhotoVo taskPhotoVo) {
		
		long attachmentId = IdWorker.getId();
		long taskPhotoId = IdWorker.getId();
		taskPhotoVo.getAttachmentDto().setRelatedId(taskPhotoId);
		taskPhotoVo.getAttachmentDto().setFdId(attachmentId);
		// 保存附件
		attachmentServiceImpl.save(taskPhotoVo.getAttachmentDto());

		// 保存任务图片对象
		taskPhotoVo.getTaskPhotoDto().setFdId(taskPhotoId);
		taskPhotoVo.getTaskPhotoDto().setPhotoId(attachmentId);
//		validate(taskPhotoVo.getTaskPhotoDto(), false);
		TaskPhoto entity = dtoToEntityMapper.map(taskPhotoVo.getTaskPhotoDto(), TaskPhoto.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		
		return "保存成功";
	}
	
	@Override
	public List<TaskPhotoVo> findByParam(Long taskId, String photoType) {
		System.err.println(UserUtils.getUserInfo());
		List<TaskPhotoVo> result = new ArrayList<>();
		Wrapper<TaskPhoto> wrapper = new CriterionWrapper<>(TaskPhoto.class);
		wrapper.eq("task_id", taskId);
		wrapper.eq("state", 1);
		if (photoType != null && !photoType.isEmpty()) {
			wrapper.eq("photo_type", photoType);
			
		}else{
			wrapper.notIn("photo_type", 5);
		}		
		List<TaskPhoto> list = super.selectList(wrapper);
		for (TaskPhoto taskPhoto : list) {
			Attachment attachment = attachmentMapper.selectById(taskPhoto.getPhotoId());
			TaskPhotoVo taskPhotoVo = new TaskPhotoVo();
			taskPhotoVo.setAttachmentDto(coverToAttachmentDto(attachment));
			taskPhotoVo.getAttachmentDto().setRelatedPath(DOWNLOAD_FOLDER+taskPhotoVo.getAttachmentDto().getRelatedPath());
			taskPhotoVo.setTaskPhotoDto(entityToDtoMapper.map(taskPhoto, TaskPhotoDto.class));
			result.add(taskPhotoVo);
		}
		return result;
	}
	
	
	@Override
	public List<TaskPhotoVo> findByParam2(Long taskId, String photoType) {
		System.err.println(UserUtils.getUserInfo());
		List<TaskPhotoVo> result = new ArrayList<>();
		Wrapper<TaskPhoto> wrapper = new CriterionWrapper<>(TaskPhoto.class);
		wrapper.eq("state", 1);
		wrapper.eq("task_id", taskId);
		wrapper.orderBy("photo_type");
		if (photoType != null && !photoType.isEmpty()) {
			wrapper.eq("photo_type", photoType);
			
		}else{
			wrapper.notIn("photo_type", 5);
		}		
		List<TaskPhoto> list = super.selectList(wrapper);
		for (TaskPhoto taskPhoto : list) {
			Attachment attachment = attachmentMapper.selectById(taskPhoto.getPhotoId());
			TaskPhotoVo taskPhotoVo = new TaskPhotoVo();
			taskPhotoVo.setAttachmentDto(coverToAttachmentDto(attachment));
			taskPhotoVo.getAttachmentDto().setRelatedPath(DOWNLOAD_FOLDER_OUT+taskPhotoVo.getAttachmentDto().getRelatedPath());
			taskPhotoVo.setTaskPhotoDto(entityToDtoMapper.map(taskPhoto, TaskPhotoDto.class));
			result.add(taskPhotoVo);
		}
		return result;
	}
	
	
	private void validate(TaskPhotoDto taskPhotoDto, boolean isUpdate) {
		Assert.notNull(taskPhotoDto.getTaskId(), "任务ID不能为空");
		Assert.notBlank(taskPhotoDto.getMobile(), "用户当前所用的手机号码不能为空");
		Assert.notNull(taskPhotoDto.getPhotoId(), "照片在附件表中的ID不能为空");
		Assert.notNull(taskPhotoDto.getPhotoType(), "照片类型不能为空");
		Assert.notBlank(taskPhotoDto.getGps(), "GPS经纬度不能为空");
		Assert.notBlank(taskPhotoDto.getCity(), "拍照所在城市不能为空");
		Assert.notBlank(taskPhotoDto.getAddress(), "拍照地址不能为空");
		Assert.notNull(taskPhotoDto.getPhotoTime(), "拍照时间不能为空");
		Assert.notNull(taskPhotoDto.getState(), "状态不能为空");
		if (isUpdate) {
			Assert.notNull(taskPhotoDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(taskPhotoDto.getCreatorId(), "创建人ID不能为空");
		}
	}
	
	private AttachmentDto coverToAttachmentDto(Attachment attachment){
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Attachment.class, AttachmentDto.class).byDefault().register();
		return factory.getMapperFacade().map(attachment, AttachmentDto.class);
	}
	
	@Override
	public RptTask getPhotoDetail(Long photoId){
		RptTask taskPhoto=taskPhotoMapper.getPhotoDetail(photoId);
		return taskPhoto;
		
	}
	//图片审核
	@Override
	public TaskPhotoDto auditPhoto(String auditType,Integer acceptanceResult,String reason,Long fdId){
		//审核节点
		String user=UserUtils.getUserInfo().getName();
		Wrapper<Dict> wraDict=new CriterionWrapper<Dict>(Dict.class);
		wraDict.eq("type_name", auditType);
		wraDict.eq("is_delete", 0);
		Dict dict=dictService.selectFirst(wraDict);
		if(dict==null){
			Assert.throwException("审核节点不能为空");
		}
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("loginname", user);
		wraUser.eq("user_type", dict.getItemName());
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			Assert.throwException("当前用户没有权限进行审核，请联系用户管理员进行配置");
		}
		
		TaskPhoto photo=super.selectById(fdId);
		Assert.notNull(photo, "找不到指定图片");
//		String dept=UserUtils.getUserInfo().getFdDepartmentName();
//		photo.setAudit1(acceptanceResult);
//		photo.setAuditReason1(reason);
//		photo.setAcceptanceResult(acceptanceResult);
//		System.err.println(acceptanceResult);
		if(auditType.equals("auditOne")){
			photo.setAudit1(acceptanceResult);
			photo.setAuditReason1(reason);
			photo.setAuditor1(user);
			photo.setAuditTime1(new Date());
		}
		if(auditType.equals("auditTwo")){
			photo.setAudit2(acceptanceResult);
			photo.setAuditReason2(reason);
			photo.setAuditor2(user);
			photo.setAuditTime2(new Date());
		}
		if(auditType.equals("auditThree")){
			photo.setAudit3(acceptanceResult);
			photo.setAuditReason3(reason);
			photo.setAuditor3(user);
			photo.setAuditTime3(new Date());
		}
		super.updateById(photo);
		if(!auditType.equals("auditOne")){
			
		}
//		Wrapper<TaskPhoto> tphoto=new CriterionWrapper<>(TaskPhoto.class);
//		tphoto.eq("task_id", photo.getTaskId());
//		tphoto.eq("state", 1);
//		List<TaskPhoto> tPhotos=super.selectList(tphoto);
//		if(tPhotos.size()>=3){
//			Task task=taskService.selectById(photo.getTaskId());
//			String reString="";
//			task.setPhotoAcceptanceResult(1);
//			for(TaskPhoto tPhoto2:tPhotos){
//				if(tPhoto2.getAcceptanceResult()==2){
//					String type="";
//					if(tPhoto2.getPhotoType()==1){
//						type="车牌";
//					}
//					if(tPhoto2.getPhotoType()==2){
//						type="车头";
//					}
//					if(tPhoto2.getPhotoType()==3){
//						type="车身";
//					}
//					if(tPhoto2.getPhotoType()==4){
//						type="车尾";
//					}
//					System.err.println(acceptanceResult);
//					task.setPhotoAcceptanceResult(tPhoto2.getAcceptanceResult());
//					System.err.println(task.getPhotoAcceptanceResult());
//					reString=reString+type+":"+tPhoto2.getAuditReason1()+";";
//					System.err.println(reString);
//				}
//			}
//			System.err.println(task.getPathAcceptanceResult());
//			if(task.getPhotoAcceptanceResult()==2){
//				task.setRemark(reString);
////				task.setStatus(11);
//			}else{
////				task.setStatus(10);
//				task.setRemark("");
//			}
//			System.err.println(task.getFdId());
//			task.setStatus(3);
//			taskService.updateAllById(task);
//		}
		return entityToDtoMapper.map(photo, TaskPhotoDto.class);
	}
	
	//图片审核
	@Override
	public String photoAudit1(List<TaskPhotoDto> taskPhotoDtos){
		String result="合格";
		if(taskPhotoDtos.size()==0){
			Assert.throwException("未提交审核图片数据");
		}
	
		Wrapper<Task> wraTask=new CriterionWrapper<Task>(Task.class);
		wraTask.eq("fd_id", taskPhotoDtos.get(0).getTaskId());
		Task task=taskService.selectFirst(wraTask);		
		for(TaskPhotoDto dPhotoDto1:taskPhotoDtos){
			System.err.println(dPhotoDto1.getAuditor1());
			TaskPhoto dPhotoDto=new TaskPhoto();
			dPhotoDto.setFdId(dPhotoDto1.getFdId());			
			Assert.notNull(dPhotoDto1.getAudit1(),dPhotoDto1.getPhotoTypeText()+ "审核结果不能为空");
			dPhotoDto.setAudit1(dPhotoDto1.getAudit1());
			if(dPhotoDto1.getAudit1()==2){
				result="不合格";
				if(dPhotoDto1.getAuditReason1()!=null&&!dPhotoDto1.getAuditReason1().isEmpty()){
					dPhotoDto.setAuditReason1(dPhotoDto1.getAuditReason1());
				}else{
					Assert.throwException("不合格原因不能为空");
				}
			}
//			dPhotoDto.setAuditor1(dPhotoDto1.getAuditor1());
			dPhotoDto.setAuditTime1(new Date());
			
			super.updateById(dPhotoDto);
		}
		task.setStatus(3);
//		task.setLastAlterTime(new Date());					
		taskService.updateById(task);
//		DateTime date =(DateTime) new Date();
		FunctionConfig fc = new FunctionConfig();
		String regionName="";
		String address="";
		if(task.getCurrentRegionId()!=null){
			RegionDetail regionDetail=regionDetailService.selectById(task.getCurrentRegionId());
			regionName=regionDetail.getRegionName();
			address=regionDetail.getAddress();
		}else{
			regionName=task.getStartPositionCity();
			address=task.getStartPositionAddress();
		}
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime=df.format(new Date());
		fc.setEnv(Env); //SIT 测试  PRO 正式
		fc.setFunctionName("UpdQZD");  //存储过程方法名
		fc.setIsResult(false); //是否有返回值
		fc.setParams(Arrays.asList(1,Integer.valueOf(task.getPoundId()),taskPhotoDtos.get(0).getAuditor1(),nowTime,regionName,address,result));
		fc.setResultType(Types.VARCHAR);
		fc.setSystemCode("SYSTEM_YLZSG");
//		commonService.executeFunction(fc);
		return "审核1完成";		
	}
	
	
	@Override
	public String photoAudit2(List<TaskPhotoDto> taskPhotoDtos,Long taskId,Integer pathAcceptanceResult,String reason){
		if(taskPhotoDtos.size()==0){
			Assert.throwException("未提交审核图片数据");
		}
		Assert.notNull(taskId, "任务ID不能为空");
		Assert.notNull(pathAcceptanceResult, "轨迹审核结果不能为空");
		if(pathAcceptanceResult==2){
			Assert.notBlank(reason, "不合格原因不能为空");
		}
		trackInfoService.trackAudit2(taskId, pathAcceptanceResult, reason);
		String user=UserUtils.getUserInfo().getName();		
		Wrapper<Dict> wraDict2=new CriterionWrapper<Dict>(Dict.class);
		wraDict2.eq("type_name", "审核");
		wraDict2.eq("is_delete", 0);
		wraDict2.eq("item_no", 2);
		Dict dict2=dictService.selectFirst(wraDict2);
		if(dict2==null){
			Assert.throwException("审核节点未配置，请联系系统管理员");
		}
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("loginname", user);
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("user_type", dict2.getItemName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			Assert.throwException("当前用户没有权限进行审核，请联系用户管理员进行配置");
		}
		Wrapper<Task> wraTask=new CriterionWrapper<Task>(Task.class);
		wraTask.eq("fd_id", taskPhotoDtos.get(0).getTaskId());
		Task task=taskService.selectFirst(wraTask);	
		if(taskPhotoDtos.get(0).getAudit1()!=null&&taskPhotoDtos.get(0).getAudit3()!=null){
			task.setPhotoAcceptanceResult(1);
			if(task.getPathAcceptanceResult()==1){
				task.setStatus(10);
			}
		}
		
		for(TaskPhotoDto dPhotoDto1:taskPhotoDtos){
			TaskPhoto dPhotoDto=new TaskPhoto();
			dPhotoDto.setFdId(dPhotoDto1.getFdId());
			Assert.notNull(dPhotoDto1.getAudit2(),dPhotoDto1.getPhotoTypeText()+ "审核结果不能为空");
			dPhotoDto.setAudit2(dPhotoDto1.getAudit2());
			dPhotoDto.setAuditReason2(dPhotoDto1.getAuditReason2());
			dPhotoDto.setAuditor2(user);
			dPhotoDto.setAuditTime2(new Date());
			if(taskPhotoDtos.get(0).getAudit1()!=null&&taskPhotoDtos.get(0).getAudit3()!=null){
				if(dPhotoDto1.getAudit1()==2||dPhotoDto1.getAudit2()==2||dPhotoDto1.getAudit3()==2){
					task.setPhotoAcceptanceResult(2);
					if(task.getPathAcceptanceResult()!=0){
						task.setStatus(13);
					}
				}
//			task.setLastAlterTime(new Date());
			taskService.updateById(task);
			}			
			super.updateById(dPhotoDto);
		}				
		return "审核完成";		
	}
	
	
	@Override
	public String photoAudit3(List<TaskPhotoDto> taskPhotoDtos,Long taskId,Integer pathAcceptanceResult,String reason){
		if(taskPhotoDtos.size()==0){
			Assert.throwException("未提交审核图片数据");
		}
		Assert.notNull(taskId, "任务ID不能为空");
		Assert.notNull(pathAcceptanceResult, "轨迹审核结果不能为空");
		if(pathAcceptanceResult==2){
			Assert.notBlank(reason, "不合格原因不能为空");
		}
		trackInfoService.trackAudit3(taskId, pathAcceptanceResult, reason);
		String user=UserUtils.getUserInfo().getName();		
		Wrapper<Dict> wraDict3=new CriterionWrapper<Dict>(Dict.class);
		wraDict3.eq("type_name", "审核");
		wraDict3.eq("is_delete", 0);
		wraDict3.eq("item_no", 3);
		Dict dict3=dictService.selectFirst(wraDict3);
		if(dict3==null){
			Assert.throwException("审核节点未配置，请联系系统管理员");
		}
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("loginname", user);
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("user_type", dict3.getItemName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);		
		if(userInfo==null){
			Assert.throwException("当前用户没有权限进行审核，请联系用户管理员进行配置");
		}
		System.err.println(userInfo.getUserType());
		Wrapper<Task> wraTask=new CriterionWrapper<Task>(Task.class);
		wraTask.eq("fd_id", taskPhotoDtos.get(0).getTaskId());
		Task task=taskService.selectFirst(wraTask);	
		if(taskPhotoDtos.get(0).getAudit1()!=null&&taskPhotoDtos.get(0).getAudit2()!=null){
			task.setPhotoAcceptanceResult(1);
			if(task.getPathAcceptanceResult()==1){
				task.setStatus(10);
			}
		}
		for(TaskPhotoDto dPhotoDto1:taskPhotoDtos){
			TaskPhoto dPhotoDto=new TaskPhoto();
			dPhotoDto.setFdId(dPhotoDto1.getFdId());
			Assert.notNull(dPhotoDto1.getAudit3(),dPhotoDto1.getPhotoTypeText()+ "审核结果不能为空");
			dPhotoDto.setAudit3(dPhotoDto1.getAudit3());
			dPhotoDto.setAuditReason3(dPhotoDto1.getAuditReason3());
			dPhotoDto.setAuditor3(user);
			dPhotoDto.setAuditTime3(new Date());
			if(taskPhotoDtos.get(0).getAudit1()!=null&&taskPhotoDtos.get(0).getAudit2()!=null){
				if(dPhotoDto1.getAudit1()==2||dPhotoDto1.getAudit2()==2||dPhotoDto1.getAudit3()==2){
					task.setPhotoAcceptanceResult(2);
					if(task.getPathAcceptanceResult()!=0){
						task.setStatus(13);
					}
				}
//			task.setLastAlterTime(new Date());
			taskService.updateById(task);
			}			
			super.updateById(dPhotoDto);
		}				
		return "审核完成";		
	}
	
	
	@Override
	public String photoAudit(List<TaskPhotoDto> taskPhotoDtos){
		String auditType;
		if(taskPhotoDtos.size()==0){
			Assert.throwException("未提交审核图片数据");
		}
		List<Dict> dictList=new ArrayList<>();
		String user=UserUtils.getUserInfo().getName();
		Wrapper<Dict> wraDict=new CriterionWrapper<Dict>(Dict.class);
		wraDict.eq("type_name", "审核");
		wraDict.eq("is_delete", 0);
		wraDict.eq("item_no", 1);
		Dict dict1=dictService.selectFirst(wraDict);
		Wrapper<Dict> wraDict2=new CriterionWrapper<Dict>(Dict.class);
		wraDict2.eq("type_name", "审核");
		wraDict2.eq("is_delete", 0);
		wraDict2.eq("item_no", 2);
		Dict dict2=dictService.selectFirst(wraDict2);
		Wrapper<Dict> wraDict3=new CriterionWrapper<Dict>(Dict.class);
		wraDict3.eq("type_name", "审核");
		wraDict3.eq("is_delete", 0);
		wraDict3.eq("item_no", 3);
		Dict dict3=dictService.selectFirst(wraDict3);
		dictList.add(dict1);
		dictList.add(dict2);
		dictList.add(dict3);
		if(dictList.size()==0){
			Assert.throwException("审核节点未配置，请联系系统管理员");
		}
		List<String> userType=new ArrayList<>();
		for(Dict dict:dictList){
			userType.add(dict.getItemName());
		}
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("loginname", user);
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.in("user_type", userType);
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			Assert.throwException("当前用户没有权限进行审核，请联系用户管理员进行配置");
		}
		auditType=userInfo.getUserType();
		Wrapper<Task> wraTask=new CriterionWrapper<Task>(Task.class);
		wraTask.eq("fd_id", taskPhotoDtos.get(0).getTaskId());
		Task task=taskService.selectFirst(wraTask);		
		for(TaskPhotoDto dPhotoDto1:taskPhotoDtos){
			TaskPhoto dPhotoDto=new TaskPhoto();
			dPhotoDto.setFdId(dPhotoDto1.getFdId());
			if(auditType.equals(dict1.getItemName())){
				Assert.notNull(dPhotoDto1.getAudit1(),dPhotoDto1.getPhotoTypeText()+ "审核结果不能为空");
				dPhotoDto.setAudit1(dPhotoDto1.getAudit1());
				dPhotoDto.setAuditReason1(dPhotoDto1.getAuditReason1());
				dPhotoDto.setAuditor1(user);
				dPhotoDto.setAuditTime1(new Date());
			}
			if(auditType.equals(dict2.getItemName())){
				Assert.notNull(dPhotoDto1.getAudit2(),dPhotoDto1.getPhotoTypeText()+ "审核结果不能为空");
				dPhotoDto.setAudit2(dPhotoDto1.getAudit2());
				dPhotoDto.setAuditReason2(dPhotoDto1.getAuditReason2());
				dPhotoDto.setAuditor2(user);
				dPhotoDto.setAuditTime2(new Date());
				if(taskPhotoDtos.get(0).getAudit2()!=null&&taskPhotoDtos.get(0).getAudit3()!=null){
					if(dPhotoDto1.getAudit1()==2||dPhotoDto1.getAudit2()==2||dPhotoDto1.getAudit3()==2){
						task.setPhotoAcceptanceResult(2);
						if(task.getPathAcceptanceResult()!=0){
							task.setStatus(13);
						}
					}else {
					task.setPhotoAcceptanceResult(1);
					if(task.getPathAcceptanceResult()==1){
						task.setStatus(10);
					}
				}
//				task.setLastAlterTime(new Date());
				taskService.updateById(task);
				}
			}
			if(auditType.equals(dict3.getItemName())){
				Assert.notNull(dPhotoDto1.getAudit3(),dPhotoDto1.getPhotoTypeText()+ "审核结果不能为空");
				dPhotoDto.setAudit3(dPhotoDto1.getAudit3());
				dPhotoDto.setAuditReason3(dPhotoDto1.getAuditReason3());
				dPhotoDto.setAuditor3(user);
				dPhotoDto.setAuditTime3(new Date());
				if(taskPhotoDtos.get(0).getAudit2()!=null&&taskPhotoDtos.get(0).getAudit3()!=null){
					System.err.println(dPhotoDto1.getAudit1()+"-"+dPhotoDto1.getAudit2()+"-"+dPhotoDto1.getAudit3());
					if(dPhotoDto1.getAudit1()==2||dPhotoDto1.getAudit2()==2||dPhotoDto1.getAudit3()==2){
						task.setPhotoAcceptanceResult(2);
						if(task.getPathAcceptanceResult()!=0){
							task.setStatus(13);
						}
					}else {
						task.setPhotoAcceptanceResult(1);
						if(task.getPathAcceptanceResult()==1){
							task.setStatus(10);
						}
					}
//					task.setLastAlterTime(new Date());					
					taskService.updateById(task);
				}
			}
			super.updateById(dPhotoDto);
		}				
		return "审核完成";		
	}
	
	
	@Override
	public String upadteResult(Long taskId,Integer acceptanceResult){
		List<TaskPhotoDto> list=taskPhotoMapper.getAuditPhoto(taskId, acceptanceResult);
		if(list.size()==4){
			Task task=taskService.selectById(taskId);
			task.setPhotoAcceptanceResult(acceptanceResult);
			taskService.updateById(task);
			return "通过";	
		}
		return "未通过";	
	}
	
	
	@Scheduled(cron = "0 0 0/1 * * ?") // 每小时执行一次
	@Override
	public String returnAudit(){
		Wrapper<Task> wrapper=new CriterionWrapper<Task>(Task.class);
		wrapper.eq("status", 3);
		wrapper.eq("state", 1);
		wrapper.notIn("photo_acceptance_result", 0);
		wrapper.notIn("path_acceptance_result", 0);
		List<Task> tasks=taskService.selectList(wrapper);
		for(Task task:tasks){
			if(task.getPhotoAcceptanceResult()==1&&task.getPathAcceptanceResult()==1){
				task.setStatus(10);
			}else{
				task.setStatus(13);
			}
			taskService.updateById(task);
		}
		return "回审成功";		
	}
	
	
	public String md5(String text) throws Exception {
        //加密后的字符串
        String encodeStr=DigestUtils.md5Hex(text + jwtUtil.getKey2());
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }
	
	//获取gps
	@Override
	public TaskPhoto getStartPoint(Long taskId){
		Wrapper<TaskPhoto> wrapper=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
		wrapper.eq("task_id", taskId);
		wrapper.eq("state", 1);
		TaskPhoto taskPhoto=super.selectOne(wrapper);
		return taskPhoto;
	}
	
	//轨迹初始点
	@Override
	public String startPoint(){
		Wrapper<TaskPhoto> wrapper=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
		wrapper.eq("state", 1);
		wrapper.eq("photo_type", 4);
		List<TaskPhoto> list=super.selectList(wrapper);
		for(TaskPhoto taskPhoto:list){
			List<TrackInfoDto> trackInfos=trackInfoService.getListByTaskId(taskPhoto.getTaskId());
			if(trackInfos.size()<2){
				double lat=Double.valueOf(taskPhoto.getGps().split("\\/")[1]);
				double lng=Double.valueOf(taskPhoto.getGps().split("\\/")[0]);
				TrackInfo trackInfo=new TrackInfo();
				trackInfo.setFdId(Long.valueOf(IdWorker.getId()));
				trackInfo.setLatitude(lat);
				trackInfo.setLongitude(lng);
				trackInfo.setLocTime(taskPhoto.getCreateTime());
				trackInfo.setCreateTime(taskPhoto.getCreateTime());
				trackInfo.setTaskId(taskPhoto.getTaskId());
				trackInfoService.insert(trackInfo);
			}
		}
		return "获取出发点成功";
		
	}
	
	@Override
	public String endPoint(){
		Wrapper<TaskPhoto> wrapper=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
		wrapper.eq("state", 1);
		wrapper.eq("photo_type", 4);
		List<TaskPhoto> list=super.selectList(wrapper);
		for(TaskPhoto taskPhoto:list){
			List<TrackInfoDto> trackInfos=trackInfoService.getListByTaskId(taskPhoto.getTaskId());
			if(trackInfos.size()<2){
				double lat=Double.valueOf(taskPhoto.getGps().split("\\/")[1]);
				double lng=Double.valueOf(taskPhoto.getGps().split("\\/")[0]);
				TrackInfo trackInfo=new TrackInfo();
				trackInfo.setFdId(Long.valueOf(IdWorker.getId()));
				trackInfo.setLatitude(lat);
				trackInfo.setLongitude(lng);
				trackInfo.setLocTime(taskPhoto.getCreateTime());
				trackInfo.setCreateTime(taskPhoto.getCreateTime());
				trackInfo.setTaskId(taskPhoto.getTaskId());
				trackInfoService.insert(trackInfo);
			}
		}
		return "获取出发点成功";
		
	}
	
	@Override
	public List<TaskPhoto> deletePoint(){
		Wrapper<TaskPhoto> wrapper=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
		wrapper.eq("state", 1);
		wrapper.eq("photo_type", 4);
		List<TaskPhoto> list=super.selectList(wrapper);
		List<TaskPhoto> listA=new ArrayList<>();
		for(TaskPhoto taskPhoto:list){
			List<TrackInfoDto> trackInfos=trackInfoService.getListByTaskId(taskPhoto.getTaskId());
			if(trackInfos.size()==2){	
				listA.add(taskPhoto);
			}
		}
		return listA;	
	}
}
