package com.doufangbian.entity;

/**
 * 微博回复表
 * @author C_C
 *
 */
public class Weibocomentreplylist {
	private int id;
	private int commentid;
	private int uid;
	private int replyuid;
	private String replycontent;
	private String replyTime;
	private int id_1;
	private String username_1;
	private int id_2;
	private String username_2;
	
	
	private String othername;//回复评论用户
	
	private String username;//被评论的用户
	
	
	
	
	public String getOthername() {
		return othername;
	}
	public void setOthername(String othername) {
		this.othername = othername;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getReplyuid() {
		return replyuid;
	}
	public void setReplyuid(int replyuid) {
		this.replyuid = replyuid;
	}
	public String getReplycontent() {
		return replycontent;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public int getId_1() {
		return id_1;
	}
	public void setId_1(int id_1) {
		this.id_1 = id_1;
	}
	public String getUsername_1() {
		return username_1;
	}
	public void setUsername_1(String username_1) {
		this.username_1 = username_1;
	}
	public int getId_2() {
		return id_2;
	}
	public void setId_2(int id_2) {
		this.id_2 = id_2;
	}
	public String getUsername_2() {
		return username_2;
	}
	public void setUsername_2(String username_2) {
		this.username_2 = username_2;
	}
	public Weibocomentreplylist() {
		// TODO Auto-generated constructor stub
	}
	public Weibocomentreplylist(int commentid, int uid, int replyuid,
			String replycontent, String replyTime, int id_1, String username_1,
			int id_2, String username_2) {
		this.commentid = commentid;
		this.uid = uid;
		this.replyuid = replyuid;
		this.replycontent = replycontent;
		this.replyTime = replyTime;
		this.id_1 = id_1;
		this.username_1 = username_1;
		this.id_2 = id_2;
		this.username_2 = username_2;
	}
	
}
