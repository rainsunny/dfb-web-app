package com.doufangbian.entity;

public class Merchantseelist {

	private int id;//查看表记录id
	private int merchantid;//商户id
	private int uid;//用户id
	private String seetime;//查看时间
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(int merchantid) {
		this.merchantid = merchantid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getSeetime() {
		return seetime;
	}
	public void setSeetime(String seetime) {
		this.seetime = seetime;
	}


}
