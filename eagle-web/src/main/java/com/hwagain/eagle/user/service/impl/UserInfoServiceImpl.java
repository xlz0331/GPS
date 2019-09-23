package com.hwagain.eagle.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.base.api.impl.LogLoginApiImpl;
import com.hwagain.eagle.base.dto.LogLoginDto;
import com.hwagain.eagle.base.dto.LogUserLockDto;
import com.hwagain.eagle.base.entity.LogLogin;
import com.hwagain.eagle.base.entity.LogPhoneInfo;
import com.hwagain.eagle.base.service.IDriverInfoService;
import com.hwagain.eagle.base.service.ILogLoginService;
import com.hwagain.eagle.base.service.ILogPhoneInfoService;
import com.hwagain.eagle.base.service.impl.LogUserLockServiceImpl;
import com.hwagain.eagle.org.entity.OrgRelationship;
import com.hwagain.eagle.org.service.IOrgRelationshipService;
import com.hwagain.eagle.supplier.dto.SupplierInfoDto;
import com.hwagain.eagle.supplier.service.ISupplierInfoService;
import com.hwagain.eagle.track.service.ITrackInfoService;
import com.hwagain.eagle.user.dto.UserInfoDto;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.entity.UserInfoHwagain;
import com.hwagain.eagle.user.entity.UserInfoVo;
import com.hwagain.eagle.user.mapper.UserInfoMapper;
import com.hwagain.eagle.user.service.IUserInfoService;
import com.hwagain.eagle.util.BaseContextHandler;
import com.hwagain.eagle.util.JwtUtil;
import com.hwagain.framework.core.exception.CustomException;
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

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.hwagain.eagle.util.SqlDbUtils;
/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
@Service("userInfoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	private static final Logger logger = LoggerFactory.getLogger(SqlDbUtils.class);

	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	@Value("${sys.config.RSA.publicKey}")
	private String publicKey;
	@Value("${sys.config.RSA.privateKey2}")
	private String privateKey2;
	@Value("${sys.config.RSA.publicKey2}")
	private String publicKey2;
	@Value("${sys.config.app.downloadUrl}")
	private String downloadUrl;
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired LogUserLockServiceImpl logUserLockServiceImpl;
	@Autowired BaseContextHandler baseContextHandler;
	@Autowired ISupplierInfoService supplierInfoService;
	@Autowired ILogLoginService logloginService;
	@Autowired IOrgRelationshipService orgRelationshipSrvice;
	@Autowired ITrackInfoService trackInfoService;
	@Autowired IDriverInfoService driverInfoService;
	@Autowired ILogPhoneInfoService logPhoneInfoService;
	@Value("${sys.config.RSA.privateKey}")
	private String privateKey;
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(UserInfo.class, UserInfoDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factoryTwo = new DefaultMapperFactory.Builder().build();
		factoryTwo.classMap(UserInfoDto.class, UserInfo.class).byDefault().register();
		dtoToEntityMapper = factoryTwo.getMapperFacade();
	}
	//用户信息录入，不进行注册，取消加密
	@Override
	public String save(UserInfoDto userInfoDto) {
		Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
//		validate(userInfoDto);
		UserInfo entity = dtoToEntityMapper.map(userInfoDto, UserInfo.class);
		entity.setCreateTime(new Date());
		entity.setCreatorId(creator);
		if(userInfoDto.getPassword()!=null){
			entity.setPassword(DigestUtils.md5Hex(entity.getPassword()));
		}
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(UserInfoDto userInfoDto) {
		validate(userInfoDto);
		UserInfo entity = dtoToEntityMapper.map(userInfoDto, UserInfo.class);
		entity.setLastAlterTime(new Date());
		super.updateById(entity);
		return "修改成功";
	}

	@Override
	public String delete(String fdId) {
		UserInfo entity = new UserInfo();
		entity.setFdId(Long.parseLong(fdId));
		entity.setState(2);
		super.updateById(entity);
		return "删除成功";
	}

	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		for(String fdId:id){
			Long fd=Long.valueOf(fdId);
			UserInfo fInfo=	super.selectById(fd);
			if(fInfo!=null){
				fInfo.setState(2);
				super.updateById(fInfo);
			}			
		}
		return "删除成功";
	}
	
	
	@Override
	public String deletOne(String loginname,String mobile,String userType) {
		Wrapper<UserInfo> wrapper = new CriterionWrapper<>(UserInfo.class);
		wrapper.eq("loginname",loginname);
		wrapper.eq("mobile", mobile);
		wrapper.eq("user_type", userType);
		wrapper.eq("state", 1);
		UserInfo list = super.selectFirst(wrapper);
		if(list!=null){
			list.setState(2);
			list.setLastAlterTime(new Date());
			list.setLastAltorId(Long.valueOf(UserUtils.getUserInfo().getFdId()));
			System.err.println("删除"+loginname);
			super.updateById(list);
		}else{
			System.err.println("该用户不存在");
			return "该用户不存在";
		}
		LogLoginDto logLogin=new LogLoginDto();
		logLogin.setLoginTime(new Date());
//		logLogin.setSessionId(String.valueOf(sessionId.getId()));
//		System.err.println(String.valueOf(sessionId.getId()));
		logLogin.setUserId(Long.valueOf(UserUtils.getUserInfo().getFdId()));
		logLogin.setTerminalType(1);
		logLogin.setTerminalInfo("deleteUser/"+loginname);
		logloginService.addOne(logLogin);
		logger.info(UserUtils.getUserCode()+"deleteUser/"+loginname);
		return "删除成功";
	}
	
	
	@Override
	public List<UserInfoDto> findAll() {
		
		Wrapper<UserInfo> wrapper = new CriterionWrapper<>(UserInfo.class);
		List<UserInfo> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, UserInfoDto.class);
	}

	@Override
	public UserInfo findOne(String fdId) {
		return super.selectById(fdId);
	}

	private void validate(UserInfoDto userInfoDto){
		Assert.notBlank(userInfoDto.getLoginname(), "用户名不能为空");
		Assert.notBlank(userInfoDto.getPassword(), "密码不能为空");
		Assert.notNull(userInfoDto.getHwagainEmployee(), "是否华劲员工不能为空");
		Assert.notNull(userInfoDto.getParentId(), "上级ID不能为空");
		if (userInfoDto.getFdId() != null) {
			Assert.notNull(userInfoDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(userInfoDto.getCreatorId(), "创建人ID不能为空");
		}
	}

	@Override
	public List<UserInfoDto> queryByParam(UserInfoDto userInfoDto) {
		Wrapper<UserInfo> wrapper = new CriterionWrapper<>(UserInfo.class);
		wrapper.eq("mobile", userInfoDto.getMobile());
		wrapper.eq("parent_id", userInfoDto.getParentId());
		wrapper.eq("hwagain_employee", userInfoDto.getHwagainEmployee());
		wrapper.eq("state", userInfoDto.getState());
		wrapper.eq("imei", userInfoDto.getImei());
		wrapper.eq("user_type", userInfoDto.getUserType());
		wrapper.eq("locked", userInfoDto.getLocked());
		List<UserInfo> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, UserInfoDto.class);
	}
	
	//用户登录
	@Override
	public UserInfoVo login(String userAccount, String password,String imei,HttpServletRequest request,String businessName,
			String productName,String mobileBrand,String phoneModel,String mainboardName,String deviceName,String systemVersions) throws Exception {
//		if(userAccount.equals("18178139882")||userAccount.equals("15172355162")||userAccount.equals("13811111111")||userAccount.equals("19976251089")){
//			
//		}else{
//			Assert.throwException("当前app版本已停用，请更新app");
//		}
//		if(userAccount.equals(anObject))
		Map<String, String> result = new HashMap<>();
		UserInfoVo userInfoVo=new UserInfoVo();
		//String id, String subject, String roles,String mobile
		List<UserInfo> users = userInfoMapper.queryByUserAccount(userAccount);
//		Wrapper<UserInfo> wrapper=new CriterionWrapper<UserInfo>(UserInfo.class);
//		wrapper.eq("mobile", userAccount);
//		wrapper.e
		UserInfo user1=new UserInfo();
		if(users.size()==0){
			logger.info(userAccount+"账号不存在");
			Assert.throwException("账号不存在！");
		}else{
			for(UserInfo user:users){
//				System.err.println(user.getUserType());
//				if(user.getUserType().equals("DRIVER")){
//					if (user.getPlateNumber().equals(password)){				
//			            // 凭证
//						result.put("token", jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),user.getPlateNumber(), user.getLoginname(),user.getMobile(),user.getParentId()));
//			            // 账号
//						result.put("userAccount", user.getMobile());
//						userInfoVo.setToken(jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),user.getPlateNumber(), user.getLoginname(),user.getMobile(),user.getParentId()));
//						userInfoVo.setLoginName(user.getLoginname());
//						userInfoVo.setMobile(user.getMobile());
//						userInfoVo.setPlateNumber(user.getPlateNumber());
//						userInfoVo.setParentId(user.getParentId());
//						userInfoVo.setUserType(user.getUserType());
//
//						user1=user;
//						break;
//						} else {
////						Assert.isTrue(false, "登录失败");
//					}
//				}
				if(user.getUserType().equals("SUPPLIER")){
					try {
						String pass = decryptByPublicKey(password);
						if (user.getPassword() .equals(DigestUtils.md5Hex(DigestUtils.md5Hex(pass))) ){
				            // 凭证
							result.put("token", jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),"AAAAA", user.getLoginname(),user.getMobile(),user.getParentId()));
				            // 账号
							result.put("userAccount", user.getMobile());
							userInfoVo.setToken(jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),"AAAAA", user.getLoginname(),user.getMobile(),user.getParentId()));
							userInfoVo.setLoginName(user.getLoginname());
							userInfoVo.setMobile(user.getMobile());
//							userInfoVo.setPlateNumber(user.getPlateNumber());
//							userInfoVo.setParentId(user.getParentId());
							userInfoVo.setUserType(user.getUserType());
							user1=user;
							break;
						} else {
//							Assert.isTrue(false, "登录失败");
						}		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if(user.getUserType().equals("DRIVERA")){
//					try {
						String pass=decryptByPublicKey(password);
//						System.err.println(pass);
						if(user.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(pass)))){
							if(user.getImei()!=null&&!user.getImei().isEmpty()){
								if(imei.equals(user.getImei())){
									// 凭证
									result.put("token", jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),user.getPlateNumber(), user.getLoginname(),user.getMobile(),user.getParentId()));
						            // 账号
									result.put("userAccount", user.getMobile());
									userInfoVo.setToken(jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),user.getPlateNumber(), user.getLoginname(),user.getMobile(),user.getParentId()));
									userInfoVo.setLoginName(user.getLoginname());
									userInfoVo.setMobile(user.getMobile());
									userInfoVo.setPlateNumber(user.getPlateNumber());
									userInfoVo.setParentId(user.getParentId());
									userInfoVo.setUserType(user.getUserType());
									user1=user;
								}else{
									HttpSession sessionId = request.getSession();
									LogLoginDto logLogin=new LogLoginDto();
									logLogin.setLoginTime(new Date());
									logLogin.setSessionId(String.valueOf(sessionId.getId()));
//									System.err.println(String.valueOf(sessionId.getId()));
									logLogin.setUserId(user1.getFdId());
									logLogin.setTerminalType(13);
									logLogin.setTerminalInfo(imei);
//									logloginService.addOne(logLogin);
									logger.info(user.getLoginname()+"设备不一致");
									Assert.throwException("当前账号已经创建任务，必须使用创建任务时使用的手机才可以登录，登录失败");
								}
							}else{
								// 凭证
								result.put("token", jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),user.getPlateNumber(), user.getLoginname(),user.getMobile(),user.getParentId()));
					            // 账号
								result.put("userAccount", user.getMobile());
								userInfoVo.setToken(jwtUtil.createJWT(user.getFdId()+"",user.getUserType(),user.getPlateNumber(), user.getLoginname(),user.getMobile(),user.getParentId()));
								userInfoVo.setLoginName(user.getLoginname());
								userInfoVo.setMobile(user.getMobile());
								userInfoVo.setPlateNumber(user.getPlateNumber());
								userInfoVo.setParentId(user.getParentId());
								userInfoVo.setUserType(user.getUserType());
								user1=user;
							}
							break;
						}
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}else {
//					Assert.isTrue(false, "登录失败");
				}	
			}
			if(user1.getFdId()==null){
				HttpSession sessionId = request.getSession();
				LogLoginDto logLogin=new LogLoginDto();
				logLogin.setLoginTime(new Date());
				logLogin.setSessionId(String.valueOf(sessionId.getId()));
//				System.err.println(String.valueOf(sessionId.getId()));
				logLogin.setUserId(users.get(0).getFdId());
				logLogin.setTerminalType(13);
				logLogin.setTerminalInfo(imei);
				logloginService.addOne(logLogin);
				logger.info(userAccount+"账号或密码错误，登录失败");
				Assert.isTrue(false, "账号或密码错误，登录失败");
			}
		}
//		HttpSession  session=request.getSession();
//		session.setAttribute("parentId",user1.getParentId());
//		session.setAttribute("loginName",userAccount);
//		session.setAttribute("userId",user1.getFdId());
//		System.err.println(session.getAttribute("loginName"));
//		System.err.println(session.getAttribute("parentId"));
		HttpSession sessionId = request.getSession();
		LogLoginDto logLogin=new LogLoginDto();
		logLogin.setLoginTime(new Date());
		logLogin.setSessionId(String.valueOf(sessionId.getId()));
//		System.err.println(String.valueOf(sessionId.getId()));
		logLogin.setUserId(user1.getFdId());
		logLogin.setTerminalType(2);
		logLogin.setTerminalInfo(imei);
		logloginService.addOne(logLogin);
		
//		Wrapper<LogPhoneInfo> wraPhoto=new CriterionWrapper<LogPhoneInfo>(LogPhoneInfo.class);
//		wraPhoto.eq("imei", imei);
//		List<LogPhoneInfo> logPhoneInfos=logPhoneInfoService.selectList(wraPhoto);
//		for(LogPhoneInfo phoneInfo:logPhoneInfos){
//			logPhoneInfoService.deleteById(phoneInfo.getFdId());
//		}
		LogPhoneInfo logPhoneInfo=new LogPhoneInfo();
		logPhoneInfo.setFdId(IdWorker.getId());
		logPhoneInfo.setUserId(user1.getFdId());
		logPhoneInfo.setImei(imei);
		logPhoneInfo.setMobileBrand(mobileBrand);
		logPhoneInfo.setBusinessName(businessName);
		logPhoneInfo.setDeviceName(deviceName);
		logPhoneInfo.setMainboardName(mainboardName);
		logPhoneInfo.setPhoneModel(phoneModel);
		logPhoneInfo.setLoginTime(new Date());
		logPhoneInfo.setCreateTime(new Date());
		logPhoneInfo.setProductName(productName);
		logPhoneInfo.setUserName(user1.getLoginname());
		logPhoneInfo.setSystemVersions(systemVersions);
		logPhoneInfoService.insert(logPhoneInfo);
		logger.info(user1.getLoginname()+"login");
		return userInfoVo;
	}
	
	@Override
	public String changPwd(String fdId, Map<String, String> params) {
		UserInfo user = super.selectById(fdId);
		if (user == null){
			Assert.isTrue(false, "用户不存在");
			return "修改失败";
		}
		if (user.getPassword().equals(DigestUtils.md5Hex(params.get("oldPwd")))) {
			user.setPassword(DigestUtils.md5Hex(params.get("newPwd")));
			user.setLastAlterTime(new Date());
			user.setLastAltorId(Long.valueOf(fdId));
			super.updateById(user);
			return "修改成功";
		} else {
			Assert.isTrue(false, "原密码有误");
			return "修改失败";
		}
	}
	
	//修改密码
	@Override
	public String changPassword(String mobile,String oldPwd,String newPwd) {
		List<UserInfo> users = userInfoMapper.queryByUserAccount(mobile);
		if(users.size()==0){
			logger.info(mobile+"账号不存在");
			Assert.throwException("账号不存在！");
		}else{
			for(UserInfo user:users){
				try {
					String oldpass = decryptByPublicKey(oldPwd);
					String newpass = decryptByPublicKey(newPwd);
					if (user.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(oldpass)))) {
						user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(newpass)));
						user.setLastAlterTime(new Date());
						user.setLastAltorId(user.getFdId());
						super.updateById(user);
						return "修改成功";
					} else {
						return "原密码有误,修改失败";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return "修改失败";		
	}
	
	//重置密码，恢复初始缺省密码
	@Override
	public String resetPwd(String mobile){
		List<UserInfo> users = userInfoMapper.queryByUserAccount(mobile);
		if(users.size()==0){
			logger.info(mobile+"账号不存在");
			Assert.throwException("账号不存在！");
		}else{
			for(UserInfo user:users){
				user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex("123456")));
				user.setLastAlterTime(new Date());
				super.updateById(user);
				List<String> phones=new ArrayList<>();
				phones.add(user.getMobile());
				JSONObject template = new JSONObject();
		        template.put("content","您好！您的华劲鹰眼密码已重置为初始密码123456，请及时修改密码和妥善保管您的账号及密码。华劲鹰眼下载链接："+downloadUrl);
		        template.put("createUserCode",UserUtils.getUserCode());
		        template.put("phones",phones);
		        template.put("systemCode","hwagain-eagle");
		        System.err.println(template.toString());
//			    trackInfoService.sendSms(template.toString());
			}
		}
		return "重置成功";
		
	}
	
	@Override
	public String retrievePassword(Long fdId, String password) {
		UserInfo userInfo = new UserInfo();
		userInfo.setFdId(fdId);
		userInfo.setPassword(DigestUtils.md5Hex(password));
		userInfo.setLastAlterTime(new Date());
		userInfo.setLastAltorId(fdId);
		super.updateById(userInfo);
		return "修改成功";
	}
	
	@Override
	public String updatePlateNumber(Long fdId, String plateNumber) {
		UserInfo userInfo = new UserInfo();
		userInfo.setFdId(fdId);
		userInfo.setPlateNumber(plateNumber);
		userInfo.setLastAlterTime(new Date());
		userInfo.setLastAltorId(fdId);
		super.updateById(userInfo);
		return "修改成功";
	}
	
	@Override
	public String updateLock(Long fdId, Integer lock, Long altorId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setFdId(fdId);
		userInfo.setLocked(lock);
		userInfo.setLastAlterTime(new Date());
		userInfo.setLastAltorId(altorId);
		super.updateById(userInfo);
		return "修改成功";
	}
	
	@Override
	public String updateState(Long fdId, Integer state, Long altorId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setFdId(fdId);
		userInfo.setState(state);
		userInfo.setLastAlterTime(new Date());
		userInfo.setLastAltorId(altorId);
		super.updateById(userInfo);
		return "修改成功";
	}
	
	@Override
	public String updateImei(Long fdId, String imei, Long altorId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setFdId(fdId);
		userInfo.setImei(imei);
		userInfo.setLastAlterTime(new Date());
		userInfo.setLastAltorId(altorId);
		super.updateById(userInfo);
		return "修改成功";
	}
	
	//用户上锁、解锁
	@Override
	public UserInfoDto lock(Long fdId,Integer state,String oldImei,String imei){
		UserInfo entity=super.selectById(fdId);
		entity.setState(state);
		super.updateById(entity);
		LogUserLockDto logDto=new LogUserLockDto();
		logDto.setImei(imei);
		logDto.setCurrentImei(oldImei);
		logDto.setUserId(entity.getFdId());
		logUserLockServiceImpl.addOneLog(logDto);
		return entityToDtoMapper.map(entity, UserInfoDto.class);
		
	}
	
	//供应商分配司机
	@Override
	public UserInfo addOneDriver(Long supplierId,String mobile,String plateNumber){
		Assert.notNull(supplierId, "供应商Id不能为空");
		Assert.notBlank(mobile, "手机号不能为空");
		Assert.notBlank(plateNumber, "车牌号不能为空");
		Wrapper<UserInfo> wrapper=new CriterionWrapper<UserInfo>(UserInfo.class);
		wrapper.eq("mobile", mobile);
//		wrapper.eq("plate_number", plateNumber);
		wrapper.eq("parent_id", supplierId);
		wrapper.eq("user_type", "DRIVER");
//		wrapper.eq("locked", 2);
		UserInfo user=super.selectFirst(wrapper);
		if(user!=null){
			user.setPlateNumber(plateNumber);
//			user.setState(1);
			user.setLocked(2);
			user.setLastAlterTime(new Date());
			user.setLastAltorId(supplierId);			
			super.updateById(user);
		}else{
			user=new UserInfo();
			user.setFdId(Long.valueOf(IdWorker.getId()));
			user.setMobile(mobile);
			user.setParentId(supplierId);
//			user.setState(1);
			user.setLoginname(mobile);
			user.setUserType("DRIVER");
			user.setPlateNumber(plateNumber);
			user.setCreateTime(new Date());
			user.setCreatorId(supplierId);
			super.insert(user);
		}
		return user;		
	}
	
	//任务结束，账号锁定
	@Override
	public UserInfo lockUser(){
		Long fdId=Long.valueOf(String.valueOf(baseContextHandler.getUserID()));
		UserInfo userInfo=super.selectById(fdId);
		userInfo.setLocked(1);
		userInfo.setLastAlterTime(new Date());
		userInfo.setLastAltorId(fdId);
		super.updateById(userInfo);
		return userInfo;		
	}
	
	//给供应商分配账号
	//初始
	@Override
	public List<UserInfoDto> allotAccount(String name,String mobile){
		UserInfo entity=new UserInfo();
		if(name==null&&mobile==null){
			List<SupplierInfoDto> supplier=supplierInfoService.findAll();
			if(supplier.size()>0){
				for(SupplierInfoDto dto:supplier){
					entity.setFdId(Long.valueOf(IdWorker.getId()));
					entity.setLoginname(dto.getName());
					entity.setMobile(dto.getMobile());
					entity.setPassword(DigestUtils.md5Hex("123456"));
					entity.setParentId(entity.getFdId());
					entity.setUserType("SUPPLIER");
					super.insert(entity);
				}
			}
		}
		return findAll();		
	}
	
	@Override
	public List<UserInfoDto> findAllDriver(String loginName,String mobile) {
		Wrapper<UserInfo> wrapper = new CriterionWrapper<>(UserInfo.class);
		wrapper.eq("user_type", "DRIVERA");
		wrapper.eq("state", 1);
		wrapper.like("loginname", loginName, SqlLike.DEFAULT);
		wrapper.like("mobile", mobile, SqlLike.DEFAULT);
		wrapper.eq("locked", 2);
		List<UserInfo> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, UserInfoDto.class);
	}
	
	@Override
	public UserInfoDto addNowUser(UserInfoDto dto){
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if(dto.getMobile()!=null){
			if(dto.getMobile()!=""){
				if (dto.getMobile().length()!=11) {
			       Assert.throwException("请填入正确的手机号");
			    } else {
			        Pattern p = Pattern.compile(regex);
			        Matcher m = p.matcher(dto.getMobile());
			        boolean isMatch = m.matches();
			        if (!isMatch) {
			        	 Assert.throwException("请填入正确的手机号");
			        }

			    }
			}
		}		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(dto.getCreateTime()==null){
			dto.setCreateTime(new Date());
		}
		String startTime = df.format(dto.getCreateTime());
		if(dto.getLastAlterTime()!=null){
			String endTime = df.format(dto.getLastAlterTime());
		}
//		if(new Date().after(dto.getCreateTime())&&new Date().before(dto.getLastAlterTime())){
//			dto.setState(1);
//		}else{
//			dto.setState(2);
//		}
//		int a=(int)((Math.random()*9+1)*100000);
		int a=123456;//缺省密码（测试用）
		System.err.println(a);
		Long creator=Long.parseLong("123456");
		if(dto.getUserType().equals("SUPPLIER")){
			 if(dto.getCreatorId()==null){
				 creator=Long.parseLong("123456");
			 }
		}else{
			creator=dto.getCreatorId();
		}
		Wrapper<UserInfo> wrap111=new CriterionWrapper<UserInfo>(UserInfo.class);
		wrap111.eq("mobile", dto.getMobile());
		wrap111.notIn("state", 2);
		wrap111.eq("locked", 2);
		UserInfo user111=super.selectFirst(wrap111);
		if(user111!=null){
			if(user111.getUserType().equals("SUPPLIER")){
				Assert.throwException(dto.getMobile()+"已注册为供应商账号，不可重复注册");
			}
			if(user111.getUserType().equals("DRIVERA")){
				Assert.throwException(dto.getMobile()+"已注册为司机账号，不可重复注册");
			}
		}
//		validate(userInfoDto);
		Wrapper<UserInfo> wrapper=new CriterionWrapper<UserInfo>(UserInfo.class);
		wrapper.eq("loginname", dto.getLoginname());
		wrapper.eq("mobile", dto.getMobile());
		wrapper.eq("user_type", dto.getUserType());
		wrapper.notIn("state", 2);
		UserInfo user=super.selectFirst(wrapper);
		if(user!=null){
			UserInfo entity = dtoToEntityMapper.map(dto, UserInfo.class);
			entity.setFdId(user.getFdId());
			entity.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(String.valueOf(a))));
			entity.setLocked(2);
			entity.setLastAltorId(creator);
			super.updateById(entity);
		}else{
			UserInfo entity = dtoToEntityMapper.map(dto, UserInfo.class);			
			entity.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(String.valueOf(a))));
			entity.setLocked(2);
			entity.setCreateTime(new Date());
			entity.setCreatorId(creator);
			super.insert(entity);
		}
		List<String> phones=new ArrayList<>();
		phones.add(dto.getMobile());
		JSONObject template = new JSONObject();
        template.put("content","您好！您的华劲鹰眼账号已开通，账号为您的手机号码("+dto.getMobile()+")，初始密码为123456，请及时修改密码和妥善保管您的账号及密码。华劲鹰眼下载链接："+downloadUrl);
        template.put("createUserCode",UserUtils.getUserCode());
        template.put("phones",phones);
        template.put("systemCode","hwagain-eagle");
//        System.err.println(template.toString());
	    trackInfoService.sendSms(template.toString());
        LogLoginDto logLogin=new LogLoginDto();
		logLogin.setLoginTime(new Date());
//		logLogin.setSessionId(String.valueOf(sessionId.getId()));
//		System.err.println(String.valueOf(sessionId.getId()));
		logLogin.setUserId(creator);
		logLogin.setTerminalType(1);
		logLogin.setTerminalInfo("addUer/"+dto.getLoginname());
//		logloginService.addOne(logLogin);
//		logger.info(UserUtils.getUserCode()+"addUer/"+dto.getLoginname());
		return dto;
		
	}
	
	 /**
     * RSA公钥解密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 明文
     * 解密过程中的异常信息
     */
    public String decryptByPublicKey(String str) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        // base64编码的公钥
        System.err.println(str);
        System.err.println(publicKey);
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String res = new String(cipher.doFinal(inputByte));
//        System.err.println(res);
        return res;
    }
    
    /**
     * RSA公钥解密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 明文
     * 解密过程中的异常信息
     */
    public String decryptByPublicKey2(String str) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        // base64编码的公钥
        System.err.println(str);
        System.err.println(publicKey2);
        byte[] decoded = Base64.decodeBase64(publicKey2);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String res = new String(cipher.doFinal(inputByte));
//        System.err.println(res);
        return res;
    }
    
    /**
     * RSA私钥加密
     *
     * @param str        加密字符串
     * @param privateKey 公钥
     * @return 密文
     * 加密过程中的异常信息
     */
    public String encryptByPrivateKey(String str) throws Exception {
        //base64编码的公钥
//    	 System.out.println(privateKey2);
        byte[] decoded = Base64.decodeBase64(privateKey2);     
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        System.err.println(">>>>>>>>.."+outStr);
        return outStr;
    }
    
    //华劲员工
    @Override
    public List<UserInfoDto> findAllHwagain(String userType){
    	Wrapper<UserInfo> wrappera=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrappera.eq("loginname", UserUtils.getUserInfo().getName());
    	wrappera.eq("state", 1);
    	wrappera.eq("locked", 2);
    	wrappera.eq("user_type", "ADMIN");
    	UserInfo userInfoa=super.selectFirst(wrappera);
    	if(userInfoa==null){
    		List<UserInfoDto> lista=new ArrayList<UserInfoDto>();
    		return lista;
    	}
    	List<UserInfoDto> listA=new ArrayList<>();
    	Wrapper<UserInfo> wrapper=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrapper.eq("hwagain_employee", 1);
    	wrapper.eq("user_type", userType);
    	wrapper.eq("state", 1);
    	wrapper.eq("locked", 2);
    	wrapper.orderBy("user_type");   	
    	List<UserInfo> list=super.selectList(wrapper);
    	for(UserInfo userInfo:list){
    		UserInfoDto userInfoDto=entityToDtoMapper.map(userInfo, UserInfoDto.class);
    		Wrapper<OrgRelationship> wraOrg=new CriterionWrapper<OrgRelationship>(OrgRelationship.class);
    		wraOrg.eq("UserName", userInfo.getLoginname());
    		wraOrg.eq("RelaType", "PDU");
    		wraOrg.eq("RelaPrimary", 1);
    		OrgRelationship org=orgRelationshipSrvice.selectOne(wraOrg);
    		if(org!=null){
    			userInfoDto.setCompanyName(org.getCompanyName());  
    			userInfoDto.setDeptName(org.getDeptname());
    			listA.add(userInfoDto);
    		}
    	}
		return listA;   	
    }
    
    //增加用户或删除用户，并对用户授权
    @Override
    public UserInfoDto addOneHwagain(UserInfoDto dto){
    	Wrapper<UserInfo> wrappera=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrappera.eq("loginname", UserUtils.getUserInfo().getName());
    	wrappera.eq("state", 1);
    	wrappera.eq("locked", 2);
    	wrappera.eq("user_type", "ADMIN");
    	UserInfo userInfoa=super.selectFirst(wrappera);
    	if(userInfoa==null){
    		Assert.throwException("您没有权限进行添加系统用户");
    	}
    	Long creator=Long.parseLong(UserUtils.getUserInfo().getFdId());
    	Wrapper<OrgRelationship> wraOrg=new CriterionWrapper<OrgRelationship>(OrgRelationship.class);
    	wraOrg.eq("UserName", dto.getLoginname());
    	wraOrg.eq("RelaType", "PDU");
    	wraOrg.eq("RelaPrimary", 1);
    	OrgRelationship org=orgRelationshipSrvice.selectFirst(wraOrg);
    	if(org==null){
    		Assert.throwException("找不到"+dto.getLoginname());
    	}
//    	dto.setPassword(org.getCompanyName());
    	dto.setState(1);
//    	dto.setMobile(org.getDeptname());
    	dto.setHwagainEmployee(1);
    	dto.setCreateTime(new Date());
    	dto.setCreatorId(creator);
    	super.insert(dtoToEntityMapper.map(dto, UserInfo.class));
		return dto;    	
    }
    
    @Override
	public String deleteHwagainByIds(String ids) {
    	Wrapper<UserInfo> wrappera=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrappera.eq("loginname", UserUtils.getUserInfo().getName());
    	wrappera.eq("state", 1);
    	wrappera.eq("locked", 2);
    	wrappera.eq("user_type", "ADMIN");
    	UserInfo userInfoa=super.selectFirst(wrappera);
    	if(userInfoa==null){
    		Assert.throwException("您没有权限进行删除该系统用户");
    	}
		String[] id = ids.split(";");
		for(String fdId:id){
			Long fd=Long.valueOf(fdId);
			UserInfo fInfo=	super.selectById(fd);
			if(fInfo!=null){
				fInfo.setState(2);
				super.updateById(fInfo);
			}			
		}
		return "删除成功";
	}
    
    @Override
    public List<UserInfoDto> findAllNotHwagain(String userType,String loginname){
    	Wrapper<UserInfo> wrappera=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrappera.eq("loginname", UserUtils.getUserInfo().getName());
    	wrappera.eq("state", 1);
    	wrappera.eq("locked", 2);
    	wrappera.eq("user_type", "USERADMIN");
    	UserInfo userInfoa=super.selectFirst(wrappera);
    	if(userInfoa==null){
    		List<UserInfoDto> lista=new ArrayList<UserInfoDto>();
    		return lista;
    	}
    	
    	Wrapper<UserInfo> wrapper=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrapper.eq("hwagain_employee", 2);
    	wrapper.eq("loginname", loginname);
    	wrapper.eq("user_type", userType);
    	wrapper.eq("state", 1);
    	wrapper.eq("locked", 2);
    	List<UserInfo> list=super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, UserInfoDto.class);   	
    }
    
    //定时刷新账号状态（判断是否失效）
    @Override
    public String  updateUserState(){
    	Wrapper<UserInfo> wrapper=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrapper.eq("hwagain_employee", 2);
    	wrapper.eq("state", 1);
    	wrapper.eq("locked", 2);
    	List<UserInfo> list=super.selectList(wrapper);
    	for(UserInfo user:list){
    		if(user.getLastAlterTime()!=null){
    			Date dt1=null;
        		Date dt2=null;
        		Calendar calendar = Calendar.getInstance();
        		calendar.setTime(user.getLastAlterTime());
        		calendar.add(Calendar.DAY_OF_YEAR,1);
        		if(calendar.getTime().before(new Date())){
        			user.setState(0);
        			super.updateById(user);
        		}
    		}
    	}
    	System.err.println("刷新UserInfo状态完成");
		return "状态更新成功";    	
    }
    
	@Override
	public PageDto<UserInfoDto> findByPage(int pageNum, int pageSize,String userType,String loginname) throws CustomException {
		Wrapper<UserInfo> wrappera=new CriterionWrapper<UserInfo>(UserInfo.class);
    	wrappera.eq("loginname", UserUtils.getUserInfo().getName());
    	wrappera.eq("state", 1);
    	wrappera.eq("locked", 2);
    	wrappera.eq("user_type", "USERADMIN");
    	UserInfo userInfoa=super.selectFirst(wrappera);
    	if(userInfoa==null){
    		PageDto<UserInfoDto> lista = new PageDto<UserInfoDto>();
    		return lista;
    	}
		PageDto<UserInfoDto> pageDto = new PageDto<UserInfoDto>();
		pageDto.setPage(pageNum + 1);
		pageDto.setPageSize(pageSize);
		Wrapper<UserInfo> wrapper = new CriterionWrapper<UserInfo>(UserInfo.class);
		wrapper.eq("hwagain_employee", 2);
    	wrapper.eq("loginname", loginname);
    	wrapper.eq("user_type", userType);
    	wrapper.eq("state", 1);
    	wrapper.eq("locked", 2);
		Page<UserInfo> page = super.selectPage(new Page<UserInfo>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), UserInfoDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
    
//    @Scheduled(cron = "0 0 1 ? * *") // 每天1点执行-首先执行
//	@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
    public void sync(){
    	updateUserState();
//    	supplierInfoService.updateUserState();
    	driverInfoService.updateUserState();
    }
    
    @Override
    public UserInfoDto  addHwa(UserInfoDto dto){
    	Wrapper<OrgRelationship> wraOrg=new CriterionWrapper<OrgRelationship>(OrgRelationship.class);
    	wraOrg.eq("UserName", dto.getLoginname());
    	wraOrg.eq("RelaType", "PDU");
    	wraOrg.eq("RelaPrimary", 1);
    	OrgRelationship org=orgRelationshipSrvice.selectFirst(wraOrg);
    	if(org==null){
    		Assert.throwException("找不到"+dto.getLoginname());
    	}else{
    		dto.setHwagainEmployee(1);
    		addNowUser(dto);
    	}
		return dto;
    }
    
    @Override
    public List<UserInfoHwagain> findAllHwa(String userType){
    	Wrapper<UserInfo> wrapper = new CriterionWrapper<>(UserInfo.class);
    	wrapper.eq("user_type", userType);
    	wrapper.eq("hwagain_employee", 1);
    	wrapper.eq("state", 1);
    	wrapper.eq("locked", 2);
		List<UserInfo> list = super.selectList(wrapper);
		List<UserInfoHwagain> hwagains=new ArrayList<>();
		for(UserInfo user:list){
			UserInfoHwagain hwagain=new UserInfoHwagain();
			Wrapper<OrgRelationship> wraOrg=new CriterionWrapper<OrgRelationship>(OrgRelationship.class);
	    	wraOrg.eq("RelaType", "PDU");
	    	wraOrg.eq("RelaPrimary", 1);
	    	wraOrg.eq("UserName", user.getLoginname());
	    	OrgRelationship org=orgRelationshipSrvice.selectFirst(wraOrg);
	    	hwagain.setOrgRelationship(org);
	    	hwagain.setUserInfo(entityToDtoMapper.map(user, UserInfoDto.class));
	    	hwagains.add(hwagain);
		}   	
		return hwagains;
    }
    
    @Override
    public UserInfo findByName(String name,String userType){
    	Wrapper<UserInfo> wrapper = new CriterionWrapper<>(UserInfo.class);
    	wrapper.eq("user_type", userType);
    	wrapper.eq("hwagain_employee", 1);
    	wrapper.eq("state", 1);
    	wrapper.eq("locked", 2);
    	wrapper.eq("loginname", name);
    	UserInfo userInfo=super.selectOne(wrapper);
		return userInfo;
   	
    }
    
   //在线请求
    @Override
    public String onLine(){
    	logger.info(BaseContextHandler.getUsername()+":请求成功");
		return "请求成功";    	
    }
    
    //获取token
    @Override
    public String getToken(String mobile,HttpServletRequest request) throws Exception{
    	String password="RqIGG7OgG8WwGmTgqJTEkthxz0eqg++zIFyffKdCwRsh0MHC9fbdn5eAe96zOA930QtbiG7HODAqOYVMxixfKCtLWZy6XxCAtOJCXH3satnaLQKBqYPej37laXyvM4THu/xAVO8iAE8BXg7+85wGn5eF2fKuLssu5Glln08y+bg=";
    	UserInfoVo userInfoVo=login(mobile, password, "hwagain", request, "", "", "", "", "", "","");
    	String token=userInfoVo.getToken();
		return token;  	
    }
}
