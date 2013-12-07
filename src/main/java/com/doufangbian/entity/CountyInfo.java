package com.doufangbian.entity;

public class CountyInfo {

	private int countyID;
	private int provincialID;
	private int cityId;
	private String name;
	private String wapUrl;
	
	public CountyInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public CountyInfo(int countyID, int provincialID, int cityId, String name,
			String wapUrl) {
		this.countyID = countyID;
		this.provincialID = provincialID;
		this.cityId = cityId;
		this.name = name;
		this.wapUrl = wapUrl;
	}
	public int getCountyID() {
		return countyID;
	}
	public void setCountyID(int countyID) {
		this.countyID = countyID;
	}
	public int getProvincialID() {
		return provincialID;
	}
	public void setProvincialID(int provincialID) {
		this.provincialID = provincialID;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWapUrl() {
		return wapUrl;
	}
	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

	
	
}
