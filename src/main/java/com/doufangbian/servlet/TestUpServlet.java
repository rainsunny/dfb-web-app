package com.doufangbian.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.WeiboinfoDao;
import com.doufangbian.entity.Douboinfo;
import com.doufangbian.util.ConstantUtil;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

@SuppressWarnings("serial")
public class TestUpServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	// 服务器servlet代码
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dir = request.getSession().getServletContext().getRealPath("/")+"/"+ConstantUtil.WEIBO_IMAGE+"/";
		// 开始编写上传的代码
		SmartUpload smart = new SmartUpload();
		
		// 2. 初始化
		smart.initialize(super.getServletConfig(), request, response);

		// 3. 调用上传方法

		try {
			smart.upload();
		
		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}

		
		
		WeiboinfoDao merchantinfoDao = new WeiboinfoDao();

		String content =smart.getRequest().getParameter("content") ;

		String uid = smart.getRequest().getParameter("uid");

		int countyID = Integer.parseInt(smart.getRequest().getParameter("countyID") + "");

		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		int op = Integer.parseInt(smart.getRequest().getParameter("op") + "");
		
		
		
		String photo = null;
		

		if (op == 1) {//如果op等于1表示长传了图片
			try {

				

				// 对上传的文件进行重命名
				Files fs = smart.getFiles();

				for (int i = 0; i < fs.getCount(); i++) {
					File f = fs.getFile(i);


					if (f.isMissing() == false) {
						String str = f.getFileName().substring(
								f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis() + str;
						f.saveAs("/weiboImage/" + imgName);
						photo = imgName;
//						new CompressPicDemo().compressPic(dir, dir, imgName, imgName,75,75,true);
					}
				}
			} catch (SmartUploadException e) {

				e.printStackTrace();
			}
		}

		Douboinfo wb = new Douboinfo(content, time, 0,0, 0, Integer.parseInt(uid), countyID, time, photo);

		boolean bool = merchantinfoDao.addWeibo(wb);

		response.getWriter().print(bool);

		response.getWriter().flush();
		response.getWriter().close();

	}

}
