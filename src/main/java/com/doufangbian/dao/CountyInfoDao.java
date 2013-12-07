package com.doufangbian.dao;

import java.util.List;

import com.doufangbian.entity.CityInfo;
import com.doufangbian.entity.CountyInfo;
import com.doufangbian.entity.PageModel;
import com.doufangbian.entity.ProvincialInfo;
import com.doufangbian.util.DBHelper;

public class CountyInfoDao {

	/**
	 * 
	 * 查询县相关信息
	 * 
	 * @return 返回县相关信息
	 */
	public CountyInfo getCountyInfo(int countyID) {

		String sql = "select * from countyinfo where countyID=?";// 查询语句

		List<CountyInfo> list = DBHelper.commQuery(sql, CountyInfo.class,
				countyID);

		if (list != null && list.size() > 0) {

			return list.get(0);
		}

		return null;

	}

	/**
	 * 查询县列表(超级管理员权限)
	 * 
	 * @param pageNo
	 * @param provincialID
	 * @param cityId
	 * @param name
	 *            模糊查询的值（城市）
	 * @return
	 */
	public PageModel<CountyInfo> getCountyList(Integer pageNo,
			Integer provincialID, Integer cityId, String name) {

		PageModel<CountyInfo> pm = new PageModel<CountyInfo>();

		pm.setSumCount(getCountyCount(provincialID, cityId, name));

		pm.setCurrentPage(pageNo);

		String sql = "select * from countyinfo where name like ? ";// 查询语句

		if (provincialID != null && provincialID != 0) {

			sql += " and provincialID=" + provincialID;
		}
		if (cityId != null && cityId != 0) {

			sql += " and cityId=" + cityId;
		}

		sql += "  ORDER BY countyID DESC limit "
				+ ((pageNo - 1) * pm.getSize() + "," + pm.getSize());

		List<CountyInfo> list = DBHelper.commQuery(sql, CountyInfo.class, name);

		pm.setData(list);

		return pm;

	}

	/**
	 * 
	 * 查询县总数(超级管理员权限)
	 * 
	 * @return 返回县总数
	 */
	public int getCountyCount(Integer provincialID, Integer cityId, String name) {

		String sql = "select count(*) from countyinfo where name like ? ";// 查询语句

		if (provincialID != null && provincialID != 0) {

			sql += " and provincialID=" + provincialID;
		}
		if (cityId != null && cityId != 0) {

			sql += " and cityId=" + cityId;
		}

		return DBHelper.commonQueryCount(sql, name);

	}

	/**
	 * 添加
	 * 
	 * @param pid
	 * @param cid
	 * @param county
	 * @param url
	 * @return
	 */
	public boolean addCounty(String pid, String cid, String county, String url) {
		String sql = "insert into countyinfo(provincialID,cityId,name,wapUrl) values(?,?,?,?)";
		return DBHelper.commonUpdate(sql, pid, cid, county, url);
	}

	/**
	 * 判断县是否存在
	 * 
	 * @param county
	 * @return
	 */
	public boolean queryByName(String county) {
		String sql = "select count(*) from countyinfo where name =?";
		return DBHelper.commonQueryCount(sql, county) > 0 ? true : false;
	}

	/**
	 * 判断县是否存在
	 * 
	 * @param county
	 * @return
	 */
	public CountyInfo queryCountyInfoByName(String county) {
		String sql = "select * from countyinfo where name =?";

		List<CountyInfo> list = DBHelper.commQuery(sql, CountyInfo.class,
				county);

		if (list != null) {

			if (list.size() > 0) {

				return list.get(0);
			}
		}

		return null;
	}

	/**
	 * 修改县信息
	 * 
	 * @param xian
	 * @param url
	 * @param countyID
	 * @return
	 */
	public boolean updateArea(String xian, String url, String countyID) {
		String sql = "update countyinfo set name=?,wapUrl=? where countyID=?";
		return DBHelper.commonUpdate(sql, xian, url, countyID);
	}

	/**
	 * 查询所有的县
	 * 
	 * @return
	 */
	public List<CountyInfo> queryAll() {
		String sql = "select * from countyinfo";
		return DBHelper.commQuery(sql, CountyInfo.class);
	}

	// 一下是android端代码

	/**
	 * 根据省、市、县的名称查询出该县所有信息
	 * 
	 * @param provincialName
	 *            省名称
	 * @param cityName
	 *            市名称
	 * @param countyname
	 *            县名称
	 * @return 县信息
	 */
	public CountyInfo getCountyInfo(String provincialName, String cityName,
			String countyname) {

		System.out.println(provincialName + ":" + cityName + ":" + countyname
				+ "存在吗？");

		ProvincialDao provincialDao = new ProvincialDao();

		CityInfoDao cityInfoDao = new CityInfoDao();

		String sql = "select countyinfo.* from provincialinfo,cityinfo,countyinfo where provincialinfo.name=? and cityinfo.name=? and countyinfo.name=? and provincialinfo.provincialID=countyinfo.provincialID and cityinfo.cityID=countyinfo.cityId";

		List<CountyInfo> list = DBHelper.commQuery(sql, CountyInfo.class,
				provincialName, cityName, countyname);

		if (list != null && list.size() > 0) {// 如果存在则返回数据

			System.out.println(provincialName + ":" + cityName + ":"
					+ countyname + "存在");

			return list.get(0);

		} else {// 如果不存在则添加地区信息

			// 省是否存在
			boolean p = provincialDao.queryByName(provincialName);
			
		
			if (!p) {// 不存在，添加

				provincialDao.addProvincial(provincialName);

			}

			// 市是否存在
			boolean c = cityInfoDao.queryByName(cityName);

			ProvincialInfo provincialInfo = provincialDao
					.queryProvincialInfoByName(provincialName);// 查询省

			if (!c) {// 不存在添加市

				cityInfoDao.addCity(provincialInfo.getProvincialID()+"", cityName);// 添加市

			}

			// 查询刚刚添加的县信息
			sql = "select countyinfo.* from provincialinfo,cityinfo,countyinfo where provincialinfo.name=? and cityinfo.name=? and countyinfo.name=? and provincialinfo.provincialID=countyinfo.provincialID and cityinfo.cityID=countyinfo.cityId";

			list = DBHelper.commQuery(sql, CountyInfo.class, provincialName,
					cityName, countyname);

			if (list != null) {

				if (list.size() == 0) {

					CityInfo city = cityInfoDao.queryCityInfoByName(cityName);

					this.addCounty(provincialInfo.getProvincialID() + "", city
							.getCityID()
							+ "", countyname, "http://884524.cnnmo.com/1.aspx");// 添加县

					// 查询刚刚添加的县信息
					sql = "select countyinfo.* from provincialinfo,cityinfo,countyinfo where provincialinfo.name=? and cityinfo.name=? and countyinfo.name=? and provincialinfo.provincialID=countyinfo.provincialID and cityinfo.cityID=countyinfo.cityId";

					list = DBHelper.commQuery(sql, CountyInfo.class,
							provincialName, cityName, countyname);

					if (list != null) {

						if (list.size() > 0) {


							return list.get(0);

						}

					}
				}
			}

		}

		return null;
	}

	/**
	 * 根据省名称和市名称查询该市所有县
	 * 
	 * @param provincialName
	 *            省名称
	 * @param cityName
	 *            市名称
	 * @return 县列表
	 */
	public List<CountyInfo> getCountyInfoList(String provincialName,
			String cityName) {

		String sql = "select countyinfo.* from provincialinfo,cityinfo,countyinfo where provincialinfo.name=? and cityinfo.name=? and provincialinfo.provincialID=countyinfo.provincialID and cityinfo.cityID=countyinfo.cityId";

		List<CountyInfo> list = DBHelper.commQuery(sql, CountyInfo.class,
				provincialName, cityName);

		return list;
	}

	/**
	 * 根据市查询县
	 * 
	 * @param cityId
	 * @return
	 */
	public List<CountyInfo> queryByCityId(String cityId) {
		String sql = "select * from countyinfo";
		if (cityId != null && cityId != "") {
			sql += "  where cityId = " + cityId;
		}
		return DBHelper.commQuery(sql, CountyInfo.class);
	}

}
