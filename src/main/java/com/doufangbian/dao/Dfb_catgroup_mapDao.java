package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.Dfb_catgroup_map;
import com.doufangbian.util.DBHelper;

public class Dfb_catgroup_mapDao {
	/**
	 * 添加小组到大组
	 * @return
	 */
	public boolean addCatgroup(String catId,String[] gurops){
		String sql = "insert into dfb_catgroup_map(cat_id,group_id) values ";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < gurops.length; i++) {
			sb.append("("+catId+","+gurops[i]+"),");
		}
		sql += sb.substring(0,sb.length()-1);
		return DBHelper.commonUpdate(sql);
	}
	/**
	 * 删除关系中有关某大组id的关系
	 * @param catId
	 * @return
	 */
	public boolean delCatgroupByCatId(String catId){
		String sql = "delete from dfb_catgroup_map where cat_id = ?";
		return DBHelper.commonUpdate(sql, catId);
	}
	/**
	 * 从大组里面移除信息
	 * @param catId
	 * @param groupId
	 * @return
	 */
	public boolean delCatgroupByGroupId(String catId,String groupId){
		String sql ="delete from dfb_catgroup_map where cat_id = ? and group_id = ?";
		return DBHelper.commonUpdate(sql, catId,groupId);
	}
	
	
	/**
	 * 
	 * 根据大组id和地区编号查询该大组所有小组
	 * @param catID 大组id
	 * @param countyID 地区id
	 * @return
	 */
	public List<Dfb_catgroup_map> getCatGroupMapByCatID(int countyID) {

		String sql="select dfb_catgroup_map.*,countyid from dfb_catgroup_map,dfb_merchant_group where countyid=? and dfb_catgroup_map.group_id=dfb_merchant_group.id";

		return DBHelper.commQuery(sql, Dfb_catgroup_map.class,countyID);
	}
	
	/**
	 * 
	 * 查询某大组的小组是否有更新
	 * @param catID
	 * @param countyID
	 * @param updateTime
	 * @return
	 */
	public List<Dfb_catgroup_map> getNewCatGroupMap(int countyID,
			String updateTime) {
		String sql="select dfb_catgroup_map.*,countyid from dfb_catgroup_map,dfb_merchant_group where countyid=? and dfb_catgroup_map.group_id=dfb_merchant_group.id and UNIX_TIMESTAMP(dfb_catgroup_map.updatetime)>UNIX_TIMESTAMP(?)";
		
		return DBHelper.commQuery(sql, Dfb_catgroup_map.class,countyID,updateTime);
	}
}
