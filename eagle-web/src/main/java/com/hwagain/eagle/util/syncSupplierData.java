package com.hwagain.eagle.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;

/**
 * <p>
 * 定时任务-PS基础数据
 * </p>
 *
 * @author guoym
 * @since 2018-05-07
 */

@Configurable
@EnableScheduling
@Component
public class syncSupplierData {

	private final static Logger logger = LoggerFactory.getLogger(syncSupplierData.class);

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
	@Autowired
	JDBCConfig jDBCConfig;
	
//	@Scheduled(cron = "0 0 0/1 * * ?") // 每小时执行一次
//	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
//	@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void Discount() {

		//if (!testControl) return; // 【 测试用】
		//testControl = false;// 【 测试用】

		doDate = new Date();
		getPsBaseData();

	}

	public JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}

	// 查询OA数据库数据
	private List<Map<String, Object>> queryOaData(String sql) {

		String url = getjDBCConfig().getHJPMUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		String username = getjDBCConfig().getHJPMUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getHJPMPassword();// 你安装时选设置的密码

		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			// 获取进口数据
			Class.forName("net.sourceforge.jtds.jdbc.Driver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			con = DriverManager.getConnection(url, username, password);// 获取连接
			
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			System.err.println(result);
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
		 for(int i=0;i<listMap.size();i++){
			 SupplierInfoDto dto=new SupplierInfoDto();
			 dto.setMobile(String.valueOf(listMap.get(i).get("c_ghrTel")));
			 dto.setName(String.valueOf(listMap.get(i).get("c_ghName")));
			 supplierInfoService.addOne(dto);
		 }
		return listMap;
	}


	
	// 获取PS系统职位信息
	public String  getPsBaseData() {
		logMeno = "链接合同数据库查询供应商基础数据";
		logger.info(logMeno);

		String sSql = "select DISTINCT c_ghName ,c_ghrTel  from HJ_TBContract C inner join HJ_TBContractList L on C.c_cntid=L.l_htid where c_ghName is not null and c_cntId LIKE 'XJ%' and l_htetime is not null";
//		String sSql="select * from HJ_TBContract";
		listMap.clear();
		listMap = queryOaData(sSql);

		if (listMap.size() < 1) {
			logMeno = "未查询到同步的数据";
			logger.info(logMeno);
			return "同步完成";
		}

		logMeno = "供应商信息";
		logger.info(logMeno);
		return "同步完成";

		// 全部删除【PS系统】的记录
//		Wrapper<RegBaseData> wrapperBase = new CriterionWrapper<RegBaseData>(RegBaseData.class);
//		wrapperBase.eq("datafrom", "PS系统");
//		regBaseDataService.delete(wrapperBase);

//		doPsBaseInfo();

	}

	

	public String stringIsNotNull(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public BigDecimal bigDecimalIsNotNull(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}


}