package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.Dfb_merchant_cat;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class Dfb_merchant_catDao {
	/**
	 * 查询所有的大组信息
	 * @return
	 */
	public List<Dfb_merchant_cat> queryAll(){
		String sql ="SELECT *  FROM dfb_merchant_cat";
		return DBHelper.commQuery(sql, Dfb_merchant_cat.class);
	}
	
	
	/**
	 * 
	 * 查询所有大组信息
	 * @param countyID 地区id
	 * @return 商家信息
	 */
	public List<Dfb_merchant_cat> getDfb_merchant_catAll(int countyID){
		
		String sql="select *,(select count(*) from dfb_catgroup_map,dfb_groupmerchant_map,merchantinfo where dfb_catgroup_map.cat_id=dfb_merchant_cat.id and merchantinfo.countyID=? and dfb_catgroup_map.group_id=dfb_groupmerchant_map.group_id and dfb_groupmerchant_map.merchant_id=merchantinfo.id) as merchantCount  from dfb_merchant_cat";
		
		
		return DBHelper.commQuery(sql, Dfb_merchant_cat.class,countyID);
	}
	/**
	 * 大组分页查询
	 * @param pageNo
	 * @return
	 */
	public PageModel<Dfb_merchant_cat> queryAllCat(int pageNo){
		PageModel<Dfb_merchant_cat> pm = new PageModel<Dfb_merchant_cat>();
		
		pm.setSumCount(queryCount());
		
		pm.setCurrentPage(pageNo);
		
		String sql ="SELECT * FROM dfb_merchant_cat order by id desc limit "+(pageNo-1)*pm.getSize()+","+pm.getSize();
		
		List<Dfb_merchant_cat> list = DBHelper.commQuery(sql, Dfb_merchant_cat.class);
		
		pm.setData(list);
		
		return pm;
	}
	/**
	 * 查询大组的总条数
	 * @return
	 */
	public Integer queryCount(){
		String sql = "SELECT count(*) FROM dfb_merchant_cat";
		return DBHelper.commonQueryCount(sql);
	}
	
	/**
	 * 根据id查询大组信息
	 * @param groupId
	 * @return
	 */
	public Dfb_merchant_cat queryById(String catId){
		String sql = "SELECT * FROM dfb_merchant_cat where id=?";
		List<Dfb_merchant_cat> list = DBHelper.commQuery(sql, Dfb_merchant_cat.class, catId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据id 删除大组信息
	 * @param catId
	 * @return
	 */
	public boolean deleteById(String catId){
		String sql ="delete from dfb_merchant_cat where id = ?";
		return DBHelper.commonUpdate(sql, catId);
	}
	/**
	 * 根据Id查询图片
	 * @param id
	 * @return
	 */
	public List<String> queryImageById(String id){
		String sql = "select cat_image from dfb_merchant_cat where id =?";
		return DBHelper.queryImageName(sql, id);
	}
	/**
	 * 根据id修改大组信息
	 * @param name
	 * @param catId
	 * @return
	 */
	public boolean updateById(String name,String cat_image,String updatetime,String catId){
		String sql = "update dfb_merchant_cat set name=?,cat_image=?,updatetime=? where id = ?";
		return DBHelper.commonUpdate(sql, name,cat_image,updatetime,catId);
	}
	/**
	 * 添加大组
	 * @param name
	 * @return
	 */
	public boolean addCat(String name,String cat_image,String updatetime){
		String sql = "insert into dfb_merchant_cat(name,cat_image,updatetime) values(?,?,?)";
		return DBHelper.commonUpdate(sql, name,cat_image,updatetime);
	}
	
	
	/**
	 * 
	 * 获取最新商家大组列表
	 * @param updateTime
	 * @return
	 */
	public List<Dfb_merchant_cat> getNewMerchantCat(String updateTime,int countyID) {
		
		String sql="select *,(select count(*) from dfb_catgroup_map,dfb_groupmerchant_map,merchantinfo where dfb_catgroup_map.cat_id=dfb_merchant_cat.id and merchantinfo.countyID=? and dfb_catgroup_map.group_id=dfb_groupmerchant_map.group_id and dfb_groupmerchant_map.merchant_id=merchantinfo.id) as merchantCount   from dfb_merchant_cat where updatetime>?";
		
		return DBHelper.commQuery(sql, Dfb_merchant_cat.class,countyID,updateTime);
	}
	
	
	public int getCatMerchantCount(int catID,int countID){
		
		String sql="select count(*) from dfb_catgroup_map,dfb_groupmerchant_map,merchantinfo where dfb_catgroup_map.cat_id=? and merchantinfo.countyID=? and dfb_catgroup_map.group_id=dfb_groupmerchant_map.group_id and dfb_groupmerchant_map.merchant_id=merchantinfo.id";
		
		
		return DBHelper.commonQueryCount(sql, catID,countID);
	}
}
