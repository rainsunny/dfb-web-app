package com.doufangbian.entity;

public class AreaInfo {

	private int areaId;

	private String name;

	private String wapUrl;

	/**
	 * 
	 * 获取管理员用户的基本信息
	 * 
	 * @param name  管理员名称
	 * @param url	该地区手机网址
	 * 
	 */
	public AreaInfo(String name, String wapUrl) {
		this.name = name;
		this.wapUrl = wapUrl;
	}

	public int getId() {
		return areaId;
	}

	public void setId(int areaId) {
		this.areaId = areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return wapUrl;
	}

	public void setUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

}
