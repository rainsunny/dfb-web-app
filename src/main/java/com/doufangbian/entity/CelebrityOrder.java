package com.doufangbian.entity;

/**
 * 
 * 名人榜
 * @author SONY
 *
 */
public class CelebrityOrder {

	private int id;//用户id
	
	private String username;//用户名
	
	private int up;//顶条数
	
	private int down;//踩条数
	
	
	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	private String headphoto;//用户头像

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}

	public String getHeadphoto() {
		return headphoto;
	}
	
	
}
