package com.doufangbian.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.Dfb_merchant_commentDao;
import com.doufangbian.entity.Dfb_merchant_comment;
import com.doufangbian.entity.PageModel;
/**
 * 对商家的评论
 * @author C_C
 *
 */
public class Dfb_merchant_commentServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String op = request.getParameter("op");
			PrintWriter out = response.getWriter();
			/**
			 * 查询全部
			 */
			if("query".equals(op)){
				String pageNo = request.getParameter("pageNo");
				int no = 1;
				if(pageNo!=null && pageNo!=""){
					no =Integer.parseInt(pageNo);
				}
				String mid = request.getParameter("mid");
				PageModel<Dfb_merchant_comment> pm = new Dfb_merchant_commentDao().query(no, mid);
				request.setAttribute("pm", pm);
			}
			/**
			 * 查询单个评论信息
			 */
			if("queryById".equals(op)){
				String id = request.getParameter("id");
				Dfb_merchant_comment comment = new Dfb_merchant_commentDao().queryById(id);
				request.setAttribute("comment", comment);
			}
			/**
			 * 删除
			 */
			if("delete".equals(op)){
				String id = request.getParameter("id");
				boolean bool = new Dfb_merchant_commentDao().deleteById(id);
				out.print(bool);
				out.flush();
				out.close();
			}
			/**
			 * 修改
			 */
			if("updateById".equals(op)){
				String id = request.getParameter("id");
				String uid = request.getParameter("uid");
				String content = request.getParameter("content");
				boolean bool = new Dfb_merchant_commentDao().updateComment(uid, content, id);
				out.print(bool);
				out.flush();
				out.close();
			}
	}

}
