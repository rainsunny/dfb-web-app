package com.doufangbian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.Dfb_weibo_replyDao;
import com.doufangbian.dao.WeibocomentreplylistInfoDao;
import com.doufangbian.dao.WeiboinfoDao;
import com.doufangbian.entity.Doubo_reply;
import com.doufangbian.entity.PageModel;

public class Dfb_weibo_replyServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		/**
		 * 查询微博评论
		 */
		if("query".equals(op)){
			
			String wid  = request.getParameter("wid");
			
			String pageNo = request.getParameter("pageNo");
			
			int no = 1;
			
			if(pageNo!=null && pageNo!=""){
				
				no = Integer.parseInt(pageNo);
			}
			
			PageModel<Doubo_reply> pm = new Dfb_weibo_replyDao().queryAllByWid(no,wid);
			
			request.setAttribute("pm",pm);
		}
		/**
		 * 根据评论id查询评论信息
		 */
		if("queryById".equals(op)){
			
			String replyId  = request.getParameter("replyId");
			
			Doubo_reply reply = new Dfb_weibo_replyDao().queryById(replyId);
			
			request.setAttribute("reply", reply);
		}
		/**
		 * 根据id删除评论
		 */
		if("deleteById".equals(op)){
			String replyId = request.getParameter("replyId");
			PrintWriter out = response.getWriter();
			try {
				new WeibocomentreplylistInfoDao().deleteByIds(replyId);
				
				boolean bool = new Dfb_weibo_replyDao().deletebyId(replyId);
				
				out.print(bool);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
			
		}
		/**
		 * 添加微博评论
		 */
		if("add".equals(op)){
			String wid = request.getParameter("wid");
			String content = request.getParameter("content");
			String uid = request.getParameter("uid");
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			PrintWriter out = response.getWriter();
			try {
				boolean bool = new Dfb_weibo_replyDao().addReply(wid, uid, time, content);
				new WeiboinfoDao().updateTimeById(wid, time);
				out.print(bool);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
			
		}
		
	}

}
