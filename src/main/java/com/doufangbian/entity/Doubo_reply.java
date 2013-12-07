package com.doufangbian.entity;

/**
 * 微博评论
 * @author C_C
 *
 */
public class Doubo_reply {
	
	private int id;     //id
	private String time;  //评论时间
	private int uid; // 用户id
	private int wid; //微博id
	
	private String othername;//回复评论用户
	
	private String headphoto;//回复者的头像
	
	private String username;  // 用户名称
	
	private String replycontent;//评论内容
	

	private String content;  //豆博内容
	
	
	
	public String getOthername() {
		return othername;
	}
	public void setOthername(String othername) {
		this.othername = othername;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}
	public String getHeadphoto() {
		return headphoto;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	public String getReplycontent() {
		return replycontent;
	}


	
	

	
	
}
