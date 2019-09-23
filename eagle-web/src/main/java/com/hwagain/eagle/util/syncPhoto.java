package com.hwagain.eagle.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.hwagain.eagle.attachment.entity.Attachment;
import com.hwagain.eagle.attachment.service.IAttachmentService;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;

/**
 * <p>
 * 定时任务-PS基础数据
 * </p>
 *
 * @author xionglz
 * @since 2019-05-05
 */

@Configurable
@EnableScheduling
@Component
public class syncPhoto {

	private final static Logger logger = LoggerFactory.getLogger(syncPhoto.class);

	protected static ExecutorService executorService = null;
	static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();// 本次要处理的列表

	protected Date doDate;
	protected String logType;
	protected String logMeno;
	protected List<String> repPositionNbr; // 判断职位重复
	protected static String logClassName = "syncSalaryPosition";
	//protected Boolean testControl = true; // 【 测试用】控制只执行一次

	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}

	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired ITaskService taskService;
	@Autowired ITaskPhotoService taskPhotoService;
	@Autowired IAttachmentService attachmentService;
	@Autowired
	JDBCConfig jDBCConfig;
	@Value("${sys.config.upload.basePath}")
	private String UPLOAD_FOLDER;
//	@Scheduled(cron = "0 0 0/1 * * ?") // 每小时执行一次
//	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
//	@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void Discount() {

		//if (!testControl) return; // 【 测试用】
		//testControl = false;// 【 测试用】

		doDate = new Date();
//		getPhotoData();

	}

	public JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}

	private List<Map<String, Object>> queryData(String plateNumber,String poundId,String sql,Long TaskId) {

		String url = getjDBCConfig().getPurUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		String username = getjDBCConfig().getPurUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getPurPassword();// 你安装时选设置的密码
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			// 获取进口数据
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			con = DriverManager.getConnection(url, username, password);// 获取连接
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			ResultSetMetaData md = pre.getMetaData();
			int columnCount = md.getColumnCount();
			while (result.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), result.getObject(i));
				}
				listMap.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 System.err.println(listMap); //输出数据到控制台
		 if(listMap.size()==0){
			 Assert.throwException("找不到【"+plateNumber+"】车辆的过磅图片，请联系系统管理员");
		 }
		 for(int i=0;i<listMap.size();i++){
			Task task=taskService.selectById(TaskId);
			byte[] image=(byte[]) listMap.get(i).get("全景图片");
			byte[] image2=(byte[]) listMap.get(i).get("特写图片");
			String datePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String relatedPath2 = datePath + "/"+IdWorker.getId()+"_"+String.valueOf(listMap.get(i).get("车牌"))+".jpg" ;
			Path path2 = Paths.get(UPLOAD_FOLDER + relatedPath2);
			String relatedPath = datePath + "/"+IdWorker.getId()+"_"+String.valueOf(listMap.get(i).get("车牌"))+".jpg" ;
			Path path = Paths.get(UPLOAD_FOLDER + relatedPath);
			//如果没有files文件夹，创建
			if (!Files.isWritable(path2)) {
				try {
					Files.createDirectories(Paths.get(UPLOAD_FOLDER + datePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Wrapper<TaskPhoto> wrapper2=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
				wrapper2.eq("task_id", TaskId);
				wrapper2.eq("photo_type", 5);
				wrapper2.eq("state", 1);
				List<TaskPhoto> taskPhotos=taskPhotoService.selectList(wrapper2);
				if(taskPhotos.size()==0){
					Files.write(path2, image2);				
					long attachmentId = IdWorker.getId();
					long taskPhotoId = IdWorker.getId();
					Attachment attachment=new Attachment();
					attachment.setFdId(attachmentId);
					attachment.setRelatedId(taskPhotoId);
					attachment.setAttachType(1);
					attachment.setItemType(5);
					attachment.setRelatedPath(relatedPath);
					attachment.setExt("jpg");
					attachment.setSize(image2.length/1024);
					attachment.setCreateTime(new Date());
//					attachment.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));				
					attachmentService.insert(attachment);
					TaskPhoto taskPhoto=new TaskPhoto();
					taskPhoto.setFdId(taskPhotoId);
					taskPhoto.setPhotoId(attachmentId);
					taskPhoto.setTaskId(TaskId);
					taskPhoto.setMobile(task.getUserMobile());
					taskPhoto.setPhotoType(5);
					taskPhoto.setAddress(String.valueOf(listMap.get(i).get("过磅地点")));
					taskPhoto.setPhotoTime(new Date());
					taskPhoto.setCreateTime(new Date());
//					taskPhoto.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));
					taskPhotoService.insert(taskPhoto);
					Files.write(path, image);				
					long attachmentId1 = IdWorker.getId();
					long taskPhotoId1 = IdWorker.getId();
					Attachment attachment1=new Attachment();
					attachment1.setFdId(attachmentId1);
					attachment1.setRelatedId(taskPhotoId1);
					attachment1.setAttachType(1);
					attachment1.setItemType(5);
					attachment1.setRelatedPath(relatedPath2);
					attachment1.setExt("jpg");
					attachment1.setSize(image2.length/1024);
					attachment1.setCreateTime(new Date());
//					attachment1.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));				
					attachmentService.insert(attachment1);
					TaskPhoto taskPhoto1=new TaskPhoto();
					taskPhoto1.setFdId(taskPhotoId1);
					taskPhoto1.setPhotoId(attachmentId1);
					taskPhoto1.setTaskId(task.getFdId());
					taskPhoto1.setMobile(task.getUserMobile());
					taskPhoto1.setPhotoType(5);
					taskPhoto1.setAddress(String.valueOf(listMap.get(i).get("过磅地点")));
					taskPhoto1.setPhotoTime(new Date());
					taskPhoto1.setCreateTime(new Date());
//					taskPhoto1.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));
					taskPhotoService.insert(taskPhoto1);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}		 
		return listMap;
	}
	public String  getPhotoData(String plateNumber,String poundId,Long TaskId,String createTime,String guoband) {
		logMeno = "链接过磅图片数据";
		logger.info(logMeno+plateNumber);
		String sSql = "select top 1  *  from [dbo].[过磅车辆]  WHERE  [车牌]='"+plateNumber+"' and [特写图片] is not NULL and [全景图片] is not NULL "
				+ "and [创建时间] < '"+guoband+"' and [创建时间] > '"+createTime+"' and [图片] is not NULL order by id desc" ;
		logger.info(sSql);
		listMap.clear();
		listMap = queryData(plateNumber,poundId,sSql,TaskId);
		
		if (listMap.size() < 1) {
			logMeno = "未查询到同步的数据";
			logger.info(logMeno);
			return "同步完成";
		}

		logMeno = "过磅图片信息";
		logger.info(logMeno);
		return "过磅图片获取成功";
	}
	
	
	private List<Map<String, Object>> queryDataUpdate(String plateNumber,String poundId,String sql,Long TaskId) {

		String url = getjDBCConfig().getPurUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		String username = getjDBCConfig().getPurUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getPurPassword();// 你安装时选设置的密码
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			// 获取进口数据
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			con = DriverManager.getConnection(url, username, password);// 获取连接
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			ResultSetMetaData md = pre.getMetaData();
			int columnCount = md.getColumnCount();
			while (result.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), result.getObject(i));
				}
				listMap.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 System.err.println(listMap); //输出数据到控制台
		 if(listMap.size()==0){
			 Assert.throwException("找不到【"+plateNumber+"】车辆的过磅图片，请联系系统管理员");
		 }
		 for(int i=0;i<listMap.size();i++){
			Task task=taskService.selectById(TaskId);
			byte[] image=(byte[]) listMap.get(i).get("全景图片");
			byte[] image2=(byte[]) listMap.get(i).get("特写图片");
			String datePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String relatedPath2 = datePath + "/"+IdWorker.getId()+"_"+String.valueOf(listMap.get(i).get("车牌"))+".jpg" ;
			Path path2 = Paths.get(UPLOAD_FOLDER + relatedPath2);
			String relatedPath = datePath + "/"+IdWorker.getId()+"_"+String.valueOf(listMap.get(i).get("车牌"))+".jpg" ;
			Path path = Paths.get(UPLOAD_FOLDER + relatedPath);
			//如果没有files文件夹，创建
			if (!Files.isWritable(path2)) {
				try {
					Files.createDirectories(Paths.get(UPLOAD_FOLDER + datePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Wrapper<TaskPhoto> wrapper2=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
				wrapper2.eq("task_id", TaskId);
				wrapper2.eq("photo_type", 5);
				wrapper2.eq("state", 1);
				List<TaskPhoto> taskPhotos=taskPhotoService.selectList(wrapper2);
				for (TaskPhoto dto:taskPhotos) {
					dto.setState(2);
					taskPhotoService.updateById(dto);
				}
				Files.write(path2, image2);				
				long attachmentId = IdWorker.getId();
				long taskPhotoId = IdWorker.getId();
				Attachment attachment=new Attachment();
				attachment.setFdId(attachmentId);
				attachment.setRelatedId(taskPhotoId);
				attachment.setAttachType(1);
				attachment.setItemType(5);
				attachment.setRelatedPath(relatedPath);
				attachment.setExt("jpg");
				attachment.setSize(image2.length/1024);
				attachment.setCreateTime(new Date());
//					attachment.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));				
				attachmentService.insert(attachment);
				TaskPhoto taskPhoto=new TaskPhoto();
				taskPhoto.setFdId(taskPhotoId);
				taskPhoto.setPhotoId(attachmentId);
				taskPhoto.setTaskId(TaskId);
				taskPhoto.setMobile(task.getUserMobile());
				taskPhoto.setPhotoType(5);
				taskPhoto.setAddress(String.valueOf(listMap.get(i).get("过磅地点")));
				taskPhoto.setPhotoTime(new Date());
				taskPhoto.setCreateTime(new Date());
//					taskPhoto.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));
				taskPhotoService.insert(taskPhoto);
				Files.write(path, image);				
				long attachmentId1 = IdWorker.getId();
				long taskPhotoId1 = IdWorker.getId();
				Attachment attachment1=new Attachment();
				attachment1.setFdId(attachmentId1);
				attachment1.setRelatedId(taskPhotoId1);
				attachment1.setAttachType(1);
				attachment1.setItemType(5);
				attachment1.setRelatedPath(relatedPath2);
				attachment1.setExt("jpg");
				attachment1.setSize(image2.length/1024);
				attachment1.setCreateTime(new Date());
//					attachment1.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));				
				attachmentService.insert(attachment1);
				TaskPhoto taskPhoto1=new TaskPhoto();
				taskPhoto1.setFdId(taskPhotoId1);
					taskPhoto1.setPhotoId(attachmentId1);
					taskPhoto1.setTaskId(task.getFdId());
					taskPhoto1.setMobile(task.getUserMobile());
					taskPhoto1.setPhotoType(5);
					taskPhoto1.setAddress(String.valueOf(listMap.get(i).get("过磅地点")));
					taskPhoto1.setPhotoTime(new Date());
					taskPhoto1.setCreateTime(new Date());
//					taskPhoto1.setCreatorId(Long.valueOf(String.valueOf(UserUtils.getUserInfo().getFdId())));
					taskPhotoService.insert(taskPhoto1);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}		 
		return listMap;
	}
	public String  getPhotoDataUpdate(String plateNumber,String poundId,Long TaskId,String createTime,String guoband) {
		logMeno = "链接过磅图片数据";
		logger.info(logMeno+plateNumber);
		String sSql = "select top 1  *  from [dbo].[过磅车辆]  WHERE  [车牌]='"+plateNumber+"' and [特写图片] is not NULL and [全景图片] is not NULL "
				+ "and [创建时间] < '"+guoband+"' and [创建时间] > '"+createTime+"' and [图片] is not NULL order by id desc" ;
		listMap.clear();
		listMap = queryDataUpdate(plateNumber,poundId,sSql,TaskId);

		if (listMap.size() < 1) {
			logMeno = "未查询到同步的数据";
			logger.info(logMeno);
			return "同步完成";
		}

		logMeno = "过磅图片信息";
		logger.info(logMeno);
		return "过磅图片获取成功";
	}
	public String stringIsNotNull(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public BigDecimal bigDecimalIsNotNull(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}


}