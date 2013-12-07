package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.Dfb_groupmerchant_map;
import com.doufangbian.util.DBHelper;

public class Dfb_groupmerchant_mapDao {
	/**
	 * 添加商家到小组
	 * @return
	 */
	public boolean addGroupmerchant(String groupId,String[] merchants){
		String sql = "insert into dfb_groupmerchant_map(group_id,merchant_id) values ";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < merchants.length; i++) {
			sb.append("("+groupId+","+merchants[i]+"),");
		}
		sql += sb.substring(0,sb.length()-1); 
		return DBHelper.commonUpdate(sql);
	}
	public boolean addGroupmerchant(String[] groups,String merchantId){
		String sql = "insert into dfb_groupmerchant_map(group_id,merchant_id) values ";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < groups.length; i++) {
			sb.append("("+groups[i]+","+merchantId+"),");
		}
		sql += sb.substring(0,sb.length()-1); 
		return DBHelper.commonUpdate(sql);
	}
	/**
	 * 移除在组的商家
	 * @param groupId
	 * @param merchantId
	 * @return
	 */
	
	public boolean delGroupmerchant(String groupId,String[] merchants){
		String sql = "delete from dfb_groupmerchant_map where group_id=? and merchant_id in ";
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < merchants.length; i++) {
			sb.append(merchants[i]+",");
		}
		sql += sb.substring(0,sb.length()-1)+")";
		return DBHelper.commonUpdate(sql, groupId);
	}
	public boolean delGroupmerchant(String[] groups,String merchantId){
		String sql = "delete from dfb_groupmerchant_map where merchant_id = ? and group_id in ";
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < groups.length; i++) {
			sb.append(groups[i]+",");
		}
		sql += sb.substring(0,sb.length()-1)+")";
		return DBHelper.commonUpdate(sql, merchantId);
	}
	/**
	 * 删除关系中有关某小组id的关系
	 * @param 
	 * @return
	 */
	public boolean delCatgroupByCatId(String groupId){
		String sql = "delete from dfb_groupmerchant_map where group_id = ?";
		return DBHelper.commonUpdate(sql, groupId);
	}
	/**
	 * 删除小组商家中的关系   商家
	 * @param merchantId
	 * @return
	 */
	public boolean delgroupmerchant(String merchantId){
		String sql = "delete from dfb_groupmerchant_map where merchant_id= ?";
		return DBHelper.commonUpdate(sql, merchantId);
	}
	
	
	/**
	 * 
	 * 查询某小组和商家关系表
	 * @param groupID
	 * @param countyID
	 * @return
	 */
	public List<Dfb_groupmerchant_map> getGroupMerchantMapByGroupID(int countyID) {
		String sql="select dfb_groupmerchant_map.* from dfb_groupmerchant_map,merchantinfo where merchantinfo.countyID=? and dfb_groupmerchant_map.merchant_id=merchantinfo.id";
		

		return DBHelper.commQuery(sql, Dfb_groupmerchant_map.class,countyID);
	}
	
	
	/**
	 * 
	 * 某小组的商家是否有更新
	 * @param groupID
	 * @param countyID
	 * @param updateTime
	 * @return
	 */
	public List<Dfb_groupmerchant_map> getNewGroupMerchantMap(int countyID, String updateTime) {
		
		String sql="select dfb_groupmerchant_map.* from dfb_groupmerchant_map,merchantinfo where  merchantinfo.countyID=? and dfb_groupmerchant_map.merchant_id=merchantinfo.id and UNIX_TIMESTAMP(dfb_groupmerchant_map.updatetime)>UNIX_TIMESTAMP(?)";
		
		
		
		return DBHelper.commQuery(sql, Dfb_groupmerchant_map.class,countyID,updateTime);
	}
	
}
