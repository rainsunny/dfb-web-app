package com.doufangbian.dao;

import com.doufangbian.entity.AdminUserInfo;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

import java.util.List;

public class AdminUserInfoDao {
    /**
     * 登录验证
     *
     * @param username
     * @param password
     * @return
     */
    public AdminUserInfo checkLogin(String username, String password) {

        String sql = "select * from adminuserinfo where  username=? and password=?";//查询语句

        List<AdminUserInfo> list = DBHelper.commQuery(sql, AdminUserInfo.class, username, password);//用户对象

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 添加
     *
     * @param username
     * @param password
     * @param level
     * @param areaId
     * @return
     */
    public boolean addAdminUser(String username, String password, String level, String areaId) {
        String sql = "insert into adminuserinfo(username,password,level,areaId) values(?,?,?,?)";
        return DBHelper.commonUpdate(sql, username, password, level, areaId);
    }

    /**
     * 根据id修改信息
     *
     * @param id
     * @param username
     * @param password
     * @param level
     * @param areaId
     * @return
     */
    public boolean updateAdminUser(String username, String password, String level, String areaId, String id) {
        String sql = "update adminuserinfo set username=?,password=?,level=?,areaId=? where id =?";
        return DBHelper.commonUpdate(sql, username, password, level, areaId, id);
    }

    /**
     * 分页查询所有的管理员
     */
    public PageModel<AdminUserInfo> queryAll(int pageNo) {
        String sql = "SELECT adminuserinfo.*,NAME as areaName FROM adminuserinfo JOIN provincialinfo ON adminuserinfo.areaId = provincialinfo.provincialID WHERE LEVEL= 1"
                + " UNION SELECT adminuserinfo.*,NAME FROM adminuserinfo JOIN cityinfo ON adminuserinfo.areaId = cityinfo.cityId WHERE LEVEL= 2"
                + " UNION SELECT adminuserinfo.*,NAME FROM adminuserinfo JOIN countyinfo ON adminuserinfo.areaId = countyinfo.countyID WHERE LEVEL= 3";
        PageModel<AdminUserInfo> pm = new PageModel<AdminUserInfo>();

        pm.setSumCount(querySumCount());

        pm.setCurrentPage(pageNo);

        sql += "  ORDER BY id DESC limit " + (pageNo - 1) * pm.getSize() + "," + pm.getSize();

        List<AdminUserInfo> list = DBHelper.commQuery(sql, AdminUserInfo.class);

        pm.setData(list);

        return pm;
    }

    /**
     * 查询管理员总条数
     *
     * @return
     */
    public Integer querySumCount() {
        String sql = "select count(*) from ( SELECT adminuserinfo.*,NAME FROM adminuserinfo JOIN provincialinfo ON adminuserinfo.areaId = provincialinfo.provincialID WHERE LEVEL= 1"
                + " UNION SELECT adminuserinfo.*,NAME FROM adminuserinfo JOIN cityinfo ON adminuserinfo.areaId = cityinfo.cityId WHERE LEVEL= 2"
                + " UNION SELECT adminuserinfo.*,NAME FROM adminuserinfo JOIN countyinfo ON adminuserinfo.areaId = countyinfo.countyID WHERE LEVEL= 3) as abc";
        return DBHelper.commonQueryCount(sql);
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @param id
     * @return
     */
    public boolean updatePasswordById(String newPassword, int id) {
        String sql = "update adminuserinfo set password=? where id = ?";
        return DBHelper.commonUpdate(sql, newPassword, id);
    }

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    public boolean queryCheckUsername(String username) {
        String sql = "select count(*) from adminuserinfo where username =?";
        return DBHelper.commonQueryCount(sql, username) > 0 ? true : false;
    }

    /**
     * 根据Id查询管理员信息
     *
     * @param id
     * @return
     */
    public AdminUserInfo queryById(String id) {
        String sql = "select * from adminuserinfo where id =?";

        List<AdminUserInfo> list = DBHelper.commQuery(sql, AdminUserInfo.class, id);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 删除管理员 根据Id
     *
     * @param id
     * @return
     */
    public boolean deleteAdminUser(String id) {
        String sql = "delete from adminuserinfo where id =?";
        return DBHelper.commonUpdate(sql, id);
    }
}
