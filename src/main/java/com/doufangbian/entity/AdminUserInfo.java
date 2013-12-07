package com.doufangbian.entity;

public class AdminUserInfo {

	private int ID;
	private String username;
	private String password;
	private int level;
	private int areaId;// 地区ID
	private String areaName;

	public AdminUserInfo(int ID, String username, String password, int level,
			int areaId) {

		this.ID = ID;
		this.username = username;
		this.password = password;
		this.level = level;
		this.areaId = areaId;
	}

	/*
	 * 
	 * 保存index需要的信息（这些信息保存在session中）
	 */
	public AdminUserInfo(int level, int areaId, String areaName) {
		this.level = level;
		this.areaId = areaId;
		this.areaName = areaName;
	}

	public AdminUserInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaName() {
		return areaName;
	}

}
