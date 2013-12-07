package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.CelebrityOrder;
import com.doufangbian.util.DBHelper;

/**
 * 
 * 名人榜操作类
 * @author SONY
 *
 */
public class CelebrityOrderDao {
	
	public List<CelebrityOrder> getCelebrityOrderList(int pagenum){
		
		String sql="select userinfo.id,userinfo.username,SUM(up) up,SUM(down) down,userinfo.headphoto from weiboinfo,userinfo where weiboinfo.uid=userinfo.id group by uid order by up desc limit ?,20";
	
		
		return DBHelper.commQuery(sql, CelebrityOrder.class, pagenum*20);
	}

	
	/**
	 * 
	 * 获取某个用户的笑脸哭脸总数
	 * @param id
	 * @return
	 */
	public CelebrityOrder getCelebrityOrderByID(int id){
		
		
		String sql="select userinfo.id,userinfo.username,SUM(up) up,SUM(down) down,userinfo.headphoto from weiboinfo,userinfo where userinfo.id=? and weiboinfo.uid=userinfo.id";
		
		List<CelebrityOrder> list=DBHelper.commQuery(sql, CelebrityOrder.class, id);
		
		if(list!=null){
			
			if(list.size()>0){
			
				return list.get(0);
				
			}
			
			
			
		};
		
		return null; 
		
	}
	
}
