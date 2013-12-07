package com.doufangbian.entity;

public class UserseeListInfo {

	private int id;//浏览id
	private int uid;//被查看用户id
	private int otherid;//当前查看用户id
	private String seetime;//查看时间
	
	private int wid;//微博id
	private String username;//浏览者姓名
	private String headphoto;//头像
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadphoto() {
		return headphoto;
	}
	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getOtherid() {
		return otherid;
	}
	public void setOtherid(int otherid) {
		this.otherid = otherid;
	}

	public String getSeetime() {
		return seetime;
	}
	public void setSeetime(String seetime) {
		this.seetime = seetime;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public int getWid() {
		return wid;
	}
	

}
