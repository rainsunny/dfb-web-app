package com.doufangbian.entity;

public class CityInfo {

	private int cityID;
	private String name;
	private int provincialID;
	
	
	
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CityInfo(int cityID, String name) {
		this.cityID = cityID;
		this.name = name;
	}
	
	public int getProvincialID() {
		return provincialID;
	}
	public void setProvincialID(int provincialID) {
		this.provincialID = provincialID;
	}
	public CityInfo() {
		// TODO Auto-generated constructor stub
	}
	

	
}
