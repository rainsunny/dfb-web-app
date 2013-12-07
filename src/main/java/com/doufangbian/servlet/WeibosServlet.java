package com.doufangbian.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.doufangbian.dao.Dfb_weibo_replyDao;
import com.doufangbian.dao.WeibocomentreplylistInfoDao;
import com.doufangbian.dao.WeiboinfoDao;
import com.doufangbian.entity.Doubo_reply;
import com.doufangbian.entity.PageModel;
import com.doufangbian.entity.Douboinfo;
import com.doufangbian.util.CompressPicDemo;
import com.doufangbian.util.ConstantUtil;
import com.doufangbian.util.DeleteServicesImage;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

@SuppressWarnings("serial")
public class WeibosServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		
		//获取session
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
		WeiboinfoDao merchantinfoDao=new WeiboinfoDao();
		
		if("query".equals(op)){
			String pageNo = request.getParameter("pageNo");
			String keywords = request.getParameter("keywords");
			if(keywords!=null && keywords!="" && "get".equalsIgnoreCase(request.getMethod())){
				keywords = new String(keywords.getBytes("iso-8859-1"),"utf-8");
			}
			
			//转换成int
			Integer no = 1;
			if (pageNo != null && pageNo != "") {
				no = Integer.parseInt(pageNo);
			}
			//前面处理中文乱码的时候有问题，是get提交，但是采用了post的方式处理乱码
			if(keywords==null){
				keywords="";
			}
			request.setAttribute("keywords", keywords);
			PageModel<Douboinfo> plist=merchantinfoDao.getWeiboinfoList(no, level, areaID, "%"+keywords+"%");
			request.setAttribute("pm", plist);
		}
		/**
		 * 添加微博
		 */
		if("add".equals(op)){
			try {
				String dir = request.getSession().getServletContext().getRealPath("/")+"/"+ConstantUtil.WEIBO_IMAGE+"/";
				//开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				//2. 初始化
				smart.initialize(super.getServletConfig(), request, response);
				//3. 调用上传方法
				smart.upload();
				String content = smart.getRequest().getParameter("content");
				String up = smart.getRequest().getParameter("up");
				String down = smart.getRequest().getParameter("down");
				String share = smart.getRequest().getParameter("share");
				String uid = smart.getRequest().getParameter("uid");

				int countyID = Integer.parseInt(session.getAttribute("areaID")+"");
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
				String photo="null.jpg";
				
				//对上传的文件进行重命名
				Files fs = smart.getFiles();
				for(int i=0;i<fs.getCount();i++){
					File f = fs.getFile(i);
					if(f.isMissing()==false){
						String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis()+str;
						f.saveAs("/"+ConstantUtil.WEIBO_IMAGE+"/"+imgName);
						photo=imgName;
						new CompressPicDemo().compressPic(dir, dir, imgName, imgName,560,380,true);
					}							
				}
				
				Douboinfo wb = new Douboinfo(content, time, Integer.parseInt(up), Integer.parseInt(down), Integer.parseInt(share), Integer.parseInt(uid), countyID, time,photo);
				boolean bool = merchantinfoDao.addWeibo(wb);
				if(bool){
					response.sendRedirect("admin/weibo_table.jsp");
				}else{
					request.setAttribute("addMsg", "系统繁忙,请稍后在试.....");
				}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 根据id查询微博信息
		 */
		if("queryById".equals(op)){
			
			String id = request.getParameter("id");
			
			Douboinfo wb = merchantinfoDao.queryById(id);

			request.setAttribute("wb", wb);
		}
		/**
		 * 根据id 删除
		 */
		if("deleteById".equals(op)){
			String id = request.getParameter("id");
			
			boolean bool=false;
			try {
				//根据微博id 查询到当前微博评论的Id
				List<Doubo_reply> list = new Dfb_weibo_replyDao().queryAll(id);
				StringBuffer sb = new StringBuffer(); 
				if(list!=null && list.size()>0){
					for (Doubo_reply dfbWeiboReply : list) {
						sb.append(dfbWeiboReply.getId()+",");
					}
					String delStr = sb.substring(0,sb.length()-1);
					if(!"".equals(delStr)){
						new WeibocomentreplylistInfoDao().deleteByIds(delStr);
					}
				}
				
				new Dfb_weibo_replyDao().deletebywid(id);
				
				//查询微博的图片
				
				List<String> lst = merchantinfoDao.queryWeiboImage(id);
				//删除微博
				 bool = merchantinfoDao.deleteById(id);
				 
				 if(bool){
					 DeleteServicesImage.deleteServicesImage(request, ConstantUtil.WEIBO_IMAGE, lst);
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				response.getWriter().print(bool);
				response.getWriter().flush();
				response.getWriter().close();
			}
			
		}
		/**
		 * 修改信息
		 */
		if("updateById".equals(op)){
			try {
				String dir = request.getSession().getServletContext().getRealPath("/")+"/"+ConstantUtil.WEIBO_IMAGE+"/";
				//开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				//2. 初始化
				smart.initialize(super.getServletConfig(), request, response);
				//3. 调用上传方法
				smart.upload();
				String id = smart.getRequest().getParameter("id");
				String updatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
				String content = smart.getRequest().getParameter("content");
				String up = smart.getRequest().getParameter("up");
				String down = smart.getRequest().getParameter("down");
				String share = smart.getRequest().getParameter("share");
				String uid = smart.getRequest().getParameter("uid");
				String photo = smart.getRequest().getParameter("photo");
				
				//对上传的文件进行重命名
				Files fs = smart.getFiles();
				for(int i=0;i<fs.getCount();i++){
					File f = fs.getFile(i);
					if(f.isMissing()==false){
						String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis()+str;
						f.saveAs("/"+ConstantUtil.WEIBO_IMAGE+"/"+imgName);
						photo=imgName;
						new CompressPicDemo().compressPic(dir, dir, imgName, imgName,560,380,true);
					}							
				}
				boolean bool = merchantinfoDao.updateById(content, up, down, share,uid,updatetime,photo,id);
				
				if(bool){
					response.sendRedirect("admin/weibo_table.jsp");
				}else{
					request.setAttribute("addMsg", "系统繁忙,请稍后在试.....");
				}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
