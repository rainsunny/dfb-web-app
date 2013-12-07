package com.doufangbian.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.WeibocomentreplylistInfoDao;
import com.doufangbian.entity.Weibocomentreplylist;
public class WeibocomentreplylistInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		/*
		 * 根据评论id 查询回复内容
		 */
		if("query".equals(op)){
			String replyId = request.getParameter("replyId");
			List<Weibocomentreplylist> list = new WeibocomentreplylistInfoDao().queryByCommentId(replyId);
			request.setAttribute("weibocomentList", list);
		}
	}

}
