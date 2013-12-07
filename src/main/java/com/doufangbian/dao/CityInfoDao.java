package com.doufangbian.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.doufangbian.entity.CityInfo;
import com.doufangbian.util.DBHelper;

public class CityInfoDao {

	/**
	 * 
	 * 查询市级列表(超级管理员权限)
	 * 
	 * @return 返回市列表
	 */
	public List<CityInfo> getCityList(Integer provincialID) {

		String sql = "select * from cityinfo";// 查询语句

		if (provincialID != null && provincialID != 0) {

			sql = "SELECT cityinfo.cityID,cityinfo.provincialID,cityinfo.`name` FROM cityinfo where provincialID="
					+ provincialID;
		}

		List<CityInfo> list = DBHelper.commQuery(sql, CityInfo.class);

		if (list != null && list.size() > 0) {
			return list;
		}

		return null;

	}

	/**
	 * 添加市
	 * 
	 * @param pid
	 * @param city
	 */
	public boolean addCity(String pid, String city) {
		String sql = "insert into cityinfo(provincialID,name) values(?,?)";
		return DBHelper.commonUpdate(sql, pid, city);
	}

	/**
	 * 检查市 是否存在
	 * 
	 * @param city
	 * @return
	 */
	public boolean queryByName(String city) {
		String sql = "select count(*) from cityinfo where name=?";
		return DBHelper.commonQueryCount(sql, city) > 0 ? true : false;
	}
	
	
	/**
	 * 检查市 是否存在(返回市信息)
	 * 
	 * @param city
	 * @return
	 */
	public CityInfo queryCityInfoByName(String city) {
		String sql = "select * from cityinfo where name=?";
		
		List<CityInfo> list=DBHelper.commQuery(sql, CityInfo.class, city);
		
		if(list!=null){
			
			if(list.size()>0){
				
				return list.get(0);
			}
		}
		
		return null;
	}
	
	
	/**
	 * 根据市id查询市
	 * @param cityId
	 * @return
	 */
	public CityInfo getCityById(Integer cityId){
		String sql = "select * from cityinfo where cityId = ?";
		
		List<CityInfo> list = DBHelper.commQuery(sql, CityInfo.class, cityId);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	 * 根据省级ID查询市级列表
	 * 
	 * @return 返回县列表
	 */
	public List<CityInfo> getCityListByProvincialId(int provincialID) {

		Connection conn = DBHelper.getConn();// 获取连接

		PreparedStatement pstmt = null;// 命令对象

		ResultSet rs = null;// 数据集

		// String Sql =
		// "select cityinfo.cityID,cityinfo.name from countyinfo,cityinfo where provincialID=? and countyinfo.cityId=cityinfo.cityID GROUP BY cityinfo.name";//
		// 查询语句
		String Sql = "SELECT cityinfo.cityID,cityinfo.provincialID,cityinfo.`name` FROM cityinfo where provincialID="
				+ provincialID;

		List<CityInfo> list = new ArrayList<CityInfo>();

		try {

			pstmt = conn.prepareStatement(Sql);// 获取命令对象

			pstmt.setInt(1, provincialID);

			rs = pstmt.executeQuery();// 执行查询

			while (rs.next()) {// 读取数据

				int cityID = rs.getInt("cityID");

				String name = rs.getString("name");

				CityInfo cityInfo = new CityInfo(cityID, name);

				list.add(cityInfo);

			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 关闭资源

			DBHelper.closeConn(conn, rs, pstmt);
		}

		return null;

	}

	public CityInfo getQueryCityByID(int cityID) {

		Connection conn = DBHelper.getConn();// 获取连接

		PreparedStatement pstmt = null;// 命令对象

		ResultSet rs = null;// 数据集

		String Sql = "select * from cityinfo where cityID=?";// 查询语句

		CityInfo cityInfo = null;// 省级对象

		try {

			pstmt = conn.prepareStatement(Sql);// 获取命令对象

			pstmt.setInt(1, cityID);

			rs = pstmt.executeQuery();// 执行查询

			while (rs.next()) {// 读取数据

				String name = rs.getString("name");

				cityInfo = new CityInfo(cityID, name);

			}

			return cityInfo;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 关闭资源

			DBHelper.closeConn(conn, rs, pstmt);
		}

		return null;

	}

}
