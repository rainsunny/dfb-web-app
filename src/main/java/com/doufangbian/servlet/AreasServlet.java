package com.doufangbian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.doufangbian.dao.CountyInfoDao;
import com.doufangbian.entity.AdminUserInfo;
import com.doufangbian.entity.CountyInfo;
import com.doufangbian.entity.PageModel;

@SuppressWarnings("serial")
public class AreasServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String op = request.getParameter("op");
		HttpSession session=request.getSession();
		//获取当前管理员权限
		int level=0;
		if(session.getAttribute("level")!=null && session.getAttribute("level")!=""){
			level = Integer.parseInt(session.getAttribute("level")+"");
		}
		//获取当前管理员areaid
		int areaID=0;
		if(session.getAttribute("areaID")!=null && session.getAttribute("areaID")!=""){
			areaID = Integer.parseInt(session.getAttribute("areaID")+"");
		}
		PrintWriter out = response.getWriter();
		
		if ("query".equals(op)) {
			String pageNo = request.getParameter("pageNo");
			String keywords = request.getParameter("keywords");
			if(keywords!=null && keywords!="" && "get".equalsIgnoreCase(request.getMethod())){
				keywords = new String(keywords.getBytes("iso-8859-1"),"utf-8");
			}
			String provincialID = request.getParameter("provincialID");
			String cityId  = request.getParameter("cityId");
			
			//转换成int
			Integer no = 1;
			if (pageNo != null && pageNo != "") {
				no = Integer.parseInt(pageNo);
			}
			CountyInfoDao cdao = new CountyInfoDao();
			PageModel<CountyInfo> pm = null;
			
			Integer pid = 0;
			if(provincialID!=null && provincialID!=""){
				pid = Integer.parseInt(provincialID);
			}
			Integer cid = 0;
			if(cityId!=null && cityId!=""){
				cid = Integer.parseInt(cityId);
			}
			
			
			switch (level) {
			case 1://如果是省级管理员
				pid=areaID;
				break;
			case 2://如果是市级管理员
				cid=areaID;
				break;
			}
			
			if(keywords==null){
				keywords="";
			}
			request.setAttribute("keywords", keywords);
			
			pm = cdao.getCountyList(no, pid, cid,"%"+keywords+"%");
			
			request.setAttribute("pm", pm);
		}else if("addUser".equals(op)){//为当前管理员添加可操作账户

			int areid = Integer.parseInt(request.getParameter("areid"));
			String lv = request.getParameter("lv");
			if(lv==null || lv==""){
				lv="3";
			}
			// 获取该县相关信息
			CountyInfoDao countyInfoDao = new CountyInfoDao();

			CountyInfo countyInfo = countyInfoDao.getCountyInfo(areid);

			List<AdminUserInfo> list = (List<AdminUserInfo>) session
					.getAttribute("admin_user_list");

			boolean isAdd = true;

			for (AdminUserInfo adminUserInfo : list) {//

				if (adminUserInfo.getAreaId() == areid) {

					isAdd = false;

				}

			}

			if (isAdd) {
				AdminUserInfo adminUserInfo = new AdminUserInfo(Integer.parseInt(lv), areid,
						countyInfo.getName());
				
				session.setAttribute("areaName", adminUserInfo.getAreaName());

				list.add(adminUserInfo);
			}

			session.setAttribute("skip_url", "admin/merchant_table.jsp");
			

			session.setAttribute("admin_user_list", list);

			// 保存地址信息
			session.setAttribute("areaID", areid);
			

			session.setAttribute("level", lv);//点击这个添加的管理员默认是县级管理员

		}else if ("change_city".equals(op)) {// 切换城市

			level = Integer.parseInt(request.getParameter("level") + "");

			System.out.println("------------------->"+level);
			int areid = Integer.parseInt(request.getParameter("areid") + "");

			// 保存地址信息
			session.setAttribute("areaID", areid);

			session.setAttribute("level", level);
			
			

		}else if("add".endsWith(op)){
			//添加
			String provincial = request.getParameter("provincial");
			String city = request.getParameter("city");
			String xian = request.getParameter("xian");
			String wapUrl = request.getParameter("wapUrl");
			
			CountyInfoDao cdao = new CountyInfoDao();
			
			boolean bool = cdao.addCounty(provincial, city, xian, wapUrl);
			
			out.print(bool);
			out.flush();
			out.close();
		}else if("check".equals(op)){
			//判断县是否存在
			String xian = request.getParameter("xian");
			CountyInfoDao cdao = new CountyInfoDao();
			boolean bool = cdao.queryByName(xian);
			out.print(bool);
			out.flush();
			out.close();
		}else if("queryById".endsWith(op)){
			//根据id查询信息
			String countyId = request.getParameter("countyId");
			CountyInfoDao cdao = new CountyInfoDao();
			CountyInfo cy = cdao.getCountyInfo(Integer.parseInt(countyId));
			request.setAttribute("cy", cy);
		}else if("update".equals(op)){
			//修改信息
			String xian = request.getParameter("xian");
			String wapUrl = request.getParameter("wapUrl");
			String countyID = request.getParameter("countyID");
			CountyInfoDao cdao = new CountyInfoDao();
			boolean bool = cdao.updateArea(xian, wapUrl, countyID);
			out.print(bool);
			out.flush();
			out.close();
			
		}else if("sel".equals(op)){
			//获取县
			String cityId = request.getParameter("cityId");
			List<CountyInfo> list = new CountyInfoDao().queryByCityId(cityId);
			StringBuffer sb = new StringBuffer("<option value='0' selected='selected'>所有县</option>");
			for (CountyInfo cy: list) {
				sb.append("<option value='"+cy.getCountyID()+"'>"+cy.getName()+"</option>");
			}
			out.print(sb);
			out.flush();
			out.close();
		}else if("queryAll".equals(op)){
			List<CountyInfo> list =  new CountyInfoDao().queryByCityId(null);
			request.setAttribute("cyList", list);
		}
		
	}

}
