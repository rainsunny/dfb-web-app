package com.doufangbian.services;

import com.doufangbian.dao.*;
import com.doufangbian.entity.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DoufangbianServiceImpl implements IDoufangbianService {

    MerchantinfoDao merchantinfoDao = new MerchantinfoDao();// 商家操作类

    WeiboinfoDao weiboinfoDao = new WeiboinfoDao();// 微博操作类

    CountyInfoDao countyInfoDao = new CountyInfoDao();// 县级信息操作类

    CelebrityOrderDao celebrityOrderDao = new CelebrityOrderDao();// 名人排序操作类

    Dfb_weibo_replyDao dfbWeiboReplyDao = new Dfb_weibo_replyDao();// 评论列表

    UserinfoDao userinfoDao = new UserinfoDao();// 用户操作类

    Dfb_merchant_commentDao merchantCommentDao = new Dfb_merchant_commentDao();// 商家操作类

    Dfb_merchant_catDao dfbMerchantCatDao = new Dfb_merchant_catDao();// 大组操作类

    Dfb_merchant_groupDao dfbMerchantGroupDao = new Dfb_merchant_groupDao();// 小组操作类

    Dfb_catgroup_mapDao dfbCatgroupMapDao = new Dfb_catgroup_mapDao();// 大组小组关系操作类

    Dfb_groupmerchant_mapDao dfbGroupmerchantMapDao = new Dfb_groupmerchant_mapDao();// 小组商家操作类

	/* 获取大组信息以及检查更新 */

    /**
     * （做过） 获取所有大分组
     *
     * @param CountyID 地区id
     * @return 大组
     */
    public List<Dfb_merchant_cat> getMerchantCat(int countyID) {

        return dfbMerchantCatDao.getDfb_merchant_catAll(countyID);
    }

    /**
     * (做过)
     * <p/>
     * 根据修改时间查询大分组是否有修改
     *
     * @param updateTime 更新时间
     * @return 大分组列表
     */
    public List<Dfb_merchant_cat> getNewMerchantCat(String updateTime, int countyID) {
        return dfbMerchantCatDao.getNewMerchantCat(updateTime, countyID);
    }

    // select * from dfb_merchant_group where countyid=9 获取某地区的所有小组

    // select * from dfb_merchant_group where countyid=9 and
    // UNIX_TIMESTAMP(updatetime)>UNIX_TIMESTAMP('2013-04-09 01:02:03')
    // 获取小组是否有更新

    // select dfb_catgroup_map.* from dfb_catgroup_map,dfb_merchant_group where
    // cat_id=1 and countyid=9 and
    // dfb_catgroup_map.group_id=dfb_merchant_group.id 获取某个大分组和小分组的关系表
    // select dfb_catgroup_map.* from dfb_catgroup_map,dfb_merchant_group where
    // cat_id=1 and countyid=9 and
    // dfb_catgroup_map.group_id=dfb_merchant_group.id and
    // UNIX_TIMESTAMP(updatetime)>UNIX_TIMESTAMP(?) 检查某个大分组和小分组的关系表是否有更新

    // select dfb_groupmerchant_map.* from dfb_groupmerchant_map,merchantinfo
    // where merchantinfo.countyID=9 and
    // dfb_groupmerchant_map.merchant_id=merchantinfo.id 获取小分组和商家的关系表
    // select dfb_groupmerchant_map.* from dfb_groupmerchant_map,merchantinfo
    // where group_id=1 and merchantinfo.countyID=9 and
    // dfb_groupmerchant_map.merchant_id=merchantinfo.id and
    // UNIX_TIMESTAMP(updatetime)>UNIX_TIMESTAMP(?) 获取小分组和商家的关系表是否有更新

    /**
     * 获取某地区的所有小组
     */
    public List<Dfb_merchant_group> getAllMerchant_group(int countyID) {

        return dfbMerchantGroupDao.getAllGroup(countyID);
    }

    /**
     * 检查小组信息是否有更新
     *
     * @param updateTime
     * @return
     */
    public List<Dfb_merchant_group> getNewMerchant_group(String updateTime, int countyID) {

        return dfbMerchantGroupDao.getNewMerchant_group(updateTime, countyID);
    }

    /**
     * 修改豆币
     *
     * @param count 大小
     * @param uid
     */
    public void updateCash(int count, int uid) {

        userinfoDao.updateCash(count, uid);

    }

    /**
     * 查询某个大分组的小组的关系表
     *
     * @param catID
     * @return
     */
    public List<Dfb_catgroup_map> getCatGroupMapByCatID(int countyID) {

        return dfbCatgroupMapDao.getCatGroupMapByCatID(countyID);
    }

    /**
     * 检查某大组和和小组的关系表是否有更新
     *
     * @param catID
     * @param updateTime
     * @param countyID   地区编号
     * @return
     */
    public List<Dfb_catgroup_map> getNewCatGroupMap(int countyID, String updateTime) {
        return dfbCatgroupMapDao.getNewCatGroupMap(countyID, updateTime);
    }

    /**
     * 根据分组id和地区id查询该地区的某个小组的商家关系表
     *
     * @param countyID 地区编号
     * @return
     */
    public List<Dfb_groupmerchant_map> getGroupMerchantMapByGroupID(int countyID) {
        return dfbGroupmerchantMapDao.getGroupMerchantMapByGroupID(countyID);
    }

    /**
     * 检查某小组和商家的关系表是否有更新
     *
     * @param groupID
     * @param countyID
     * @param updateTime
     * @return
     */
    public List<Dfb_groupmerchant_map> getNewGroupMerchantMap(int countyID, String updateTime) {

        return dfbGroupmerchantMapDao.getNewGroupMerchantMap(countyID, updateTime);
    }

	/* 商家详细信息模块 */

    /**
     * (做过)
     * <p/>
     * 获取最新商家
     *
     * @param lastTime 最后评论时间
     * @param countyID 地区编号
     * @return 商家列表
     */
    public List<MerchantInfo> getNewMerchantInfoList(String lastTime, String lastUpdateTime, int countyID) {

        return merchantinfoDao.getNewMerchantInfoList(lastTime, lastUpdateTime,
                countyID);
    }

    /**
     * (做过) 获取以前的商家列表
     *
     * @param fristTime 早期时间
     * @param countyID  地区编号
     * @return 商家列表
     */
    public List<MerchantInfo> getOLDMerchantInfoList(String fristTime, int countyID) {

        return merchantinfoDao.getOLDMerchantInfoList(fristTime, countyID);
    }

    /**
     * 添加商户查看列表
     */
    public boolean addSeeMerchantList(int mid, int uid) {

        return merchantinfoDao.addSeeMerchantList(mid, uid);
    }

    /**
     * 获取商家评论列表（第一次加载）
     */
    public List<Dfb_merchant_comment> getMerchant_commentList(int mid, int uid) {

        if (uid > 0) {

            addSeeMerchantList(mid, uid);// 增加查看列表

        }

//		updateMerchantInfo_seeCount(mid);// 添加浏览次数

        updateMerchantSeecount(mid);//添加列表浏览次数

        return merchantCommentDao.getMerchant_commentList(mid);
    }

    /**
     * 更新商家查看列表
     *
     * @param mid
     */
    public void updateMerchantSeecount(int mid) {

        merchantinfoDao.updateMerchantSeecount(mid);

    }

    /**
     * 获取商家评论列表(第二次加载)
     */
    public List<Dfb_merchant_comment> getMerchant_commentListByMidAndUpdateTime(int mid, String last_reply, int uid) {

        if (uid > 0) {

            addSeeMerchantList(mid, uid);// 增加查看列表

        }

//		updateMerchantInfo_seeCount(mid);// 添加浏览次数

        updateMerchantSeecount(mid);

        return merchantCommentDao.getMerchant_commentListByMidAndUpdateTime(mid, last_reply);
    }

    /**
     * 评论商家
     */
    public List<Dfb_merchant_comment> comentMerchat(int mid, int uid, String content) {


        int count = merchantCommentDao.queryCountByUid(mid, uid);


        if (count == 0) {
            updateCash(2, uid);
        }
        return merchantCommentDao.comentMerchat(mid, uid, content);
    }

    /**
     * 获取商家图片列表
     */
    public List<Dfb_merchant_image> getMerchant_imageList(int mid) {
        return merchantinfoDao.getMerchant_imageList(mid);
    }

    /**
     * 修改商家查看总数
     */
    public MerchantInfo updateMerchantInfo_seeCount(int mid) {

        return merchantinfoDao.updateMerchantInfo_seeCount(mid);
    }

    /**
     * 修改商家分享次数
     */
    public MerchantInfo updateMerchantShare(int mid) {

        return merchantinfoDao.updateMerchantShare(mid);
    }

    /**
     * 根据省、市、县的名称查询出该县所有信息
     *
     * @param provincialName 省名称
     * @param cityName       市名称
     * @param countyname     县名称
     * @return 县信息
     */
    public CountyInfo getCountyInfo(String provincialName, String cityName, String countyname) {

        return countyInfoDao.getCountyInfo(provincialName, cityName, countyname);
    }

    /**
     * 根据省名称和市名称查询该市所有县
     *
     * @param provincialName 省名称
     * @param cityName       市名称
     * @return 县列表
     */
    public List<CountyInfo> getCountyInfoList(String provincialName, String cityName) {

        return countyInfoDao.getCountyInfoList(provincialName, cityName);
    }

	/* 微博区域 */

    /**
     * (做过) android获取时间戳上新豆博
     *
     * @param lastTime 最后回复时间
     * @param countyID 县级编号
     * @return 豆博列表
     */
    public List<Douboinfo> getNewWeiboInfoList(String lastTime, String lastUpdateTime, int countyID) {

        return weiboinfoDao.getNewWeiboInfoList(lastTime, lastUpdateTime,
                countyID);
    }

    /**
     * (做过) android获取时间戳上旧微博
     *
     * @param fristTime 早期回复时间
     * @param countyID  县级编号
     * @return 微博列表
     */
    public List<Douboinfo> getOLDWeiboInfoList(String fristTime, int countyID) {

        List<Douboinfo> list = weiboinfoDao.getOLDWeiboInfoList(fristTime,
                countyID);
        return list;

    }

    /**
     * （做过） 返回排行榜
     */
    public List<CelebrityOrder> getCelebrityOrderList(int pagenum) {

        List<CelebrityOrder> list = celebrityOrderDao
                .getCelebrityOrderList(pagenum);

        return list;
    }

    /**
     * （做过） 修改哭脸
     */
    public Douboinfo updateDown(int weiId) {

        updateSeeCount(weiId);// 修改浏览量

        return weiboinfoDao.updateDown(weiId);
    }

    /**
     * (做过) 修改笑脸
     */
    public Douboinfo updateUp(int weiId) {

        updateSeeCount(weiId);// 修改浏览量

        return weiboinfoDao.updateUp(weiId);
    }

	/* 评论模块 */

    /**
     * (做过)
     * <p/>
     * 根据微博id查询评论列表(第一次查询)
     *
     * @param weiId   微博id
     * @param uid     被查看的id
     * @param otherid 查看者的id
     * @return 评论列表
     */
    public List<Doubo_reply> getCommentListByWid(int weiId, int uid, int otherId) {

        if (uid != otherId && otherId > 0) {// 如果被查看者不是查看者，则添加此记录(weiid就是查看内容)

            addUserSeeInfo(uid, otherId, weiId + "");

            updateSeeCount(weiId);// 修改浏览量

        }

        return dfbWeiboReplyDao.queryAll(weiId + "");
    }

    /**
     * （做过） 评论二次获取检查更新（以微博id和最后的评论时间）添加查看豆博记录
     *
     * @param weiId    微博id
     * @param lasttime 最后评论lasttime
     * @param uid      被查看的id
     * @param otherid  查看者的id
     * @return 评论列表
     */
    public List<Doubo_reply> getCommentListByWidAndTime(int weiId,
                                                        String lasttime, int uid, int otherId) {

        if (uid != otherId && otherId > 0) {// 如果被查看者不是查看者，则添加此记录(weiid就是查看内容)

            addUserSeeInfo(uid, otherId, weiId + "");// 修改查看列表
        }

        updateSeeCount(weiId);// 修改浏览量

        return dfbWeiboReplyDao.getCommentListByWidAndTime(weiId, lasttime);

    }

    /**
     * （做过） 添加用户查看记录
     *
     * @param uid        被查看者的用户id
     * @param otherId    其他用户id
     * @param seeContent 查看内容
     */
    public boolean addUserSeeInfo(int uid, int otherId, String seeContent) {

        return userinfoDao.addUserSeeInfo(uid, otherId, seeContent);

    }

    /**
     * （做过） 修改浏览量
     *
     * @param wid 微博id
     */
    public boolean updateSeeCount(int wid) {

        return weiboinfoDao.updateSeeCount(wid);
    }

    /**
     * （做过） 添加微博评论
     *
     * @param content 评论内容
     * @param time    时间
     * @param uid     用户id
     * @param wid     微博id
     * @return 是否成功
     */
    public boolean addComentWeibo(String content, int uid, int wid) {

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()); // 当前时间

        int count = dfbWeiboReplyDao.queryCountByUid(wid, uid);

        Douboinfo douboinfo = weiboinfoDao.queryById(wid + "");

        if (count == 0) {//如果豆博评论的用户id不等于这条豆博的用户id那么执行加豆币操作
            updateCash(1, uid);
        }

        return dfbWeiboReplyDao.addReply(wid + "", uid + "", time, content);
    }

    /**
     * 修改微博分享次数
     */
    public Douboinfo updateShare(int weiId) {

        return weiboinfoDao.updateShare(weiId);
    }

    public Userinfo userLogin(String username, String password) {

        return userinfoDao.userLogin(username, password);
    }

    public Userinfo updateCheckin(int uid) {

        return userinfoDao.updateCheckin(uid);
    }

    public List<MerchantInfo> getMerchantByCatID(int CatId, int CountyID) {

        System.out.println(CatId + ":" + CountyID);

        return merchantinfoDao.getMerchantByCatID(CatId, CountyID);
    }

    /**
     * 注册用户
     */
    public Userinfo addUserInfo(String headphoto, String username,
                                String password, int gender, String phone, String qq) {

        Userinfo userinfo = userLogin(username, password);

        if (userinfo == null) {

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date());

            Userinfo user = new Userinfo(headphoto, username, password, gender,
                    phone, qq, 0, time, "没有消息", 1);

            boolean b = userinfoDao.addUser(user);

            if (b) {

                return userLogin(username, password);
            }
        }

        return null;
    }

    /**
     * 修改用户资料
     *
     * @param uid
     * @param headphoto
     * @param username
     * @param password
     * @param gender
     * @param phone
     * @param qq
     * @return
     */
    public Userinfo updateUserInfo(int uid, String headphoto, String username,
                                   String password, int gender, String phone, String qq) {

        boolean b = tempDao.updateUser(uid, headphoto, username, password,
                gender, phone, qq);

        if (b) {

            return userLogin(username, password);

        }

        return null;
    }

    /**
     * 以下是不缓存 *
     */

    TempDao tempDao = new TempDao();

    /**
     * 根据大组id和地区id查询该地区的大组所有的小组
     */
    public List<Dfb_merchant_group> getMerchantGroupByCatCountyID(int catID,
                                                                  int countyID) {

        return tempDao.getMerchantGroupByCatCountyID(catID, countyID);
    }

    /**
     * 根据小组id查询该小组所有的商家
     */
    public List<MerchantInfo> getMerchantInfoByGroupID(int groupID) {

        return tempDao.getMerchantInfoByGroupID(groupID);
    }

    /**
     * 获取新豆博
     */
    public List<Douboinfo> getNewDouboInfoList(String lastReply, int countyID) {
        return tempDao.getNewDouboInfoList(lastReply, countyID);
    }

    /**
     * 获取旧豆博
     */
    public List<Douboinfo> getOLDDouboInfoList(String fristReply, int countyID) {

        return tempDao.getOLDDouboInfoList(fristReply, countyID);
    }

    /**
     * 根据微博id查询微博信息
     */
    public Douboinfo getDouboInfoByWid(int wid) {

        return tempDao.getDouboInfoByWid(wid);
    }

    /**
     * 获取该用户的新豆博
     */
    public List<Douboinfo> getNewWeiboListByUid(String lastTime, int uid) {

        return tempDao.getNewWeiboListByUid(lastTime, uid);
    }

    /**
     * 获取该用户的旧豆博
     */
    public List<Douboinfo> getOldWeiboListByUid(String fristTime, int uid) {

        return tempDao.getOldWeiboListByUid(fristTime, uid);
    }

    /**
     * 获取某个用户的新评论列表
     */
    public List<Doubo_reply> getNewWeibo_replyByUid(int uid, String lastTime) {

        return tempDao.getNewWeibo_replyByUid(uid, lastTime);
    }

    /**
     * 获取某个用户的旧评论列表
     */
    public List<Doubo_reply> getOldWeibo_replyByUid(int uid, String fristTime) {

        return tempDao.getOldWeibo_replyByUid(uid, fristTime);
    }

    /**
     * 获取新查看列表
     */
    public List<UserseeListInfo> getNewSeelistInfo(int uid, String seeTime) {
        return tempDao.getNewSeelistInfo(uid, seeTime);
    }

    /**
     * 获取旧查看列表
     */
    public List<UserseeListInfo> getOldSeelistInfo(int uid, String seetime) {

        return tempDao.getOldSeelistInfo(uid, seetime);
    }

    /**
     * 查看用户总数
     */
    public int getUserSeecount(int uid) {
        return tempDao.getUserSeecount(uid);
    }

    public Userinfo getUserinfoByID(int id) {

        return userinfoDao.queryById(id + "");
    }

    public List<Douboinfo> getNewMyComment(String lastTime, int uid) {

        return tempDao.getNewMyComment(lastTime, uid);
    }

    public List<Douboinfo> getOldMyComment(String frist, int uid) {

        return tempDao.getOldMyComment(frist, uid);
    }

    /**
     * 根据关键字搜索
     */
    public List<MerchantInfo> getSearch(String keyword, int countyID,
                                        int pagenum) {

        return tempDao.getSearch("%" + keyword + "%", countyID, pagenum);
    }

    /**
     * 根据商家id获取商家信息
     */
    public MerchantInfo getMerchantInfoByID(int id) {

        return merchantinfoDao.queryById(id + "");
    }

    /**
     * 获取本省和其他省列表
     */
    public List<ProvincialInfo> getProvincialInfo(int provincialID) {

        return tempDao.getProvincialInfo(provincialID);
    }

    /**
     * 获取该省所有市
     */
    public List<CityInfo> getCityInfoByProvincialID(int provincialID, int cityID) {

        return tempDao.getCityInfoByProvincialID(provincialID, cityID);
    }

    /**
     * 获取该市所有县
     */
    public List<CountyInfo> getCountyInfoByCityID(int cityID, int countyID) {

        return tempDao.getCountyInfoByCityID(cityID, countyID);
    }

    /**
     * 获取用户相册
     */
    public List<Userimagelist> getUserImageList(int uid) {

        return tempDao.getUserImageList(uid);
    }

    public List<CelebrityOrder> getUserInfoListByKey(String key, int pagenum) {

        return tempDao.getUserInfoListByKey(key, pagenum);
    }

    /**
     * 获取相册大小
     */
    public int getUserImageListSize(int uid) {

        List<Userimagelist> list = tempDao.getUserImageList(uid);

        if (list != null) {

            return list.size();
        }
        return 0;
    }

    /**
     * 获取大组下面商家个数
     */
    public int getCatMerchantCount(int catID, int countID) {

        System.out.println("-------------->商家个数");
        return dfbMerchantCatDao.getCatMerchantCount(catID, countID);
    }

    public int updateShareMerchant(int merchantID) {
        return merchantinfoDao.updateShareMerchant(merchantID);
    }

    public int updateShareWeibo(int wid) {
        return weiboinfoDao.updateShareWeibo(wid);
    }

    public CelebrityOrder getCelebrityOrderByID(int id) {

        return celebrityOrderDao.getCelebrityOrderByID(id);
    }

    /**
     * 根据大组id查询小组列表
     */
    public List<Dfb_merchant_group> getMerchantGroupByCatID(int catID) {

        return tempDao.getMerchantGroupByCatCountyID(catID);
    }

    /**
     * 根据小组id和地区id查询该小组所有的商家
     */
    public List<MerchantInfo> getMerchantInfoByGroupCountyID(int groupID, int countyID) {

        return tempDao.getMerchantInfoByGroupID(groupID, countyID);
    }

}