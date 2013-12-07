package com.doufangbian.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.doufangbian.entity.Dfb_merchant_comment;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

/**
 * 对商家的评论
 * @author C_C
 *
 */
public class Dfb_merchant_commentDao {
	/**
	 * 分页查询
	 * @param pageNo
	 * @return
	 */
	public PageModel<Dfb_merchant_comment> query(int pageNo,String mid){
		PageModel<Dfb_merchant_comment> pm = new PageModel<Dfb_merchant_comment>();
		pm.setSumCount(querySumCount(mid));
		pm.setCurrentPage(pageNo);
		String sql = "SELECT dfb_merchant_comment.id,mid,uid,content,time,username FROM userinfo,dfb_merchant_comment WHERE dfb_merchant_comment.uid = userinfo.id and mid =?";
		sql += " limit "+(pageNo-1)*pm.getSize()+","+pm.getSize();
		List<Dfb_merchant_comment> list = DBHelper.commQuery(sql, Dfb_merchant_comment.class, mid);
		pm.setData(list);
		return pm;
	}
	/**
	 * 查询总条数
	 * @return
	 */
	public Integer querySumCount(String mid){
		String sql = "SELECT count(*) FROM userinfo,dfb_merchant_comment WHERE dfb_merchant_comment.uid = userinfo.id and mid =?";
		return DBHelper.commonQueryCount(sql,mid);
	}
	/**
	 * 修改
	 * @return
	 */
	public boolean updateComment(String uid,String content,String id){
		String sql = "update dfb_merchant_comment set uid=?,content=? where id =?";
		return DBHelper.commonUpdate(sql, uid,content,id);
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(String id){
		String sql = "delete from dfb_merchant_comment where id = ?";
		return DBHelper.commonUpdate(sql, id);
	}
	/**
	 * 删除多个
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(String ids){
		String sql = "delete from dfb_merchant_comment where id in ("+ids+")";
		return DBHelper.commonUpdate(sql);
	}
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public Dfb_merchant_comment queryById(String id){
		String sql = "SELECT dfb_merchant_comment.id,mid,uid,content,time,username FROM userinfo,dfb_merchant_comment WHERE dfb_merchant_comment.uid = userinfo.id and dfb_merchant_comment.id =?";
		List<Dfb_merchant_comment> list = DBHelper.commQuery(sql, Dfb_merchant_comment.class, id);
		if(list!= null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 评论商家
	 * @param mid 商家id
	 * @param uid 用户id
	 * @param content 评论内容
	 */
	public List<Dfb_merchant_comment> comentMerchat(int mid, int uid, String content){
		
		String sql="insert into dfb_merchant_comment(mid,uid,content,time) values(?,?,?,?)";
		
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
		
		//添加商家评论
		boolean result=DBHelper.commonUpdate(sql, mid,uid,content,time);
		
		
		List<Dfb_merchant_comment> list=null;
		
		if(result){//添加评论成功，修改最后回复时间，和最后评论时间
			
			sql="update merchantinfo set updatetime=?,last_reply=? where id=?";
			
			boolean isflag=DBHelper.commonUpdate(sql, time,time,mid);
			
		
			if(isflag){
				
				list=getMerchant_commentList(mid);
				
			}
			
			
		}
		
		return list;
		
	}
	
	/**
	 * 
	 * 获取商家评论列表
	 * @param mid 商家id
	 * @return 商家评论列表
	 */
	public List<Dfb_merchant_comment> getMerchant_commentList(int mid) {

		String sql="select dfb_merchant_comment.*,userinfo.username,userinfo.gender,userinfo.headphoto from dfb_merchant_comment,userinfo where mid=? and userinfo.id=dfb_merchant_comment.uid order by time asc ";
		
		return DBHelper.commQuery(sql, Dfb_merchant_comment.class, mid);
	}
	
	/**
	 * 
	 * 检测商家评论是否有更新
	 * @param mid 商户id
	 * @param  last_reply 最后评论时间
	 * @return 商户评论列表
	 */
	public List<Dfb_merchant_comment> getMerchant_commentListByMidAndUpdateTime(int mid, String last_reply) {
		
		String sql="select dfb_merchant_comment.*,userinfo.username,userinfo.gender,userinfo.headphoto from dfb_merchant_comment,userinfo where mid=? and time>? and userinfo.id=dfb_merchant_comment.uid order by time asc";
		
		
		System.out.println("select dfb_merchant_comment.*,userinfo.username,userinfo.gender,userinfo.headphoto from dfb_merchant_comment,userinfo where mid="+mid+" and time>'"+last_reply+"'");
		
		return DBHelper.commQuery(sql, Dfb_merchant_comment.class, mid,last_reply);
	}
	/**
	 * 根据商家id查询商家中该用户的评论条数
	 * @param mid
	 */
	public int queryCountByUid(int mid,int uid) {
		
		String sql="select count(*) from dfb_merchant_comment where uid=? and mid=?";
		
		return DBHelper.commonQueryCount(sql, uid,mid);
		
	}
	
}
