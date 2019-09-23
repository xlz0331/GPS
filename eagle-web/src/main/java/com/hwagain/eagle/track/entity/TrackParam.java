package com.hwagain.eagle.track.entity;

import java.util.List;

public class TrackParam {
	private static final long serialVersionUID = 1L;
	private String ak;
	private List<TrackInfoVo> point_list;
	public String getak() {
		return ak;
	}
	public void setak(String ak) {
		this.ak = ak;
	}
	public List<TrackInfoVo> getPoint_list() {
		return point_list;
	}
	public void setPoint_list(List<TrackInfoVo> point_list) {
		this.point_list = point_list;
	}
	

}
