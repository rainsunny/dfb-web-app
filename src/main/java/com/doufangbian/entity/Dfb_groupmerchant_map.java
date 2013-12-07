package com.doufangbian.entity;
/**
 * 加入小组的关系表
 * @author C_C
 *
 */
public class Dfb_groupmerchant_map {
	private int id;
	private int group_id;
	private int merchant_id;
	private String updatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int groupId) {
		group_id = groupId;
	}
	public int getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(int merchantId) {
		merchant_id = merchantId;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	
	
}
