package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xionglz
 * @since 2019-06-21
 */
@TableName("log_phone_info")
public class LogPhoneInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 用户名
     */
	@TableField("user_name")
	private String userName;
    /**
     * 登录时间
     */
	@TableField("login_time")
	private Date loginTime;
    /**
     * 登出时间
     */
	@TableField("logout_time")
	private Date logoutTime;
    /**
     * 移动端信息
     */
	@TableField("terminal_info")
	private String terminalInfo;
    /**
     * 设备识别码
     */
	private String imei;
    /**
     * 手机厂商名
     */
	@TableField("business_name")
	private String businessName;
    /**
     * 产品名
     */
	@TableField("product_name")
	private String productName;
    /**
     * 系统版本号
     */
	@TableField("system_versions")
	private String systemVersions;
    /**
     * 手机品牌
     */
	@TableField("mobile_brand")
	private String mobileBrand;
    /**
     * 手机型号
     */
	@TableField("phone_model")
	private String phoneModel;
    /**
     * 主板名
     */
	@TableField("mainboard_name")
	private String mainboardName;
    /**
     * 设备名
     */
	@TableField("device_name")
	private String deviceName;
    /**
     * 创建时间
     */
	@TableField("create_time")
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

	public String getSystemVersions() {
		return systemVersions;
	}

	public void setSystemVersions(String systemVersions) {
		this.systemVersions = systemVersions;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
