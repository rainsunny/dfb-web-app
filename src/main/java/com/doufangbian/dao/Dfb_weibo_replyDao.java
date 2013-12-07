package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.Doubo_reply;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

public class Dfb_weibo_replyDao {
	/**
	 * 查询微博评论(根据微博id)
	 * @param wid
	 * @return
	 */
	public PageModel<Doubo_reply> queryAllByWid(Integer pageNo,String wid){
		PageModel<Doubo_reply> pm = new PageModel<Doubo_reply>();
		
		pm.setSumCount(queryByCount(wid));
		
		pm.setCurrentPage(pageNo);
		
		String sql = "SELECT * FROM (SELECT dfb_weibo_reply.id,dfb_weibo_reply.replycontent,dfb_weibo_reply.time,dfb_weibo_reply.uid,dfb_weibo_reply.wid,userinfo.id AS user_id,userinfo.username FROM dfb_weibo_reply ,userinfo where  dfb_weibo_reply.uid= userinfo.id and dfb_weibo_reply.wid = ?) AS weibo ORDER BY weibo.time desc ";
		
		sql += " limit "+(pageNo-1)*pm.getSize()+","+pm.getSize();
		
		List<Doubo_reply> list = DBHelper.commQuery(sql, Doubo_reply.class, wid);
		
		pm.setData(list);
	
		return pm;
	}
	/**
	 * 查询总条数
	 * @param wid
	 * @return
	 */
	public Integer queryByCount(String wid){
		String sql = "SELECT count(*) FROM (SELECT dfb_weibo_reply.id,dfb_weibo_reply.replycontent,dfb_weibo_reply.time,dfb_weibo_reply.uid,dfb_weibo_reply.wid,userinfo.id AS user_id,userinfo.username FROM dfb_weibo_reply ,userinfo where  dfb_weibo_reply.uid= userinfo.id and dfb_weibo_reply.wid = ?) AS weibo";
		return DBHelper.commonQueryCount(sql, wid);
	}
	/**
	 * 根据id 查询评论信息
	 * @param id
	 * @return
	 */
	public Doubo_reply queryById(String id){
		String sql = "SELECT dfb_weibo_reply.id,dfb_weibo_reply.replycontent,dfb_weibo_reply.time,dfb_weibo_reply.uid,dfb_weibo_reply.wid,userinfo.id,userinfo.username FROM dfb_weibo_reply ,userinfo where dfb_weibo_reply.uid = userinfo.id and dfb_weibo_reply.id=?";
		List<Doubo_reply> list = DBHelper.commQuery(sql, Doubo_reply.class, id);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据微博Id查询所有评论
	 * @return
	 */
	public List<Doubo_reply> queryAll(String wid){
		
		String sql ="select dfb_weibo_reply.*,userinfo.username,userinfo.headphoto from dfb_weibo_reply,userinfo where wid=? and dfb_weibo_reply.uid=userinfo.id order by time asc ";
		
		List<Doubo_reply> list = DBHelper.commQuery(sql, Doubo_reply.class, wid);
		
		if(list!= null && list.size()>0){
			return list;
		}
		return null;
	}
	/**
	 * 删除微博id的评论
	 * @param wid
	 * @return
	 */
	public boolean deletebywid(String wid){
		String sql = "delete from dfb_weibo_reply where wid =?";
		return DBHelper.commonUpdate(sql, wid);
	}
	/**
	 * 根据id删除微博
	 * @param replyId
	 * @return
	 */
	public boolean deletebyId(String replyId){
		String sql = "delete from dfb_weibo_reply where id =?";
		return DBHelper.commonUpdate(sql, replyId);
	}
	/**
	 * 添加评论
	 * @param wid
	 * @param uid
	 * @param content
	 * @return
	 */
	public boolean addReply(String wid,String uid,String time,String content){
		
		String sql = "insert into dfb_weibo_reply(wid,uid,time,replycontent) values(?,?,?,?)";
	
		
		return DBHelper.commonUpdate(sql, wid,uid,time,content)&&updateReply(wid, time);
	}
	
	
	/**
	 * 
	 * 修改last_reply和updatetime的时间
	 * @param wid 微博id
	 * @param time 时间
	 * @return 是否成功修改
	 */
	public boolean updateReply(String wid,String time){
		
		String sql="update weiboinfo set last_reply=?,updatetime=? where id=?";
	
		return DBHelper.commonUpdate(sql,time,time,wid);
	}
	
	/**
	 * 获取该条微博最新评论
	 * @param weiId 微博id
	 * @param lasttime 最后评论时间
	 * @return
	 */
	public List<Doubo_reply> getCommentListByWidAndTime(int weiId,
			String lasttime) {
		
	
		
		String sql="select dfb_weibo_reply.*,userinfo.username,userinfo.headphoto from dfb_weibo_reply,userinfo where wid=? and time>? and dfb_weibo_reply.uid=userinfo.id order by id asc";
	
		
		List<Doubo_reply> list=DBHelper.commQuery(sql, Doubo_reply.class, weiId,lasttime);
		
		
		return list;
	}
	
	public int queryCountByUid(int wid, int uid) {
		
		String sql="select count(*) from dfb_weibo_reply where uid=? and wid=?";
		
		return DBHelper.commonQueryCount(sql, uid,wid)  ;
	
	}
}
