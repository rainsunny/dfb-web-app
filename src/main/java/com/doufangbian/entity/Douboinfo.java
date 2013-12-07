package com.doufangbian.entity;

public class Douboinfo {

	private int id;//微博ID
	private String content;//微博内容
	private String time;//微博时间
	private int up;//顶
	private int down;//踩
	private int share;//分享
	private int uid;//用户IDphoto
	private int countyID;//县级ID
	private String last_reply;//评论最后修改时间
	
	private String photo;//图片名称
	
	private String username;//用户名
	private String name;//地区名称
	private int countPL;//手机端不使用（此为服务器程序字段客户端不使用）
	
	private String headphoto;//头像
	
	private String updatetime;//修改时间
	
	private int comentcount;//评论条数
	
	private int seecount;//浏览次数
	
	private int upcount;//顶总条数
	
	private int downcount;//踩总条数
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
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
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCountyID() {
		return countyID;
	}
	public void setCountyID(int countyID) {
		this.countyID = countyID;
	}
	public String getLast_reply() {
		return last_reply;
	}
	public void setLast_reply(String lastReply) {
		this.last_reply = lastReply;
	}
	public int getCountPL() {
		return countPL;
	}
	public void setCountPL(int countPL) {
		this.countPL = countPL;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Douboinfo() {
	}
	public Douboinfo(String content, String time, int up, int down, int share,
			int uid, int countyID, String lastReply) {
		this.content = content;
		this.time = time;
		this.up = up;
		this.down = down;
		this.share = share;
		this.uid = uid;
		this.countyID = countyID;
		this.last_reply = lastReply;
	}
	public Douboinfo(String content, String time, int up, int down, int share,
			int uid, int countyID, String lastReply,String photo) {

		this.content = content;
		this.time = time;
		this.up = up;
		this.down = down;
		this.share = share;
		this.uid = uid;
		this.countyID = countyID;
		this.last_reply = lastReply;
		this.photo=photo;
		
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdatetime() {
		return updatetime;
	}

	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}
	public String getHeadphoto() {
		return headphoto;
	}
	public void setComentcount(int comentcount) {
		this.comentcount = comentcount;
	}
	public int getComentcount() {
		return comentcount;
	}
	public void setSeecount(int seecount) {
		this.seecount = seecount;
	}
	public int getSeecount() {
		return seecount;
	}
	public void setUpcount(int upcount) {
		this.upcount = upcount;
	}
	public int getUpcount() {
		return upcount;
	}
	public void setDowncount(int downcount) {
		this.downcount = downcount;
	}
	public int getDowncount() {
		return downcount;
	}

	
}
