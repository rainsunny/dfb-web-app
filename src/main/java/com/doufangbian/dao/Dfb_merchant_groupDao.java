package com.doufangbian.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.doufangbian.entity.Dfb_merchant_group;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

/**
 * 小组
 * @author C_C
 *
 */
public class Dfb_merchant_groupDao {
	/**
	 * 查询小组信息   根据大组Id  大组Id可以不存在
	 * @param catId
	 * @return
	 */
	public List<Dfb_merchant_group> queryByCatId(String catId){
		
		String sql ="SELECT * FROM dfb_merchant_group where dfb_merchant_group.id IN (SELECT dfb_catgroup_map.group_id FROM dfb_catgroup_map)";
		if(catId!=null && !catId.equals("") && !"0".equals(catId)){
			sql="SELECT * FROM dfb_merchant_group where dfb_merchant_group.id IN (SELECT dfb_catgroup_map.group_id FROM dfb_catgroup_map where dfb_catgroup_map.cat_id="+catId+")";
		}
		
		List<Dfb_merchant_group> list = DBHelper.commQuery(sql, Dfb_merchant_group.class);
		
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	/**
	 * 查询全部的小组信息
	 * @return
	 */
	public PageModel<Dfb_merchant_group> queryAll(int pageNo,int level,int areaID,String catId,String chiose,String keywords){
		PageModel<Dfb_merchant_group> pm =new PageModel<Dfb_merchant_group>();
		
		pm.setSumCount(queryAllSumCount(level, areaID, catId, chiose, keywords));
		
		pm.setCurrentPage(pageNo);
		
		String sql = "SELECT dfb_merchant_group.id,dfb_merchant_group.`name`,dfb_merchant_group.updatetime FROM dfb_merchant_group where  dfb_merchant_group.name like ?";
		/*查询某大组下的小组（已经加入进来的组）*/
		if(chiose!=null && !"".equals(chiose) && "yes".equals(chiose)){
			sql = "SELECT * FROM `dfb_merchant_group`  WHERE id IN (SELECT group_id FROM dfb_catgroup_map WHERE cat_id="+catId+") and name like ?";
		}
		/*查询某大组下未加入的小组*/
		if(chiose!=null && !"".equals(chiose) && "no".equals(chiose)){
			sql = "SELECT * FROM `dfb_merchant_group`  WHERE id NOT IN (SELECT group_id FROM dfb_catgroup_map WHERE cat_id="+catId+") and name like ?";
		}
//		if(level!=0){
//			sql+=" and dfb_merchant_group.countyID="+areaID;
//		}
		sql += "  ORDER BY id DESC LIMIT "+(pageNo-1)*pm.getSize()+","+pm.getSize();
		
		List<Dfb_merchant_group> list = DBHelper.commQuery(sql, Dfb_merchant_group.class,keywords);
		
		pm.setData(list);
		
		return pm;
	}
	
	
	/**
	 * 查询总条数
	 * @return
	 */
	public Integer queryAllSumCount(int level,int areaID, String catId,String chiose,String keywords){
		
		String sql = "SELECT count(*) FROM dfb_merchant_group ,countyinfo where  dfb_merchant_group.name like ?";
		/*查询某大组下的小组（已经加入进来的组）*/
		if(chiose!=null && !"".equals(chiose) && "yes".equals(chiose)){
			sql = "SELECT count(*) FROM `dfb_merchant_group`  WHERE id IN (SELECT group_id FROM dfb_catgroup_map WHERE cat_id="+catId+") and name like ?";
		}
		/*查询某大组下未加入的小组*/
		if(chiose!=null && !"".equals(chiose) && "no".equals(chiose)){
			sql = "SELECT count(*) FROM `dfb_merchant_group`  WHERE id NOT IN (SELECT group_id FROM dfb_catgroup_map WHERE cat_id="+catId+") and name like ?";
		}
//		if(level!=0){
//			sql+=" and dfb_merchant_group.countyID="+areaID;
//		}
		return DBHelper.commonQueryCount(sql,keywords);
	}
	/**
	 * 查询商家所在组
	 * @param pageNo
	 * @param level
	 * @param areaID
	 * @param mid
	 * @param chiose
	 * @return
	 */
	public PageModel<Dfb_merchant_group> querybymid(int pageNo,int level,int areaID,String mid,String chiose){
		PageModel<Dfb_merchant_group> pm =new PageModel<Dfb_merchant_group>();
		
		pm.setSumCount(querybymidSumCount(level, areaID, mid, chiose));
		
		pm.setCurrentPage(pageNo);
		String sql ="";
		/*查询某商家加入小组（已经加入进来的组）*/
		if(chiose==null || "".equals(chiose) || "yes".equals(chiose)){
			sql = "SELECT * FROM `dfb_merchant_group` WHERE id IN (SELECT group_id FROM  `dfb_groupmerchant_map` WHERE merchant_id = "+mid+")";
		}
		/*查询某商家未加入的小组*/
		if(chiose!=null && !"".equals(chiose) && "no".equals(chiose)){
			sql = "SELECT * FROM `dfb_merchant_group` WHERE id NOT IN (SELECT group_id FROM  `dfb_groupmerchant_map` WHERE merchant_id = "+mid+")";
		}
		if(level!=0){
			sql+=" and dfb_merchant_group.countyID="+areaID;
		}
		sql += "  ORDER BY id DESC LIMIT "+(pageNo-1)*pm.getSize()+","+pm.getSize();
		
		List<Dfb_merchant_group> list = DBHelper.commQuery(sql, Dfb_merchant_group.class);
		
		pm.setData(list);
		
		return pm;
	}
	
	public Integer querybymidSumCount(int level,int areaID,String mid,String chiose){
		String sql ="";
		/*查询某商家加入小组（已经加入进来的组）*/
		if(chiose==null || "".equals(chiose) || "yes".equals(chiose)){
			sql = "SELECT count(*) FROM `dfb_merchant_group` WHERE id IN (SELECT group_id FROM  `dfb_groupmerchant_map` WHERE merchant_id = "+mid+")";
		}
		/*查询某商家未加入的小组*/
		if(chiose!=null && !"".equals(chiose) && "no".equals(chiose)){
			sql = "SELECT count(*) FROM `dfb_merchant_group` WHERE id NOT IN (SELECT group_id FROM  `dfb_groupmerchant_map` WHERE merchant_id = "+mid+")";
		}
		if(level!=0){
			sql+=" and dfb_merchant_group.countyID="+areaID;
		}
		return DBHelper.commonQueryCount(sql);
	}
	/**
	 * 根据id查询小组信息
	 * @param groupId
	 * @return
	 */
	public Dfb_merchant_group queryById(String groupId){
		String sql = "SELECT * FROM `dfb_merchant_group` where id=?";
		List<Dfb_merchant_group> list = DBHelper.commQuery(sql, Dfb_merchant_group.class, groupId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 删除小组信息(根据Id)
	 * @param groupId
	 * @return
	 */
	public boolean deleteById(String groupId){
		String sql = "delete from dfb_merchant_group where id = ?"; 
		return DBHelper.commonUpdate(sql, groupId);
	}
	/**
	 * 修改小组信息
	 * @param groupId
	 * @return
	 */
	public boolean updateById(String name,String groupId){
		String sql = "update dfb_merchant_group set name=? where id=?";
		return DBHelper.commonUpdate(sql, name,groupId);
	}
	/**
	 * 添加小组
	 * @param name
	 * @return
	 */
	public boolean addGroup(String name,String areaID){
		String sql = "insert into dfb_merchant_group(name,updatetime) values(?,?)";
		
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		return DBHelper.commonUpdate(sql, name,time);
	}
	
	
	
	
	
	
	
	
	/**
	 * 根据地区编号查询该地区的所有小分组
	 * @param countyID
	 * @return
	 */
	public List<Dfb_merchant_group> getAllGroup(int countyID) {
		
		String sql="select * from dfb_merchant_group";
		
		return DBHelper.commQuery(sql, Dfb_merchant_group.class);
	}
	
	
	
	/**
	 * 检查该地区小组是否有更新
	 * @param updateTime 最后更新时间
	 * @return 小组列表
	 */
	public List<Dfb_merchant_group> getNewMerchant_group(String updateTime,int countyID) {

		String sql="select * from dfb_merchant_group where  UNIX_TIMESTAMP(updatetime)>UNIX_TIMESTAMP(?)";
		
		return DBHelper.commQuery(sql, Dfb_merchant_group.class,updateTime);
	}
	
	
}
