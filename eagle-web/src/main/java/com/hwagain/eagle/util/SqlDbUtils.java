package com.hwagain.eagle.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.security.common.util.UserUtils;


public class SqlDbUtils {

	private static final Logger logger = LoggerFactory.getLogger(SqlDbUtils.class);

	static JDBCConfig jDBCConfig;

	public static JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}

	// 新增OA流程记录
	public static Integer sentOaFlow(String temId, String recordId, String title, String toUserId, String toUsername,
			String memo) {
		Integer iresult = 1;

		String url = getjDBCConfig().getOAUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		String username = getjDBCConfig().getOAUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getOAPassword();// 你安装时选设置的密码

		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载sql驱动程序

			con = DriverManager.getConnection(url, username, password);// 获取连接

			String sql = " INSERT INTO td_AppFromOtherSystem (temId,recordId,title,toUserId,toUsername,appstatus,apptime,triggerMan,memo)  "
					+ "  VALUES('" + temId + "','" + recordId + "','" + title + "','" + toUserId + "','" + toUsername
					+ "',0,GETDATE(),'华劲鹰眼','" + memo + "')  ";

			pre = con.prepareStatement(sql);// 实例化预编译语句
			// result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			pre.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			iresult = 0;
		} finally {
			try {
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return iresult;
	}


}

