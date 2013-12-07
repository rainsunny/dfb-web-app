package com.doufangbian.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.doufangbian.entity.PageModel;
import com.doufangbian.entity.Userinfo;
import com.doufangbian.util.DBHelper;


public class UserinfoDao {

	
	/**
	 * 返回用户列表
	 * @param pageNo 页数
	 * @param name 查询关键字
	 * @return
	 */
	public PageModel<Userinfo> getUserInfoList(int pageNo,String keyword){
		
		PageModel<Userinfo> pm = new PageModel<Userinfo>();
		
		pm.setSumCount(getCountyCount(keyword));
		
		pm.setCurrentPage(pageNo);
		
		String sql="select * from userinfo where username like ?";//默认表示全国
		
		sql+=" order by checkin desc";
		
		sql += " limit "+((pageNo-1)*pm.getSize()+","+pm.getSize());
		
		List<Userinfo> list= DBHelper.commQuery(sql, Userinfo.class,keyword);
		
		pm.setData(list);
		
		return pm;
		
	}
	
	/**
	 * 
	 *查询用户总数 
	 * @param name 查询关键字
	 * @return
	 */
	public int getCountyCount(String keyword) {
		String sql="select count(*)  from userinfo where username like ?";//默认表示全部用户
		
		return DBHelper.commonQueryCount(sql,keyword);
		
	}
	/**
	 * 根据id 查询用户
	 * @param uid
	 * @return
	 */
	public Userinfo queryById(String uid){
		
		String sql ="select * from userinfo where id=?";
		
		List<Userinfo> list = DBHelper.commQuery(sql, Userinfo.class, uid);
		
		if(list!=null && list.size()>0){
		
			return list.get(0);
		}
		return null;
	}
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean addUser(Userinfo user){
		
		String sql ="insert into userinfo(headphoto,username,password,gender,phone,qq,cash,checkin,status) values(?,?,?,?,?,?,?,?,?)";
		
		return DBHelper.commonUpdate(sql, user.getHeadphoto(),user.getUsername(),user.getPassword(),user.getGender(),user.getPhone(),user.getQq(),user.getCash(),user.getCheckin(),user.getStatus());
	}
	/**
	 * 删除用户 （用户Id）
	 * @return
	 */
	public boolean deleteById(String uid){
		
		String sql = "delete from userinfo where id = ?";
		
		return DBHelper.commonUpdate(sql, uid);
	}
	
	/**
	 * 
	 * 根据用户相册id删除该相册信息
	 * @param id
	 * @return
	 */
	public boolean deleteUserImageByID(String id){

		String sql = "delete from userimagelist where id=?";
		
		return DBHelper.commonUpdate(sql, id);
		
	}
	/**
	 * 查询用户的头像 （用户Id）
	 * @return
	 */
	public List<String> queryPhotoById(String uid){
		
		String sql = "select headphoto from userinfo where id = ?";
		
		return DBHelper.queryImageName(sql, uid);
	}
	
	
	/**
	 * 
	 * 根据相册id查询相册图片名称
	 * @param id
	 * @return
	 */
	public List<String> queryUserImagebyID(String id){

		String sql = "select imageurl from userimagelist where id=?";
		
		return DBHelper.queryImageName(sql, id);
		
	}
	/**
	 * 根据用户Id 修改用户信息
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
	public boolean updateUser(String uid, String photo,String username,String password,String gender,String phone,String qq,String cash,String status){
		String sql ="update userinfo set headphoto=?,username=?,password=?,gender=?,phone=?,qq=?,cash=?,status=? where id =?";
		return DBHelper.commonUpdate(sql,photo, username, password, gender, phone, qq, cash, status,uid);
	}
	/**
	 * 根据姓名查询用户
	 * @param username
	 * @return
	 */
	public Userinfo queryByName(String username){
		String sql ="select * from userinfo where username = ?";
		List<Userinfo> list = DBHelper.commQuery(sql, Userinfo.class,username);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 添加用户查看记录
	 * @param uid 用户id
	 * @param otherId 浏览者id
	 * @param seeContent 查看内容(这个查看内容可以是wid也可以是其他)
	 */
	public boolean addUserSeeInfo(int uid, int otherId, String seeContent) {
		
		
		String checksql="select count(*) as count from userseelist where uid=? and otherid=?";
		
		
		int count=DBHelper.commonQueryCount(checksql, uid,otherId);
		
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); //最新时间
	
		
		String sql="";
		boolean result=false;
		if(count>0){//如果该浏览记录已经存在，则修改浏览时间
			
			 sql="update userseelist set seetime=?  where uid=? and otherid=? ";
			
			 
			 System.out.println(sql);
			
			 result=DBHelper.commonUpdate(sql,time,uid,otherId);
			
		}else{
		
			 sql="insert into userseelist(uid,otherid,seeTime) values(?,?,?)";
			
			result=DBHelper.commonUpdate(sql, uid,otherId,time);
		}
		
		return result;
		
	}

	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	public Userinfo userLogin(String username, String password) {
		
		String sql="select * from userinfo where username=? and password=?";
		
		List<Userinfo> list=DBHelper.commQuery(sql, Userinfo.class, username,password);
		
		if(list!=null){
			
			if(list.size()!=0){
				
				return updateCheckin(list.get(0).getId());
				
				
			}
		}
		
		
		return null;
	}

	public Userinfo updateCheckin(int uid) {
		
		String sql="update userinfo set checkin=? where id=?";
		
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
		
		boolean result=DBHelper.commonUpdate(sql,time,uid);
		
		
		
		if(result){
			
			return queryById(uid+"");
		}
		
		return null;
	}

	/**
	 * 修改豆币
	 * @param count
	 * @param uid
	 */
	public void updateCash(int count,int uid) {
		
		String sql="update userinfo set cash=cash+? where id=?";

		
		DBHelper.commonUpdate(sql, count,uid);
		
		
	}
	
	
	
	
}
