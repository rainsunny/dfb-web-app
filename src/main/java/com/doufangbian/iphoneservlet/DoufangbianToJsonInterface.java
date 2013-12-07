package com.doufangbian.iphoneservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.doufangbian.dao.TempDao;
import com.doufangbian.dao.UserinfoDao;
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
import com.doufangbian.services.DoufangbianServiceImpl;
import com.doufangbian.util.ConstantUtil;
import com.doufangbian.util.DeleteServicesImage;

public class DoufangbianToJsonInterface extends HttpServlet {

	UserinfoDao userinfoDao = new UserinfoDao();

	DoufangbianServiceImpl doufangbianServiceImpl = new DoufangbianServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		response.setContentType("text/json");

		PrintWriter out = response.getWriter();

		String method = request.getParameter("method");

		String json = "";

		if ("getMerchantCat".equals(method)) {// 获取全部大组

			// 参数
			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Dfb_merchant_cat> list = doufangbianServiceImpl
					.getMerchantCat(countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getNewMerchantCat".equals(method)) {// 检查大组是否有更新

			// 参数

			String updateTime = request.getParameter("updateTime");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Dfb_merchant_cat> list = doufangbianServiceImpl
					.getNewMerchantCat(updateTime, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getAllMerchant_group".equals(method)) {// 获取全部小组

			// 参数
			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Dfb_merchant_group> list = doufangbianServiceImpl
					.getAllMerchant_group(countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getNewMerchant_group".equals(method)) {// 获取小组是否有更新

			// 参数

			String updateTime = request.getParameter("updateTime");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用

			List<Dfb_merchant_group> list = doufangbianServiceImpl
					.getNewMerchant_group(updateTime, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getCatGroupMapByCatID".equals(method)) {// 查询某个大分组的小组的关系表（暂时未使用）

			// 参数
			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Dfb_catgroup_map> list = doufangbianServiceImpl
					.getCatGroupMapByCatID(countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getNewCatGroupMap".equals(method)) {// 检查某大组和和小组的关系表是否有更新(暂时未使用)

			// 参数

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			String updateTime = request.getParameter("updateTime");

			// 调用

			List<Dfb_catgroup_map> list = doufangbianServiceImpl
					.getNewCatGroupMap(countyID, updateTime);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getGroupMerchantMapByGroupID".equals(method)) {// 根据分组id和地区id查询该地区的某个小组的商家关系表（暂时未使用）

			// 参数

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用

			List<Dfb_groupmerchant_map> list = doufangbianServiceImpl
					.getGroupMerchantMapByGroupID(countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getNewGroupMerchantMap".equals(method)) {// 检查某小组和商家的关系表是否有更新(暂时未使用)

			// 参数

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			String updateTime = request.getParameter("updateTime");

			// 调用
			List<Dfb_groupmerchant_map> list = doufangbianServiceImpl
					.getNewGroupMerchantMap(countyID, updateTime);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getNewMerchantInfoList".equals(method)) {// 获取最新商家

			// 参数

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			String lastTime = request.getParameter("lastTime");

			String lastUpdateTime = request.getParameter("lastUpdateTime");

			// 调用
			List<MerchantInfo> list = doufangbianServiceImpl
					.getNewMerchantInfoList(lastTime, lastUpdateTime, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getOLDMerchantInfoList".equals(method)) {// 获取以前的商家列表

			// 参数

			String fristTime = request.getParameter("fristTime");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<MerchantInfo> list = doufangbianServiceImpl
					.getOLDMerchantInfoList(fristTime, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("addSeeMerchantList".equals(method)) {// 添加商户查看列表（已通过其他方式自动调用，此方法如果有需要可单独调用）

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			int uid = Integer.parseInt(request.getParameter("uid"));

			boolean result = doufangbianServiceImpl
					.addSeeMerchantList(mid, uid);

			jsonObject.accumulate("result", result);

			json = jsonObject.toString();

		} else if ("getMerchant_commentList".equals(method)) {// 获取商家评论列表（第一次加载）（暂时未使用）

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			int uid = Integer.parseInt(request.getParameter("uid"));

			List<Dfb_merchant_comment> list = doufangbianServiceImpl
					.getMerchant_commentList(mid, uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getMerchant_commentListByMidAndUpdateTime".equals(method)) {// 获取商家评论列表(第二次加载)(暂时未使用)

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			String last_reply = request.getParameter("last_reply");

			int uid = Integer.parseInt(request.getParameter("uid"));

			List<Dfb_merchant_comment> list = doufangbianServiceImpl
					.getMerchant_commentListByMidAndUpdateTime(mid, last_reply,
							uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("comentMerchat".equals(method)) {// 评论商家

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			int uid = Integer.parseInt(request.getParameter("uid"));

			String content = request.getParameter("content");

			// 调用
			List<Dfb_merchant_comment> list = doufangbianServiceImpl
					.comentMerchat(mid, uid, content);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getMerchant_imageList".equals(method)) {// 获取商家图片列表

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			// 调用
			List<Dfb_merchant_image> list = doufangbianServiceImpl
					.getMerchant_imageList(mid);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("updateMerchantInfo_seeCount".equals(method)) {// 修改商家查看总数（已通过其他方式自动调用，此方法如果有需要可单独调用）

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			// 调用
			MerchantInfo merchantInfo = doufangbianServiceImpl
					.updateMerchantInfo_seeCount(mid);

			jsonObject.accumulate("merchantInfo", merchantInfo);

			json = jsonObject.toString();

		} else if ("updateMerchantShare".equals(method)) {// 修改商家分享次数

			// 参数
			int mid = Integer.parseInt(request.getParameter("mid"));

			// 调用
			MerchantInfo merchantInfo = doufangbianServiceImpl
					.updateMerchantShare(mid);

			jsonObject.accumulate("merchantInfo", merchantInfo);

			json = jsonObject.toString();

		} else if ("getCountyInfo".equals(method)) {// 根据省、市、县的名称查询出该县所有信息

			// 参数
			String provincialName = request.getParameter("provincialName");

			String cityName = request.getParameter("cityName");

			String countyname = request.getParameter("countyname");

			// 调用
			CountyInfo countyInfo = doufangbianServiceImpl.getCountyInfo(
					provincialName, cityName, countyname);

			jsonObject.accumulate("merchantInfo", countyInfo);

			json = jsonObject.toString();

		} else if ("getCountyInfoList".equals(method)) {// 根据省名称和市名称查询该市所有县

			// 参数
			String provincialName = request.getParameter("provincialName");

			String cityName = request.getParameter("cityName");

			// 调用
			List<CountyInfo> list = doufangbianServiceImpl.getCountyInfoList(
					provincialName, cityName);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getNewWeiboInfoList".equals(method)) {// 获取新豆博列表

			// 参数
			String lastTime = request.getParameter("lastTime");

			String lastUpdateTime = request.getParameter("lastUpdateTime");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getNewWeiboInfoList(
					lastTime, lastUpdateTime, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getOLDWeiboInfoList".equals(method)) {// android获取时间戳上旧微博

			// 参数
			String fristTime = request.getParameter("fristTime");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getOLDWeiboInfoList(
					fristTime, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getCelebrityOrderList".equals(method)) {// 返回排行榜

			// 参数
			int pagenum = Integer.parseInt(request.getParameter("pagenum"));

			// 调用
			List<CelebrityOrder> list = doufangbianServiceImpl
					.getCelebrityOrderList(pagenum);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("updateDown".equals(method)) {// 修改哭脸

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			// 调用
			Douboinfo douboinfo = doufangbianServiceImpl.updateDown(weiId);

			jsonObject.accumulate("douboinfo", douboinfo);

			json = jsonObject.toString();

		} else if ("updateUp".equals(method)) {// 修改笑脸

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			// 调用
			Douboinfo douboinfo = doufangbianServiceImpl.updateUp(weiId);

			jsonObject.accumulate("douboinfo", douboinfo);

			json = jsonObject.toString();

		} else if ("getCommentListByWid".equals(method)) {// 根据微博id查询评论列表(第一次查询)

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			int uid = Integer.parseInt(request.getParameter("uid"));

			int otherId = Integer.parseInt(request.getParameter("otherId"));

			// 调用
			List<Doubo_reply> list = doufangbianServiceImpl
					.getCommentListByWid(weiId, uid, otherId);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getCommentListByWidAndTime".equals(method)) {// 评论二次获取检查更新（以微博id和最后的评论时间）添加查看豆博记录

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			String lasttime = request.getParameter("lasttime");

			int uid = Integer.parseInt(request.getParameter("uid"));

			int otherId = Integer.parseInt(request.getParameter("otherId"));

			// 调用
			List<Doubo_reply> list = doufangbianServiceImpl
					.getCommentListByWidAndTime(weiId, lasttime, uid, otherId);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("addUserSeeInfo".equals(method)) {// 添加用户查看记录（已通过其他方式自动调用，此方法如果有需要可单独调用）

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			int otherId = Integer.parseInt(request.getParameter("otherId"));

			String seeContent = request.getParameter("seeContent");

			// 调用
			boolean result = doufangbianServiceImpl.addUserSeeInfo(uid,
					otherId, seeContent);

			jsonObject.accumulate("result", result);

			json = jsonObject.toString();

		} else if ("updateSeeCount".equals(method)) {// 修改浏览量（已通过其他方式自动调用，此方法如果有需要可单独调用）

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			// 调用
			boolean result = doufangbianServiceImpl.updateSeeCount(weiId);

			jsonObject.accumulate("result", result);

			json = jsonObject.toString();
		} else if ("addComentWeibo".equals(method)) {// 添加微博评论

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			String content = request.getParameter("content");

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用
			boolean result = doufangbianServiceImpl.addComentWeibo(content,
					uid, weiId);

			jsonObject.accumulate("result", result);

			json = jsonObject.toString();
		} else if ("updateShare".equals(method)) {// 修改微博分享次数

			// 参数
			int weiId = Integer.parseInt(request.getParameter("weiId"));

			// 调用
			Douboinfo douboinfo = doufangbianServiceImpl.updateShare(weiId);

			jsonObject.accumulate("douboinfo", douboinfo);

			json = jsonObject.toString();

		} else if ("userLogin".equals(method)) {// 用户登录

			// 参数
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Userinfo userinfo = doufangbianServiceImpl.userLogin(username,
					password);

			jsonObject.accumulate("userinfo", userinfo);

			json = jsonObject.toString();
		} else if ("updateCheckin".equals(method)) {// 根据用户id修改用户最后登录时间

			// 参数
			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用

			Userinfo userinfo = doufangbianServiceImpl.updateCheckin(uid);

			jsonObject.accumulate("userinfo", userinfo);

			json = jsonObject.toString();

		} else if ("getMerchantByCatID".equals(method)) {// 获取某地区下面的大组的商家

			// 参数
			int CatId = Integer.parseInt(request.getParameter("CatId"));

			int CountyID = Integer.parseInt(request.getParameter("CountyID"));

			// 调用

			List<MerchantInfo> list = doufangbianServiceImpl
					.getMerchantByCatID(CatId, CountyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("addUserInfo".equals(method)) {// 注册用户

			// 参数
			String headphoto = request.getParameter("headphoto");

			String username = request.getParameter("username");

			String password = request.getParameter("password");

			int gender = Integer.parseInt(request.getParameter("gender"));

			String phone = request.getParameter("phone");

			String qq = request.getParameter("qq");

			// 调用
			Userinfo userinfo = doufangbianServiceImpl.addUserInfo(headphoto,
					username, password, gender, phone, qq);

			jsonObject.accumulate("userinfo", userinfo);

			json = jsonObject.toString();

		} else if ("updateUserInfo".equals(method)) {// 修改用户资料

			// 参数
			int uid = Integer.parseInt(request.getParameter("uid"));

			String headphoto = request.getParameter("headphoto");

			String username = request.getParameter("username");

			String password = request.getParameter("password");

			int gender = Integer.parseInt(request.getParameter("gender"));

			String phone = request.getParameter("phone");

			String qq = request.getParameter("qq");

			// 调用
			Userinfo userinfo = doufangbianServiceImpl.updateUserInfo(uid,
					headphoto, username, password, gender, phone, qq);

			jsonObject.accumulate("userinfo", userinfo);

			json = jsonObject.toString();
		} else if ("getMerchantGroupByCatCountyID".equals(method)) {// 根据大组id和地区id查询该地区的大组所有的小组
			// 参数
			int catID = Integer.parseInt(request.getParameter("catID"));

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Dfb_merchant_group> list = doufangbianServiceImpl
					.getMerchantGroupByCatCountyID(catID, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getMerchantInfoByGroupID".equals(method)) {// 根据小组id查询该小组所有的商家

			// 参数
			int catID = Integer.parseInt(request.getParameter("catID"));

			// 调用
			List<MerchantInfo> list = doufangbianServiceImpl
					.getMerchantInfoByGroupID(catID);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getNewDouboInfoList".equals(method)) {// 获取新豆博

			// 参数

			String lastReply = request.getParameter("lastReply");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getNewDouboInfoList(
					lastReply, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getOLDDouboInfoList".equals(method)) {// 获取旧豆博

			// 参数

			String fristReply = request.getParameter("fristReply");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getOLDDouboInfoList(
					fristReply, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getDouboInfoByWid".equals(method)) {// 根据微博id查询微博信息

			// 参数
			int wid = Integer.parseInt(request.getParameter("wid"));

			// 调用
			Douboinfo douboinfo = doufangbianServiceImpl.getDouboInfoByWid(wid);

			jsonObject.accumulate("douboinfo", douboinfo);

			json = jsonObject.toString();

		} else if ("getNewWeiboListByUid".equals(method)) {// 获取该用户的新豆博

			// 参数

			String lastTime = request.getParameter("lastTime");

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getNewWeiboListByUid(
					lastTime, uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getOldWeiboListByUid".equals(method)) {// 获取该用户的旧豆博

			// 参数

			String fristTime = request.getParameter("fristTime");

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用

			List<Douboinfo> list = doufangbianServiceImpl.getOldWeiboListByUid(
					fristTime, uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getNewWeibo_replyByUid".equals(method)) {// 获取某个用户的新评论列表

			// 参数

			String lastTime = request.getParameter("lastTime");

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用

			List<Doubo_reply> list = doufangbianServiceImpl
					.getNewWeibo_replyByUid(uid, lastTime);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getOldWeibo_replyByUid".equals(method)) {// 获取某个用户的旧评论列表

			// 参数

			String fristTime = request.getParameter("fristTime");

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用

			List<Doubo_reply> list = doufangbianServiceImpl
					.getOldWeibo_replyByUid(uid, fristTime);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getNewSeelistInfo".equals(method)) {// 获取新查看列表

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			String seeTime = request.getParameter("seeTime");
			// 调用

			List<UserseeListInfo> list = doufangbianServiceImpl
					.getNewSeelistInfo(uid, seeTime);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getOldSeelistInfo".equals(method)) {// 获取旧查看列表

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			String seeTime = request.getParameter("seeTime");
			// 调用

			List<UserseeListInfo> list = doufangbianServiceImpl
					.getOldSeelistInfo(uid, seeTime);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getUserSeecount".equals(method)) {// 查看用户总数

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用
			int count = doufangbianServiceImpl.getUserSeecount(uid);

			jsonObject.accumulate("count", count);

			json = jsonObject.toString();
		} else if ("getUserinfoByID".equals(method)) {// 根据用户id查询用户信息

			// 参数

			int id = Integer.parseInt(request.getParameter("id"));

			// 调用
			Userinfo userinfo = doufangbianServiceImpl.getUserinfoByID(id);

			jsonObject.accumulate("userinfo", userinfo);

			json = jsonObject.toString();

		} else if ("getNewMyComment".equals(method)) {// 获取新我评论

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			String lastTime = request.getParameter("lastTime");

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getNewMyComment(
					lastTime, uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getOldMyComment".equals(method)) {// 获取旧我评论

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			String frist = request.getParameter("frist");

			// 调用
			List<Douboinfo> list = doufangbianServiceImpl.getOldMyComment(
					frist, uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getSearch".equals(method)) {// 根据关键字搜索

			// 参数
			String keyword = request.getParameter("keyword");

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			int pagenum = Integer.parseInt(request.getParameter("pagenum"));

			// 调用
			List<MerchantInfo> list = doufangbianServiceImpl.getSearch(keyword,
					countyID, pagenum);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getMerchantInfoByID".equals(method)) {// 根据商家id获取商家信息

			// 参数
			int id = Integer.parseInt(request.getParameter("id"));

			// 调用
			MerchantInfo merchantInfo = doufangbianServiceImpl
					.getMerchantInfoByID(id);

			jsonObject.accumulate("merchantInfo", merchantInfo);

			json = jsonObject.toString();

		} else if ("getProvincialInfo".equals(method)) {// 获取本省和其他省列表

			// 参数
			int provincialID = Integer.parseInt(request
					.getParameter("provincialID"));

			// 调用
			List<ProvincialInfo> list = doufangbianServiceImpl
					.getProvincialInfo(provincialID);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getCityInfoByProvincialID".equals(method)) {// 获取该省所有市

			// 参数
			int provincialID = Integer.parseInt(request
					.getParameter("provincialID"));

			int cityID = Integer.parseInt(request.getParameter("cityID"));

			// 调用
			List<CityInfo> list = doufangbianServiceImpl
					.getCityInfoByProvincialID(provincialID, cityID);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getCountyInfoByCityID".equals(method)) {// 获取该市所有县

			// 参数

			int cityID = Integer.parseInt(request.getParameter("cityID"));

			int countyID = Integer.parseInt(request.getParameter("countyID"));

			// 调用
			List<CountyInfo> list = doufangbianServiceImpl
					.getCountyInfoByCityID(cityID, countyID);

			jsonArray.addAll(list);

			json = jsonArray.toString();
		} else if ("getUserImageList".equals(method)) {// 获取用户相册
			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用

			List<Userimagelist> list = doufangbianServiceImpl
					.getUserImageList(uid);

			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getUserInfoListByKey".equals(method)) {// 根据关键字查询用户

			// 参数

			int pagenum = Integer.parseInt(request.getParameter("pagenum"));

			String key = request.getParameter("key");
			// 调用

			List<CelebrityOrder> list = doufangbianServiceImpl
					.getUserInfoListByKey(key, pagenum);
			jsonArray.addAll(list);

			json = jsonArray.toString();

		} else if ("getUserImageListSize".equals(method)) {// 获取相册大小

			// 参数

			int uid = Integer.parseInt(request.getParameter("uid"));

			// 调用

			int count = doufangbianServiceImpl.getUserImageListSize(uid);

			jsonObject.accumulate("count", count);

			json = jsonObject.toString();
		} else if ("getCatMerchantCount".equals(method)) {// 获取大组下面商家个数

			// 参数

			int catID = Integer.parseInt(request.getParameter("catID"));

			int countID = Integer.parseInt(request.getParameter("countID"));

			// 调用

			int count = doufangbianServiceImpl.getCatMerchantCount(catID,
					countID);

			jsonObject.accumulate("count", count);

			json = jsonObject.toString();
		} else if ("updateShareMerchant".equals(method)) {// 修改商家转发条数

			// 参数

			int merchantID = Integer.parseInt(request
					.getParameter("merchantID"));

			// 调用

			int count = doufangbianServiceImpl.updateShareMerchant(merchantID);

			jsonObject.accumulate("count", count);

			json = jsonObject.toString();

		} else if ("updateShareWeibo".equals(method)) {// 修改微博转发条数
			// 参数

			int wid = Integer.parseInt(request.getParameter("wid"));

			// 调用

			int count = doufangbianServiceImpl.updateShareWeibo(wid);

			jsonObject.accumulate("count", count);

			json = jsonObject.toString();
		} else if ("getCelebrityOrderByID".equals(method)) {

			// 参数

			int id = Integer.parseInt(request.getParameter("id"));

			// 调用

			CelebrityOrder celebrityOrder = doufangbianServiceImpl
					.getCelebrityOrderByID(id);

			jsonObject.accumulate("celebrityOrder", celebrityOrder);

			json = jsonObject.toString();
		} else if ("deleteUserImage".equals(method)) {
			
			
			// 参数

			
			String id = request.getParameter("id");

			

			System.out.println("------------>删除用户相册"+id);
			// 查询用户的头像
			List<String> list = new UserinfoDao().queryUserImagebyID(id);

			boolean bool = false;
			if (list.size() > 0) {

				bool = new UserinfoDao().deleteUserImageByID(id);

				if (bool) {
					DeleteServicesImage.deleteServicesImage(request,
							ConstantUtil.USER_IMAGE, list);
				}
			}

			jsonObject.accumulate("isdelete", bool);
			
			json=jsonObject.toString();
		}

		out.println(json);

		out.flush();
		out.close();

	}

}
