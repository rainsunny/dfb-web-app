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
import javax.servlet.http.HttpSession;


import com.doufangbian.dao.Dfb_groupmerchant_mapDao;
import com.doufangbian.dao.Dfb_merchant_imageDao;
import com.doufangbian.dao.MerchantinfoDao;
import com.doufangbian.entity.MerchantInfo;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.ConstantUtil;
import com.doufangbian.util.DeleteServicesImage;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class MerchantsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        String dir = request.getSession().getServletContext().getRealPath("/") + "/" + ConstantUtil.MERCHANT_IMAGE + "/";
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
        PrintWriter out = response.getWriter();

        if ("query".equals(op)) {
            String pageNo = request.getParameter("pageNo");
            String keywords = request.getParameter("keywords");
            if (keywords != null && !keywords.equals("") && "get".equalsIgnoreCase(request.getMethod())) {
                keywords = new String(keywords.getBytes("iso-8859-1"), "utf-8");
            }
            String catId = request.getParameter("catId");
            String groupId = request.getParameter("groupId");

            String state = request.getParameter("state");
            if (state == null || state.equals("")) {
                state = "1";
            }
            MerchantinfoDao merchantinfoDao = new MerchantinfoDao();

            //转换成int
            Integer no = 1;
            if (pageNo != null && !pageNo.equals("")) {
                no = Integer.parseInt(pageNo);
            }

            if (keywords == null) {
                keywords = "";
            }
            request.setAttribute("keywords", keywords);

            PageModel<MerchantInfo> plist = merchantinfoDao.getMerchantList(state, no, level, areaID, "%" + keywords + "%", catId, groupId);

            request.setAttribute("pm", plist);
        }
        /**
         * 查询所有的小组
         */
        if ("queryAll".equals(op)) {
            String pageNo = request.getParameter("pageNo");
            String keywords = request.getParameter("keywords");
            String groupId = request.getParameter("groupId");
            String chiose = request.getParameter("chiose");
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
            PageModel<MerchantInfo> pm = new MerchantinfoDao().queryAll(level, areaID, no, groupId, chiose, "%" + keywords + "%");

            request.setAttribute("pm", pm);
        }
        /**
         * 添加商家
         */
        if ("add".equals(op)) {
            //开始编写上传的代码
            SmartUpload smart = new SmartUpload();
            //2. 初始化
            smart.initialize(super.getServletConfig(), request, response);
            //3. 调用上传方法
            try {
                smart.upload();
                String name = smart.getRequest().getParameter("name");
                String major = smart.getRequest().getParameter("major");
                String introduction = smart.getRequest().getParameter("introduction");
                String news = smart.getRequest().getParameter("news");
                String address = smart.getRequest().getParameter("address");
                String phone = smart.getRequest().getParameter("phone");
                String isrecommend = smart.getRequest().getParameter("isrecommend") == null ? "0" : "1";
                String status = smart.getRequest().getParameter("status");
                String up = smart.getRequest().getParameter("up");
                String countyID = request.getSession().getAttribute("areaID") + "";
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String photo = "null.jpg";
                //对上传的文件进行重命名
                Files fs = smart.getFiles();
                for (int i = 0; i < fs.getCount(); i++) {
                    File f = fs.getFile(i);
                    if (!f.isMissing()) {
                        String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
                        String imgName = System.currentTimeMillis() + str;
                        f.saveAs("/" + ConstantUtil.MERCHANT_IMAGE + "/" + imgName);
                        photo = imgName;
                    }
                }
                if ("0".equals(countyID)) {
                    //查询所有的地区id
                    countyID = "-1";
                }
                new MerchantinfoDao().addMerchants(name, major, introduction, news, address, phone, isrecommend, status, up, countyID, time, photo);

            } catch (SmartUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                response.sendRedirect("admin/merchant_table.jsp");
            }
        }
        /**
         * 根据Id查询商家
         */
        if ("queryById".equals(op)) {
            String merchantId = request.getParameter("merchantId");
            MerchantInfo merchant = new MerchantinfoDao().queryById(merchantId);
            request.setAttribute("merchant", merchant);
        }
        /**
         * 修改商家信息
         */
        if ("update".equals(op)) {
            String pageNo = "";
            String vague = "";
            //开始编写上传的代码
            SmartUpload smart = new SmartUpload();
            //2. 初始化
            smart.initialize(super.getServletConfig(), request, response);
            //3. 调用上传方法
            try {
                smart.upload();
                pageNo = smart.getRequest().getParameter("pageNo");
                if (pageNo.trim() == null || "".equals(pageNo.trim())) {
                    pageNo = "1";
                }
                vague = smart.getRequest().getParameter("vague");
                String name = smart.getRequest().getParameter("name");
                String major = smart.getRequest().getParameter("major");
                String introduction = smart.getRequest().getParameter("introduction");
                String news = smart.getRequest().getParameter("news");
                String address = smart.getRequest().getParameter("address");
                String phone = smart.getRequest().getParameter("phone");
                String isrecommend = smart.getRequest().getParameter("isrecommend") == null ? "0" : "1";
                String status = smart.getRequest().getParameter("status");
                String up = smart.getRequest().getParameter("up");
                String updatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                String merchantId = smart.getRequest().getParameter("merchantId");
                String photo = smart.getRequest().getParameter("photo");
                //对上传的文件进行重命名
                Files fs = smart.getFiles();
                for (int i = 0; i < fs.getCount(); i++) {
                    File f = fs.getFile(i);
                    if (!f.isMissing()) {
                        String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
                        String imgName = System.currentTimeMillis() + str;
                        f.saveAs("/" + ConstantUtil.MERCHANT_IMAGE + "/" + imgName);
                        photo = imgName;
                    }
                }
                new MerchantinfoDao().updateMerchant(name, major, introduction, news, address, phone, isrecommend, status, up, updatetime, photo, merchantId);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                request.getRequestDispatcher("admin/merchant_table.jsp?pageNo=" + pageNo + vague).forward(request, response);
            }
        }

        /**
         * 删除商家
         */
        if ("delete".equals(op)) {
            String merchantId = request.getParameter("merchantId");
            //删除小组中的对应商家关系
            new Dfb_groupmerchant_mapDao().delgroupmerchant(merchantId);
            //查询商家存在的图片信息
            List<String> list = new Dfb_merchant_imageDao().queryImageNameByMid(merchantId);
            //查询商家的logo
            List<String> log = new MerchantinfoDao().queryImageLogoByMid(merchantId);
            //删除该商家的图标信息
            boolean b = new Dfb_merchant_imageDao().deleleByMid(merchantId);
            if (b) {
                DeleteServicesImage.deleteServicesImage(request, ConstantUtil.MERCHANT_IMAGE, list);
            }
            //删除商家
            boolean bool = new MerchantinfoDao().deleteById(merchantId);
            if (bool) {
                DeleteServicesImage.deleteServicesImage(request, ConstantUtil.MERCHANT_IMAGE, log);
            }
            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 启用禁用商家
         */
        if ("enabled".equals(op)) {
            String merchantId = request.getParameter("merchantId");

            String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            String state = request.getParameter("state");

            boolean bool = new MerchantinfoDao().enabled(state, updateTime, merchantId);
            out.print(bool);
            out.flush();
            out.close();
        }

    }

}
