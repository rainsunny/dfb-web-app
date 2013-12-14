package com.doufangbian.servlet;

import com.doufangbian.dao.Dfb_groupmerchant_mapDao;
import com.doufangbian.dao.Dfb_merchant_groupDao;
import com.doufangbian.entity.Dfb_merchant_group;
import com.doufangbian.entity.PageModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 小分组
 *
 * @author C_C
 */
public class Dfb_merchant_groupServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");
        PrintWriter out = response.getWriter();
        /**
         * 查询小组信息 可根据大组
         */
        if ("query".equals(op)) {
            String catId = request.getParameter("catId");

            List<Dfb_merchant_group> list = new Dfb_merchant_groupDao().queryByCatId(catId);

            request.setAttribute("listGroup", list);
        }
        /**
         * 查询所有小组
         */
        if ("queryAll".equals(op)) {
            //获取session
            HttpSession session = request.getSession();
            //获取当前管理员权限
            int level = 0;
            if (session.getAttribute("level") != null && session.getAttribute("level") != "") {
                level = Integer.parseInt(session.getAttribute("level") + "");
            }
            //获取当前管理员areaid
            int areaID = 0;
            if (session.getAttribute("areaID") != null && session.getAttribute("areaID") != "") {
                areaID = Integer.parseInt(session.getAttribute("areaID") + "");
            }
            String pageNo = request.getParameter("pageNo");
            String catId = request.getParameter("catId");
            String chiose = request.getParameter("chiose");
            String keywords = request.getParameter("keywords");
            if (keywords != null && !keywords.equals("") && "get".equalsIgnoreCase(request.getMethod())) {
                keywords = new String(keywords.getBytes("iso-8859-1"), "utf-8");
            }
            request.setAttribute("keywords", keywords);
            int no = 1;
            if (pageNo != null && !"".equals(pageNo)) {
                no = Integer.parseInt(pageNo);
            }
            if (keywords == null) {
                keywords = "";
            }
            PageModel<Dfb_merchant_group> pm = new Dfb_merchant_groupDao().queryAll(no, level, areaID, catId, chiose, "%" + keywords + "%");

            request.setAttribute("pm", pm);
        }
        /**
         * 根据商家id查询入组情况
         */
        if ("querybymid".equals(op)) {
            String mid = request.getParameter("mid");
            //获取session
            HttpSession session = request.getSession();
            //获取当前管理员权限
            int level = 0;
            if (session.getAttribute("level") != null && session.getAttribute("level") != "") {
                level = Integer.parseInt(session.getAttribute("level") + "");
            }
            //获取当前管理员areaid
            int areaID = 0;
            if (session.getAttribute("areaID") != null && session.getAttribute("areaID") != "") {
                areaID = Integer.parseInt(session.getAttribute("areaID") + "");
            }
            String pageNo = request.getParameter("pageNo");
            String chiose = request.getParameter("chiose");
            int no = 1;
            if (pageNo != null && !"".equals(pageNo)) {
                no = Integer.parseInt(pageNo);
            }

            PageModel<Dfb_merchant_group> pm = new Dfb_merchant_groupDao().querybymid(no, level, areaID, mid, chiose);

            request.setAttribute("pm", pm);
        }
        /**
         * 根据小组Id查询小组信息
         */
        if ("queryById".equals(op)) {
            String groupId = request.getParameter("groupId");
            Dfb_merchant_group group = new Dfb_merchant_groupDao().queryById(groupId);
            request.setAttribute("group", group);
        }
        /***
         * 删除小组信息
         */
        if ("delete".equals(op)) {
            String groupId = request.getParameter("groupId");
            //删除小组商家关系中的小组信息
            new Dfb_groupmerchant_mapDao().delCatgroupByCatId(groupId);
            //删除小组信息
            boolean bool = new Dfb_merchant_groupDao().deleteById(groupId);

            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 修改小组信息
         */
        if ("update".equals(op)) {
            String groupId = request.getParameter("groupId");
            String name = request.getParameter("name");

            boolean bool = new Dfb_merchant_groupDao().updateById(name, groupId);
            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 添加小组
         */
        if ("add".equals(op)) {
            String name = request.getParameter("name");
            String catId = request.getParameter("catId");
            String areaID = request.getSession().getAttribute("areaID") + "";
            boolean bool = new Dfb_merchant_groupDao().addGroup(name, areaID, catId);
            out.print(bool);

            out.flush();
            out.close();
        }
    }

}
