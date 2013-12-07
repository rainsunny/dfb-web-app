package com.doufangbian.entity;

public class Userinfo {

	
	private int id;//用户ID
	private String headphoto;//图片
	private String username;//用户名
	private String password;//密码
	private int gender;//性别
	private String phone;//电话
	private String qq;//qq
	private int cash;//豆币
	private String checkin;//最后登录时间
	private String message;//消息
	private int status;//状态
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getHeadphoto() {
		return headphoto;
	}
	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
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
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Userinfo() {
		// TODO Auto-generated constructor stub
	}
	public Userinfo(String headphoto, String username, String password, int gender,
			String phone, String qq, int cash, String checkin, String message,
			int status) {
		this.headphoto = headphoto;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.phone = phone;
		this.qq = qq;
		this.cash = cash;
		this.checkin = checkin;
		this.message = message;
		this.status = status;
	}
	

	
}
