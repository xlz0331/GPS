package com.hwagain.eagle.track.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Track;

import org.codehaus.janino.Java.SuperclassFieldAccessExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//import net.minidev.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hwagain.eagle.base.dto.DictDto;
import com.hwagain.eagle.base.dto.LogLoginDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.entity.SupplierInfo;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.task.dto.TaskPhotoDto;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskAnalyze;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.mapper.TaskMapper;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.track.dto.TrackInfoDto;
import com.hwagain.eagle.track.entity.TrackAnalyze;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.entity.TrackInfoVo;
import com.hwagain.eagle.track.entity.TrackParam;
import com.hwagain.eagle.track.mapper.TrackInfoMapper;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.SqlDbUtils;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.e;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
@Configurable
@EnableScheduling
@Component
@Service("trackInfoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrackInfoServiceImpl extends ServiceImpl<TrackInfoMapper, TrackInfo> implements ITrackInfoService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	private static final Logger logger = LoggerFactory.getLogger(SqlDbUtils.class);
	@Autowired ITaskService taskService;
	@Autowired BaseContextHandler baseContextHandler;
	@Autowired IUserInfoService userInfoService;
	@Autowired IDictService dictService;
	@Autowired ITaskPhotoService taskPhotoService;
	@Autowired IRegionDetailService regionDetailService;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired ILogLoginService logloginService;
	@Autowired TaskMapper taskMapper;
	@Value("${sys.config.url.baiduGpsSwitch}")
	private String baiduGpsSwitch;
	@Value("${sys.config.url.ak}")
	private String ak;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(TrackInfo.class, TrackInfoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(TrackInfoDto.class, TrackInfo.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
    @Value("${sys.config.url.smsUrl}")
    private String smsUrl;
	private static double EARTH_RADIUS = 6378.137;  
    
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    } 
    
	@Override
	public String save(List<TrackInfo> trackInfos) {
		for(TrackInfo trackInfoDto:trackInfos){
			validate(trackInfoDto);
			Task task=taskService.selectById(trackInfoDto.getTaskId());
			if (null!=task.getStatus()&&task.getStatus()!=1) {
				return "请求成功，任务已结束";
			}
//			Wrapper<TrackInfo> wrapper=new CriterionWrapper<TrackInfo>(TrackInfo.class);
//			wrapper.eq("task_id",trackInfoDto.getTaskId());
//			wrapper.orderBy("loc_time",false);
//			TrackInfo track=super.selectFirst(wrapper);
//			
//			double distance=0;
//			int  direction=0;
//			double speed=0;
//			if(track!=null){
//				double lat1 = rad(track.getLatitude());
//				double lat2 = rad(trackInfoDto.getLatitude());
//				double lon1 = rad(track.getLongitude());
//				double lon2 = rad(trackInfoDto.getLongitude());
//				double a = lat1 - lat2;  
//		        double b = lon1 - lon2;
//		        //与上一个位置间经过的距离（m）
//		        distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
//		                + Math.cos(lat1) * Math.cos(lat2)  
//		                * Math.pow(Math.sin(b / 2), 2)));
//		        distance = distance * EARTH_RADIUS;
//		        distance = Math.round(distance * 10000d) / 10000d;  
//		        distance = distance*1000;
//		        //方向(0°-360°)
//		        double azimuth = Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1)
//	            * Math.cos(lat2) * Math.cos(lon2 - lon1);
//		        azimuth = Math.sqrt(1 - azimuth * azimuth);
//		        azimuth = Math.cos(lat2) * Math.sin(lon2 - lon1) / azimuth;
//		        azimuth = Math.asin(azimuth) * 180 / Math.PI;
//		        direction = (new Double(azimuth)).intValue();
//		        //速度(km/h)
//		        long nd = 1000 * 24 * 60 * 60;
//		        long nh = 1000 * 60 * 60;
//		        long nm = 1000 * 60;
//		        Date nowDate=new Date();
//		        long diff =(nowDate.getTime()- track.getLocTime().getTime());
//		        speed=(distance/diff)*3600;
//		        System.err.println(direction+";"+distance+";"+speed);
//			}
			if ("gps".equals(trackInfoDto.getCoordTypeInput())) {
				String path=baiduGpsSwitch+"?coords="+trackInfoDto.getLongitude()+","+trackInfoDto.getLatitude()+"&from=1&to=5&ak="+ak;
				double lng=0;
				double lat=0;
		        RestTemplate r = new RestTemplate();
		        String res = r.getForObject(path, String.class);
		        JSONObject json = JSONObject.parseObject(res);
		        JSONArray arr = json.getJSONArray("result");
		        System.err.println(String.valueOf(arr));
		        for (Object object : arr) {
		        	JSONObject o = (JSONObject) object;
		        	String x=o.getString("x");
		        	String y=o.getString("y");
		        	lng=Double.valueOf(x);
		        	lat=Double.valueOf(y);
		        }
		        trackInfoDto.setLongitude(lng);
		        trackInfoDto.setLatitude(lat);
			}
			TrackInfo entity = dtoToEntityMapper.map(trackInfoDto, TrackInfo.class);
			entity.setCreateTime(new Date());
			Date dt2=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt2);
			calendar.add(Calendar.HOUR,-6);
			System.err.println(entity.getLongitude());
			if(entity.getLocTime().after(calendar.getTime())){
				super.insert(entity);
			}else {
				logger.info("缓存点："+entity);
			}
		}
		
		return "保存成功";
	}
	
	//查询在途轨迹
	@Override
	public TrackInfo getTrackPointByTaskId(Long taskId) {
		TrackInfo list=new TrackInfo();
		System.err.println(taskId);
		Long.valueOf(taskId);
		Wrapper<TrackInfo> wrappers= new CriterionWrapper<>(TrackInfo.class);
		wrappers.eq("task_id", taskId);
		wrappers.orderBy("loc_time");
		List<TrackInfo> lTrackInfos = super.selectList(wrappers);
		
		Wrapper<TrackInfo> wrapper = new CriterionWrapper<>(TrackInfo.class);
		wrapper.eq("task_id", taskId);
		wrapper.orderBy("loc_time",false);
		TrackInfo listA = super.selectFirst(wrapper);

		if(listA==null){
			Wrapper<TaskPhoto> wraPhoto=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
			wraPhoto.eq("task_id", taskId);
			wraPhoto.eq("state", 1);
			TaskPhoto taskPhoto=taskPhotoService.selectFirst(wraPhoto);
			String log=taskPhoto.getGps().split("/")[0];
			String lat=taskPhoto.getGps().split("/")[1];
			System.err.println(Double.valueOf(lat));
			list.setFdId(taskPhoto.getFdId());
			list.setTaskId(taskId);
			list.setLatitude(Double.valueOf(lat));
			list.setLongitude(Double.valueOf(log));
			list.setCreateTime(taskPhoto.getCreateTime());
		}else {
			list=listA;
		}
		
		return list;
	}

	@Override
	public List<TrackInfoDto> getListByTaskId(Long taskId) {
//		System.err.println(taskId);
//		Long.valueOf(taskId);
		Wrapper<TrackInfo> wrapper = new CriterionWrapper<>(TrackInfo.class);
		wrapper.eq("task_id", taskId);
		wrapper.orderBy("loc_time");
		List<TrackInfo> list = super.selectList(wrapper);
//		for(TrackInfo trackInfo:list){
//			
//		}
//		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
//		System.err.println(jsonArray.toString());
//		RestTemplate r = new RestTemplate();
//	    MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
//        requestEntity.add("ak", "3qRx80AWMi9iot5lusSEoAxCEbLfu1rS");
//        requestEntity.add("point_list",jsonArray.toString());
//		String s = r.postForObject("http://api.map.baidu.com/rectify/v1/track?", requestEntity, String.class);
//		System.err.println(s);
		return entityToDtoMapper.mapAsList(list, TrackInfoDto.class);
	}
	
	//轨迹中断
	@Override
	public List<TrackInfoDto> getBugListByTaskId(Long taskId) {
		List<Dict> dict=dictService.findBytypeName("断点允许距离");
		if (dict.size()==0) {
			Assert.throwException("请配置断点允许距离");
		}
		System.err.println(taskId);
		Long.valueOf(taskId);
		Wrapper<TrackInfo> wrapper = new CriterionWrapper<>(TrackInfo.class);
		wrapper.eq("task_id", taskId);
		wrapper.orderBy("loc_time");
		List<TrackInfo> list = super.selectList(wrapper);
		List<TrackInfo> listA=new ArrayList<>();
		for(int i=1;i<list.size();i++){
			TrackInfo tInfo=list.get(i);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dt1=null;
			Date dt2=null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tInfo.getLocTime());
			calendar.add(Calendar.MINUTE,-60);
			calendar.getTime();
			if(list.get(i-1).getLocTime().before(calendar.getTime())){
				TrackInfo track=list.get(i-1);
				TrackInfo trackInfoDto=list.get(i);
				double distance=0;
				int  direction=0;
				double lat1 = rad(track.getLatitude());
				double lat2 = rad(trackInfoDto.getLatitude());
				double lon1 = rad(track.getLongitude());
				double lon2 = rad(trackInfoDto.getLongitude());
				double a = lat1 - lat2;  
		        double b = lon1 - lon2;
		        //与上一个位置间经过的距离（m）
		        distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
		                + Math.cos(lat1) * Math.cos(lat2)  
		                * Math.pow(Math.sin(b / 2), 2)));
		        distance = distance * EARTH_RADIUS;
		        distance = Math.round(distance * 10000d) / 10000d;  
		        distance = distance*1000;
		       if(distance>dict.get(0).getItemNo()){
		    	   listA.add(list.get(i-1));
					listA.add(tInfo);
		       }
				System.err.println(">>>>>>>>"+i);
			}else{
				System.err.println(">"+i);
			}
			
		}
		return entityToDtoMapper.mapAsList(listA, TrackInfoDto.class);
	}
	
	
	private void validate(TrackInfo trackInfoDto) {
		Assert.notNull(trackInfoDto.getLocTime(), "定位时间不能为空");
		long diffTime = new Date().getTime()  -trackInfoDto.getLocTime().getTime();
		
		long diffMin = diffTime / (1000 * 60);
		System.err.println("time"+diffMin);
		Assert.isTrue(diffMin >-10, "定位时间（locTime）不能大于当前服务端时间10分钟");		
		Assert.notNull(trackInfoDto.getTaskId(), "任务ID不能为空");
		Assert.notNull(trackInfoDto.getLatitude(), "纬度不能为空");
		Assert.notNull(trackInfoDto.getLongitude(), "经度不能为空");
//		Assert.notNull(trackInfoDto.getDistance(), "与上一个位置间经过的距离不能为空");
		Assert.notNull(trackInfoDto.getDirection(), "方向（0-359度不能为空");
		Assert.notNull(trackInfoDto.getHeight(), "高度（米）不能为空");
		Assert.notNull(trackInfoDto.getSpeed(), "时速（千米/时）不能为空");
		Assert.notNull(trackInfoDto.getRadius(), "定位精度（米）不能为空");
		Assert.notBlank(trackInfoDto.getCoordTypeInput(), "坐标类型不能为空");
	}
	
//	@Scheduled(cron = "0 0 1 ? * *") // 每天1点执行-首先执行
	@Scheduled(cron = "0 */20 * * * ?") // 20分钟一次【 测试用】
//	@Scheduled(cron = "0/10 * * * * ?") // 每天2点执行
	@Override
	public String warnDriver(){
		System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		
		Date doDate=new Date();
		Wrapper<Task> wraTask=new CriterionWrapper<Task>(Task.class);
		wraTask.eq("state", 1);
		wraTask.eq("status", 1);
		List<Task> list=taskService.selectList(wraTask);
		for(Task task:list){
			Wrapper<TrackInfo> wrapper = new CriterionWrapper<>(TrackInfo.class);
			wrapper.eq("task_id", task.getFdId());
			wrapper.orderBy("loc_time",false);
			TrackInfo listInfo = super.selectFirst(wrapper);
			if(listInfo==null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dt1=null;
				Date dt2=null;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(task.getCreateTime());
				calendar.add(Calendar.MINUTE,30);
				calendar.getTime();
				System.err.println("||"+calendar.getTime()+"||"+new Date());
				String nowTime = df.format(doDate);
				String sessionTime = df.format(calendar.getTime());
				if(new Date().after(calendar.getTime())){
					logger.info("轨迹异常，需要发送短信");
					String mobile=String.valueOf(baseContextHandler.getMobile());
					List<String> phones=new ArrayList<>();
					SupplierInfo supplierInfo=supplierInfoService.selectById(task.getSupplierId());
					phones.add(supplierInfo.getMobile());
					phones.add(task.getUserMobile());
					Wrapper<LogLogin> wraLogin=new CriterionWrapper<LogLogin>(LogLogin.class);
				        wraLogin.le("login_time", new Date());
				        wraLogin.ge("login_time", task.getCreateTime());
				        wraLogin.eq("user_id", task.getFdId());
				        wraLogin.eq("session_id", "trackBug");
				        LogLogin login=logloginService.selectOne(wraLogin);
				        if(login==null){
				        	JSONObject template = new JSONObject();
					        template.put("content","您好，从"+df.format(task.getCreateTime())+"开始，我司已超过20分钟未收到"+task.getUserPlateNumber()+"车运输轨迹信息，请检查华劲鹰眼运行状态或手机网络是否正常。轨迹信息异常将会影响结算！");
					        template.put("createUserCode","111");
					        template.put("phones",phones);
					        template.put("systemCode","hwagain-eagle");
//					        System.err.println(template.toString());
//					        logger.info(template.toString());
				        	sendSms(template.toString());
						    LogLoginDto logLogin=new LogLoginDto();
							logLogin.setLoginTime(new Date());
//							System.err.println(String.valueOf(sessionId.getId()));
							logLogin.setUserId(task.getFdId());
							logLogin.setTerminalType(1);
							logLogin.setSessionId("trackBug");
							logLogin.setTerminalInfo(template.getString("phones"));
							logloginService.addOne(logLogin);
				        }else{
				        	LogLoginDto logLogin=new LogLoginDto();
							logLogin.setLoginTime(new Date());
//							System.err.println(String.valueOf(sessionId.getId()));
							logLogin.setUserId(task.getFdId());
							logLogin.setTerminalType(1);
							logLogin.setSessionId("trackBuged");
							logLogin.setTerminalInfo(String.valueOf(phones));
							logloginService.addOne(logLogin);
//				        	logger.info(task.getDriverName()+task.getUserPlateNumber()+"车轨迹多次异常");
				        }
//					 JSONObject template = new JSONObject();
//				        template.put("content","您好，从"+df.format(task.getCreateTime())+"开始，您的"+task.getUserPlateNumber()+"车在运输过程中无有效的GPS信息上传，请检查APP的运行状态或手机网络是否正常。上传失败会影响运补结算！");
//				        template.put("createUserCode","hwagain-eagle");
//				        template.put("phones",phones);
//				        template.put("systemCode","hwagain-eagle");
//				        System.err.println(template.toString());
//				        logger.info(template.toString());
//				        sendSms(template.toString());
//				        LogLoginDto logLogin=new LogLoginDto();
//						logLogin.setLoginTime(new Date());
////						System.err.println(String.valueOf(sessionId.getId()));
//						logLogin.setUserId(task.getFdId());
//						logLogin.setTerminalType(1);
//						logLogin.setSessionId("trackBug");
//						logLogin.setTerminalInfo(template.getAsString("phones"));
//						logloginService.addOne(logLogin);
				}
			}else{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dt1=null;
				Date dt2=null;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(listInfo.getCreateTime());
				calendar.add(Calendar.MINUTE,30);
				calendar.getTime();
				System.err.println("||"+calendar.getTime()+"||"+new Date());
				String nowTime = df.format(doDate);
				String sessionTime = df.format(calendar.getTime());
				if(new Date().after(calendar.getTime())){
					String mobile=String.valueOf(baseContextHandler.getMobile());
					List<String> phones=new ArrayList<>();
					SupplierInfo supplierInfo=supplierInfoService.selectById(task.getSupplierId());
					phones.add(supplierInfo.getMobile());
					phones.add(task.getUserMobile());
			        Wrapper<LogLogin> wraLogin=new CriterionWrapper<LogLogin>(LogLogin.class);
			        wraLogin.le("login_time", new Date());
			        wraLogin.ge("login_time", listInfo.getCreateTime());
			        wraLogin.eq("user_id", task.getFdId());
			        wraLogin.eq("session_id", "trackBug");
			        LogLogin login=logloginService.selectOne(wraLogin);
			        if(login==null){
			        	JSONObject template = new JSONObject();
				        template.put("content","您好，从"+df.format(listInfo.getCreateTime())+"开始，我司已超过20分钟未收到"+task.getUserPlateNumber()+"运输轨迹信息，请检查华劲鹰眼运行状态或手机网络是否正常。轨迹信息异常将会影响结算！");
				        template.put("createUserCode","111");
				        template.put("phones",phones);
				        template.put("systemCode","hwagain-eagle");
//				        System.err.println(template.toString());
//				        logger.info(template.toString());
			        	sendSms(template.toString());
					    LogLoginDto logLogin=new LogLoginDto();
						logLogin.setLoginTime(new Date());
//						System.err.println(String.valueOf(sessionId.getId()));
						logLogin.setUserId(task.getFdId());
						logLogin.setTerminalType(1);
						logLogin.setSessionId("trackBug");
						logLogin.setTerminalInfo(template.getString("phones"));
						logloginService.addOne(logLogin);
			        }else{
//			        	logger.info(task.getDriverName()+task.getUserPlateNumber()+"车轨迹多次异常");
			        }
				    
				}
			}
		}
//		logger
		return "已提示司机开启定位";		
	}
	
	
	@Override
	public String sendSms(String data){
        String result="";
        try {
            RestTemplate restTemplate =new RestTemplate();
            HttpHeaders httpHeaders=new HttpHeaders();
            MediaType type=MediaType.parseMediaType("application/json;charset=utf-8");
            httpHeaders.setContentType(type);
            httpHeaders.add("Accept",MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> formEntity=new HttpEntity<String>(data,httpHeaders);
            result = restTemplate.postForObject(smsUrl, formEntity, String.class);
            System.err.println(">>>>>"+result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
	
	//轨迹审核
	@Override
	public String trackAudit(Long taskId,Integer pathAcceptanceResult,String reason){
		String auditType;
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
		Task task=taskService.selectById(taskId);	
		if(task==null){
			Assert.throwException("找不到当前任务");
		}
		Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
		wraTrack.eq("task_id", taskId);
		List<TrackInfo> tList=super.selectList(wraTrack);
		if(tList.size()==0){
			Assert.throwException("找不到当前任务轨迹");
		}
		if(auditType.equals(dict1.getItemName())){
			TrackInfo tInfo=tList.get(0);
			tInfo.setAudit1(pathAcceptanceResult);
			tInfo.setAuditReason1(reason);
			tInfo.setAuditor1(user);
			tInfo.setAuditTime1(new Date());
			super.updateById(tInfo);
		}
		if(auditType.equals(dict2.getItemName())){
			TrackInfo tInfo=new TrackInfo();
			for(TrackInfo tInfo1:tList){
				if(tInfo1.getAudit1()!=null){
					tInfo=tInfo1;
					break;
				}
			}
			tInfo.setAudit2(pathAcceptanceResult);
			tInfo.setAuditReason2(reason);
			tInfo.setAuditor2(user);
			tInfo.setAuditTime2(new Date());
			super.updateById(tInfo);
			if(tList.get(0).getAudit2()!=null&&tList.get(0).getAudit3()!=null){
				if(tList.get(0).getAudit1()==2||tList.get(0).getAudit2()==2||tList.get(0).getAudit3()==2){
					task.setPathAcceptanceResult(2);
					if(task.getPhotoAcceptanceResult()!=0){
						task.setStatus(13);
					}
				}else {
					task.setPathAcceptanceResult(1);
					if(task.getPhotoAcceptanceResult()==1){
						task.setStatus(10);
					}
				}
//				task.setLastAlterTime(new Date());
				taskService.updateById(task);
			}
		}
		if(auditType.equals(dict3.getItemName())){
			TrackInfo tInfo=new TrackInfo();
			for(TrackInfo tInfo1:tList){
				if(tInfo1.getAudit1()!=null){
					tInfo=tInfo1;
					break;
				}
			}
			tInfo.setAudit3(pathAcceptanceResult);
			tInfo.setAuditReason3(reason);
			tInfo.setAuditor3(user);
			tInfo.setAuditTime3(new Date());
			super.updateById(tInfo);
			if(tList.get(0).getAudit2()!=null&&tList.get(0).getAudit3()!=null){
				if(tList.get(0).getAudit1()==2||tList.get(0).getAudit2()==2||tList.get(0).getAudit3()==2){
					task.setPathAcceptanceResult(2);
					if(task.getPhotoAcceptanceResult()!=0){
						task.setStatus(13);
					}
				}else {
					task.setPathAcceptanceResult(1);
					if(task.getPhotoAcceptanceResult()==1){
						task.setStatus(10);
					}
				}
//				task.setLastAlterTime(new Date());
				
				taskService.updateById(task);
			}
		}
		return "轨迹审核完成";
	}
	
	//轨迹审核
	@Override
	public String trackAudit1(Long taskId,Integer pathAcceptanceResult,String reason){
		String user=UserUtils.getUserInfo().getName();		
		Wrapper<Dict> wraDict1=new CriterionWrapper<Dict>(Dict.class);
		wraDict1.eq("type_name", "审核");
		wraDict1.eq("is_delete", 0);
		wraDict1.eq("item_no", 1);
		Dict dict1=dictService.selectFirst(wraDict1);		
		if(dict1==null){
			Assert.throwException("审核节点未配置，请联系系统管理员");
		}
		
		Wrapper<UserInfo> wraUser=new CriterionWrapper<UserInfo>(UserInfo.class);
		wraUser.eq("loginname", user);
		wraUser.eq("state", 1);
		wraUser.eq("locked", 2);
		wraUser.eq("user_type", dict1.getItemName());
		UserInfo userInfo=userInfoService.selectFirst(wraUser);
		if(userInfo==null){
			Assert.throwException("当前用户没有权限进行审核，请联系用户管理员进行配置");
 		}
		Task task=taskService.selectById(taskId);	
		if(task==null){
			Assert.throwException("找不到当前任务");
		}
		Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
		wraTrack.eq("task_id", taskId);
		List<TrackInfo> tList=super.selectList(wraTrack);
		if(tList.size()==0){
			Assert.throwException("找不到当前任务轨迹");
		}
		TrackInfo tInfo=new TrackInfo();
		tInfo=tList.get(0);
		tInfo.setAudit1(pathAcceptanceResult);
		tInfo.setAuditReason1(reason);
		tInfo.setAuditor1(user);
		tInfo.setAuditTime1(new Date());
		super.updateById(tInfo);
//		task.setStatus(3);
//		task.setLastAlterTime(new Date());		
//		taskService.updateById(task);
		return "轨迹1审核完成";
	}
	
	@Override
	public String trackAudit2(Long taskId,Integer pathAcceptanceResult,String reason){
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
		Task task=taskService.selectById(taskId);	
		if(task==null){
			Assert.throwException("找不到当前任务");
		}
		Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
		wraTrack.eq("task_id", taskId);
		List<TrackInfo> tList=super.selectList(wraTrack);
		if(tList.size()==0){
			Assert.throwException("找不到当前任务轨迹");
		}
		TrackInfo tInfo=new TrackInfo();
		for(TrackInfo tInfo1:tList){
			tInfo=tInfo1;
			tInfo.setAudit2(pathAcceptanceResult);
			tInfo.setAuditReason2(reason);
			tInfo.setAuditor2(user);
			tInfo.setAuditTime2(new Date());
			super.updateById(tInfo);
		}
		tInfo.setAudit2(pathAcceptanceResult);
		tInfo.setAuditReason2(reason);
		tInfo.setAuditor2(user);
		tInfo.setAuditTime2(new Date());
		super.updateById(tInfo);
		if(tList.get(0).getAudit3()!=null){
			if(pathAcceptanceResult==2||tList.get(0).getAudit3()==2){
				task.setPathAcceptanceResult(2);
				if(task.getPhotoAcceptanceResult()!=0){
					task.setStatus(13);
				}
			}else {
				task.setPathAcceptanceResult(1);
				if(task.getPhotoAcceptanceResult()==1){
					task.setStatus(10);
				}
			}
//			task.setLastAlterTime(new Date());
			
			taskService.updateById(task);
		}
		return "轨迹2审核完成";
	}
	
	//轨迹审核
	@Override
	public String trackAudit3(Long taskId,Integer pathAcceptanceResult,String reason){
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
		Task task=taskService.selectById(taskId);	
		if(task==null){
			Assert.throwException("找不到当前任务");
		}
		Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
		wraTrack.eq("task_id", taskId);
		List<TrackInfo> tList=super.selectList(wraTrack);
		if(tList.size()==0){
			Assert.throwException("找不到当前任务轨迹");
		}
		TrackInfo tInfo=new TrackInfo();
		for(TrackInfo tInfo1:tList){
			tInfo=tInfo1;
			tInfo.setAudit3(pathAcceptanceResult);
			tInfo.setAuditReason3(reason);
			tInfo.setAuditor3(user);
			tInfo.setAuditTime3(new Date());
			super.updateById(tInfo);
		}
		
		if(tList.get(0).getAudit2()!=null){
			if(tList.get(0).getAudit2()==2||pathAcceptanceResult==2){
				task.setPathAcceptanceResult(2);
				if(task.getPhotoAcceptanceResult()!=0){
					task.setStatus(13);
				}
			}else {
				task.setPathAcceptanceResult(1);
				if(task.getPhotoAcceptanceResult()==1){
					task.setStatus(10);
				}
			}
//			task.setLastAlterTime(new Date());
			
			taskService.updateById(task);
		}
		return "轨迹3审核完成";
	}
	
	//拍照定位位置不属于发车定点位置
	@Override
	public List<String> checkStartPoint(Long TaskId){
		List<String> result=new ArrayList<>();
		Task task=taskService.selectById(TaskId);
		if(task==null){
			Assert.throwException("找不到当前任务");
		}
		Wrapper<TaskPhoto> wraPhoto=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
		wraPhoto.eq("task_id", TaskId);
		wraPhoto.eq("state", 1);
		TaskPhoto taskPhoto=taskPhotoService.selectFirst(wraPhoto);
		if(taskPhoto==null){
			Assert.throwException("找不到当前任务");
		}
		
		Wrapper<Dict> wraDict=new CriterionWrapper<Dict>(Dict.class);
		wraDict.eq("is_delete", 0);
		wraDict.eq("type_name", "供货范围允许误差");
		Dict dict=dictService.selectFirst(wraDict);
		if(dict==null){
			logger.info("找不到供货范围允许误差配置");
			Assert.throwException("找不到供货范围允许误差配置");
		}
		Wrapper<Dict> wraDict2=new CriterionWrapper<Dict>(Dict.class);
		wraDict2.eq("is_delete", 0);
		wraDict2.eq("type_name", "拍照点与发车点允许误差");
		Dict dict2=dictService.selectFirst(wraDict2);
		if(dict2==null){
			logger.info("拍照点与发车点允许误差设置");
			Assert.throwException("拍照点与发车点允许误差设置");
		}
		Wrapper<TrackInfo> wraTrack=new CriterionWrapper<TrackInfo>(TrackInfo.class);
		wraTrack.eq("task_id", TaskId);
		wraTrack.orderBy("loc_time");
		TrackInfo track=super.selectFirst(wraTrack);
		if(track==null){
			result.add("无轨迹信息");
			return result;
		}
		
		String[] gpss = taskPhoto.getGps().split("/");
		if(gpss.length==2){
			double lona=Double.valueOf(gpss[0]);
			double lata=Double.valueOf(gpss[1]);
			//判断 拍照定位位置不属于发车定点位置
			double distanced=0;
			double lat1d = rad(track.getLatitude());
			double lon1d = rad(track.getLongitude());
			double lat2d = rad(lata);
			double lon2d = rad(lona);
			System.err.println(lata+"/"+lona+";"+track.getLatitude()+"/"+track.getLongitude());
//	      	距离（m）
	        double ad = lat1d - lat2d;  
	        double bd = lon1d - lon2d;
	        distanced = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(ad / 2), 2)  
	                + Math.cos(lat1d) * Math.cos(lat2d)  
	                * Math.pow(Math.sin(bd / 2), 2)));
	        distanced = distanced * EARTH_RADIUS;
	        distanced = Math.round(distanced * 10000d) / 10000d;  
	        distanced = distanced*1000;
	        System.err.println(distanced+"米");
	        if(distanced>dict2.getItemNo()){
	        	result.add("拍照定点位置不属于发车定点位置");
	        }
	        //判断 发车拍照定点位置不属于报备点
	        SupplierInfo supplierInfos=supplierInfoService.selectById(task.getSupplierId());
			Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
			wrapper.eq("state", 1);
			wrapper.eq("supplier_name", supplierInfos.getName());
			List<RegionDetail> region=regionDetailService.selectList(wrapper);		
			if(region.size()==0){
				result.add("找不到当前供应商在发货区域的供货报备点，请联系相关人员添加");
//				Assert.throwException("当前任务供应商供货报备点，请联系相关人员添加");
			}
			double distance=0;
			if(region.size()>0){
				for(int i=0;i<region.size();i++){
					RegionDetail regionDetail=region.get(i);
					double lat1 = rad(regionDetail.getLatitude());
					double lon1 = rad(regionDetail.getLongitude());
					double lat2 = rad(lata);
					double lon2 = rad(lona);
			        System.err.println(lata+"/"+lona+";"+regionDetail.getLatitude()+"/"+regionDetail.getLongitude());
//			      	距离（m）
			        double a = lat1 - lat2;  
			        double b = lon1 - lon2;
			        //与上一个位置间经过的距离（m）
			        distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
			                + Math.cos(lat1) * Math.cos(lat2)  
			                * Math.pow(Math.sin(b / 2), 2)));
			        distance = distance * EARTH_RADIUS;
			        distance = Math.round(distance * 10000d) / 10000d;  
			        distance = distance*1000;
			        System.err.println(distance+"米");
			        if(distance<dict.getItemNo()){
//			        	result.add("定位点在供货范围内");
			        	break;
			        }
			        if(i==region.size()-1){
			        	result.add("定位点不在供货范围内");
			        	logger.info("定位点不在供货范围内");
			        }
			      
				}
			}
		}	
		List<TrackInfoDto> trackInfos=getBugListByTaskId(TaskId);
		if(trackInfos.size()>0){
			result.add("运输定点轨迹有中断");
		}
		return result;		
	}	
	//运输定点轨迹有中断
//	@Override
//	public List<TrackInfoDto> checkBugPoint(Long taskId){
//		
//		return null;
//		
//	}
	
	@Autowired TrackInfoMapper trackInfoMapper;
	@Override
	public List<TrackInfoVo> findTrackByTaskId(Long taskId){
//		TrackParam tParam=new TrackParam();
		List<TrackInfoVo> list=trackInfoMapper.findTrackByTaskId(taskId);
		 String url = "http://api.map.baidu.com/rectify/v1/track?";
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
	        Map<String, Object> params = new HashMap<>();
	        params.put("point_list", list);
	        params.put("ak", "3qRx80AWMi9iot5lusSEoAxCEbLfu1rS");
//	        JSONObject jsonObj = JSONObject.fromObject(params);
//	        
//	        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity httpEntity = new HttpEntity(params,headers);
	        ResponseEntity<String> request = restTemplate.postForEntity(url, httpEntity,String.class);
	        System.out.println(request.getBody());
		return list;		
	}
	
	@Override
	public List<TrackAnalyze> findAllTrackAnalyze(String startTime,String endTime,String status) {
		if (startTime==null||startTime.isEmpty()) {
			startTime="2019-01-01";
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 endTime = formatter.format(new Date());
		}
		List<TrackAnalyze> list=new ArrayList<>();
		if (null!=status&&!status.isEmpty()) {
			list=trackInfoMapper.findAllTrackAnalyze(startTime, endTime);
			for(TrackAnalyze entity:list){
				if (null!=entity.getIsFull()&&!entity.getIsFull().isEmpty()) {
					if ("0".equals(entity.getIsFull())) {
						entity.setIsFull("完整");
					}else {
						entity.setIsFull("中断"+entity.getIsFull()+"次");
					}
				}else {
					Map<String, String> list2=findTrackAnalyze(entity.getFdId());
					entity.setIsFull(list2.get("isFull"));
					entity.setTrackNum(Integer.valueOf(list2.get("trackNum")));
					entity.setTimeNum(Integer.valueOf(list2.get("trackTime")));
					entity.setTotalTime(Integer.valueOf(list2.get("totalTime")));
					if (1==entity.getStatus()) {
						entity.setIsContinue(list2.get("isContinue"));
					}					
				}				
			}
		}else{
			list=trackInfoMapper.findAllTrackAnalyze2(startTime, endTime);
			
		}
		return list;
		
	}
 
	public Map<String, String> findTrackAnalyze(Long taskId) {
		List<Dict> dict=dictService.findBytypeName("断点允许距离");
		if (dict.size()==0) {
			Assert.throwException("请配置断点允许距离");
		}
		Map<String, String> map=new HashMap<String, String>();
		Long.valueOf(taskId);
		Wrapper<TrackInfo> wrapper = new CriterionWrapper<>(TrackInfo.class);
		wrapper.eq("task_id", taskId);
		wrapper.orderBy("loc_time");
		List<TrackInfo> list = super.selectList(wrapper);
		List<TrackInfo> listA=new ArrayList<>();
		String isFull="完整";
		Long trackTime=(long) 0;
		Long trackTime1=(long) 0;
		Long trackTime3=(long) 0;
		Long trackTime2=(Long) (list.get(list.size()-1).getLocTime().getTime()-list.get(0).getLocTime().getTime())/(1000*60);
		Integer trackI=0;
		Integer bug=0;
		Long totalTime=(long) 0;
		for(int i=1;i<list.size();i++){
			TrackInfo tInfo=list.get(i);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dt1=null;
			Date dt2=null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tInfo.getLocTime());
			calendar.add(Calendar.MINUTE,-60);
			calendar.getTime();
			if(list.get(i-1).getLocTime().before(calendar.getTime())){
				TrackInfo track=list.get(i-1);
				TrackInfo trackInfoDto=list.get(i);
				double distance=0;
				int  direction=0;
				double lat1 = rad(track.getLatitude());
				double lat2 = rad(trackInfoDto.getLatitude());
				double lon1 = rad(track.getLongitude());
				double lon2 = rad(trackInfoDto.getLongitude());
				double a = lat1 - lat2;  
		        double b = lon1 - lon2;
		        //与上一个位置间经过的距离（m）
		        distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
		                + Math.cos(lat1) * Math.cos(lat2)  
		                * Math.pow(Math.sin(b / 2), 2)));
		        distance = distance * EARTH_RADIUS;
		        distance = Math.round(distance * 10000d) / 10000d;  
		        distance = distance*1000;
		       if(distance>dict.get(0).getItemNo()){
		    	   trackTime=(Long) (list.get(i-1).getLocTime().getTime()-list.get(trackI).getLocTime().getTime())/(1000*60);
		    	  
		    	   totalTime=totalTime+(Long) (list.get(i).getLocTime().getTime()-list.get(i-1).getLocTime().getTime())/(1000*60);  
		    	   if(trackTime>trackTime1){
			        	trackTime1=trackTime;
			        }
			        trackI=i;
			        listA.add(list.get(i-1));
					listA.add(tInfo);
					isFull="轨迹有中断";
					bug++;
		       }
//				System.err.println(">>>>>>>>"+i);
			}else{
//				System.err.println(">"+i);
			}
		}
		trackTime3=(Long) (list.get(list.size()-1).getLocTime().getTime()-list.get(trackI).getLocTime().getTime())/(1000*60);
		if (trackTime3>trackTime1) {
			trackTime1=trackTime3;
		}
		if (isFull.equals("完整")) {
			trackTime1=trackTime2;
		}		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE,-60);
		cal.getTime();
		if (list.get(list.size()-1).getLocTime().before(cal.getTime())) {
			map.put("isContinue", "已停止");
		}else {
			map.put("isContinue","持续中");
		}
		if (bug>0) {
			map.put("isFull","中断"+ bug+"次");
		}else {
			map.put("isFull", isFull);
		}			
		map.put("trackNum", String.valueOf(list.size()));
		map.put("trackTime", String.valueOf(trackTime1));
		map.put("totalTime", String.valueOf(totalTime));
		map.put("bug", String.valueOf(bug));
		return map;
	}
	
	@Scheduled(cron = "0 */50 * * * ?") // 20分钟一次【 测试用】
	@Override
	public String taskAnalyzeSync(){
//		System.err.println("121#########################3");
		String [] status={"2","3","10","13"};
		Wrapper<Task> wrapper=new CriterionWrapper<Task>(Task.class);
		wrapper.in("status", status);
		wrapper.isNull("is_full");
		List<Task> list=taskService.selectList(wrapper);
		for(Task entity:list){
			Map<String, String> list2=findTrackAnalyze(entity.getFdId());
			entity.setIsFull(Integer.valueOf(list2.get("bug")));
			entity.setTrackNum(Integer.valueOf(list2.get("trackNum")));
			entity.setTrackTime(Integer.valueOf(list2.get("trackTime")));
			entity.setTotalBreakTime(Integer.valueOf(list2.get("totalTime")));
			taskService.updateById(entity);
		}
		return "记录统计成功";		
	}
	
	@Override
	public List<TrackAnalyze> findAllTaskTrackAnalyze(String startTime,String endTime,String supplierName) {
		if (startTime==null||startTime.isEmpty()) {
			startTime="2019-01-01";
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 endTime = formatter.format(new Date());
		}
		List<TrackAnalyze> list=new ArrayList<>();
		if (null!=supplierName&&!supplierName.isEmpty()) {
			list=trackInfoMapper.findAllTaskTrackAnalyze2(startTime, endTime,supplierName);
		}else{
			list=trackInfoMapper.findAllTaskTrackAnalyze(startTime, endTime);
		}
		for(TrackAnalyze entity:list){
			if (null!=entity.getIsFull()&&!entity.getIsFull().isEmpty()) {
				if ("0".equals(entity.getIsFull())) {
					entity.setIsFull("完整");
				}else {
					entity.setIsFull("中断"+entity.getIsFull()+"次");
				}
			}else {
				Map<String, String> list2=findTrackAnalyze(entity.getFdId());
				entity.setIsFull(list2.get("isFull"));
				entity.setTrackNum(Integer.valueOf(list2.get("trackNum")));
				entity.setTimeNum(Integer.valueOf(list2.get("trackTime")));
				entity.setTotalTime(Integer.valueOf(list2.get("totalTime")));
				if (1==entity.getStatus()) {
					entity.setIsContinue(list2.get("isContinue"));
				}
				
			}
			
		}
		return list;
		
	}
	
	@Override
	public List<TaskAnalyze> findAllTaskAnalyze(String startTime,String endTime) {
		if (startTime==null||startTime.isEmpty()) {
			startTime="2019-01-01";
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 endTime = formatter.format(new Date());
		}
		List<TaskAnalyze> list=taskMapper.findAllTaskAnalyze(startTime, endTime);
//		for(TaskAnalyze entity:list){
//			
//			Map<String, String> list2=findTrackAnalyze(entity.getFdId());
//			entity.setBugPoint(Integer.valueOf(list2.get("bug")));
//			entity.setTotalTime(Integer.valueOf(list2.get("totalTime")));
//		}
		return list;
		
	}
}
