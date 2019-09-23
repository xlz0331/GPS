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
import com.hwagain.eagle.base.entity.Material;
import com.hwagain.eagle.base.service.IMaterialService;
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
public class syncYuanLZhu {

	private final static Logger logger = LoggerFactory.getLogger(syncYuanLZhu.class);

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
	@Autowired IMaterialService materialService;
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

	// 查询OA数据库数据
	private List<Map<String, Object>> queryData(String poundId,String sql,Long TaskId) {

		String url = getjDBCConfig().getYLZUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		System.err.println(url);
		String username = getjDBCConfig().getYLZUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getYLZPassword();// 你安装时选设置的密码
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			// 获取进口数据
			Class.forName("net.sourceforge.jtds.jdbc.Driver");// 加载Oracle驱动程序
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
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
			 System.err.println("找不到磅单号【"+poundId+"】任务");
		 }
		 for(int i=0;i<listMap.size();i++){
			Task task=taskService.selectById(TaskId);
			if(task!=null){
				String material=String.valueOf(listMap.get(i).get("zhumc"));
				
				BigDecimal weightTons=new BigDecimal(Float.valueOf(String.valueOf(listMap.get(i).get("jingzhong"))));
				System.err.println(weightTons);
				Wrapper<Material> wrapper=new CriterionWrapper<Material>(Material.class);
				wrapper.eq("name", material);
				wrapper.eq("state", 1);
				Material mater=materialService.selectFirst(wrapper); 
				if(mater!=null){
					System.err.println(mater.getCode());
					task.setMaterial(mater.getCode());
				}
				
				task.setWeightTons(weightTons);
				taskService.updateById(task);
			}
			}		 
		return listMap;
	}	
	
	public String  getYLZData(String poundId,Long TaskId) {
		System.err.println(poundId);
		logMeno = "链接合原料运费补助库查询磅单信息";
		logger.info(logMeno);
		String sSql="select cheh,zhumc,jingzhong,cyrMC,gzsMC from [dbo].[bdinf] where id='"+poundId+"'";
		listMap.clear();
		listMap = queryData(poundId,sSql,TaskId);

		if (listMap.size() < 1) {
			logMeno = "未查询磅单号为【'"+poundId+"'】数据";
//			logger.info(logMeno);
			System.err.println(logMeno);
			return "同步完成";
		}

		logMeno = "磅单信息";
		logger.info(logMeno);
		return "获取成功";
	}
	
	
	public List<Map<String, Object>>  getYLZDatas(String poundId) {
		System.err.println(poundId);
		logMeno = "链接合原料运费补助库查询磅单数据";
		logger.info(logMeno);
//		String sSql = "select top 1  *  from [dbo].[过磅车辆]  WHERE  [车牌]='"+plateNumber+"' and [特写图片] is not NULL and [全景图片] is not NULL and [图片] is not NULL order by id desc" ;
		String sSql="select cheh,zhumc,jingzhong,cyrMC,gzsMC,PhotoR,RouteResult,guoBtime,SiBr from [dbo].[bdinf] where id='"+poundId+"'";
		listMap.clear();
		listMap = queryDatas(poundId,sSql);

		if (listMap.size() < 1) {
			logMeno = "未查询磅单号为【'"+poundId+"'】数据";
//			logger.info(logMeno);
			System.err.println(logMeno);
			return listMap;
		}

		logMeno = "磅单数据";
		logger.info(logMeno);
		logger.info(String.valueOf(listMap.size()));
		return listMap;
	}
	private List<Map<String, Object>> queryDatas(String poundId,String sql) {

		String url = getjDBCConfig().getYLZUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		System.err.println(url);
		String username = getjDBCConfig().getYLZUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getYLZPassword();// 你安装时选设置的密码
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			// 获取进口数据
			Class.forName("net.sourceforge.jtds.jdbc.Driver");// 加载Oracle驱动程序
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
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
				logger.info(String.valueOf(listMap.size()));
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
			 System.err.println("找不到磅单号【"+poundId+"】任务");
		 }	 
		return listMap;
	}
	
	
	public List<Map<String, Object>>  getHTZDatas(String sSql) {
		logMeno = "链接合原料运费补助库查询合同信息";
		logger.info(logMeno);
//		sSql="SELECT * FROM [dbo].[t_Contract] WHERE ContractNO LIKE 'XJ%' and EndDate>getdate() ORDER BY CheckTime desc";
//		sSql="EXECUTE splitCitys @citys='广东省广州市'";
		listMap.clear();
		listMap = queryHTDatas(sSql);

		if (listMap.size() < 1) {
			logMeno = "未查询到合同数据";
//			logger.info(logMeno);
			System.err.println(logMeno);
			return listMap;
		}

		logMeno = "合同数据";
		logger.info(logMeno);
		logger.info(String.valueOf(listMap.size()));
		return listMap;
	}
	private List<Map<String, Object>> queryHTDatas(String sql) {

		String url = getjDBCConfig().getYLZUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		System.err.println(url);
		String username = getjDBCConfig().getYLZUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getYLZPassword();// 你安装时选设置的密码
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		
		try {
			// 获取进口数据
			Class.forName("net.sourceforge.jtds.jdbc.Driver");// 加载Oracle驱动程序
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
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
				logger.info(String.valueOf(listMap.size()));
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
			 System.err.println("找不到合同数据");
		 }	 
		return listMap;
	}
	public String stringIsNotNull(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public BigDecimal bigDecimalIsNotNull(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}


}