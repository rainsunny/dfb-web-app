package com.doufangbian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doufangbian.dao.TempDao;
import com.doufangbian.dao.UserinfoDao;
import com.doufangbian.entity.PageModel;
import com.doufangbian.entity.Userinfo;
import com.doufangbian.util.CompressPicDemo;
import com.doufangbian.util.ConstantUtil;
import com.doufangbian.util.DeleteServicesImage;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

@SuppressWarnings("serial")
public class UsersServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ "/" + ConstantUtil.USER_IMAGE + "/";
		String op = request.getParameter("op");
		/**
		 * 查询
		 */
		if ("query".equals(op)) {
			String pageNo = request.getParameter("pageNo");
			String keywords = request.getParameter("keywords");
			if (keywords != null && keywords != ""
					&& "get".equalsIgnoreCase(request.getMethod())) {
				keywords = new String(keywords.getBytes("iso-8859-1"), "utf-8");
			}

			UserinfoDao userinfoDao = new UserinfoDao();

			// 转换成int
			Integer no = 1;
			if (pageNo != null && pageNo != "") {
				no = Integer.parseInt(pageNo);
			}

			// 前面处理中文乱码的时候有问题，是get提交，但是采用了post的方式处理乱码
			if (keywords == null) {
				keywords = "";
			}

			request.setAttribute("keywords", keywords);

			PageModel<Userinfo> plist = userinfoDao.getUserInfoList(no, "%"
					+ keywords + "%");

			request.setAttribute("pm", plist);
		}
		/**
		 * 根据用户的id查询名称
		 */
		if ("queryNameById".equals(op)) {
			String uid = request.getParameter("uid");
			Userinfo user = new UserinfoDao().queryById(uid);
			if (user != null && user.getUsername() != null) {
				out.print(user.getUsername());
			} else {
				out.print("");
			}
			out.flush();
			out.close();
		}
		/**
		 * 根据用户id查询用户信息
		 */
		if ("queryById".equals(op)) {
			String uid = request.getParameter("uid");
			Userinfo user = new UserinfoDao().queryById(uid);
			request.setAttribute("user", user);
		}
		/**
		 * 删除用户
		 */
		if ("delete".equals(op)) {
			String uid = request.getParameter("uid");
			// 查询用户的头像
			List<String> list = new UserinfoDao().queryPhotoById(uid);
			boolean bool = new UserinfoDao().deleteById(uid);
			// 删除用户的头像
			if (bool) {
				DeleteServicesImage.deleteServicesImage(request,
						ConstantUtil.USER_IMAGE, list);
			}
			out.print(bool);
			out.flush();
			out.close();
		}
		if ("update".equals(op)) {
			String pageNo = "";
			String vague = "";
			try {

				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);

				// 3. 调用上传方法
				smart.upload();
				pageNo = smart.getRequest().getParameter("pageNo");
				if (pageNo.trim() == null || "".equals(pageNo.trim())) {
					pageNo = "1";
				}
				vague = smart.getRequest().getParameter("vague");
				String uid = smart.getRequest().getParameter("uid");
				String photo = smart.getRequest().getParameter("photo");
				String username = smart.getRequest().getParameter("username");
				String password = smart.getRequest().getParameter("password");
				String gender = smart.getRequest().getParameter("gender");
				String phone = smart.getRequest().getParameter("phone");
				String qq = smart.getRequest().getParameter("qq");
				String cash = smart.getRequest().getParameter("cash");
				String status = smart.getRequest().getParameter("status");
				// 对上传的文件进行重命名
				Files fs = smart.getFiles();
				for (int i = 0; i < fs.getCount(); i++) {
					File f = fs.getFile(i);
					if (f.isMissing() == false) {
						String str = f.getFileName().substring(
								f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis() + str;
						f.saveAs("/" + ConstantUtil.USER_IMAGE + "/" + imgName);
						photo = imgName;
						new CompressPicDemo().compressPic(dir, dir, imgName,
								imgName, 75, 75, true);
					}
				}
				// 查询用户的头像
				List<String> list = new UserinfoDao().queryPhotoById(uid);
				boolean bool = new UserinfoDao().updateUser(uid, photo,
						username, password, gender, phone, qq, cash, status);
				if (bool) {
					DeleteServicesImage.deleteServicesImage(request,
							ConstantUtil.USER_IMAGE, list);
					request.setAttribute("updataMsg", "恭喜您!用户信息修改成功!");
				} else {
					request.setAttribute("updataMsg", "系统繁忙,请稍后在试.....");
				}

			} catch (SmartUploadException e) {

				e.printStackTrace();
			} finally {
				request.getRequestDispatcher(
						"admin/user_table.jsp?pageNo=" + pageNo + vague)
						.forward(request, response);
			}

		}
		
		
		

		if ("updateHeadImage".equals(op)) {// 修改用户头像
			try {

				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);

				String photo = smart.getRequest().getParameter("photo");

				String uid = smart.getRequest().getParameter("uid");

				// 3. 调用上传方法
				smart.upload();

				// 对上传的文件进行重命名
				Files fs = smart.getFiles();
				for (int i = 0; i < fs.getCount(); i++) {
					File f = fs.getFile(i);
					if (f.isMissing() == false) {
						String str = f.getFileName().substring(
								f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis() + str;
						f.saveAs("/" + ConstantUtil.USER_IMAGE + "/" + imgName);
						photo = imgName;
						// new CompressPicDemo().compressPic(dir, dir, imgName,
						// imgName,75,75,true);
					}
				}
				// 查询用户的头像
				List<String> list = new UserinfoDao().queryPhotoById(uid);

				boolean bool = new TempDao().updateHead(photo, uid);

				if (bool) {
					DeleteServicesImage.deleteServicesImage(request,
							ConstantUtil.USER_IMAGE, list);

				}

				response.getWriter().print(bool);

				response.getWriter().flush();
				response.getWriter().close();

			} catch (SmartUploadException e) {

				e.printStackTrace();
			}

		}

		if ("add".equals(op)) {
			try {
				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);

				// 3. 调用上传方法
				smart.upload();
				String photo = "";
				String username = smart.getRequest().getParameter("username");
				String password = smart.getRequest().getParameter("password");
				String gender = smart.getRequest().getParameter("gender");
				String phone = smart.getRequest().getParameter("phone");
				String qq = smart.getRequest().getParameter("qq");
				String cash = smart.getRequest().getParameter("cash");
				String checkin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				// 对上传的文件进行重命名
				Files fs = smart.getFiles();
				for (int i = 0; i < fs.getCount(); i++) {
					File f = fs.getFile(i);
					if (f.isMissing() == false) {
						String str = f.getFileName().substring(
								f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis() + str;
						f.saveAs("/" + ConstantUtil.USER_IMAGE + "/" + imgName);
						photo = imgName;
						new CompressPicDemo().compressPic(dir, dir, imgName,
								imgName, 75, 75, true);
					}
				}
				if (photo == null || photo == "") {
					photo = "null.jpg";
				}
				Userinfo user = new Userinfo(photo, username, password, Integer
						.parseInt(gender), phone, qq, Integer.parseInt(cash),
						checkin, null, 1);

				boolean bool = new UserinfoDao().addUser(user);
				if (bool) {
					request.setAttribute("addMsg", "恭喜您!用户信息添加成功!");
				} else {
					request.setAttribute("addMsg", "系统繁忙,请稍后在试.....");
				}

			} catch (SmartUploadException e) {

				e.printStackTrace();
			} finally {
				request.getRequestDispatcher("admin/user_table.jsp").forward(
						request, response);
			}
		}

		if ("adduserinfo".equals(op)) {

			try {

				UserinfoDao userinfoDao = new UserinfoDao();

				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);

				// 3. 调用上传方法
				smart.upload();
				String photo = "";
				String username = smart.getRequest().getParameter("username");

				Userinfo userinfo = userinfoDao.queryByName(username);

				boolean bool = false;


				if (userinfo == null) {
				
					String password = smart.getRequest().getParameter("password");
					String gender = smart.getRequest().getParameter("gender");
					String phone = smart.getRequest().getParameter("phone");
					String qq = smart.getRequest().getParameter("qq");
					String checkin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
					// 对上传的文件进行重命名
					Files fs = smart.getFiles();
					for (int i = 0; i < fs.getCount(); i++) {
						File f = fs.getFile(i);
						if (f.isMissing() == false) {
							String str = f.getFileName().substring(
									f.getFileName().lastIndexOf("."));
							String imgName = System.currentTimeMillis() + str;
							f.saveAs("/" + ConstantUtil.USER_IMAGE + "/"
									+ imgName);
							photo = imgName;
							new CompressPicDemo().compressPic(dir, dir,
									imgName, imgName, 75, 75, true);
						}
					}
					if (photo == null || photo == "") {
						photo = "null.jpg";
					}
					Userinfo user = new Userinfo(photo, username, password,
							Integer.parseInt(gender), phone, qq, 0, checkin,
							null, 1);

					bool = new UserinfoDao().addUser(user);
				}

				response.getWriter().print(bool);

				response.getWriter().flush();
				response.getWriter().close();

			} catch (SmartUploadException e) {

				e.printStackTrace();
			}
		}

		if ("addCamera".equals(op)) {

			try {
				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);

				// 3. 调用上传方法
				smart.upload();

				String photo = null;

				String uid = smart.getRequest().getParameter("uid");

				// 对上传的文件进行重命名
				Files fs = smart.getFiles();
				for (int i = 0; i < fs.getCount(); i++) {
					File f = fs.getFile(i);
					if (f.isMissing() == false) {
						String str = f.getFileName().substring(
								f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis() + str;
						f.saveAs("/" + ConstantUtil.USER_IMAGE + "/" + imgName);
						photo = imgName;
					}
				}

				boolean bool = new TempDao().addCamera(uid, photo);

				response.getWriter().print(bool);

				response.getWriter().flush();
				response.getWriter().close();

			} catch (SmartUploadException e) {

				e.printStackTrace();
			}
		}

		if ("updateuserinfo".equals(op)) {

			try {
				// 开始编写上传的代码
				SmartUpload smart = new SmartUpload();
				// 2. 初始化
				smart.initialize(super.getServletConfig(), request, response);

				// 3. 调用上传方法
				smart.upload();
				String photo = null;

				String id = smart.getRequest().getParameter("id");
				String username = smart.getRequest().getParameter("username");
				String password = smart.getRequest().getParameter("password");
				String gender = smart.getRequest().getParameter("gender");
				String phone = smart.getRequest().getParameter("phone");
				String qq = smart.getRequest().getParameter("qq");

				// 对上传的文件进行重命名
				Files fs = smart.getFiles();
				for (int i = 0; i < fs.getCount(); i++) {
					File f = fs.getFile(i);
					if (f.isMissing() == false) {
						String str = f.getFileName().substring(
								f.getFileName().lastIndexOf("."));
						String imgName = System.currentTimeMillis() + str;
						f.saveAs("/" + ConstantUtil.USER_IMAGE + "/" + imgName);
						photo = imgName;
						new CompressPicDemo().compressPic(dir, dir, imgName,
								imgName, 75, 75, true);
					}
				}

				// 查询用户的头像
				List<String> list = new UserinfoDao().queryPhotoById(id);

				boolean bool = new TempDao().updateUser(Integer.parseInt(id),
						photo, username, password, Integer.parseInt(gender),
						phone, qq);

				if (bool && photo != null) {// 删除以前的头像

					DeleteServicesImage.deleteServicesImage(request,
							ConstantUtil.USER_IMAGE, list);

				}

				System.out.println("---------------->上传头像成功了吗?" + bool);
				response.getWriter().print(bool);

				response.getWriter().flush();
				response.getWriter().close();

			} catch (SmartUploadException e) {

				e.printStackTrace();
			}
		}

		/**
		 * 验证名字是否存在
		 */
		if ("checkName".equals(op)) {
			String username = request.getParameter("username");
			Userinfo user = new UserinfoDao().queryByName(username);
			out.print(user == null ? true : false);
			out.flush();
			out.close();
		}
	}

}
