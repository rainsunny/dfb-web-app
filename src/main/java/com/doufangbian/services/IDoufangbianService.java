package com.doufangbian.services;

import java.util.List;

import com.doufangbian.entity.CelebrityOrder;
import com.doufangbian.entity.CityInfo;
import com.doufangbian.entity.CountyInfo;
import com.doufangbian.entity.Dfb_catgroup_map;
import com.doufangbian.entity.Dfb_groupmerchant_map;
import com.doufangbian.entity.Dfb_merchant_cat;
import com.doufangbian.entity.Dfb_merchant_comment;
import com.doufangbian.entity.Dfb_merchant_group;
import com.doufangbian.entity.Dfb_merchant_image;
import com.doufangbian.entity.Doubo_reply;
import com.doufangbian.entity.Douboinfo;
import com.doufangbian.entity.MerchantInfo;
import com.doufangbian.entity.ProvincialInfo;
import com.doufangbian.entity.Userimagelist;
import com.doufangbian.entity.Userinfo;
import com.doufangbian.entity.UserseeListInfo;



public interface IDoufangbianService {
	
	
	
	/*临时模块（不缓存）*/

	/**
	 * 
	 * 根据大组id和地区id查询小组列表(备用)
	 */
	public List<Dfb_merchant_group> getMerchantGroupByCatCountyID(int catID,int countyID);
	
	
	/**
	 * 
	 * 根据大组id查询小组列表
	 */
	public List<Dfb_merchant_group> getMerchantGroupByCatID(int catID);
	
	
	/**
	 * 根据小组id查询该小组所有的商家(备用)
	 * @param groupID
	 * @return
	 */
	public List<MerchantInfo> getMerchantInfoByGroupID(int groupID);
	
	
	
	/**
	 * 根据小组id和地区id查询该小组所有的商家
	 * @param groupID
	 * @return
	 */
	public List<MerchantInfo> getMerchantInfoByGroupCountyID(int groupID,int countyID);
	
	
	/**
	 * 更新商家列表中的浏览量
	 * @param mid
	 */
	public void updateMerchantSeecount(int mid);
	
	
	/**
	 * 根据大组id和地区id获取大组数量
	 * @param catID 大组id小组
	 * @param countID
	 * @return
	 */
	public int getCatMerchantCount(int catID,int countID);
	
	

	/**
	 * 获取新豆博
	 * @param last_reply 最后回复时间
	 * @param countyID 县级id
	 * @return 豆博列表
	 */
	public List<Douboinfo> getNewDouboInfoList(String last_reply,int countyID);
	
	
	/**
	 * 
	 * 获取旧豆博
	 * @param frist_reply 早期时间
	 * @param countyID 县级id
	 * @return 豆博列表
	 */
	public List<Douboinfo> getOLDDouboInfoList(String frist_reply,int countyID);
	
	
	
	/**
	 * 
	 * 根据微博id查询微博信息
	 * @param wid 微博id
	 * @return 微博信息
	 */
	public Douboinfo getDouboInfoByWid(int wid);
	
	
	
	
	
	/**
	 * 获取该用户的新微博
	 * @param lastTime 最后发布时间
	 * @param uid 用户id
	 * @return 微博列表
	 */
	public List<Douboinfo> getNewWeiboListByUid(String lastTime,int uid);


	/**
	 * 获取该用户旧微博
	 * @param fristTime 早期发布时间
	 * @param uid 用户id
	 * @return 微博列表
	 */
	public List<Douboinfo> getOldWeiboListByUid(String fristTime,int uid);
	
	
	
	/**
	 * 
	 * 获取某个用户的笑脸苦脸总数
	 * @param id
	 * @return
	 */
	public CelebrityOrder getCelebrityOrderByID(int id);
	
	
	/**  
	 * 获取某个用户的新评论
	 * @return
	 */
	public List<Doubo_reply> getNewWeibo_replyByUid(int uid,String lastTime);
	
	
	/**
	 * 获取某个用户的旧评论
	 * @return
	 */
	public List<Doubo_reply> getOldWeibo_replyByUid(int uid,String fristTime);
	
	
	
	/**
	 * 获取新用户访问列表
	 * a@return
	 */
	public List<UserseeListInfo> getNewSeelistInfo(int uid,String seeTime);
	
	/**
	 * 获取旧用户访问列表
	 * @param uid 用户id
	 * @param seetime 用户查看时间
	 * @return 旧查看列表
	 */
	public List<UserseeListInfo> getOldSeelistInfo(int uid,String seetime);
	
	/**
	 * 获取查看总数
	 * @param count
	 * @return
	 */
	public int getUserSeecount(int uid);
	
	
	/**
	 * 获取新我评论
	 * @return 豆博列表
	 */
	public List<Douboinfo> getNewMyComment(String lastTime,int uid);
	
	/**
	 * 
	 * 获取旧我评论
	 */
	public List<Douboinfo> getOldMyComment(String frist,int uid);
	
	
	/**
	 * 
	 * 搜索商家
	 * @param keyword
	 * @return
	 */
	public List<MerchantInfo> getSearch(String keyword,int countyID,int pagemum);
	
	
	/**
	 * 根据商家id获取商家信息
	 * @param id
	 * @return
	 */
	public MerchantInfo getMerchantInfoByID(int id);
	
	
	/**
	 * 
	 * 获取所有省
	 * @return 省列表
	 */
	public List<ProvincialInfo> getProvincialInfo(int provincialID);
	
	/**
	 * 
	 * 获取该省所有市（当前市在前面）
	 */
	public List<CityInfo> getCityInfoByProvincialID(int provincialID,int cityID);
	
	/**
	 * 获取该市所有县（当前县在前面）
	 */
	public List<CountyInfo> getCountyInfoByCityID(int cityID,int countyID);
	
	
	
	/**
	 * 
	 * 获取用户相册
	 * @param uid 用户id
	 * @return 用户相册列表
	 */
	public List<Userimagelist> getUserImageList(int uid);
	
	
	/**
	 * 
	 * 获取用户相册大小
	 * @param uid 用户id
	 * @return
	 */
	public int getUserImageListSize(int uid);
	
	/* 分组模块 */
	
	/**
	 * 根据地区id查询该地区的所有大分组
	 * @param CountyID 地区id
	 * @return 大组
	 */
	public List<Dfb_merchant_cat> getMerchantCat(int countyID);
	
	
	/**
	 * 
	 * 根据修改时间查询大分组是否有修改
	 * @param updateTime 更新时间
	 * @return 大分组列表
	 */
	public List<Dfb_merchant_cat> getNewMerchantCat(String updateTime,int countyID);

	
	
	
	
	/**
	 * 
	 * 根据大分组的id查询所有小分组
	 * @param catId 大分组
	 * @return 小分组列表
	 */
	public List<Dfb_merchant_group> getAllMerchant_group(int countyID);
	
	
	/**
	 * 
	 * 根据修改时间查询小分组是否有修改
	 * @param updateTime 修改时间
	 * @return 小分组列表
	 */
	public List<Dfb_merchant_group> getNewMerchant_group(String updateTime,int countyID);
	
	
	
	/**
	 * 查询某个大分组的小组的关系表
	 * @param catID
	 * @return
	 */
	public List<Dfb_catgroup_map> getCatGroupMapByCatID(int countyID);
	
	
	
	/**
	 * 
	 * 检查某大组和和小组的关系表是否有更新
	 * @param catID
	 * @param updateTime
	 * @param countyID 地区编号
	 * @return
	 */
	public List<Dfb_catgroup_map> getNewCatGroupMap(int countyID,String updateTime);
	
	
	
	/**
	 * 
	 * 根据分组id和地区id查询该地区的某个小组的商家关系表
	 * @param groupID  小组id
	 * @param countyID 地区编号
	 * @return
	 */
	public List<Dfb_groupmerchant_map> getGroupMerchantMapByGroupID(int countyID);
	
	
	
	/**
	 * 检查某小组和商家的关系表是否有更新
	 * @param groupID
	 * @param countyID
	 * @param updateTime
	 * @return
	 */
	public List<Dfb_groupmerchant_map> getNewGroupMerchantMap(int countyID,String updateTime);
	
	
	
	
	
	
	/**
	 * 
	 * 获取某地区下面的大组的商家
	 * @param CatId 大组id
	 * @param CountyID 地区编号
	 * @return 商家列表
	 */
	public List<MerchantInfo> getMerchantByCatID(int CatId,int CountyID);
	
	
	/**
	 * 
	 * 获取最新商家
	 * @param lastTime 最后评论时间
	 * @param countyID 地区编号
	 * @return 商家列表
	 */
	public List<MerchantInfo> getNewMerchantInfoList(String lastTime,String lastUpdateTime,
			int countyID);
	
	/**
	 * 
	 * 获取以前的商家列表
	 * @param fristTime 早期时间
	 * @param countyID 地区编号
	 * @return 商家列表
	 */
	public List<MerchantInfo> getOLDMerchantInfoList(String fristTime,int countyID);
	
	
	
	
	/* 商家详情模块  */
	
	
	
	/**
	 * 
	 * 获取商家评论列表（第一次加载）
	 * @param mid 商家id
	 * @return 商家评论列表
	 */
	public List<Dfb_merchant_comment> getMerchant_commentList(int mid,int uid);
	
	
	/**
	 * 
	 * 根据商家id和最后评论时间查询该商家的新内容
	 * @param mid 商家id
	 * @param last_reply 最后评论时间
	 * @return 商家列表
	 */
	public List<Dfb_merchant_comment> getMerchant_commentListByMidAndUpdateTime(int mid,String last_reply,int uid);
	
	
	/**
	 * 商户查看列表(根据商户id和用户id)该商家记录用户的查看
	 * @param mid 商户id
	 * @param uid 用户id
	 */
	public boolean addSeeMerchantList(int mid,int uid);
	
	
	/**
	 * 
	 * 修改商户浏览量
	 * @param mid  商户id
	 * @return 商户信息
	 */
	public MerchantInfo updateMerchantInfo_seeCount(int mid);
	
	
	/**
	 * 根据商家id修改商家的分享次数
	 * @param mid
	 * @return
	 */
	public MerchantInfo updateMerchantShare(int mid);
	
	
	/**
	 * 
	 * 评论商家
	 * @param mid 商家id
	 * @param uid 用户id
	 * @param content 评论内容
	 * @return 商家信息
	 */
	public List<Dfb_merchant_comment> comentMerchat(int mid,int uid,String content);
	
	
	/**
	 * 根据商家id获取商家 图片
	 * @param mid 商家id
	 * @return 商家图片列表
	 */
	public List<Dfb_merchant_image> getMerchant_imageList(int mid);
	
	
	

	
	
	
	
	/* 微博模块 */
	
	/**
	 * android获取时间戳上新豆博
	 * @param lastTime 最后回复时间
	 * @param countyID 县级编号
	 * @return 豆博列表
	 */
	public List<Douboinfo> getNewWeiboInfoList(String lastTime,String lastUpdateTime,
			int countyID);
	
	
	/**
	 * android获取时间戳上旧微博
	 * @param fristTime 早期回复时间
	 * @param countyID 县级编号
	 * @return 微博列表
	 */
	public List<Douboinfo> getOLDWeiboInfoList(String fristTime,int countyID);
	
	
	
	/**
	 * 
	 * 查询名人排行榜
	 * @param pagenum 页数
	 * @return 名人列表
	 */
	public List<CelebrityOrder> getCelebrityOrderList(int pagenum);
	
	
	/**
	 * 修改笑脸
	 * @return
	 */
	public Douboinfo updateUp(int weiId);
	
	/**
	 * 修改哭脸
	 * @return
	 */
	public Douboinfo updateDown(int weiId);
	
	
	/**
	 * 修改分享次数
	 * @param weiId 微博id
	 * @return 微博内容
	 */
	public Douboinfo updateShare(int weiId);
	
	
	/**
	 * 
	 * 根据微博id查询评论列表(第一次查询)
	 * @param weiId 微博id
	 * @param uid 被查看的id
	 * @param otherid 查看者的id
	 * @return 评论列表
	 */
	public List<Doubo_reply> getCommentListByWid(int weiId,int uid,int otherId);
	
	
	/**
	 * 
	 * 评论二次获取检查更新（以微博id和最后的评论时间）
	 * @param weiId 微博id
	 * @param lastTime 最后评论lastTime
	 * @return 评论列表
	 */
	public List<Doubo_reply> getCommentListByWidAndTime(int weiId,String lastTime,int uid,int otherId);
	
	
	
	/**
	 * 
	 * 添加用户查看记录
	 * @param uid 被查看者的用户id
	 * @param otherId  其他用户id
	 * @param seeContent 查看内容
	 */
	public boolean addUserSeeInfo(int uid,int otherId,String seeContent);
	
	
	/**
	 * 
	 * 修改浏览量
	 * @param wid 微博id
	 */
	public boolean updateSeeCount(int wid);

	/**
	 * 添加微博评论
	 * @param content 评论内容
	 * @param time 时间
	 * @param uid 用户id
	 * @param wid 微博id
	 * @return 是否成功
	 */
	public boolean addComentWeibo(String content,int uid,int wid);
	
	
	
	
	/* 地区模块 */
	
	/**
	 * 根据省名称和市名称查询该市所有县
	 * @param provincialName 省名称
	 * @param cityName 市名称
	 * @return 县列表
	 */
	public List<CountyInfo> getCountyInfoList(String provincialName,String  cityName);
	
	/**
	 * 根据省、市、县的名称查询出该县所有信息
	 * @param provincialName 省名称
	 * @param cityName 市名称
	 * @param countyname 县名称
	 * @return 县信息
	 */
	public CountyInfo getCountyInfo(String provincialName,String  cityName,String countyname);
	
	
	/* 用户模块 */
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public Userinfo userLogin(String username,String password);
	
	
	/**
	 * 根据用户id修改用户最后登录时间
	 * @param uid 用户id
	 * @return 用户信息
	 */
	public Userinfo updateCheckin(int uid);
	
	
	
	
	/**
	 * 
	 * 修改用户信息
	 * @param headphoto
	 * @param password
	 * @param gender
	 * @param phone
	 * @param qq
	 * @return
	 */
	public Userinfo updateUserInfo(int uid,String headphoto,String username,String password,int gender, String phone, String qq);
	
	/**
	 * 
	 * 注册用户
	 * @param headphoto 头像
	 * @param username 用户名称
	 * @param password 密码
	 * @param gender
	 * @param phone
	 * @param qq
	 * @return
	 */
	public Userinfo addUserInfo(String headphoto,String username,String password,int gender,String phone,String qq);
	
	
	
	/**
	 * 
	 * 根据用户id查询用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	public Userinfo getUserinfoByID(int id);
	
	
	
	/**
	 * 
	 * 根据关键字查询用户
	 * @param key
	 * @return
	 */
	public List<CelebrityOrder> getUserInfoListByKey(String key,int pagenum);
	
	
	
	/**
	 * 
	 * 修改商家转发条数
	 * @param merchantID
	 * @return
	 */
	public int updateShareMerchant(int merchantID);
	
	
	
	/**
	 * 
	 * 修改微博转发条数
	 * @param wid
	 * @return
	 */
	public int updateShareWeibo(int wid);
	
	
}