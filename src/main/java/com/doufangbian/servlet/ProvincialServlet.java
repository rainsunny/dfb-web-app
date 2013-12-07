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

import com.doufangbian.dao.ProvincialDao;
import com.doufangbian.entity.ProvincialInfo;

@SuppressWarnings("serial")
public class ProvincialServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		ProvincialDao pdao = new ProvincialDao();
		
		if("query".equals(op)){
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
			
			List<ProvincialInfo> list=new ArrayList<ProvincialInfo>();//省级城市列表
			
			switch (level) {
			case 0:
				list = pdao.getProvincialList();
				break;

			case 1:
				
				list.add(pdao.getQueryProvincialByID(areaID));
				break;
			case 2:
				list.add(pdao.getQueryProvincialByCityID(areaID));
				break;
			}
			
			request.setAttribute("proList", list);
		}
		//用于Ajax获取的省
		if("sel".equals(op)){
			List<ProvincialInfo> listProv   = pdao.getProvincialList();
			StringBuffer sb = new StringBuffer("<option value='0' selected='selected'>全国</option>");
			for (ProvincialInfo pro : listProv) {
				sb.append("<option value='"+pro.getProvincialID()+"'>"+pro.getName()+"</option>");
			}
			out.print(sb);
			out.flush();
			out.close();
		}
		//添加
		if("add".equals(op)){
			String provinaial = request.getParameter("provinaial");
			boolean bool = pdao.addProvincial(provinaial);
			out.print(bool);
			out.flush();
			out.close();
		}
		//验证省是否存在
		if("check".equals(op)){
			String provinaial = request.getParameter("provinaial");
			boolean bool = pdao.queryByName(provinaial);
			out.print(bool);
			out.flush();
			out.close();
		}
		/**
		 * 根据Id查询对应的省
		 */
		if("queryById".equals(op)){
			String provincialID = request.getParameter("provincialID");
			ProvincialInfo provin = pdao.getQueryProvincialByID(Integer.parseInt(provincialID));
			request.setAttribute("provin", provin);
		}
		/**
		 * 查询所有的的省
		 */
		if("queryAll".equals(op)){
			List<ProvincialInfo> list = pdao.getProvincialList();
			request.setAttribute("provList", list);
		}
	}

}
