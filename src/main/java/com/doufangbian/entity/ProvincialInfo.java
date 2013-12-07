package com.doufangbian.entity;

public class ProvincialInfo {

	private int provincialID;
	private String name;
	
	
	/**
	 * 
	 * 县级相关全部信息
	 * @param provincialID
	 * @param name
	 */
	public ProvincialInfo(int provincialID, String name) {
		this.provincialID = provincialID;
		this.name = name;
	}
	public ProvincialInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public int getProvincialID() {
		return provincialID;
	}
	public void setProvincialID(int provincialID) {
		this.provincialID = provincialID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

	
}
