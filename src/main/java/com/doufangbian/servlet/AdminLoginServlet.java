package com.doufangbian.servlet;

import com.doufangbian.dao.AdminUserInfoDao;
import com.doufangbian.dao.CityInfoDao;
import com.doufangbian.dao.CountyInfoDao;
import com.doufangbian.dao.ProvincialDao;
import com.doufangbian.entity.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class AdminLoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");
        PrintWriter out = response.getWriter();

        if (op == null || "".equals(op)) {

            // 获取参数

            String username = request.getParameter("userName");// 获取用户名
            String pwd = request.getParameter("userPwd");// 获取密码

            AdminUserInfoDao userInfoDao = new AdminUserInfoDao();

            AdminUserInfo userInfo = userInfoDao.checkLogin(username, pwd);

            HttpSession Session = request.getSession();// 获取Session

            // 保存地区相关信息
            String areaName = "";
            int areaID = 0;

            if (userInfo != null) {// 如果通过则把用户的一些信息存储在Session中

                int level = userInfo.getLevel();
                // 下面进行权限判断操作
                switch (level) {

                    case 0:// 超级管理员

                        areaName = "全国";

                        Session.setAttribute("wapUrl", "http://www.doufangbian.com");

                        break;

                    case 1:// 省级管理员

                        // 获取省级相关信息
                        ProvincialDao provincialDao = new ProvincialDao();

                        ProvincialInfo provincialInfo = provincialDao
                                .getQueryProvincialByID(userInfo.getAreaId());

                        // 将信息保存到session中
                        areaName = provincialInfo.getName();

                        areaID = provincialInfo.getProvincialID();

                        break;

                    case 2:// 市级管理员

                        //获取该市相关信息
                        CityInfoDao cityInfoDao = new CityInfoDao();

                        CityInfo cityInfo = cityInfoDao.getQueryCityByID(userInfo.getAreaId());

                        //将信息保存到session中
                        areaName = cityInfo.getName();

                        areaID = cityInfo.getCityID();

                        break;
                    case 3:// 县级管理员

                        // 获取该县相关信息
                        CountyInfoDao countyInfoDao = new CountyInfoDao();

                        CountyInfo countyInfo = countyInfoDao.getCountyInfo(userInfo
                                .getAreaId());

                        // 将信息保存到session中
                        areaName = countyInfo.getName();// 地区名称

                        areaID = countyInfo.getCountyID();// 地区编号

                        String wapUrl = countyInfo.getWapUrl();

                        Session.setAttribute("wapUrl", wapUrl);

                        break;

                }

                List<AdminUserInfo> list = new ArrayList<AdminUserInfo>();

                AdminUserInfo adminUserInfo = new AdminUserInfo(level, areaID, areaName);

                list.add(adminUserInfo);

                //保存好了地区用户等级相关信息
                Session.setAttribute("admin_user_list", list);

                //保存地址信息
                Session.setAttribute("areaID", areaID);

                Session.setAttribute("areaName", areaName);

                // 保存用户相关信息
                Session.setAttribute("userInfo", userInfo);

                Session.setAttribute("level", level);

                String skip_url = "admin/merchant_table.jsp";
                Session.setAttribute("skip_url", skip_url);

                // 设置时间
                Session.setAttribute("LoginTime", new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").format(new Date()));

                response.sendRedirect("admin/index.jsp");

            } else {// 如果用户检查没有通过，则跳回登陆页面,使用内部跳转传递参数

                request.setAttribute("Msg", "用户名和密码输入不正确");

                request.getRequestDispatcher("admin/login.jsp").forward(request,
                        response);
            }

        }
        /**
         * 添加管理员
         */
        if ("add".equals(op)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String level = request.getParameter("level");
            String areaId = request.getParameter("areaId");

            boolean bool = new AdminUserInfoDao().addAdminUser(username, password, level, areaId);
            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 管理员修改
         */
        if ("update".equals(op)) {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String level = request.getParameter("level");
            String areaId = request.getParameter("areaId");

            boolean bool = new AdminUserInfoDao().updateAdminUser(username, password, level, areaId, id);
            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 根据Id查询管理员信息
         */
        if ("queryById".equals(op)) {
            String id = request.getParameter("id");
            AdminUserInfo adminuser = new AdminUserInfoDao().queryById(id);
            request.setAttribute("adminUser", adminuser);
        }
        /**
         * 修改密码
         */
        if ("updatePassword".equals(op)) {

            AdminUserInfo admin = (AdminUserInfo) request.getSession().getAttribute("userInfo");

            String newPassword = request.getParameter("newPassword");

            boolean bool = new AdminUserInfoDao().updatePasswordById(newPassword, admin.getID());
            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 删除 根据Id
         */
        if ("delete".equals(op)) {
            String id = request.getParameter("id");
            boolean bool = new AdminUserInfoDao().deleteAdminUser(id);
            out.print(bool);

            out.flush();
            out.close();

        }
        /**
         *判断管理员的名称是否存在
         */
        if ("checkName".equals(op)) {
            String username = request.getParameter("username");
            boolean bool = new AdminUserInfoDao().queryCheckUsername(username);
            out.print(bool);

            out.flush();
            out.close();

        }
        /**
         * 分页查询
         */
        if ("query".equals(op)) {
            String pageNo = request.getParameter("pageNo");

            int no = 1;
            if (StringUtils.isNotBlank(pageNo)) {

                no = Integer.parseInt(pageNo);
            }

            PageModel<AdminUserInfo> pm = new AdminUserInfoDao().queryAll(no);

            request.setAttribute("pm", pm);
        }

    }

}
