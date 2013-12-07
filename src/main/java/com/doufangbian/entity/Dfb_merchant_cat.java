package com.doufangbian.entity;
/**
 * 大分组
 * @author C_C
 *
 */
public class Dfb_merchant_cat {
	private int id;
	private String name;
	private String cat_image;
	private String updatetime;
	
	private int merchantCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCat_image() {
		return cat_image;
	}
	public void setCat_image(String catImage) {
		cat_image = catImage;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}
	public int getMerchantCount() {
		return merchantCount;
	}
	
}
