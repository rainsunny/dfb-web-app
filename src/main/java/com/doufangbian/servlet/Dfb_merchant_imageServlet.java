package com.doufangbian.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.Dfb_merchant_imageDao;
import com.doufangbian.entity.Dfb_merchant_image;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.CompressPicDemo;
import com.doufangbian.util.ConstantUtil;
import com.doufangbian.util.DeleteServicesImage;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class Dfb_merchant_imageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("op");
		if ("add".equals(op)) {
			String dir = request.getSession().getServletContext().getRealPath("/")+"/"+ConstantUtil.MERCHANT_IMAGE+"/";
			try {
				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);
				// 3. 调用上传方法
				smart.upload();
				String mid = smart.getRequest().getParameter("mid");
				String image = "null.jpg";
				//对上传的文件进行重命名
				Files fs = smart.getFiles();
				for(int i=0;i<fs.getCount();i++){
					File f = fs.getFile(i);
					if(f.isMissing()==false){
						String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis()+str;
						f.saveAs("/"+ConstantUtil.MERCHANT_IMAGE+"/"+imgName);
						image=imgName;
						new CompressPicDemo().compressPic(dir, dir, imgName, imgName,560,380,true);
					}							
				}
				boolean bool = new Dfb_merchant_imageDao().addMerImage(mid, image);
				if(bool){
					request.getRequestDispatcher("admin/merchantImage_table.jsp?mid="+mid).forward(request, response);
				}
			} catch (SmartUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		/**
		 * 查询所有图片
		 */
		if("query".equals(op)){

			String pageNo = request.getParameter("pageNo");
			int no = 1;
			if(pageNo!=null && pageNo!=""){
				no=Integer.parseInt(pageNo);
			}
			String mid = request.getParameter("mid");
			
			PageModel<Dfb_merchant_image> pm = new Dfb_merchant_imageDao().queryAll(no,mid);
			
			request.setAttribute("pm", pm);
		}
		/**
		 * 删除指定图片
		 */
		if("deleteById".equals(op)){
			Dfb_merchant_imageDao dao = new Dfb_merchant_imageDao();
			String id = request.getParameter("id");
			//查询到当前id对应的图片名称集合
			List<String> list = dao.queryImageNameById(id);
			//删除对应的数据
			boolean bool = dao.deleteById(id);
			//删除对应的图片
			if(bool){
				DeleteServicesImage.deleteServicesImage(request, ConstantUtil.MERCHANT_IMAGE, list);
			}
		}
	}
	
}
