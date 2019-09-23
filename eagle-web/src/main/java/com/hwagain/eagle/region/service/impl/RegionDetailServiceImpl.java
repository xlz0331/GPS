package com.hwagain.eagle.region.service.impl;

import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.region.entity.RegionDetail;
import com.hwagain.eagle.region.entity.RegionOa;
import com.hwagain.eagle.region.entity.RegionSupplier;
import com.hwagain.eagle.region.entity.TaskRoute;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hwagain.eagle.base.dto.OaAduitDetailDto;
import com.hwagain.eagle.base.entity.Dict;
import com.hwagain.eagle.base.entity.OaAduit;
import com.hwagain.eagle.base.entity.OaAduitDetail;
import com.hwagain.eagle.base.service.IDictService;
import com.hwagain.eagle.base.service.IOaAduitDetailService;
import com.hwagain.eagle.base.service.IOaAduitService;
import com.hwagain.eagle.region.dto.RegionDetailDto;
import com.hwagain.eagle.region.mapper.RegionDetailMapper;
import com.hwagain.eagle.region.service.IRegionDetailService;
import com.hwagain.eagle.region.service.IRegionOaService;
import com.hwagain.eagle.region.service.IRegionService;
import com.hwagain.eagle.region.service.IRegionSupplierService;
import com.hwagain.eagle.region.service.ITaskRouteService;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.task.entity.Task;
import com.hwagain.eagle.task.entity.TaskPhoto;
import com.hwagain.eagle.task.service.ITaskPhotoService;
import com.hwagain.eagle.task.service.ITaskService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.SqlDbUtils;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.redis.RedisUtil;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.util.PageDto;
import com.sun.mail.imap.protocol.BODY;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oracle.net.aso.o;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xionglz
 * @since 2019-04-13
 */
@Service("regionDetailService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Slf4j
public class RegionDetailServiceImpl extends ServiceImpl<RegionDetailMapper, RegionDetail> implements IRegionDetailService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Autowired BaseContextHandler baseContextHandler;
	@Autowired IDictService dictService;
	@Autowired IRegionService regionService;
	@Autowired IRegionOaService regionOaService;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired IOaAduitService oaAduitService;
	@Autowired IOaAduitDetailService oaAduitDetailService;
	@Autowired RegionDetailMapper regionDetailMapper;
	@Autowired IRegionSupplierService regionSupplierSevice;
	@Autowired ITaskService taskService;
	@Autowired ITaskPhotoService taskPhotoService;
	@Value("${sys.config.url.baiduApi}")
	private String baiduApi;
	@Value("${sys.config.url.ak}")
	private String ak;
	@Value("${sys.config.url.baiduRoadWay}")
	private String baiduRoadWay;
	@Autowired RedisUtil redisUtil;
	
	@Autowired ITaskRouteService taskRouteService;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegionDetail.class, RegionDetailDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegionDetailDto.class, RegionDetail.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Override
	public List<RegionDetailDto> getRegionDetailBySupplierId(){
		Long supplierId=Long.valueOf(String.valueOf(baseContextHandler.getSupplierId()));
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("supplier_id", supplierId);
		wrapper.eq("state", 1);
		List<RegionDetail> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegionDetailDto.class);		
	}
	@Override
	public RegionDetail findById(Long fdId){
		
		return super.selectById(fdId);
		
	}
	@Override
	public List<RegionDetailDto> findAll(String supplierName,String regionName){
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("supplier_name", supplierName);
//		wrapper.eq("car_type", CarType);
		wrapper.like("region_name", regionName, SqlLike.DEFAULT);
		wrapper.eq("state", 1);
		List<RegionDetail> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegionDetailDto.class);
	}
	@Override
	public RegionDetailDto addOne(RegionDetailDto dto){
//		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
//		String creatorName=UserUtils.getUserInfo().getName();
		
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("supplier_name", dto.getSupplierName());
		wrapper.eq("address", dto.getAddress());
		wrapper.eq("state", 1);
		RegionDetail list=super.selectFirst(wrapper);
		if(list!=null){
			dto.setFdId(list.getFdId());
			dto.setLastAlterTime(new Date());
//			dto.setLastAltorId(creatorId);
			super.updateById(dtoToEntityMapper.map(dto, RegionDetail.class));
		}else{
			dto.setFdId(Long.valueOf(IdWorker.getId()));
			dto.setCreateTime(new Date());
//			dto.setCreatorId(creatorId);
			super.insert(dtoToEntityMapper.map(dto, RegionDetail.class));
		}
		return dto;
	}
	
	@Override
	public String deleteByIds(String ids){
		if(ids.isEmpty()){
			Assert.throwException("未选中要删除的行");
		}
		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
		String[] id = ids.split(";");
		for(String fdId:id){
			RegionDetail car=super.selectById(fdId);
			car.setState(2);
			car.setLastAltorId(creatorId);
			car.setLastAlterTime(new Date());
			super.updateById(car);
		}
		return ids;
		
	}
	
	//导入文本文件
	@Override
	public String InsertRegionDeatilData(InputStream inputStream){
//		Long creatorId=Long.parseLong(UserUtils.getUserInfo().getFdId());
//		Date doDate = new Date(); 
//		String cUserid = UserUtils.getUserInfo().getName();
//			File file = new File(path);
//			if(file.exists()){
//				System.err.println("file exists");
//			}else{
//				System.out.println("file not exists, create it ...");
//	            try {
//	               file.createNewFile();
//	            } catch (IOException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            }
//			}
		InputStreamReader inputStreamReader = null ;
		ArrayList<String> arrayList = new ArrayList<>();
		try {
			inputStreamReader = new InputStreamReader( inputStream,"GBK" ) ; //创建转换输入流
			BufferedReader bw = new BufferedReader(inputStreamReader);
			String line = null;
			int i=0;
			while((line = bw.readLine()) != null){
				arrayList.add(line);				
				String[] arr = arrayList.get(i).split(","); // 用,分割
				i++;
//				Wrapper<Region> wraRegion = new CriterionWrapper<Region>(Region.class);
//				wraRegion.eq("state", 1);
//				wraRegion.eq("supplier_name", arr[1]);
//				wraRegion.eq("address", arr[5]);
//				Region region=regionService.selectFirst(wraRegion);
//				Region regiona=new Region();
//				regiona.setFdId(Long.valueOf(arr[0]));
//				regiona.setSupplierName(arr[1]);
//				regiona.setProvince(arr[2]);
//				regiona.setCity(arr[3]);
//				regiona.setRegionName(arr[4]);
//				Float distance=Float.valueOf(arr[5]);
//				regiona.setRegionDistance(distance);
//				regiona.setMaterial(arr[5]);
//				regiona.setSubsidy(Float.valueOf(arr[4]));
//				regiona.setEffdt(Long.valueOf(arr[7]));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				try {
//					Date cDate=sdf.parse(arr[8]);
//					regiona.setCreateTime(cDate);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				regionService.insert(regiona);
				
//				RegionOa regiona=new RegionOa();
//				regiona.setFdId(Long.valueOf(arr[0]));
//				regiona.setSupplierName(arr[1]);
//				regiona.setProvince(arr[2]);
//				regiona.setCity(arr[3]);
////				regiona.setRegionName(arr[4]);
////				Float distance=Float.valueOf(arr[5]);
////				regiona.setRegionDistance(distance);
//				regiona.setMaterial(arr[5]);
//				regiona.setSubsidy(Float.valueOf(arr[4]));
//				regiona.setSubsidy2(Float.valueOf(arr[4]));
//				regiona.setEffdt(Long.valueOf(arr[7]));
////				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////				try {
////					Date cDate=sdf.parse(arr[8]);
////					regiona.setCreateTime(cDate);
////				} catch (ParseException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//				regionOaService.insert(regiona);
				RegionSupplier regiona=new RegionSupplier();
				regiona.setFdId(IdWorker.getId());
				regiona.setSupplierName(arr[0]);
				regiona.setProvince(arr[1]);
				regiona.setCity(arr[2]);
				System.err.println(arr[3]);
				regiona.setRegionName(arr[3]);
//				Float distance=Float.valueOf(arr[6]);
//				regiona.setRegionDistance(distance);
				regiona.setMaterial("刨板片");
				regiona.setSubsidy(Float.valueOf(arr[4]));
//				regiona.setEffdt(Long.valueOf(arr[7]));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				try {
//					Date cDate=sdf.parse(arr[8]);
//					regiona.setCreateTime(cDate);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				regionSupplierSevice.insert(regiona);
				
//				Wrapper<RegionDetail> wrapper = new CriterionWrapper<RegionDetail>(RegionDetail.class);
//				wrapper.eq("state", 1);
//				wrapper.eq("supplier_name", arr[1]);
//				wrapper.eq("address", arr[5]);
//				List<RegionDetail> list = super.selectList(wrapper);
//				if(list.size()==0){
//					RegionDetail region=new RegionDetail();
//					region.setFdId(Long.valueOf(arr[0]));
//					region.setSupplierName(arr[1]);
//					region.setProvince(arr[2]);
//					region.setRegionName(arr[3]);
//					region.setMaterial(arr[4]);
//					region.setAddress(arr[5]);
//					double lng=Double.valueOf(arr[6]);
//					region.setLongitude(lng);
//					double lat=Double.valueOf(arr[7]);
//					region.setLatitude(lat);
//					Float distance=Float.valueOf(arr[8]);
//					region.setRegionDistance(BigDecimal.valueOf(distance));
//					BigDecimal subsidy = BigDecimal.valueOf(Integer.valueOf(arr[9]));
//					region.setSubsidy1(subsidy);
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					try {
//						Date cDate=sdf.parse(arr[10]);
//						region.setCreateTime(cDate);
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
////					region.setCreatorId(creatorId);
//					super.insert(region);
					System.out.println(arrayList);
				}				
//			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("load data success!");
		return "读取成功";
	}
		
	//收购点经纬度
	@Override
	public List<RegionDetailDto> queryPurchaseGps(String address){
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("state", 10);
		wrapper.eq("region_name", address);
		List<RegionDetail> list=super.selectList(wrapper);
		Wrapper<Dict> wraDict=new CriterionWrapper<Dict>(Dict.class);
		wraDict.eq("is_delete", 0);
		wraDict.eq("type_name", "收购范围允许误差");
		Dict dict=dictService.selectFirst(wraDict);
		if(dict==null){
			Assert.throwException("找不到供货范围允许误差配置");
		}
		if(list.size()==0){
			Assert.throwException("找不到收购点");
		}else{
			for(RegionDetail regionDetail:list){
				regionDetail.setRegionDistance(BigDecimal.valueOf(dict.getItemNo()));
			}
		}
		return entityToDtoMapper.mapAsList(list, RegionDetailDto.class);			
	}
		
	@Override
	public PageDto<RegionDetailDto> findByPage(int pageNum, int pageSize,String supplierName,String regionName) throws CustomException {
		
		PageDto<RegionDetailDto> pageDto = new PageDto<RegionDetailDto>();
		pageDto.setPage(pageNum + 1);
		pageDto.setPageSize(pageSize);
		Wrapper<RegionDetail> wrapper = new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("state", 1);
		wrapper.like("supplier_name", supplierName, SqlLike.DEFAULT);
		wrapper.like("region_name", regionName, SqlLike.DEFAULT);
		Page<RegionDetail> page = super.selectPage(new Page<RegionDetail>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegionDetailDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	@Override
	public List<RegionDetailDto> findBySupplier(String supplierName,String regionName) throws CustomException {
		Wrapper<RegionDetail> wrapper = new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("state", 1);
		wrapper.like("supplier_name", supplierName, SqlLike.DEFAULT);
		wrapper.like("region_name", regionName, SqlLike.DEFAULT);
		wrapper.orderBy("province");
		wrapper.orderBy("region_name");
		List<RegionDetail> list=super.selectList(wrapper);
		
		return entityToDtoMapper.mapAsList(list, RegionDetailDto.class);
	}
	@Override
	public RegionDetailDto findOne(Long fdId){
		RegionDetail regionDetail=super.selectById(fdId);
		return entityToDtoMapper.map(regionDetail, RegionDetailDto.class);
		
	}
	private void validate(RegionDetailDto dto){
		Assert.notBlank(dto.getProvince(), "省份不能为空");
		Assert.notBlank(dto.getRegionName(), "市不能为空");
		Assert.notBlank(dto.getPurchasePoint(), "报备地点名称不能为空");
		Assert.notBlank(dto.getAddress(), "详细地址不能为空");
//		Assert.notNull(dto.getRegionDistance(), "距离不能为空");
		Assert.notNull(dto.getLatitude(), "纬度不能为空");
		Assert.notNull(dto.getLongitude(), "经度不能为空");
	}
	//新增供货点
	@Override
	public String addOneDetailRegion(RegionDetail entity){
		
		char[] cc=entity.getProvince().toCharArray();
		String proStr="";
		if(cc.length>1){
			proStr=String.valueOf(cc[0])+cc[1];		
		}	
		log.info(proStr+entity.getRegionName()+entity.getCounty()+entity.getAddress());
		validate(entityToDtoMapper.map(entity, RegionDetailDto.class));
		if(entity.getAuditor1()==null){
			entity.setAuditor1("赣州");
		}
		List<RegionDetailDto> det=queryPurchaseGps(entity.getAuditor1());
		if(det.size()==0){
			Assert.throwException("找不到收购点配置");
		}
		RegionDetailDto distance=det.get(0);
		String origins=String.valueOf(entity.getLatitude())+","+String.valueOf(entity.getLongitude());
		String destinations=String.valueOf(distance.getLatitude())+","+String.valueOf(distance.getLongitude());
		String path=baiduApi+"?output=json&origins="+origins+"&destinations="+destinations+"&ak="+ak;
		try {
			System.err.println(path);
			BigDecimal dis =baiduDistance(path);
			entity.setRegionDistance(dis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date doDate=new Date();
		String loginName=String.valueOf(baseContextHandler.getUsername());
		Long userId=Long.valueOf(String.valueOf(baseContextHandler.getUserID()));
		String mobile=String.valueOf(baseContextHandler.getMobile());
		System.err.println("login"+loginName);
		validate(entityToDtoMapper.map(entity, RegionDetailDto.class));
		String [] stra={"0","1"};
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("province",proStr);
		wrapper.eq("region_name",entity.getRegionName());
//		wrapper.eq("purchase_point", entity.getPurchasePoint());
		wrapper.eq("address", proStr+entity.getRegionName()+entity.getCounty()+entity.getAddress());
//		wrapper.eq("latitude", entity.getLatitude());
//		wrapper.eq("longitude", entity.getLongitude());
		wrapper.in("state", stra);
		RegionDetail regionDetail=super.selectFirst(wrapper);
		String str = "";
		if(regionDetail!=null){
			Assert.throwException("该供应点已存在");
		}else{
			List<Dict> dicts=dictService.findBytypeName("供货点审批人");
			if(dicts.size()==0){
				Assert.throwException("找不到供货点审批人配置信息");
			}
			
			
			Wrapper<Region> wraRegion=new CriterionWrapper<Region>(Region.class);		
			if(cc.length>1){
				str=String.valueOf(cc[0])+cc[1];
				System.err.println(str);
				wraRegion.eq("province", str);				
			}			
			if(!str.equals("海南")){
				wraRegion.eq("city", entity.getRegionName());
			}
			wraRegion.eq("state", 1);
			wraRegion.eq("material", entity.getMaterial());
			if(str.equals("福建")&&entity.getRegionName().equals("龙岩市")&&entity.getMaterial().equals("刨板片")){
				wraRegion.eq("region_name", entity.getCounty());
			}
			Region region=regionService.selectOne(wraRegion);
			log.info(str+"/"+entity.getRegionName()+"/"+loginName+"/"+entity.getMaterial()+"/"+entity.getCounty());
//			if(region==null){
//				System.err.println("你没有在当前区域的供货权限，无法报备供货点");
//				Assert.throwException("你没有在当前区域的供货权限，无法报备供货点");
//			}else{
//				for(Region region2:region){
					String oaCode = String.valueOf(IdWorker.getId());
					SupplierInfoDto supplierInfo=supplierInfoService.findByName(loginName);
					entity.setFdId(Long.valueOf(IdWorker.getId()));
//					entity.setMaterial(region2.getMaterial());
					entity.setSupplierName(loginName);
					entity.setAuditor1(null);
					if(supplierInfo!=null){
						entity.setSupplierId(supplierInfo.getFdId());
					}
					if(region!=null){
						entity.setSubsidy1(new BigDecimal(region.getSubsidy()));
					}else{
						entity.setSubsidy1(new BigDecimal("0"));
					}
					entity.setOaCode(oaCode);
					entity.setState(0);
					entity.setMobile(mobile);
					entity.setProvince(str);
//					entity.setMaterial(region2.getMaterial());
					entity.setCreateTime(new Date());
					entity.setCreatorId(userId);
					entity.setAddress(str+entity.getRegionName()+entity.getCounty()+entity.getAddress());
					super.insert(entity);
					
					String flowName="供应商收购地点报备申请表";
					String platform="regionDetail";
					Dict dic = dictService.findOaTemId(flowName, platform);
					Assert.notNull(dic, "没有找到[" + flowName + "]的OA流程配置信息");
					Assert.notBlank(dic.getCode(), "没有找到[" + flowName + "]的OA流程配置信息");
					String temid = dic.getCode();
					String title = dic.getText();
					
					String userEmpNo =dicts.get(0).getGroupName();
					String userName = dicts.get(0).getItemName();
					Integer iresult = SqlDbUtils.sentOaFlow(temid, oaCode, title, userEmpNo, userName, platform + ";" + flowName);
					Assert.isTrue(iresult == 1, "提交OA失败");
					OaAduit oa = new OaAduit();
					oa.setFdId(IdWorker.getId());
					oa.setOaCode(oaCode);
					oa.setPlatform(platform);
					oa.setTableName("region_detail");
					oa.setFlowName(flowName);
					oa.setNodeName("提交OA");
					oa.setEmpName(userName);
					oa.setEmpNo(userEmpNo);
					oa.setStatus(0);
					oa.setCreaterId(String.valueOf(userId));
					oa.setCreateTime(doDate);
					oaAduitService.insert(oa);
					OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
					OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
					oaDetail.setFdId(IdWorker.getId());
					oaDetail.setRecFdId(entity.getFdId());
					oaAduitDetailService.insert(oaDetail);
//				}				
//			}		
		}
		return "新增成功";		
	}
	
	@Override
	public RegionDetailDto findRegionDetailByOaCode(String oaCode){
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);		
		wrapper.eq("oa_code", oaCode);
		RegionDetail regionDetail=super.selectFirst(wrapper);
		return entityToDtoMapper.map(regionDetail, RegionDetailDto.class);		
	}
	
	@Override
	public String audit(Integer state,String oaCode,String node,String empName,String empNo,String flowDjbh,String flowDjlsh){
		Assert.isTrue(state==1 || state==2,"状态无效（只能是1或2）");
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("oa_code", oaCode);
		List<RegionDetail> regionDetailList=super.selectList(wrapper);
		Assert.notNull(regionDetailList, "没有找到oACode对应的运费补助补正记录");
		Assert.isTrue(!regionDetailList.isEmpty(), "没有找到oACode对应的运费补助补正记录");
		for(RegionDetail regionDetail:regionDetailList){
			if(node.endsWith("1")){
				if(regionDetail.getAudit1()!=0){
					Assert.throwException("部分记录已经审批");
				}
			}
			if(node.endsWith("2")){
				if(regionDetail.getAudit2()!=0){
					Assert.throwException("部分记录已经审批");
				}
			}
		
		}
		Date doDate = new Date();
		String flowName="供应商收购地点报备申请表";
		String platform="regionDetail";
		//记录OA日志
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oaCode);
		oa.setPlatform(platform);
		oa.setTableName("region_detail");
		oa.setFlowName(flowName);
		if(node.equals("1")){
			oa.setNodeName("原料收购部主管审核");
		}
		if(node.equals("2")){
			oa.setNodeName("运营总经理审批");
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
		for(RegionDetail regionDetail:regionDetailList){
			if(node.equals("1")){
				regionDetail.setAudit1(state);
				regionDetail.setAuditor1(empName);
				regionDetail.setAuditTime1(new Date());
				regionDetail.setState(3);
			}
			if(node.equals("2")){
				regionDetail.setAudit2(state);
				regionDetail.setAuditor2(empName);
				regionDetail.setAuditTime2(new Date());
				regionDetail.setState(state);
				regionDetail.setLastAlterTime(doDate);
			}
			super.updateById(regionDetail);
			OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
			oaDetail.setFdId(IdWorker.getId());
			oaDetail.setRecFdId(regionDetail.getFdId());
			oaAduitDetailService.insert(oaDetail);
		}
		return "审核完毕";		
	}
	
	//提交OA
	@Override
	public String sendToOA(){
		String oaCode = String.valueOf(IdWorker.getId());
		String flowName="竹木原料运输补助标准调整申请表";
		String platform="region";
		Dict dic = dictService.findOaTemId(flowName, platform);
		Assert.notNull(dic, "没有找到[" + flowName + "]的OA流程配置信息");
		Assert.notBlank(dic.getCode(), "没有找到[" + flowName + "]的OA流程配置信息");
		String temid = dic.getCode();
		String title = dic.getText();
		String userName=UserUtils.getUserInfo().getName();
		String userEmpNo=UserUtils.getUserCode();
		Integer iresult = SqlDbUtils.sentOaFlow(temid, oaCode, title, userEmpNo, userName, platform + ";" + flowName);
		Assert.isTrue(iresult == 1, "提交OA失败");
		OaAduit oa = new OaAduit();
		oa.setFdId(IdWorker.getId());
		oa.setOaCode(oaCode);
		oa.setPlatform(platform);
		oa.setTableName("region");
		oa.setFlowName(flowName);
		oa.setNodeName("提交OA");
		oa.setEmpName(userName);
		oa.setEmpNo(userEmpNo);
		oa.setStatus(0);
		oa.setCreaterId(userEmpNo);
		oa.setCreateTime(new Date());
		oaAduitService.insert(oa);
		OaAduitDetailDto oaDetailDto = entityToDtoMapper.map(oa, OaAduitDetailDto.class);
		OaAduitDetail oaDetail = dtoToEntityMapper.map(oaDetailDto, OaAduitDetail.class);
		oaDetail.setFdId(IdWorker.getId());
		oaDetail.setRecFdId(IdWorker.getId());
		oaAduitDetailService.insert(oaDetail);
		return "提交OA成功";
	}
	
	@Override
	public List<RegionDetail> querySupplier(){
		List<RegionDetail> list=regionDetailMapper.querySupplier();
		return list;	
	}
	
	@Override
	public BigDecimal baiduDistance(String path) throws IOException{
		BigDecimal dis=new BigDecimal(0);
        RestTemplate r = new RestTemplate();
        String res = r.getForObject(path, String.class);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray arr = json.getJSONArray("result");
        System.err.println(String.valueOf(arr));
        for (Object object : arr) {
        	JSONObject o = (JSONObject) object;
        	String distance=o.getJSONObject("distance").getString("text");
        	dis =new BigDecimal(distance.split("公里")[0]);
        }
        log.info(res.toString());
        return dis;
    }
	
	/**
	 * 获取路线,参数tactics
	 * 0：默认
	 * 3：不走高速
	 * 4：高速优先
	 * 5：躲避拥堵
	 * 6：少收费
	 */
	@Override
	public List<Map<String, Double>> getRoute(Long taskId,String tactics){
//		taskService.recordTaskRoute(taskId, tactics);
		String city=(String) redisUtil.get(taskId+"city");
//		System.err.println(city);
		if (null==taskId) {
			Assert.throwException("当前taskId无效");
		}
		String origin=(String) redisUtil.get(taskId+"origin");
		if (null!=origin&&!origin.isEmpty()) {
			
		}else {
			Wrapper<TaskPhoto> wta=new CriterionWrapper<TaskPhoto>(TaskPhoto.class);
			wta.eq("task_id", taskId);
			wta.eq("state", 1);
			TaskPhoto taskPhoto=taskPhotoService.selectFirst(wta);
			if (taskPhoto!=null) {
				origin=taskPhoto.getGps().split("/")[1]+","+taskPhoto.getGps().split("/")[0];
				city=taskPhoto.getCity();
			}
		}
		Task task=taskService.selectById(taskId);
		if (null==task) {
			Assert.throwException("不存在Id为"+taskId+"的任务");
		}		
		task.setTaskRoute(origin+"/"+tactics);
		taskService.updateById(task);
		System.err.println(origin);
		List<Map<String, Double>> points=new ArrayList<>();
		Wrapper<TaskRoute> wrapper=new CriterionWrapper<TaskRoute>(TaskRoute.class);
		wrapper.eq("origin", origin);
		wrapper.eq("tactics", tactics);
		List<TaskRoute> list=taskRouteService.selectList(wrapper);
		if (list.size()>0) {
			System.err.println(list.size());
			for(TaskRoute line:list){
				Map<String, Double> map=new HashMap<String, Double>();
	    		map.put("latitude",line.getLatitude());
	    		map.put("longitude",  line.getLongitude());
	    		points.add(map);
			}
			return points;
		}
		
		if (null==origin) {
			Assert.throwException("当前taskId无效");
		}
//		String origin="23.936043,113.181819";
		String url=baiduRoadWay+"origin="+origin+"&destination=25.892007,114.93475&ak="+ak+"&tactics="+tactics;
		System.err.println(url);
		RestTemplate r=new RestTemplate();
		String res=r.getForObject(url, String.class);
		JSONObject json = JSONObject.parseObject(res);
		JSONObject arr=json.getJSONObject("result");
		JSONArray routes = arr.getJSONArray("routes");
		for(Object route:routes){
			JSONObject route_a = (JSONObject) route;
			JSONArray steps = route_a.getJSONArray("steps");
			String totalDistance=route_a.getString("distance");
			for(Object step:steps){
				List<Map<String, Double>> point_a=new ArrayList<>();
				JSONObject step_a = (JSONObject) step;
				String distance=step_a.getString("distance");
				String roadName=step_a.getString("road_name");
//				String path=step_a.getString("path");
//				String [] paths=path.split(";");
//				for(String path_a:paths){
//		    		String [] point=path_a.split(",");
//		    		Map<String, Double> map=new HashMap<String, Double>();
//		    		map.put("latitude", Double.valueOf(point[1]));
//		    		map.put("longitude",  Double.valueOf(point[0]));
//		    		points.add(map);
//		    		TaskRoute line=new TaskRoute();
//		    		line.setLatitude( Double.valueOf(point[1]));
//		    		line.setLongitude( Double.valueOf(point[0]));
//		    		line.setTactics(tactics);
//		    		line.setOrigin(origin);
////		    		taskLineService.insert(line);
//		    	}
				
				//获取路段始终点
				JSONObject start_location=step_a.getJSONObject("start_location");
				Map<String, Double> map=new HashMap<String, Double>();
	    		map.put("latitude", Double.valueOf(start_location.getString("lat")));
	    		map.put("longitude",  Double.valueOf(start_location.getString("lng")));
	    		points.add(map);
	    		point_a.add(map);
//	    		Map<String, Double> map_b=new HashMap<String, Double>();
//	    		JSONObject end_location=step_a.getJSONObject("end_location");
//	    		map_b.put("latitude", Double.valueOf(end_location.getString("lat")));
//	    		map_b.put("longitude",  Double.valueOf(end_location.getString("lng")));
//	    		points.add(map_b);
//	    		point_a.add(map_b);
	    		for(Map<String, Double> map_a:point_a){
	    			TaskRoute line=new TaskRoute();
		    		line.setFdId(IdWorker.getId());
//		    		line.setRegionId(rDetail.getFdId());
//		    		line.setProvince(rDetail.getProvince());
		    		line.setCity(city);
//		    		line.setCounty(rDetail.getCounty());
		    		line.setDistance(Double.valueOf(distance));
		    		line.setTotalDistance(Double.valueOf(totalDistance));
//		    		line.setPurchasePoint(rDetail.getPurchasePoint());
//		    		line.setAddress(rDetail.getAddress());
		    		line.setLatitude( map_a.get("latitude"));
		    		line.setLongitude( map_a.get("longitude"));
		    		line.setTactics(tactics);
		    		line.setOrigin(origin);
		    		line.setRoadName(roadName);
		    		line.setCreateTime(new Date());
		    		taskRouteService.insert(line);
	    		}
			}
		}
		return points;		
	}
	
	/**
	 * 获取路线,参数tactics
	 * 0：默认
	 * 3：不走高速
	 * 4：高速优先
	 * 5：躲避拥堵
	 * 6：少收费
	 */
	@Override
	public List<List<Map<String, String>>> getRoutes(Long taskId){
		List<List<Map<String,String>>> routes=new ArrayList<>();
//		String city=(String) redisUtil.get(taskId+"region");	
		String origin=(String) redisUtil.get(taskId+"origin");
		String [] tactics={"0","3","4","5","6"};
		System.err.println(origin);
		for(int i=0;i<tactics.length;i++){
			String tactic=tactics[i];
			List<Map<String,String>> points=new ArrayList<>();
			Wrapper<TaskRoute> wrapper=new CriterionWrapper<TaskRoute>(TaskRoute.class);
			wrapper.eq("origin", origin);
			wrapper.eq("tactics", tactic);
			List<TaskRoute> list=taskRouteService.selectList(wrapper);
			if (list.size()>0) {
				for(TaskRoute line:list){
					Map<String, String> map=new HashMap<String, String>();
		    		map.put("latitude",String.valueOf(line.getLatitude()));
		    		map.put("longitude", String.valueOf( line.getLongitude()));
		    		if ("0".equals(tactic)) {
		    			map.put("tactics", "默认");
					}
		    		if ("3".equals(tactic)) {
		    			map.put("tactics", "不走高速");
					}
		    		if ("4".equals(tactic)) {
		    			map.put("tactics", "高速优先");
					}
		    		if ("5".equals(tactic)) {
		    			map.put("tactics", "躲避拥堵");
					}
		    		if ("6".equals(tactic)) {
		    			map.put("tactics", "最少收费");
					}
		    		points.add(map);
				}
				routes.add(i, points);
			}
			
		}
		System.err.println(routes.size()+"/"+routes.get(0).size()+"/"+routes.get(1).size()+"/"+routes.get(2).size()+"/"+routes.get(3).size()+"/"+routes.get(4).size());
		return routes;		
	}
	
	/**
	 * 获取路线,参数tactics
	 * 0：默认
	 * 3：不走高速
	 * 4：高速优先
	 * 5：躲避拥堵
	 * 6：少收费
	 */
	@Override
	public List<Map<String, Double>> getLine(String tactics){
		List<Map<String, Double>> points=new ArrayList<>();
		Wrapper<RegionDetail> wrapper=new CriterionWrapper<RegionDetail>(RegionDetail.class);
		wrapper.eq("state", 1);
//		wrapper.eq("fd_id", 1);
//		wrapper.le("fd_id", 601);
//		wrapper.ge("fd_id", 600);
//		wrapper.groupBy("region_name");
		List<RegionDetail> list=super.selectList(wrapper);
//		List<RegionDetail> list=new ArrayList<>();
//		for(RegionDetail rDetaila:list1){
//			if (rDetaila.getFdId()>700) {
//				list.add(rDetaila);
//			}
//		}
		System.err.println(list.size());
		String [] tacs={"0","3","4","5","6"};
		for(RegionDetail rDetail:list){
			String origin=rDetail.getLatitude()+","+rDetail.getLongitude();
			Wrapper<TaskRoute> wraLine=new CriterionWrapper<TaskRoute>(TaskRoute.class);
			wraLine.eq("origin", origin);
			wraLine.eq("tactics", tactics);
			List<TaskRoute> listLine=taskRouteService.selectList(wraLine);
			if (listLine.size()==0) {
				for(String tac:tacs){
				String url=baiduRoadWay+"origin="+origin+"&destination=25.892007,114.93475&ak="+ak+"&tactics="+tac;
				System.err.println(url);
				RestTemplate r=new RestTemplate();
				String res=r.getForObject(url, String.class);
				JSONObject json = JSONObject.parseObject(res);
				JSONObject arr=json.getJSONObject("result");
				JSONArray routes = arr.getJSONArray("routes");
				for(Object route:routes){
					JSONObject route_a = (JSONObject) route;
					JSONArray steps = route_a.getJSONArray("steps");
					String totalDistance=route_a.getString("distance");
					System.err.println(totalDistance);
					for(Object step:steps){
						List<Map<String, Double>> point_a=new ArrayList<>();
						JSONObject step_a = (JSONObject) step;
						String path=step_a.getString("path");
						String [] paths=path.split(";");
						String roadName=step_a.getString("road_name");
						String distance=step_a.getString("distance");
						
						//获取路段始终点
						JSONObject start_location=step_a.getJSONObject("start_location");
						Map<String, Double> map=new HashMap<String, Double>();
			    		map.put("latitude", Double.valueOf(start_location.getString("lat")));
			    		map.put("longitude",  Double.valueOf(start_location.getString("lng")));
			    		points.add(map);
			    		point_a.add(map);
//			    		Map<String, Double> map_b=new HashMap<String, Double>();
//			    		JSONObject end_location=step_a.getJSONObject("end_location");
//			    		map_b.put("latitude", Double.valueOf(end_location.getString("lat")));
//			    		map_b.put("longitude",  Double.valueOf(end_location.getString("lng")));
//			    		points.add(map_b);
//			    		point_a.add(map_b);
			    		for(Map<String, Double> map_a:point_a){
			    			TaskRoute line=new TaskRoute();
				    		line.setFdId(IdWorker.getId());
				    		line.setRegionId(rDetail.getFdId());
				    		line.setProvince(rDetail.getProvince());
				    		line.setCity(rDetail.getRegionName());
				    		line.setCounty(rDetail.getCounty());
				    		line.setDistance(Double.valueOf(distance));
				    		line.setTotalDistance(Double.valueOf(totalDistance));
				    		line.setPurchasePoint(rDetail.getPurchasePoint());
				    		line.setAddress(rDetail.getAddress());
				    		line.setLatitude( map_a.get("latitude"));
				    		line.setLongitude( map_a.get("longitude"));
				    		line.setTactics(tac);
				    		line.setOrigin(origin);
				    		line.setRoadName(roadName);
				    		line.setCreateTime(new Date());
				    		taskRouteService.insert(line);
			    		}
					}
				}
				}
			}
		}
		
		return points;		
	}

	/**
	 * 获取已规划路线,参数tactics
	 * 0：默认
	 * 3：不走高速
	 * 4：高速优先
	 * 5：躲避拥堵
	 * 6：少收费
	 */
	@Override
	public List<Map<String, Double>> getTaskRoute(Long taskId){
//		String city=(String) redisUtil.get(taskId+"city");
//		if (null==taskId||null==city) {
//			Assert.throwException("当前taskId无效");
//		}

		Task task=taskService.selectById(taskId);
		if (null==task) {
			Assert.throwException("当前taskId无效");
		}
		if (null!=task.getTaskRoute()&&!task.getTaskRoute().isEmpty()) {			
		}else {
			return null;
		}
		String origin=task.getTaskRoute().split("/")[0];
		String tactics=task.getTaskRoute().split("/")[1];
		List<Map<String, Double>> points=new ArrayList<>();
		Wrapper<TaskRoute> wrapper=new CriterionWrapper<TaskRoute>(TaskRoute.class);
		wrapper.eq("origin", origin);
		wrapper.eq("tactics", tactics);
		List<TaskRoute> list=taskRouteService.selectList(wrapper);
		if (list.size()>0) {
			for(TaskRoute line:list){
				Map<String, Double> map=new HashMap<String, Double>();
	    		map.put("latitude",line.getLatitude());
	    		map.put("longitude",  line.getLongitude());
	    		points.add(map);
			}
			return points;
		}
		
		if (null==origin) {
			Assert.throwException("当前taskId无效");
		}
		String url=baiduRoadWay+"origin="+origin+"&destination=25.892007,114.93475&ak="+ak+"&tactics="+tactics;
		System.err.println(url);
		RestTemplate r=new RestTemplate();
		String res=r.getForObject(url, String.class);
		JSONObject json = JSONObject.parseObject(res);
		JSONObject arr=json.getJSONObject("result");
		JSONArray routes = arr.getJSONArray("routes");
		for(Object route:routes){
			JSONObject route_a = (JSONObject) route;
			JSONArray steps = route_a.getJSONArray("steps");
			for(Object step:steps){
				JSONObject step_a = (JSONObject) step;
				
				//获取路段始终点
				JSONObject start_location=step_a.getJSONObject("start_location");
				Map<String, Double> map=new HashMap<String, Double>();
	    		map.put("latitude", Double.valueOf(start_location.getString("lat")));
	    		map.put("longitude",  Double.valueOf(start_location.getString("lng")));
	    		points.add(map);
	    		Map<String, Double> map_b=new HashMap<String, Double>();
	    		JSONObject end_location=step_a.getJSONObject("end_location");
	    		map_b.put("latitude", Double.valueOf(end_location.getString("lat")));
	    		map_b.put("longitude",  Double.valueOf(end_location.getString("lng")));
	    		points.add(map_b);
			}
		}
		return points;		
	}
}
