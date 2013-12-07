package com.doufangbian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.doufangbian.dao.CityInfoDao;
import com.doufangbian.entity.CityInfo;

public class CityInfoServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String op = request.getParameter("op");
		//获取session
		HttpSession session=request.getSession();
		CityInfoDao cdao = new CityInfoDao();//城市
		PrintWriter out = response.getWriter();
		
		if("query".equals(op)){
			String provincialID = request.getParameter("provincialID");
			int pid = 0;
			if(provincialID!= null && provincialID!=""){
				pid = Integer.parseInt(provincialID);
			}
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
			
			//城市列表
			List<CityInfo> list=new ArrayList<CityInfo>();
			switch (level) {
			case 0:
				list = cdao.getCityList(pid);
				break;
			case 1:
				list=cdao.getCityListByProvincialId(areaID);
				break;
			case 2:
				list.add(cdao.getQueryCityByID(areaID));
				break;
			}
			request.setAttribute("cityList", list);
		}
		/**
		 * ajax获取市
		 */
		if("sel".equals(op)){
			String provincialID = request.getParameter("provincialID");
			int pid = 0;
			if(provincialID!= null && provincialID!=""){
				pid = Integer.parseInt(provincialID);
			}
			 List<CityInfo> listCity = cdao.getCityList(pid);
			 StringBuffer sb = new StringBuffer("<option value='0' selected='selected'>所有市</option>");
			 for (CityInfo cityInfo : listCity) {
				sb.append("<option value='"+cityInfo.getCityID()+"'>"+cityInfo.getName()+"</option>");
			}
			out.print(sb);
			out.flush();
			out.close();
		}
		/**
		 * 
		 * 添加
		 */
		if("add".equals(op)){
			String city = request.getParameter("city");
			String provincial = request.getParameter("provincial");
			boolean bool = cdao.addCity(provincial, city);
			out.print(bool);
			out.flush();
			out.close();
		}
		/**
		 * 判断市是否存在
		 */
		if("check".equals(op)){
			String city = request.getParameter("city");
			boolean bool = cdao.queryByName(city);
			out.print(bool);
			out.flush();
			out.close();
		}
		/**
		 * 根据市id查询市
		 */
		if("queryById".endsWith(op)){
			String cityId = request.getParameter("cityId");
			CityInfo c = cdao.getCityById(Integer.parseInt(cityId));
			request.setAttribute("c", c);
		}
		/**
		 * 查询所有的市
		 */
		if("queryAll".equals(op)){
			List<CityInfo> list = cdao.getCityList(null);
			request.setAttribute("cityList", list);
		}
		
	}

}
