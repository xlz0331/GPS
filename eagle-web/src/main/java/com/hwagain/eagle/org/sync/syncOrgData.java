package com.hwagain.eagle.org.sync;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.org.entity.OrgRelationship;
import com.hwagain.eagle.org.entity.PsCompanyTbl;
import com.hwagain.eagle.org.service.IOrgRelationshipService;
import com.hwagain.eagle.org.service.IPsCompanyTblService;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;


/**
 * <p>
 * 定时任务-同步组织机构信息-从PS系统
 * </p>
 *
 * @author guoym
 * @since 2018-05-07
 */


@Configurable
@EnableScheduling
@Component
public class syncOrgData {

	private final static Logger logger = LoggerFactory.getLogger(syncOrgData.class);

	protected static ExecutorService executorService = null;
	static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();// 本次要处理的列表

	protected Date doDate;
	protected String logType;
	protected String logMeno;
	protected static String logClassName = "syncOrgData";
	protected Boolean testControl = true; // 【 测试用】控制只执行一次
	@Autowired IOrgRelationshipService orgRelationshipService;
	@Autowired
	IPsCompanyTblService psCompanyTblService;
	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}
	@Scheduled(cron = "0 0 6 ? * *") // 每天6点执行	
//	@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void syncData() {

		doDate = new Date();

		logType = String.valueOf(IdWorker.getId());
		logMeno = "【开始】定时任务-同步组织机构信息-从PS系统";
		logger.info(logMeno);
	
//		relationshipInfoSync();
		psCompanyTblSync();

		logMeno = "【结束】定时任务-同步组织机构信息-从PS系统";
		
		logger.info(logMeno);
		System.out.println(logMeno);
	}

	public String relationshipInfoSync() {	

		logMeno = "查询PS系统-关系信息";
		
		logger.info(logMeno);
		System.out.println(logMeno);
		
		String sSql =    "	select * from sysadm.C_OA_RelationshipUsers2  ";
		listMap.clear();
		listMap = PublicSyncDbUtil.queryPsData(sSql,true);

		if (null==listMap || listMap.size() < 1) {
			logMeno = "未查询到同步的数据【关系信息】";
			logger.info(logMeno);
//			sysLogService.AddSysLog(logType, logClassName, null, logMeno);
			System.out.println(logMeno);
			return logMeno;
		}
		
		// 全部删除
		Wrapper<OrgRelationship> wrapperDept = new CriterionWrapper<OrgRelationship>(OrgRelationship.class);
		orgRelationshipService.delete(wrapperDept);	

		for (int i = 0; i < listMap.size(); i++) {
			relationshipInfoMap(listMap.get(i));
		}
		
		logMeno = "同步PS系统【关系信息】成功";
		logger.info(logMeno);
//		sysLogService.AddSysLog(logType, logClassName, null, logMeno);
		System.out.println(logMeno);
		
		return logMeno;

	}

	private boolean relationshipInfoMap(Map<String, Object> obj) {

		OrgRelationship saveObj = new OrgRelationship();
		saveObj.setFdId(String.valueOf(IdWorker.getId()));
		saveObj.setRelaType(notNullString(obj.get("RELATYPE")));
		saveObj.setUserID(notNullString(obj.get("USERID")));
		saveObj.setUserName(notNullString(obj.get("USERNAME")));
		saveObj.setDeptID(notNullString(obj.get("DEPTID")));
		saveObj.setDeptIDPS(notNullString(obj.get("DEPTID_PS")));
		saveObj.setPosiID(notNullString(obj.get("POSIID")));
		saveObj.setPosiName(notNullString(obj.get("POSINAME")));
		saveObj.setStatID(notNullBigDecimal(obj.get("STATID")).intValue());
		saveObj.setStatName(notNullString(obj.get("STATNAME")));
		
		saveObj.setDeptLeader(notNullBigDecimal(obj.get("DEPTLEADER")).intValue());
		saveObj.setUserOrder(notNullBigDecimal(obj.get("USERORDER")).intValue());
		saveObj.setRelaPrimary(notNullBigDecimal(obj.get("RELAPRIMARY")).intValue());
		saveObj.setEmplRcd(notNullBigDecimal(obj.get("EMPL_RCD")).intValue());
		saveObj.setEffdt(notNullDate(obj.get("EFFDT")));
		saveObj.setJobIndicator(notNullString(obj.get("JOB_INDICATOR")));
		saveObj.setPositionNbr(notNullString(obj.get("POSITION_NBR")));
		saveObj.setSupvLvlId(notNullString(obj.get("SUPV_LVL_ID")));
		saveObj.setSupvLvlDescr(notNullString(obj.get("SUPV_LVL_DESCR")));
		saveObj.setHrStatus(notNullString(obj.get("HR_STATUS")));
		
		saveObj.setDeptIDL1(notNullString(obj.get("DeptID_L1")));
		saveObj.setDeptIDL2(notNullString(obj.get("DeptID_L2")));
		saveObj.setDeptIDL3(notNullString(obj.get("DeptID_L3")));
		saveObj.setDeptIDL4(notNullString(obj.get("DeptID_L4")));
		saveObj.setDeptIDL5(notNullString(obj.get("DeptID_L5")));
		saveObj.setDeptIDL6(notNullString(obj.get("DeptID_L6")));		
		saveObj.setCompanyName(notNullString(obj.get("COMPANYNAME")));
		saveObj.setDeptname(notNullString(obj.get("DEPTNAME")));

		orgRelationshipService.insert(saveObj);

		return true;
	}
	public String psCompanyTblSync() {

		//按EFFDT升序排序-确保后生效的记录覆盖已经失效的记录
		String sSql = " select COMPANY,EFFDT,EFF_STATUS,DESCR,DESCRSHORT,ADDRESS1 from SYSADM.Ps_Company_Tbl order by EFFDT ";
		listMap.clear();
		listMap = PublicSyncDbUtil.queryPsData(sSql,true);
		
		if (null==listMap || listMap.isEmpty() || listMap.size() < 1) {
			logMeno = "未查询到同步的数据【公司记录】";
			logger.info(logMeno);
			
			return logMeno;
		}

		// 全部逻辑删除
		Wrapper<PsCompanyTbl> wrapper = new CriterionWrapper<PsCompanyTbl>(PsCompanyTbl.class);
		List<PsCompanyTbl> list = psCompanyTblService.selectList(wrapper);
		for( PsCompanyTbl del : list)
		{
			del.setIsDelete(1);
			psCompanyTblService.updateById(del);
		}
		//psCompanyTblService.delete(wrapperDelete);
		
		Boolean bNew=false;
		for (int i = 0; i < listMap.size(); i++) {
			Map<String, Object> d = listMap.get(i);
			bNew = false;

			String company = notNullString(d.get("COMPANY"));
			wrapper = new CriterionWrapper<PsCompanyTbl>(PsCompanyTbl.class);
			wrapper.eq("company", company);
			PsCompanyTbl n = psCompanyTblService.selectFirst(wrapper);
			if (null == n) {
				bNew = true;
				n = new PsCompanyTbl();
				n.setFdId(String.valueOf(IdWorker.getId()));
			}

			n.setCompany(company);
			n.setEffDate(notNullDate(d.get("EFFDT")));
			n.setEffStatus(notNullString(d.get("EFF_STATUS")));
			n.setDescr(notNullString(d.get("DESCR")));
			n.setDescrShort(notNullString(d.get("DESCRSHORT")));
			n.setAddress1(notNullString(d.get("ADDRESS1")));
			n.setIsDelete(0);
			
//			OrgDepartmentDto dept =orgDepartmentService.findOneByDeptName(n.getDescrShort());
//			if(null!=dept)
//				n.setCompanyId(dept.getDeptId());

			if (bNew)
				psCompanyTblService.insert(n);
			else
				psCompanyTblService.updateAllById(n);
		}
	 
		return logMeno;
	}

	public String notNullString(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public Integer notNullInteger(Object obj) {
		return obj != null ? (Integer) obj : 0;
	}
	
	public BigDecimal notNullBigDecimal(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}

	public Date notNullDate(Object obj) {
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
