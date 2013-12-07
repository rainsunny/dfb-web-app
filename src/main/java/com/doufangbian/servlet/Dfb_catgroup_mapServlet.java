package com.doufangbian.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.Dfb_catgroup_mapDao;

public class Dfb_catgroup_mapServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String op = request.getParameter("op");
			/**
			 * 添加大组与小组的关系
			 */
			if("add".equals(op)){
				String catId = request.getParameter("catId");
				String groupIds = request.getParameter("groupIds");
				groupIds = groupIds.substring(0,groupIds.length()-1);
				
				String[] groups = groupIds.split(","); 
				
				Dfb_catgroup_mapDao dao = new Dfb_catgroup_mapDao();
				
				dao.addCatgroup(catId, groups);
			}
			/**
			 * 移除大组小组的关系
			 */
			if("delete".equals(op)){
				String catId = request.getParameter("catId");
				String groupIds = request.getParameter("groupIds");
				groupIds = groupIds.substring(0,groupIds.length()-1);
				
				String[] groups = groupIds.split(","); 
				
				Dfb_catgroup_mapDao dao = new Dfb_catgroup_mapDao();
				
				for (int i = 0; i < groups.length; i++) {
					dao.delCatgroupByGroupId(catId, groups[i]);
				}
				
			}
	}

}
