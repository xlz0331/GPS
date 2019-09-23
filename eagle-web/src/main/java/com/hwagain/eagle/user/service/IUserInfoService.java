package com.hwagain.eagle.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hwagain.eagle.org.entity.OrgRelationship;
import com.hwagain.eagle.user.dto.UserInfoDto;
import com.hwagain.eagle.user.entity.UserInfo;
import com.hwagain.eagle.user.entity.UserInfoHwagain;
import com.hwagain.eagle.user.entity.UserInfoVo;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.util.PageDto;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-19
 */
public interface IUserInfoService extends IService<UserInfo> {
	
    String save(UserInfoDto userInfoDto);

    String update(UserInfoDto userInfoDto);

    String delete(String fdId);

    String deleteByIds(String ids);

    List<UserInfoDto> findAll();

    UserInfo findOne(String fdId);

    List<UserInfoDto> queryByParam(UserInfoDto userInfoDto);

    UserInfoVo login(String userAccount, String password,String imei,HttpServletRequest request,String businessName,
			String productName,String mobileBrand,String phoneModel,String mainboardName,String deviceName,String systemVersions)throws Exception;

	String changPwd(String fdId, Map<String, String> params);

	String retrievePassword(Long fdId, String password);

	String updatePlateNumber(Long fdId, String plateNumber);

	String updateLock(Long fdId, Integer lock, Long altorId);

	String updateState(Long fdId, Integer state, Long altorId);

	String updateImei(Long fdId, String imei, Long altorId);

	UserInfoDto lock(Long fdId, Integer state, String oldImei, String imei);

	UserInfo addOneDriver(Long supplierId, String mobile, String plateNumber);

	UserInfo lockUser();

	List<UserInfoDto> allotAccount(String name, String mobile);

	List<UserInfoDto> findAllDriver(String loginName,String mobile);

	UserInfoDto addNowUser(UserInfoDto dto);

	List<UserInfoDto> findAllHwagain(String userType);

	UserInfoDto addOneHwagain(UserInfoDto dto);

	String deleteHwagainByIds(String ids);

	String deletOne(String loginname, String mobile,String userType);

	List<UserInfoDto> findAllNotHwagain(String userType,String loginname);

	String updateUserState();

	PageDto<UserInfoDto> findByPage(int pageNum, int pageSize,String userType,String loginname) throws CustomException;

	UserInfoDto addHwa(UserInfoDto dto);

	List<UserInfoHwagain> findAllHwa(String userType);

	UserInfo findByName(String name, String userType);

	String changPassword(String mobile, String oldPwd, String newPwd);

	String resetPwd(String mobile);

	String onLine();

	String getToken(String mobile,HttpServletRequest request)throws Exception;
}
