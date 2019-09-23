package com.hwagain.eagle.util;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jdbc.config")
public class JDBCConfig {

	private String OAUrl;
	
	private String OAUsername;
	
	private String OAPassword;
	
	private String OARequestType;
	
	private String PSUrl;
	
	private String PSUsername;
	
	private String PSPassword;
	
	private String OA1Url;
	
	private String K3Url;
	
	private String K3Username;
	
	private String K3Password;
	
	private String PDUrl;
	
	private String PDUsername;
	
	private String PDPassword;
	
	private String POMUrl;
	
	private String POMUsername;
	
	private String POMPassword;
	
	private String HJPMUrl;
	
	private String HJPMUsername;
	
	private String HJPMPassword;
	
	private String PurUrl;
	
	private String PurUsername;
	
	private String PurPassword;
	
	private String YLZUrl;
	
	private String YLZUsername;
	
	private String YLZPassword;

	public String getOARequestType() {
		return OARequestType;
	}

	public void setOARequestType(String oARequestType) {
		OARequestType = oARequestType;
	}

	public String getPSUrl() {
		return PSUrl;
	}

	public void setPSUrl(String pSUrl) {
		PSUrl = pSUrl;
	}

	public String getPSUsername() {
		return PSUsername;
	}

	public void setPSUsername(String pSUsername) {
		PSUsername = pSUsername;
	}

	public String getPSPassword() {
		return PSPassword;
	}

	public void setPSPassword(String pSPassword) {
		PSPassword = pSPassword;
	}

	public String getOAUrl() {
		return OAUrl;
	}

	public void setOAUrl(String oAUrl) {
		OAUrl = oAUrl;
	}

	public String getOAUsername() {
		return OAUsername;
	}

	public void setOAUsername(String oAUsername) {
		OAUsername = oAUsername;
	}

	public String getOAPassword() {
		return OAPassword;
	}

	public void setOAPassword(String oAPassword) {
		OAPassword = oAPassword;
	}

	public String getOA1Url() {
		return OA1Url;
	}

	public void setOA1Url(String oA1Url) {
		OA1Url = oA1Url;
	}
	
	public String getK3Url() {
		return K3Url;
	}

	public void setK3Url(String k3Url) {
		K3Url = k3Url;
	}

	public String getK3Username() {
		return K3Username;
	}

	public void setK3Username(String k3Username) {
		K3Username = k3Username;
	}

	public String getK3Password() {
		return K3Password;
	}
	
	public void setK3Password(String k3Password) {
		K3Password = k3Password;
	}
	

	public String getPDUsername() {
		return PDUsername;
	}

	public void setPDUsername(String pdUsername) {
		PDUsername = pdUsername;
	}

	public String getPDPassword() {
		return PDPassword;
	}
	
	public void setPDPassword(String pDPassword) {
		PDPassword = pDPassword;
	}

	public void setPDUrl(String pdUrl) {
		PDUrl = pdUrl;
	}
	
	public String getPDUrl() {
		return PDUrl;
	}

	public String getPOMUrl() {
		return POMUrl;
	}

	public void setPOMUrl(String pOMUrl) {
		POMUrl = pOMUrl;
	}

	public String getPOMUsername() {
		return POMUsername;
	}

	public void setPOMUsername(String pOMUsername) {
		POMUsername = pOMUsername;
	}

	public String getPOMPassword() {
		return POMPassword;
	}

	public void setPOMPassword(String pOMPassword) {
		POMPassword = pOMPassword;
	}

	public String getHJPMUrl() {
		return HJPMUrl;
	}

	public void setHJPMUrl(String hJPMUrl) {
		HJPMUrl = hJPMUrl;
	}

	public String getHJPMUsername() {
		return HJPMUsername;
	}

	public void setHJPMUsername(String hJPMUsername) {
		HJPMUsername = hJPMUsername;
	}

	public String getHJPMPassword() {
		return HJPMPassword;
	}

	public void setHJPMPassword(String hJPMPassword) {
		HJPMPassword = hJPMPassword;
	}

	public String getPurUrl() {
		return PurUrl;
	}

	public void setPurUrl(String purUrl) {
		PurUrl = purUrl;
	}

	public String getPurUsername() {
		return PurUsername;
	}

	public void setPurUsername(String purUsername) {
		PurUsername = purUsername;
	}

	public String getPurPassword() {
		return PurPassword;
	}

	public void setPurPassword(String purPassword) {
		PurPassword = purPassword;
	}

	public String getYLZUrl() {
		return YLZUrl;
	}

	public void setYLZUrl(String yLZUrl) {
		YLZUrl = yLZUrl;
	}

	public String getYLZUsername() {
		return YLZUsername;
	}

	public void setYLZUsername(String yLZUsername) {
		YLZUsername = yLZUsername;
	}

	public String getYLZPassword() {
		return YLZPassword;
	}

	public void setYLZPassword(String yLZPassword) {
		YLZPassword = yLZPassword;
	}
	
}

