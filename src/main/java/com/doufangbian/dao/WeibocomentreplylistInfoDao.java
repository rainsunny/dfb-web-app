package com.doufangbian.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.doufangbian.entity.Weibocomentreplylist;
import com.doufangbian.util.DBHelper;

public class WeibocomentreplylistInfoDao {
	/**
	 * 根据微博的评论id查询回复内容
	 * @param commentId
	 * @return
	 */
	public List<Weibocomentreplylist> queryByCommentId(String commentId){
		
		Connection conn = DBHelper.getConn();// 获取连接

		PreparedStatement pstmt = null;// 命令对象

		ResultSet rs = null;// 数据集

		String sql = "SELECT weibocomentreplylist.id,weibocomentreplylist.commentid,weibocomentreplylist.uid,weibocomentreplylist.replyuid,weibocomentreplylist.replycontent,weibocomentreplylist.replyTime,one.id as id_1,one.username as username_1,two.id as id_2,two.username as username_2 FROM weibocomentreplylist ,userinfo as one,userinfo as two where commentid = ? and weibocomentreplylist.uid = one.id  and weibocomentreplylist.replyuid = two.id";

		List<Weibocomentreplylist> list  = new ArrayList<Weibocomentreplylist>();

		try {

			pstmt = conn.prepareStatement(sql);// 获取命令对象
			
			pstmt.setObject(1, commentId);
			
			rs = pstmt.executeQuery();// 执行查询
			
			while (rs.next()) {// 读取数据
				int commentid=rs.getInt("commentid");
				int uid=rs.getInt("uid");
				int replyuid=rs.getInt("replyuid");
				String replycontent = rs.getString("replycontent");
				String replyTime = rs.getString("replyTime");
				int id_1 = rs.getInt("id_1");
				String username_1 = rs.getString("username_1");
				int id_2 = rs.getInt("id_2");
				String username_2 = rs.getString("username_2");
				Weibocomentreplylist  wb = new Weibocomentreplylist(commentid, uid, replyuid, replycontent, replyTime, id_1, username_1, id_2, username_2);
				
				list.add(wb);
				
			}

			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 关闭资源

			DBHelper.closeConn(conn, rs, pstmt);
		}
		
		return null;
	}
	/**
	 * 删除某微博下的所有回复
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(String ids){
		String sql = "delete from weibocomentreplylist where commentid in(?)";
		return DBHelper.commonUpdate(sql, ids);
	}
	
}
