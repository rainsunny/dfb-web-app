package com.doufangbian.dao;

import com.doufangbian.entity.Douboinfo;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeiboinfoDao {

    /**
     * 页面
     *
     * @param pageNo
     * @param level
     * @param areaID
     * @param name
     * @return
     */
    public PageModel<Douboinfo> getWeiboinfoList(int pageNo, int level, int areaID, String name) {

        PageModel<Douboinfo> pm = new PageModel<Douboinfo>();

        pm.setSumCount(getWeiboinfoCount(level, areaID, name));

        pm.setCurrentPage(pageNo);

        String sql = "SELECT *,Count(dfb_weibo_reply.wid) AS countPL from (SELECT weiboinfo.id as id,weiboinfo.content,weiboinfo.time,weiboinfo.up,weiboinfo.down,weiboinfo.`share`,weiboinfo.uid,weiboinfo.countyID,weiboinfo.last_reply,countyinfo.countyID as cy_countyID,countyinfo.provincialID,countyinfo.cityId,countyinfo.`name`,userinfo.username,userinfo.id as user_id FROM weiboinfo ,countyinfo , userinfo WHERE weiboinfo.content like ? and countyinfo.countyID = weiboinfo.countyID AND userinfo.id = weiboinfo.uid) as c LEFT JOIN dfb_weibo_reply  on c.id = dfb_weibo_reply.wid ";

        if (level == 1) {

            sql += " where c.provincialID=" + areaID;
        } else if (level == 2) {

            sql += " where c.cityId=" + areaID;
        } else if (level == 3) {
            sql += " where c.cy_countyID=" + areaID;
        }

        sql += " GROUP BY c.id order By c.id desc";

        sql += " limit " + ((pageNo - 1) * pm.getSize() + "," + pm.getSize());

        List<Douboinfo> list = DBHelper.commQuery(sql, Douboinfo.class, name);

        pm.setData(list);

        return pm;

    }

    /**
     * 查询商家数据条数
     *
     * @param level  当前管理员等级
     * @param areaID 地区id
     * @param name   搜索关键字
     * @return 返回数据条数
     */
    public int getWeiboinfoCount(int level, int areaID, String name) {

        String sql = " SELECT count(*) FROM weiboinfo ,countyinfo , userinfo WHERE weiboinfo.content LIKE ? AND countyinfo.countyID = weiboinfo.countyID AND userinfo.id = weiboinfo.uid";
        if (level == 1) {

            sql += "  AND countyinfo.provincialID=" + areaID;
        } else if (level == 2) {

            sql += "  AND countyinfo.cityId=" + areaID;
        } else if (level == 3) {
            sql += "  AND countyinfo.countyID=" + areaID;
        }
        return DBHelper.commonQueryCount(sql, name);

    }

    /**
     * 添加微博
     *
     * @param wb
     * @return
     */
    public boolean addWeibo(Douboinfo wb) {
        String sql = "insert into weiboinfo(content,time,up,down,share,uid,countyID,last_reply,photo,updatetime) values(?,?,?,?,?,?,?,?,?,?)";

        return DBHelper.commonUpdate(sql, wb.getContent(), wb.getTime(), wb
                .getUp(), wb.getDown(), wb.getShare(), wb.getUid(), wb
                .getCountyID(), wb.getLast_reply(), wb.getPhoto(), wb
                .getLast_reply());

    }

    /**
     * 根据微博的id查询微博详细信息
     *
     * @param id
     * @return
     */
    public Douboinfo queryById(String id) {
        String sql = "select weiboinfo.id,weiboinfo.content,weiboinfo.time,weiboinfo.up,weiboinfo.down,weiboinfo.`photo`,weiboinfo.`share`,weiboinfo.uid,weiboinfo.countyID,weiboinfo.last_reply,userInfo.id,userInfo.username from weiboinfo,userInfo where weiboinfo.uid=userInfo.id and weiboinfo.id= ?";

        List<Douboinfo> list = DBHelper.commQuery(sql, Douboinfo.class, id);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 删除微博 （根据id）
     *
     * @param id
     * @return
     */
    public boolean deleteById(String id) {

        String sql = "delete from weiboinfo where id =?";

        return DBHelper.commonUpdate(sql, id);
    }

    /**
     * 查询微博图片路劲
     *
     * @param id
     * @return
     */
    public List<String> queryWeiboImage(String id) {
        String sql = "SELECT photo FROM  weiboinfo WHERE id = ?";
        return DBHelper.queryImageName(sql, id);
    }

    /**
     * 修改微博信息 （根据id）
     *
     * @param
     * @return
     */
    public boolean updateById(String content, String up, String down,
                              String share, String uid, String updatetime, String photo, String id) {
        String sql = "update weiboinfo set content=?,up=?,down=?,share=?,uid=?,updatetime=?,photo=? where id =?";
        return DBHelper.commonUpdate(sql, content, up, down, share, uid,
                updatetime, photo, id);
    }

    /**
     * 修改评论时间
     *
     * @param time
     * @return
     */
    public boolean updateTimeById(String id, String time) {
        String sql = "update weiboinfo set last_reply=?,updatetime=? where id=?";
        return DBHelper.commonUpdate(sql, time, time, id);
    }

    /**
     * android获取时间戳上新豆博
     *
     * @param lastTime 最后回复时间
     * @param countyID 县级编号(如果为0表示全国)
     * @return 豆博列表
     */
    public List<Douboinfo> getNewWeiboInfoList(String lastTime,
                                               String lastUpdateTime, int countyID) {

        String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(select count(*) from weiboinfo where UNIX_TIMESTAMP(last_reply)>UNIX_TIMESTAMP(?)) as updatecount,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount  from weiboinfo,countyinfo,userinfo where unix_timestamp(updatetime)>unix_timestamp(?) "
                + (countyID == 0 ? "" : "and weiboinfo.countyID=? ")
                + " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";


        List<Douboinfo> list = null;

        if (countyID == 0) {
            list = DBHelper.commQuery(sql, Douboinfo.class, lastTime, lastUpdateTime);

        } else {
            list = DBHelper.commQuery(sql, Douboinfo.class, lastTime, lastUpdateTime, countyID);

        }

        System.out.println(list.size());

        return list;
    }

    /**
     * android获取时间戳上旧微博
     *
     * @param fristTime 早期回复时间
     * @param countyID  县级编号(如果为0表示全国)
     * @return 微博列表
     */
    public List<Douboinfo> getOLDWeiboInfoList(String fristTime, int countyID) {

        String sql = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where unix_timestamp(last_reply)<unix_timestamp(?) "
                + (countyID == 0 ? "" : "and weiboinfo.countyID=? ")
                + " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";


        String sqls = "select weiboinfo.*,countyinfo.name,userinfo.headphoto,userinfo.username,(SELECT count(*) FROM dfb_weibo_reply WHERE wid=weiboinfo.id ) AS comentcount from weiboinfo,countyinfo,userinfo where unix_timestamp(last_reply)<unix_timestamp('" + fristTime + "') "
                + (countyID == 0 ? "" : "and weiboinfo.countyID=" + countyID + " ")
                + " and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id order by last_reply desc limit 20";

        System.out.println(sqls);

        List<Douboinfo> list = null;

        System.out.println("------------------->" + countyID);

        if (countyID == 0) {
            list = DBHelper.commQuery(sql, Douboinfo.class, fristTime);

        } else {
            list = DBHelper.commQuery(sql, Douboinfo.class, fristTime, countyID);

        }

        System.out.println(list.size());

        return list;
    }

    /**
     * 修改笑脸
     */
    public Douboinfo updateUp(int weiId) {

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()); // 当前时间

        String sql = "update weiboinfo set up=up+1,updatetime=? where id=?";

        boolean result = DBHelper.commonUpdate(sql, time, weiId);

        System.out.println(result);
        if (result) {

            return queryWeiboInfoById(weiId);
        }

        return null;
    }

    /**
     * 修改哭脸
     */
    public Douboinfo updateDown(int weiId) {

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()); // 当前时间

        String sql = "update weiboinfo set down=down+1,updatetime=? where id=?";

        boolean result = DBHelper.commonUpdate(sql, time, weiId);

        if (result) {

            return queryWeiboInfoById(weiId);
        }

        return null;
    }

    /**
     * 查询顶总数
     *
     * @param weiId 微博id
     * @return 总数
     */
    public Douboinfo queryWeiboInfoById(int weiId) {

        String sql = "select weiboinfo.*,userinfo.headphoto,countyinfo.name,userinfo.username,count(dfb_weibo_reply.replycontent) as comentcount from weiboinfo,countyinfo,userinfo,dfb_weibo_reply where weiboinfo.id=? and weiboinfo.id=dfb_weibo_reply.wid and countyinfo.countyID=weiboinfo.countyID and weiboinfo.uid=userinfo.id ";

        List<Douboinfo> list = DBHelper.commQuery(sql, Douboinfo.class, weiId);

        for (Douboinfo weiboInfo : list) {
            System.out.println(weiboInfo.getHeadphoto());
        }

        if (list.size() != 0) {

            return list.get(0);

        }

        return null;
    }

    /**
     * 修改微博浏览量
     *
     * @param wid 微博id
     * @return 微博最新信息
     */
    public boolean updateSeeCount(int wid) {

        System.out.println("------------->修改微博浏览量");

        String sql = "update weiboinfo set seecount=seecount+1,updatetime=? where id=?";

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        boolean result = DBHelper.commonUpdate(sql, time, wid);

        return result;
    }

    /**
     * 修改微博分享
     *
     * @param weiId 微博id
     * @return 微博信息
     */
    public Douboinfo updateShare(int weiId) {

        String sql = "update weiboinfo set share=share+1,updatetime=? where id=?";

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        boolean result = DBHelper.commonUpdate(sql, time, weiId);

        if (result) {

            return queryById(weiId + "");
        }
        return null;
    }

    public int updateShareWeibo(int wid) {

        String sql = "update weiboinfo set share=share+1 where id=?";

        boolean b = DBHelper.commonUpdate(sql, wid);


        int share = 0;

        if (b) {

            sql = "select count(share) from weiboinfo where id=?";

            share = DBHelper.commonQueryCount(sql, wid);

        }

        return share;
    }

}
