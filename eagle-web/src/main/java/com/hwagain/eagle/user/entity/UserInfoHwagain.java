package com.hwagain.eagle.user.entity;

import com.hwagain.eagle.org.entity.OrgRelationship;
import com.hwagain.eagle.user.dto.UserInfoDto;
public class UserInfoHwagain {
	private UserInfoDto userInfo;
	private OrgRelationship orgRelationship;
	public UserInfoDto getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}
	public OrgRelationship getOrgRelationship() {
		return orgRelationship;
	}
	public void setOrgRelationship(OrgRelationship orgRelationship) {
		this.orgRelationship = orgRelationship;
	}
	

}
