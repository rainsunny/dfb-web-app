package com.doufangbian.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.Dfb_merchant_commentDao;
import com.doufangbian.dao.Merchant_comment_allDao;
import com.doufangbian.entity.Merchant_comment_all;
import com.doufangbian.entity.PageModel;

public class Merchant_comment_allServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		/**
		 * 查询
		 */
		if("queryAll".equals(op)){
			String pageNo = request.getParameter("pageNo");
			String key = request.getParameter("key");
			String keywords = request.getParameter("keywords");
			if(keywords!=null && keywords!="" && "get".equalsIgnoreCase(request.getMethod())){
				keywords = new String(keywords.getBytes("iso-8859-1"),"utf-8");
			}
			if(keywords==null){
				keywords="";
			}
			if(key==null || key==""){
				key="content";
			}
			int no = 1;
			if(pageNo!=null && pageNo!=""){
				no = Integer.parseInt(pageNo);
			}
			request.setAttribute("key", key);
			request.setAttribute("keywords", keywords);
			PageModel<Merchant_comment_all> pm = new Merchant_comment_allDao().queryAll(no, key, "%"+keywords+"%");
			request.setAttribute("pm", pm);
		}
		/**
		 * 删除
		 */
		if("delAll".equals(op)){
			String ids = request.getParameter("ids");
			ids = ids.substring(0,ids.length()-1);
			boolean bool =  new Dfb_merchant_commentDao().deleteByIds(ids);
			response.getWriter().print(bool);
		}
	}

}
