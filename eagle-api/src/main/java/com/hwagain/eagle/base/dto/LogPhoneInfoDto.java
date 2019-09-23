package com.hwagain.eagle.base.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-06-21
 */
public class LogPhoneInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long fdId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 用户名
     */
	private String userName;
    /**
     * 登录时间
     */
	private Date loginTime;
    /**
     * 登出时间
     */
	private Date logoutTime;
    /**
     * 移动端信息
     */
	private String terminalInfo;
    /**
     * 设备识别码
     */
	private String imei;
    /**
     * 手机厂商名
     */
	private String businessName;
    /**
     * 产品名
     */
	private String productName;
    /**
     * 手机品牌
     */
	private String mobileBrand;
    /**
     * 手机型号
     */
	private String phoneModel;
    /**
     * 主板名
     */
	private String mainboardName;
    /**
     * 系统版本号
     */
	private String systemVersions;
    /**
     * 设备名
     */
	private String deviceName;
    /**
     * 创建时间
     */
	private Date createTime;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getTerminalInfo() {
		return terminalInfo;
	}

	public void setTerminalInfo(String terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSystemVersions() {
		return systemVersions;
	}

	public void setSystemVersions(String systemVersions) {
		this.systemVersions = systemVersions;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMobileBrand() {
		return mobileBrand;
	}

	public void setMobileBrand(String mobileBrand) {
		this.mobileBrand = mobileBrand;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getMainboardName() {
		return mainboardName;
	}

	public void setMainboardName(String mainboardName) {
		this.mainboardName = mainboardName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
