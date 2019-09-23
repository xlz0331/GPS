package com.hwagain.eagle.org.sync;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;

public class PublicSyncDbUtil {
	// 查询PS数据库数据
	public static List<Map<String, Object>> queryPsData(String sql,Boolean isNeedFromHCPRD) {
		IDictService dictService=null;
		if (dictService == null) {
			dictService = SpringBeanUtil.getBean("dictService");
		}
		Wrapper<Dict> wrapper=new CriterionWrapper<Dict>(Dict.class);
		wrapper.eq("type_name", "system_psdb");
		wrapper.eq("is_delete", 0);
		List<Dict> list=dictService.selectList(wrapper);
		String sUrl = null;
		String user = null;
		String password = null;
		if(list.size()==0){
			Assert.throwException("找不到数据库配置");
		}else {
			for(Dict dict:list){
				if(dict.getItemName().equals("psdb_url")){
					sUrl=dict.getGroupName();
					System.err.println(sUrl);
				}
				if(dict.getItemName().equals("psdb_username")){
					user=dict.getGroupName();
				}
				if(dict.getItemName().equals("psdb_password")){
					password=dict.getGroupName();
				}
			}
		}
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序			
			//192.168.68.123:1521:HCPRD
			//jdbc:oracle:thin:@192.168.68.102:1521:HCUAT
			String url = "jdbc:oracle:thin:@" + sUrl;
			con = DriverManager.getConnection(url, user, password);// 获取连接

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
			Assert.throwException("数据库查询出错："+e.getMessage());
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
			} catch (Exception e) {
				e.printStackTrace();
				Assert.throwException("关闭数据库出错："+e.getMessage());
			}
		}
		// System.err.println(listMap); //输出数据到控制台

		return listMap;
	}

	
	public static String notNullString(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public static Integer notNullInteger(Object obj) {
		return obj != null ? (Integer) obj : 0;
	}
		
	public static BigDecimal notNullBigDecimal(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}
	
	public static BigDecimal string2BigDecimal(Object obj) {
		if(null==obj)
			return BigDecimal.valueOf(0L);
		else
			return BigDecimal.valueOf(Long.valueOf((String) obj));
	}

	public static Date notNullDate(Object obj) {
		if (null == obj)
			return null;
		try {
			// DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			return (Date) obj;
			// Date d1 = format1.parse(s1);
			// return d1;
		} catch (Exception e) {

		}
		return null;
	}

}
