package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.Dfb_merchant_image;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

/**
 * 商家图片
 * @author C_C
 *
 */
public class Dfb_merchant_imageDao {
	/**
	 * 上传商家图片
	 * @param mid
	 * @param image
	 * @return
	 */
	public boolean addMerImage(String mid,String image){
		String sql = "insert into dfb_merchant_image(mid,image) values(?,?)";
		return DBHelper.commonUpdate(sql, mid,image);
	}
	/**
	 * 分页查询
	 * @param pageNo
	 * @return
	 */
	public PageModel<Dfb_merchant_image> queryAll(int pageNo,String mid){
		PageModel<Dfb_merchant_image>pm = new PageModel<Dfb_merchant_image>();
		pm.setSize(12);
		pm.setSumCount(querySunCount(mid));
		pm.setCurrentPage(pageNo);
		String sql = "SELECT * FROM dfb_merchant_image WHERE MID = ? ORDER BY id DESC ";
		sql+=" limit "+ (pageNo - 1) * pm.getSize() + "," + pm.getSize();
		List<Dfb_merchant_image> list = DBHelper.commQuery(sql, Dfb_merchant_image.class,mid);
		pm.setData(list);
		return pm;
	}
	/**
	 * 查询总条数
	 * @return
	 */
	public Integer querySunCount(String mid){
		String sql = "select count(*) from dfb_merchant_image where mid =?";
		return DBHelper.commonQueryCount(sql,mid);
	}
	/**
	 * 根据Id删除图片
	 * @param id
	 * @return
	 */
	public boolean deleteById(String id){
		String sql = "delete from dfb_merchant_image where id =?";
		return DBHelper.commonUpdate(sql, id);
	}
	/**
	 * 根据Id查询图片名称
	 * @param id
	 * @return
	 */
	public List<String> queryImageNameById(String id){
		String sql = "select image from dfb_merchant_image where id =?";
		return DBHelper.queryImageName(sql, id);
	}
	/**
	 * 根据商家id删除相关图片
	 * @param mid
	 * @return
	 */
	public boolean deleleByMid(String mid){
		String sql = "delete from dfb_merchant_image where mid = ?";
		return DBHelper.commonUpdate(sql, mid);
	}
	/**
	 * 根据Id查询图片名称
	 * @param id
	 * @return
	 */
	public List<String> queryImageNameByMid(String mid){
		String sql = "select image from dfb_merchant_image where mid =?";
		return DBHelper.queryImageName(sql, mid);
	}
	
}
