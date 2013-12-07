package com.doufangbian.entity;

import java.util.List;

/**
 * 大组小组关系表
 * @author C_C
 *
 */
public class Dfb_catgroup_map {
	
	private int id;//关系id
	private int cat_id;//大组id
	private int group_id;//小组id
	private String updatetime;//更新时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int groupId) {
		group_id = groupId;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdatetime() {
		return updatetime;
	}

	
}
