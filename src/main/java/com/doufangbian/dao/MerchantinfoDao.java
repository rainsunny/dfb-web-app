package com.doufangbian.dao;

import com.doufangbian.entity.Dfb_merchant_group;
import com.doufangbian.entity.Dfb_merchant_image;
import com.doufangbian.entity.MerchantInfo;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.DBHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class MerchantinfoDao {

    private static Logger logger = Logger.getLogger(MerchantinfoDao.class);

    /**
     * 查询商家信息
     *
     * @param pageNo
     * @param level
     * @param areaID
     * @param keyword
     * @param catId
     * @param groupId
     * @return
     */
    public PageModel<MerchantInfo> getMerchantList(String state, int pageNo, int level, int areaID, String keyword, String catId, String groupId) {

        PageModel<MerchantInfo> pm = new PageModel<MerchantInfo>();

        pm.setSumCount(getCountyCount(state, level, areaID, keyword, catId, groupId));

        pm.setCurrentPage(pageNo);

        StringBuilder builder = new StringBuilder("select mm.* from merchantinfo mm left join countyinfo cc on mm.countyID = cc.countyID where 1=1 ");

        if (StringUtils.isNotBlank(state)) {
            builder.append(" and mm.state = ").append(state);
        }

        if (StringUtils.isNotBlank(groupId) && !"0".equals(groupId)) {
            builder.append(" and mm.group_id = ").append(groupId);
        }

        switch (level) {
            case 1:
                builder.append(" and cc.provincialID = ").append(areaID);
                break;
            case 2:
                builder.append(" and cc.cityId = ").append(areaID);
                break;
            case 3:
                builder.append(" and cc.countyID = ").append(areaID);
                break;
        }

//        builder.append(" or mm.countyID = -1");

        if (StringUtils.isNotBlank(keyword)) {
            builder.append(" and mm.name like '%").append(keyword).append("%' ");
        }

        builder.append(" order by mm.id desc");
        builder.append(" limit ").append((pageNo - 1) * pm.getSize()).append(", ").append(pm.getSize());


        /*String sql = "select * from merchantinfo, countyinfo where merchantinfo.name like ? and state = "
                + state + " and countyinfo.countyID = merchantinfo.countyID "; // 默认表示全国

        if (level == 1) {
            sql += " and countyinfo.provincialID=" + areaID;
        } else if (level == 2) {
            sql += " and countyinfo.cityId=" + areaID;
        } else if (level == 3) {
            sql += " and merchantinfo.countyID=" + areaID;
        }

        sql += " or merchantinfo.countyID=-1 ";

        String str = sql;
        if (groupId != null && !"".equals(groupId) && !"0".equals(groupId)) {
            // 选了小组
            // 在商家信息里面添加了group_id，可以直接用这个字段判断；不用考虑大组
            str += " and merchantinfo.group_id = " + groupId;
        }

        str += " GROUP BY id";
        str += " order by id desc";

        str += " limit " + ((pageNo - 1) * pm.getSize() + "," + pm.getSize());*/

        logger.debug("执行execute sql: " + builder);


        List<MerchantInfo> list = DBHelper.commQuery(builder.toString(), MerchantInfo.class);

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
    public int getCountyCount(String state, int level, int areaID, String name, String catId, String groupId) {

        String sql = "SELECT COUNT(*) from(select count(*) from merchantinfo,countyinfo where merchantinfo.name like ? and state=" + state + " and countyinfo.countyID=merchantinfo.countyID ";// 默认表示全国

        if (level == 1) {

            sql += " and countyinfo.provincialID=" + areaID;
        } else if (level == 2) {

            sql += " and countyinfo.cityId=" + areaID;
        } else if (level == 3) {
            sql += " and merchantinfo.countyID=" + areaID;
        }
        sql += " or merchantinfo.countyID=-1 ";
        String str = sql;
        if (groupId != null && !"".equals(groupId) && !"0".equals(groupId)) {
            // 选了小组
            str += " and id in(SELECT dfb_groupmerchant_map.merchant_id FROM dfb_groupmerchant_map  where group_id IN ("
                    + groupId + "))";
        } else if (catId != null && !"".equals(catId) && !"0".equals(catId)) {
            // 选了大组
            str += " and id in (SELECT dfb_groupmerchant_map.merchant_id FROM dfb_groupmerchant_map  where group_id IN (SELECT dfb_catgroup_map.group_id FROM dfb_catgroup_map WHERE dfb_catgroup_map.cat_id IN("
                    + catId + ")))";
        } else if (catId != null && !"".equals(catId) && !"0".equals(catId)
                && groupId != null && !"".equals(groupId)
                && !"0".equals(groupId)) {
            // 大小组 都选择了
            str += " and id in(SELECT dfb_groupmerchant_map.merchant_id FROM dfb_groupmerchant_map  where group_id IN (SELECT dfb_catgroup_map.group_id FROM dfb_catgroup_map WHERE dfb_catgroup_map.cat_id IN("
                    + catId + "))and group_id IN (" + groupId + "))";
        }
        str += " GROUP BY id )a";

        return DBHelper.commonQueryCount(str, name);

    }

    /**
     * android获取时间戳上新商家
     *
     * @param lastTime 最后回复时间
     * @param countyID 县级编号
     * @return 商家列表
     */
    public List<MerchantInfo> getNewMerchantInfoList(String lastTime, String lastUpdateTime, int countyID) {


        //查询本地商家总数
        String localSql = "select count(*) from merchantinfo where countyID=?";

        int localCount = DBHelper.commonQueryCount(localSql, countyID);


        //查询本地商家
        String sql = "select *,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount,(select count(*) from merchantinfo where unix_timestamp(last_reply)>unix_timestamp(?) and countyID=? ) as updatecount from merchantinfo  a where unix_timestamp(updatetime)>unix_timestamp(?) and countyID=?  order by last_reply desc";

//		System.out.println("select *,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount,(select count(*) from merchantinfo where unix_timestamp(last_reply)>unix_timestamp('"+lastTime+"') and countyID="+countyID+" ) as updatecount from merchantinfo  a where unix_timestamp(updatetime)>unix_timestamp('"+lastUpdateTime+"') and countyID="+countyID+"  order by last_reply desc limit 20");

        List<MerchantInfo> list = DBHelper.commQuery(sql, MerchantInfo.class, lastTime, countyID, lastUpdateTime, countyID);// 查询本地商家


        // 查询超级管理员添加的商家
        String superSql = "select *,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount,(select count(*) from merchantinfo where unix_timestamp(last_reply)>unix_timestamp(?) and countyID=? ) as updatecount from merchantinfo  a where unix_timestamp(updatetime)>unix_timestamp(?) and  countyID=-1 order by last_reply desc limit 20";

        List<MerchantInfo> superList = DBHelper.commQuery(superSql, MerchantInfo.class, lastTime, countyID, lastUpdateTime);// 查询管理员添加的商家

        if (localCount > 0) {

            return list;
        } else {

            return superList;
        }

    }

    /**
     * android获取时间戳上旧商家
     *
     * @param fristTime 早期回复时间
     * @param countyID  县级编号
     * @return 商家列表
     */
    public List<MerchantInfo> getOLDMerchantInfoList(String fristTime, int countyID) {

        //查询本地商家总数
        String localSql = "select count(*) from merchantinfo where countyID=?";

        int localCount = DBHelper.commonQueryCount(localSql, countyID);


        // 查询本地商家
        String sql = "select merchantinfo.*,(select count(*) from dfb_merchant_comment where merchantinfo.id=dfb_merchant_comment.mid) as commentcount from merchantinfo where unix_timestamp(last_reply)<unix_timestamp(?) and countyID=?  order by last_reply desc limit 20 ";

        List<MerchantInfo> list = DBHelper.commQuery(sql, MerchantInfo.class, fristTime, countyID);

        // 查询超级管理员添加的商家
        String superSql = "select merchantinfo.*,(select count(*) from dfb_merchant_comment where merchantinfo.id=dfb_merchant_comment.mid) as commentcount from merchantinfo where unix_timestamp(last_reply)<unix_timestamp(?) and  merchantinfo.countyID=-1 order by last_reply desc limit 20 ";

        List<MerchantInfo> superList = DBHelper.commQuery(superSql, MerchantInfo.class, fristTime);


        if (localCount > 0) {
            return list;
        } else {

            return superList;
        }


    }

    /**
     * 查询所有的商家信息（也可以是指定小组的下的商家信息 入组/未入组）
     *
     * @param pageNo
     * @param groupId
     * @param chiose
     * @param keywords
     * @return
     */
    public PageModel<MerchantInfo> queryAll(int level, int areaID, int pageNo, String groupId, String chiose, String keywords) {
        PageModel<MerchantInfo> pm = new PageModel<MerchantInfo>();

        pm.setSumCount(queryAllSumCount(level, areaID, groupId, chiose,
                keywords));

        pm.setCurrentPage(pageNo);

        String sql = "SELECT * FROM merchantinfo,countyinfo where merchantinfo.name like ?  and countyinfo.countyID=merchantinfo.countyID ";
        /* 查询某小组下的商家（已经加入进来的商家） */
        if (chiose != null && !"".equals(chiose) && "yes".equals(chiose)) {
            sql = "SELECT * FROM merchantinfo WHERE id IN (SELECT merchant_id FROM dfb_groupmerchant_map WHERE group_id = "
                    + groupId + ") and name like ?";
        }
        /* 查询某小组下未加入的商家 */
        if (chiose != null && !"".equals(chiose) && "no".equals(chiose)) {
            sql = "SELECT * FROM merchantinfo WHERE id NOT IN (SELECT merchant_id FROM dfb_groupmerchant_map WHERE group_id = "
                    + groupId + ") and name like ?";
        }

        if (level == 1) {

            sql += " and countyinfo.provincialID=" + areaID;
        } else if (level == 2) {

            sql += " and countyinfo.cityId=" + areaID;
        } else if (level == 3) {
            sql += " and merchantinfo.countyID=" + areaID;
        }

        sql += "  ORDER BY id DESC LIMIT " + (pageNo - 1) * pm.getSize() + ","
                + pm.getSize();

        List<MerchantInfo> list = DBHelper.commQuery(sql, MerchantInfo.class, keywords);

        pm.setData(list);

        return pm;
    }

    /**
     * 查询总条数
     *
     * @return
     */
    public Integer queryAllSumCount(int level, int areaID, String groupId, String chiose, String keywords) {

        String sql = "SELECT count(*) FROM merchantinfo,countyinfo where merchantinfo.name like ?  and countyinfo.countyID=merchantinfo.countyID ";
        /* 查询某小组下的商家（已经加入进来的商家） */
        if (chiose != null && !"".equals(chiose) && "yes".equals(chiose)) {
            sql = "SELECT count(*) FROM merchantinfo WHERE id IN (SELECT merchant_id FROM dfb_groupmerchant_map WHERE group_id = "
                    + groupId + ") and name like ?";
        }
        /* 查询某小组下未加入的商家 */
        if (chiose != null && !"".equals(chiose) && "no".equals(chiose)) {
            sql = "SELECT count(*) FROM merchantinfo WHERE id NOT IN (SELECT merchant_id FROM dfb_groupmerchant_map WHERE group_id = "
                    + groupId + ") and name like ?";
        }

        if (level == 1) {

            sql += " and countyinfo.provincialID=" + areaID;
        } else if (level == 2) {

            sql += " and countyinfo.cityId=" + areaID;
        } else if (level == 3) {
            sql += " and merchantinfo.countyID=" + areaID;
        }

        return DBHelper.commonQueryCount(sql, keywords);
    }

    /**
     * 添加商家
     *
     * @param name
     * @param major
     * @param introduction
     * @param news
     * @param address
     * @param isrecommend
     * @param status
     * @param up
     * @param countyID
     * @return
     */
    public boolean addMerchants(String name, String major, String introduction,
                                String news, String address, String phone, String isrecommend,
                                String status, String up, String countyID, String time, String log) {
        String sql = "INSERT INTO merchantinfo(NAME,major,introduction,news,address,phone,isrecommend,STATUS,up,countyID,last_reply,time,updatetime,log) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return DBHelper.commonUpdate(sql, name, major, introduction, news,
                address, phone, isrecommend, status, up, countyID, time, time,
                time, log);
    }

    /**
     * 修改商家信息
     *
     * @param name
     * @param major
     * @param introduction
     * @param news
     * @param address
     * @param phone
     * @param isrecommend
     * @param status
     * @param up
     * @param merchantId
     * @return
     */
    public boolean updateMerchant(String name, String major,
                                  String introduction, String news, String address, String phone,
                                  String isrecommend, String status, String up, String updatetime,
                                  String log, String merchantId) {
        String sql = "update merchantinfo set name=?,major=?,introduction=?,news=?,address=?,phone=?,isrecommend=?,status=?,up=?,updatetime=?,log=? where id =?";
        return DBHelper.commonUpdate(sql, name, major, introduction, news,
                address, phone, isrecommend, status, up, updatetime, log,
                merchantId);
    }

    /**
     * 启用禁用商家
     *
     * @param state
     * @param updateTime
     * @param merchantId
     * @return
     */
    public boolean enabled(String state, String updateTime, String merchantId) {
        String sql = "update merchantinfo set state=?,updatetime=? where id =?";
        return DBHelper.commonUpdate(sql, state, updateTime, merchantId);
    }

    /**
     * 根据Id查询商家信息
     *
     * @param merchantId
     * @return
     */
    public MerchantInfo queryById(String merchantId) {
        String sql = "select * from merchantinfo where id = ?";

        List<MerchantInfo> list = DBHelper.commQuery(sql, MerchantInfo.class, merchantId);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 删除商家
     *
     * @param merchantId
     * @return
     */
    public boolean deleteById(String merchantId) {
        String sql = "delete from merchantinfo where id =?";
        return DBHelper.commonUpdate(sql, merchantId);
    }

    /**
     * 添加商户查看记录
     *
     * @param mid 商户id
     * @param uid 用户id
     * @return 是否添加成功
     */
    public boolean addSeeMerchantList(int mid, int uid) {

        String sql = "insert into merchantseelist(merchantid,uid,seetime) values(?,?,?)";

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return DBHelper.commonUpdate(sql, mid, uid, time);

    }

    /**
     * 修改商家浏览量
     *
     * @param mid
     * @return
     */
    public MerchantInfo updateMerchantInfo_seeCount(int mid) {

        String sql = "update merchantinfo set seecount=seecount+1,updatetime=? where id=?";

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        boolean result = DBHelper.commonUpdate(sql, time, mid);

        if (result) {

            return queryById(mid + "");// 根据商家id查询商家信息
        }
        return null;

    }

    /**
     * 修改商家分享次数
     *
     * @param mid 商家id
     * @return 商家修改后最新内容
     */
    public MerchantInfo updateMerchantShare(int mid) {

        String sql = "update merchantinfo set share=share+1 where id=?";

        boolean result = DBHelper.commonUpdate(sql, mid);

        if (result) {
            return queryById(mid + "");
        }

        return null;
    }

    /**
     * 根据商家id获取商家所有图片
     *
     * @param mid 商家id
     * @return 商家图片id
     */
    public List<Dfb_merchant_image> getMerchant_imageList(int mid) {

        String sql = "select * from dfb_merchant_image where mid=?";

        return DBHelper.commQuery(sql, Dfb_merchant_image.class, mid);
    }

    public List<Dfb_merchant_group> getNewMerchant_group(int catId, String updateTime) {

        String sql = "select dfb_merchant_group.* from dfb_catgroup_map,dfb_merchant_group where dfb_catgroup_map.cat_id=? and dfb_merchant_group.updatetime>? and dfb_catgroup_map.group_id=dfb_merchant_group.id group by dfb_merchant_group.name";
        return DBHelper.commQuery(sql, Dfb_merchant_group.class, catId,
                updateTime);
    }

    /**
     * 获取某地区下面的大组的商家
     *
     * @param CatId    大组id
     * @param CountyID 地区编号
     * @return 商家列表
     */
    public List<MerchantInfo> getMerchantByCatID(int catId, int countyID) {


        String sql = "select a.*,(select count(*) from dfb_merchant_comment where dfb_merchant_comment.mid=a.id) as commentcount from dfb_catgroup_map,dfb_groupmerchant_map,merchantinfo as a where dfb_catgroup_map.cat_id=? and a.countyID=? and dfb_catgroup_map.group_id=dfb_groupmerchant_map.group_id and dfb_groupmerchant_map.merchant_id=a.id group by a.id order by a.last_reply desc";

        return DBHelper.commQuery(sql, MerchantInfo.class, catId, countyID);

    }

    /**
     * 根据Id查询logo名称
     *
     * @param id
     * @return
     */
    public List<String> queryImageLogoByMid(String mid) {
        String sql = "select log from merchantinfo where id =?";
        return DBHelper.queryImageName(sql, mid);
    }

    public int updateShareMerchant(int merchantID) {

        String sql = "update merchantinfo set share=share+1 where id=?";

        boolean b = DBHelper.commonUpdate(sql, merchantID);

        int share = 0;

        if (b) {

            sql = "select share from merchantinfo where id=?";

            DBHelper.commonQueryCount(sql, merchantID);

        }
        return share;

    }

    /**
     * 获取商家商家信息改变列表浏览量
     *
     * @param mid
     */
    public void updateMerchantSeecount(int mid) {

        MerchantInfo merchantInfo = queryById(mid + "");

        String sql = "update merchantinfo set seecount=seecount+1,updatetime=? where last_reply<=? and countyID=? ORDER BY last_reply desc LIMIT 25";

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


        System.out.println("update merchantinfo set seecount=seecount+1,updatetime='" + time + "' where last_reply<='" + merchantInfo.getLast_reply() + "' and countyID=" + merchantInfo.getCountyID() + " ORDER BY last_reply desc LIMIT 20");


        DBHelper.commonUpdate(sql, time, merchantInfo.getLast_reply(), merchantInfo.getCountyID());

    }

}
