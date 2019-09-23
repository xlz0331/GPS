package com.hwagain.eagle.track.entity;

public class TrackInfoVo {
	private static final long serialVersionUID = 1L;
	private Long loc_time;
	private Double latitude;
	private Double longitude;
	private Double radius;
	private Double speed;
	private Integer direction;
	private String coord_type_input;
	public Long getLoc_time() {
		return loc_time;
	}
	public void setLoc_time(Long loc_time) {
		this.loc_time = loc_time;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public String getCoord_type_input() {
		return coord_type_input;
	}
	public void setCoord_type_input(String coord_type_input) {
		this.coord_type_input = coord_type_input;
	}
	
}
