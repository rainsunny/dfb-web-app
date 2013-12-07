package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.Merchant_comment_all;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

public class Merchant_comment_allDao {
	/**
	 * 分页查询
	 * @param pageNo
	 * @param key
	 * @param keywords
	 * @return
	 */
	public PageModel<Merchant_comment_all> queryAll(int pageNo,String key,String keywords){
		String sql = "SELECT dfb_merchant_comment.id, dfb_merchant_comment.mid,dfb_merchant_comment.uid,"+
		" dfb_merchant_comment.content,dfb_merchant_comment.time,userinfo.username,merchantinfo.`name` FROM"+
		" dfb_merchant_comment , userinfo , merchantinfo where userinfo.id = dfb_merchant_comment.uid "+
		" and merchantinfo.id = dfb_merchant_comment.mid and "+key+" like ?"+
		" ORDER BY dfb_merchant_comment.id desc ";
		PageModel<Merchant_comment_all> pm = new PageModel<Merchant_comment_all>();
		pm.setSumCount(querySumCount(key,keywords));
		pm.setCurrentPage(pageNo);
		
		sql += " limit "+(pageNo-1)*pm.getSize()+","+pm.getSize();

		List<Merchant_comment_all> list = DBHelper.commQuery(sql, Merchant_comment_all.class,keywords);
		pm.setData(list);
		return pm;
	}
	/**
	 * 查询总条数
	 * @param key
	 * @param keywords
	 * @return
	 */
	public Integer querySumCount(String key,String keywords){
		String sql = "SELECT count(*) FROM dfb_merchant_comment , userinfo , merchantinfo where userinfo.id = dfb_merchant_comment.uid and merchantinfo.id = dfb_merchant_comment.mid and "+key+" like ?";
		return DBHelper.commonQueryCount(sql,keywords);
	}
}
