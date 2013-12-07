package com.doufangbian.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.doufangbian.entity.ProvincialInfo;
import com.doufangbian.util.DBHelper;

public class ProvincialDao {

	/**
	 * 
	 * 查询省列表(超级管理员权限)
	 * 
	 * @return 返回县列表
	 */
	public List<ProvincialInfo> getProvincialList() {

		String sql = "select * from provincialinfo";// 查询语句

		List<ProvincialInfo> list = DBHelper.commQuery(sql,
				ProvincialInfo.class);

		if (list != null && list.size() > 0) {
			return list;
		}

		return null;

	}

	/**
	 * 
	 * 根据城市ID查询该城市的省份详细信息
	 * 
	 * @param provincialID
	 * @return
	 */
	public ProvincialInfo getQueryProvincialByCityID(int cityID) {

		Connection conn = DBHelper.getConn();// 获取连接

		PreparedStatement pstmt = null;// 命令对象

		ResultSet rs = null;// 数据集

		String Sql = "select provincialinfo.provincialID,provincialinfo.name from countyinfo,provincialinfo where cityId=? and countyinfo.provincialID=provincialinfo.provincialID LIMIT 1,1";// 查询语句

		ProvincialInfo provincialInfo = null;// 省级对象

		try {

			pstmt = conn.prepareStatement(Sql);// 获取命令对象

			pstmt.setInt(1, cityID);

			rs = pstmt.executeQuery();// 执行查询

			while (rs.next()) {// 读取数据

				int provincialID = rs.getInt("provincialID");

				String name = rs.getString("name");

				provincialInfo = new ProvincialInfo(provincialID, name);

			}

			return provincialInfo;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 关闭资源

			DBHelper.closeConn(conn, rs, pstmt);
		}

		return null;

	}

	/**
	 * 
	 * 根据省份ID查询该省详细信息
	 * 
	 * @param provincialID
	 * @return
	 */
	public ProvincialInfo getQueryProvincialByID(int provincialID) {
		String sql = "select * from provincialinfo where provincialID=?";// 查询语句

		List<ProvincialInfo> list = DBHelper.commQuery(sql, ProvincialInfo.class, provincialID);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 添加
	 * 
	 * @param provincial
	 * @return
	 */
	public boolean addProvincial(String provincial) {
		String sql = "insert into provincialinfo(name) values(?)";
		return DBHelper.commonUpdate(sql, provincial);
	}

	/**
	 * 根据名称查询省
	 * 
	 * @return
	 */
	public boolean queryByName(String name) {
		String sql = "select count(*) from provincialinfo where name = ?";

		return DBHelper.commonQueryCount(sql, name) > 0 ? true : false;
	}
	
	
	
	/**
	 * 根据名称查询省
	 * 
	 * @return
	 */
	public ProvincialInfo queryProvincialInfoByName(String name) {
		String sql = "select * from provincialinfo where name = ?";

		List<ProvincialInfo> list=DBHelper.commQuery(sql, ProvincialInfo.class, name);
		
		if(list!=null){
			
			if(list.size()>0){
				
				return list.get(0);
			}
		}
		
		return null;
		
	}
	
	
	
	

}
