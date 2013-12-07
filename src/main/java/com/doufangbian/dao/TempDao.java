package com.doufangbian.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.doufangbian.entity.CelebrityOrder;
import com.doufangbian.entity.CityInfo;
import com.doufangbian.entity.CountyInfo;
import com.doufangbian.entity.Dfb_merchant_group;
import com.doufangbian.entity.Doubo_reply;
import com.doufangbian.entity.Douboinfo;
import com.doufangbian.entity.MerchantInfo;
import com.doufangbian.entity.ProvincialInfo;
import com.doufangbian.entity.Userimagelist;
import com.doufangbian.entity.UserseeListInfo;
import com.doufangbian.util.DBHelper;

/**
 * 
 * android临时Dao方法不缓存
 * 
 * @author SONY
 * 
 */
public class TempDao {

	/*
	 * 根据大组id和地区id查询该大组所有的小组（备用）
	 * 
	 * @param catID
	 * 
	 * @param countyID
	 * 
	 * @return
	 */
	public List<Dfb_merchant_group> getMerchantGroupByCatCountyID(int catID,
			int countyID) {

		String sql = "select dfb_merchant_group.* from dfb_catgroup_map,dfb_merchant_group where dfb_catgroup_map.cat_id=? and countyid=? and dfb_catgroup_map.group_id=dfb_merchant_group.id";

		return DBHelper.commQuery(sql, Dfb_merchant_group.class, catID,
				countyID);
	}

	/*
	 * 根据大组id和地区id查询该大组所有的小组
	 * 
	 * @param catID
	 * 
	 * @param countyID
	 * 
	 * @return
	 */
	public List<Dfb_merchant_group> getMerchantGroupByCatCountyID(int catID) {

		String sql = "select dfb_merchant_group.* from dfb_catgroup_map,dfb_merchant_group where dfb_catgroup_map.cat_id=?  and dfb_catgroup_map.group_id=dfb_merchant_group.id group by dfb_merchant_group.name";

		return DBHelper.commQuery(sql, Dfb_merchant_group.class, catID);
	}
	
	/**
	 * 
	 * 根据小组id获取该小组的商家列表(备用)
	 * 
	 * @param groupID
	 *            小组id
	 * @return 商家列表
	 */
	public List<MerchantInfo> getMerchantInfoByGroupID(int groupID) {

		String sql = "select a.*,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount from dfb_groupmerchant_map,merchantinfo as a where dfb_groupmerchant_map.merchant_id=a.id and dfb_groupmerchant_map.group_id=?";
		
		return DBHelper.commQuery(sql, MerchantInfo.class, groupID);
	}

	
	/**
	 * 
	 * 根据小组id获取该小组的商家列表
	 * 
	 * @param groupID
	 *            小组id
	 * @return 商家列表
	 */
	public List<MerchantInfo> getMerchantInfoByGroupID(int groupID,int  countyID) {

		String sql = "select a.*,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount from dfb_groupmerchant_map,merchantinfo as a where dfb_groupmerchant_map.merchant_id=a.id and dfb_groupmerchant_map.group_id=? and a.countyID=? group by a.id order by a.last_reply desc";
		
		System.out.println("select a.*,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount from dfb_groupmerchant_map,merchantinfo as a where dfb_groupmerchant_map.merchant_id=a.id and dfb_groupmerchant_map.group_id="+groupID+" and a.countyID="+countyID+"");
		return DBHelper.commQuery(sql, MerchantInfo.class, groupID,countyID);
	}
	
	
	/**
	 * 
	 * 获取新豆博
	 * 
	 * @param lastReply
	 *            最后回复时间
	 * @param countyID
	 *            县级id
	 * @return 豆博列表
	 */
	public List<Douboinfo> getNewDouboInfoList(String lastReply, int countyID) {

		System.out.println("获取新豆博");
		
		String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(select count(*) from weiboinfo where UNIX_TIMESTAMP(last_reply)>UNIX_TIMESTAMP(?)) as updatecount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount  from weiboinfo,countyinfo,userinfo where unix_timestamp(last_reply)>unix_timestamp(?) "
				+ (countyID == 0 ? "" : "and weiboinfo.countyID=? ")
				+ " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";

		
		String strSqls = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(select count(*) from weiboinfo where UNIX_TIMESTAMP(last_reply)>UNIX_TIMESTAMP('"+lastReply+"')) as updatecount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount  from weiboinfo,countyinfo,userinfo where unix_timestamp(last_reply)>unix_timestamp('"+lastReply+"') "
			+ (countyID == 0 ? "" : "and weiboinfo.countyID="+countyID+" ")
			+ " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";

		
		System.out.println(strSqls);
		
		List<Douboinfo> list = null;

		if (countyID == 0) {

			list=DBHelper.commQuery(sql, Douboinfo.class, lastReply,lastReply);

		} else {
			list=DBHelper.commQuery(sql, Douboinfo.class, lastReply,lastReply,
					countyID);
		}

		// 批量修改浏览量

		if (list != null) {

			if (list.size() != 0) {

				String startTime = list.get(0).getLast_reply();

				String endTime = list.get(list.size() - 1).getLast_reply();

				String strSql = "update weiboinfo,countyinfo,userinfo set seecount=seecount+1 where unix_timestamp(last_reply)<=unix_timestamp(?) and unix_timestamp(last_reply)>=unix_timestamp(?)  "
						+ (countyID == 0 ? "" : "and weiboinfo.countyID=? ")
						+ " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id";

				
			
				if (countyID == 0) {

					DBHelper.commonUpdate(strSql, startTime,endTime);
				} else {

					DBHelper.commonUpdate(strSql, startTime,endTime, countyID);
				}

			}

		}

		

		//更新操作后再次查询
		if (countyID == 0) {

			list=DBHelper.commQuery(sql, Douboinfo.class, lastReply,lastReply);

		} else {
			list=DBHelper.commQuery(sql, Douboinfo.class, lastReply,lastReply,
					countyID);
		}
		
		return list;
	}

	/**
	 * 
	 * 获取旧豆博
	 * 
	 * @param fristReply
	 *            早期回复时间
	 * @param countyID
	 *            县级id
	 * @return 豆博列表
	 */
	public List<Douboinfo> getOLDDouboInfoList(String fristReply, int countyID) {

		
		
		String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where unix_timestamp(last_reply)<unix_timestamp(?) "
				+ (countyID == 0 ? "" : "and weiboinfo.countyID=? ")
				+ " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";

		
		
		System.out.println("-safasfasfd----------------------------");
		
		String srSql="select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where unix_timestamp(last_reply)<unix_timestamp("+fristReply+") "
			+ (countyID == 0 ? "" : "and weiboinfo.countyID="+countyID+" ")
			+ " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";
		
		System.out.println(srSql);

		

		List<Douboinfo> list = null;

		
		if (countyID == 0) {

			list=DBHelper.commQuery(sql, Douboinfo.class, fristReply);

		} else {
			list=DBHelper.commQuery(sql, Douboinfo.class, fristReply,countyID);
		}

		// 批量修改浏览量

		if (list != null) {

			if (list.size() != 0) {

				String startTime = list.get(0).getLast_reply();

				String endTime = list.get(list.size() - 1).getLast_reply();

				String strSql = "update weiboinfo,countyinfo,userinfo set seecount=seecount+1 where unix_timestamp(last_reply)<=unix_timestamp(?) and unix_timestamp(last_reply)>=unix_timestamp(?)  "
						+ (countyID == 0 ? "" : "and weiboinfo.countyID=? ")
						+ " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id";

				
				
				
				if (countyID == 0) {

					DBHelper.commonUpdate(strSql, startTime,endTime);
				} else {

					DBHelper.commonUpdate(strSql, startTime,endTime, countyID);
				}

			}

		}
		
		//更新操作后再次查询
		if (countyID == 0) {

			list=DBHelper.commQuery(sql, Douboinfo.class, fristReply);

		} else {
			list=DBHelper.commQuery(sql, Douboinfo.class, fristReply,
					countyID);
		}
		
		

		return list;
	}

	/**
	 * 根据微博id查询微博信息
	 * 
	 * @param wid
	 *            微博id
	 * @return 微博信息
	 */
	public Douboinfo getDouboInfoByWid(int wid) {
		String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where weiboinfo.id=?  and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id";

		List<Douboinfo> list = DBHelper.commQuery(sql, Douboinfo.class, wid);

		Douboinfo douboinfo = null;

		if (list != null) {

			douboinfo = list.get(0);
		}

		return douboinfo;
	}

	/**
	 * 根据用户Id 修改用户信息
	 * 
	 * @param uid
	 * @param photo
	 * @param username
	 * @param password
	 * @param gender
	 * @param phone
	 * @param qq
	 * @param cash
	 * @param status
	 * @return
	 */
	public boolean updateUser(int uid, String photo, String username,
			String password, int gender, String phone, String qq) {
		
		String sql ="";
		
		
		if(photo==null){
		
			 sql = "update userinfo set username=?,password=?,gender=?,phone=?,qq=? where id =?";
			 
			return DBHelper.commonUpdate(sql, username, password, gender,
						phone, qq, uid);
			
		}else{
			sql = "update userinfo set headphoto=?,username=?,password=?,gender=?,phone=?,qq=? where id =?";
		
			return DBHelper.commonUpdate(sql, photo, username, password, gender,
					phone, qq, uid);
		}
		
	}

	/**
	 * 修改用户头像
	 * 
	 * @param photo
	 *            图片名称
	 * @param uid
	 *            用户
	 * @return
	 */
	public boolean updateHead(String photo, String uid) {

		String sql = "update userinfo set headphoto=? where id =?";

		return DBHelper.commonUpdate(sql, photo, uid);

	}

	/**
	 * 获取该用户的新微博
	 * 
	 * @param lastTime
	 *            最后发布时间
	 * @param uid
	 *            用户id
	 * @return 微博列表
	 */
	public List<Douboinfo> getNewWeiboListByUid(String lastTime, int uid) {

		String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where unix_timestamp(weiboinfo.time)>unix_timestamp(?) and weiboinfo.uid=?  and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by weiboinfo.time desc limit 20";

		return DBHelper.commQuery(sql, Douboinfo.class,lastTime, uid);
	}

	/**
	 * 获取该用户的旧微博
	 * 
	 * @param fristTime
	 *            早期发布
	 * @param uid
	 *            用户id
	 * @return 微博列表
	 */
	public List<Douboinfo> getOldWeiboListByUid(String fristTime, int uid) {

		String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where unix_timestamp(weiboinfo.time)<unix_timestamp(?) and weiboinfo.uid=?  and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by weiboinfo.time desc limit 20";

		
		return DBHelper.commQuery(sql, Douboinfo.class, fristTime, uid);

	}

	/**
	 * 获取某个用户的新微博评论
	 * 
	 * @param uid
	 *            用户id
	 * @param lastTime
	 *            最后评论时间
	 * @return 微博评论列表
	 */
	public List<Doubo_reply> getNewWeibo_replyByUid(int uid, String lastTime) {

		String sql = "select dfb_weibo_reply.*,(select username from userinfo as a where a.id=dfb_weibo_reply.uid) as othername,userinfo.username,(select headphoto from userinfo as a where a.id=dfb_weibo_reply.uid) as headphoto,weiboinfo.content from dfb_weibo_reply,weiboinfo,userinfo where weiboinfo.uid=? and dfb_weibo_reply.uid!=? and UNIX_TIMESTAMP(dfb_weibo_reply.time)>UNIX_TIMESTAMP(?) and dfb_weibo_reply.wid=weiboinfo.id and weiboinfo.uid=userinfo.id order by dfb_weibo_reply.time desc limit 20";

		return DBHelper.commQuery(sql, Doubo_reply.class, uid, uid, lastTime);
	}

	/**
	 * 获取某个用户的旧微博评论
	 * 
	 * @param uid
	 *            用户id
	 * @param lastTime
	 *            早期评论时间
	 * @return 微博评论列表
	 */
	public List<Doubo_reply> getOldWeibo_replyByUid(int uid, String fristTime) {
		String sql = "select dfb_weibo_reply.*,(select username from userinfo as a where a.id=dfb_weibo_reply.uid) as othername,userinfo.username,(select headphoto from userinfo as a where a.id=dfb_weibo_reply.uid) as headphoto,weiboinfo.content from dfb_weibo_reply,weiboinfo,userinfo where weiboinfo.uid=? and dfb_weibo_reply.uid!=? and UNIX_TIMESTAMP(dfb_weibo_reply.time)<UNIX_TIMESTAMP(?) and dfb_weibo_reply.wid=weiboinfo.id and weiboinfo.uid=userinfo.id order by dfb_weibo_reply.time desc limit 20";

		List<Doubo_reply> list = DBHelper.commQuery(sql, Doubo_reply.class,
				uid, uid, fristTime);

	
		return list;
	}

	public List<UserseeListInfo> getNewSeelistInfo(int uid, String seeTime) {

		String sql = "select userseelist.*,userinfo.username,userinfo.headphoto from userseelist,userinfo where uid=? and UNIX_TIMESTAMP(seetime)>UNIX_TIMESTAMP(?)  and userseelist.otherid=userinfo.id group by otherid ORDER BY seetime desc limit 20";

		return DBHelper.commQuery(sql, UserseeListInfo.class, uid, seeTime);
	}

	public List<UserseeListInfo> getOldSeelistInfo(int uid, String seetime) {

		String sql = "select userseelist.*,userinfo.username,userinfo.headphoto from userseelist,userinfo where uid=? and UNIX_TIMESTAMP(seetime)<UNIX_TIMESTAMP(?)  and userseelist.otherid=userinfo.id group by otherid ORDER BY seetime desc limit 20";

		return DBHelper.commQuery(sql, UserseeListInfo.class, uid, seetime);
	}

	public int getUserSeecount(int uid) {

		String sql = "select count(*) from userseelist where uid=?";

//		List<UserseeListInfo> list = DBHelper.commQuery(sql,
//				UserseeListInfo.class, uid);
//
//		
//		if (list != null) {
//
//			if(list.size()>0){
//			
//				return list.size()-1;
//			}
//		}

		return DBHelper.commonQueryCount(sql, uid);
	}

	public List<Douboinfo> getNewMyComment(String lastTime, int uid) {
		
		String sql="select weiboinfo.* from dfb_weibo_reply,weiboinfo,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount where  userinfo.id=weiboinfo.uid and UNIX_TIMESTAMP(weiboinfo.time)>UNIX_TIMESTAMP(?) and dfb_weibo_reply.uid=? and weiboinfo.id=dfb_weibo_reply.wid GROUP BY wid  order by time desc limit 20 ";
		
		
		return DBHelper.commQuery(sql, Douboinfo.class, lastTime,uid);
	}

	public List<Douboinfo> getOldMyComment(String frist, int uid) {
		
		String sql="select weiboinfo.*,userinfo.*,(select SUM(up) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as upcount,(select SUM(down) from weiboinfo,userinfo as a where userinfo.id=a.id and weiboinfo.uid=userinfo.id) as downcount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from dfb_weibo_reply,weiboinfo,userinfo where userinfo.id=weiboinfo.uid and  UNIX_TIMESTAMP(weiboinfo.time)<UNIX_TIMESTAMP(?) and dfb_weibo_reply.uid=? and weiboinfo.id=dfb_weibo_reply.wid GROUP BY wid  order by time desc limit 20 ";
		

		return DBHelper.commQuery(sql, Douboinfo.class, frist,uid);
	}

	/**
	 * 
	 * 搜索商家（根据关键字）
	 * @param keyword 关键字
	 * @param countyID 
	 * @return 商家列表
	 */
	public List<MerchantInfo> getSearch(String keyword, int countyID,int pageNum) {
		
		String sql="select *,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=merchantinfo.id) as commentcount from merchantinfo where countyID=? and (name like ? or major like ? or address like ?) limit "+(pageNum*20)+",20";
		

		return DBHelper.commQuery(sql, MerchantInfo.class,countyID,keyword,keyword,keyword);
	}

	/**
	 * 
	 * 获取省级列表
	 * @param provincialID
	 * @return
	 */
	public List<ProvincialInfo> getProvincialInfo(int provincialID) {
		
		
		//获取本省信息
		String sql="select * from provincialinfo where provincialID=?";
		
		List<ProvincialInfo> list=DBHelper.commQuery(sql, ProvincialInfo.class, provincialID);
		
		
		
		//获取其他省信息
		sql="select * from provincialinfo where provincialID!=?";
		
		List<ProvincialInfo> otherlist=DBHelper.commQuery(sql, ProvincialInfo.class, provincialID);
		
		if(list!=null&&otherlist!=null){
			
			if(list.size()>0&&otherlist.size()>0){
				
		
				list.addAll(otherlist);
				
			}
		}
		
		return list;
	}

	public List<CityInfo> getCityInfoByProvincialID(int provincialID, int cityID) {
		
		
		//获取本省信息
		String sql="select * from cityinfo where cityID=? and provincialID=?";
		
		List<CityInfo> list=DBHelper.commQuery(sql, CityInfo.class,cityID,provincialID);
		
		
		
		
		//获取其他省信息
		sql="select * from cityinfo where cityID!=? and provincialID=?";
		
		List<CityInfo> otherlist=DBHelper.commQuery(sql, CityInfo.class,cityID, provincialID);
		
		if(list!=null&&otherlist!=null){
			
			if(list.size()>0&&otherlist.size()>0){
				
		
				list.addAll(otherlist);
				
			}
		}
		
		if(list.size()==0){//如果该省不存在当前市，就加载该省全部市
			
			sql="select * from cityinfo where  provincialID=?";
					
			list=DBHelper.commQuery(sql, CityInfo.class, provincialID);		
			
		
		}
		
		return list;
	}

	public List<CountyInfo> getCountyInfoByCityID(int cityID, int countyID) {
		//获取本省信息
		String sql="select * from countyinfo where cityId=? and countyID=?";
		
		List<CountyInfo> list=DBHelper.commQuery(sql, CountyInfo.class, cityID,countyID);
		
		
		
		//获取其他省信息
		sql="select * from countyinfo where cityId=? and countyID!=?";
		
		List<CountyInfo> otherlist=DBHelper.commQuery(sql, CountyInfo.class, cityID,countyID);
		
		if(list!=null&&otherlist!=null){
			
			if(list.size()>0&&otherlist.size()>0){
				
		
				list.addAll(otherlist);
				
			}
		}
		
		if(list.size()==0){//如果该省不存在当前市，就加载该省全部市
			
			sql="select * from countyinfo where cityId=?";
					
			list=DBHelper.commQuery(sql, CountyInfo.class, cityID);		
			
		}

		
		return list;
	}

	public boolean addCamera(String uid, String photo) {
		
		String sql="insert into userimagelist(uid,imageurl,uptime) values(?,?,?)";
		
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		return DBHelper.commonUpdate(sql, uid,photo,time);
	}

	public List<Userimagelist> getUserImageList(int uid) {
		
		String sql="select * from userimagelist where uid=?";
		
		return DBHelper.commQuery(sql, Userimagelist.class, uid);
	}

	public List<CelebrityOrder> getUserInfoListByKey(String key,int pagenum) {
		
		String sql="select userinfo.id,userinfo.username,(select sum(up) from weiboinfo  where userinfo.id=weiboinfo.uid) as up,(select sum(down) from weiboinfo  where userinfo.id=weiboinfo.uid) as down,userinfo.headphoto from userinfo where userinfo.username like ?  order by up desc limit ?,20";
		
		
		System.out.println(key+"@"+pagenum);
		
		
		return DBHelper.commQuery(sql, CelebrityOrder.class,"%"+key+"%",pagenum*20);
	}

}
