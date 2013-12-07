package com.doufangbian.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.Dfb_groupmerchant_mapDao;

public class Dfb_groupmerchant_mapServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		/**
		 * 添加小组与商家的关系
		 */
		if ("add".equals(op)) {
			String groupId = request.getParameter("groupId");
			String merchantIds = request.getParameter("merchantIds");
			merchantIds = merchantIds.substring(0,merchantIds.length()-1);
			
			String[] merchants = merchantIds.split(",");

			Dfb_groupmerchant_mapDao dao = new Dfb_groupmerchant_mapDao();

			dao.addGroupmerchant(groupId, merchants);

		}
		/**
		 * 移除小组中的商家
		 */
		
		if ("delete".equals(op)) {
			String groupId = request.getParameter("groupId");
			String merchantIds = request.getParameter("merchantIds");
			merchantIds = merchantIds.substring(0,merchantIds.length()-1);
			
			String[] merchants = merchantIds.split(",");

			Dfb_groupmerchant_mapDao dao = new Dfb_groupmerchant_mapDao();

			dao.delGroupmerchant(groupId, merchants);

		}
		/**
		 * 添加商家与小组的关系
		 */
		if ("add_mh".equals(op)) {
			String groupIds = request.getParameter("groupIds");
			String mid = request.getParameter("mid");
			groupIds = groupIds.substring(0,groupIds.length()-1);
			
			String[] gIds = groupIds.split(",");

			Dfb_groupmerchant_mapDao dao = new Dfb_groupmerchant_mapDao();

			dao.addGroupmerchant(gIds, mid);

		}
		/**
		 * 移除商家中的小组
		 */
		
		if ("delete_mh".equals(op)) {
			String groupIds = request.getParameter("groupIds");
			String mid = request.getParameter("mid");
			groupIds = groupIds.substring(0,groupIds.length()-1);
			
			String[] gIds = groupIds.split(",");

			Dfb_groupmerchant_mapDao dao = new Dfb_groupmerchant_mapDao();

			dao.delGroupmerchant(gIds, mid);

		}
	}

}
