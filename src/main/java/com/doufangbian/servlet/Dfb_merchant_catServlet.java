package com.doufangbian.servlet;

import com.doufangbian.dao.Dfb_catgroup_mapDao;
import com.doufangbian.dao.Dfb_merchant_catDao;
import com.doufangbian.entity.Dfb_merchant_cat;
import com.doufangbian.entity.PageModel;
import com.doufangbian.util.ConstantUtil;
import com.doufangbian.util.DeleteServicesImage;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 大分组Servlet
 *
 * @author C_C
 */
public class Dfb_merchant_catServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        String dir = request.getSession().getServletContext().getRealPath("/") + "/" + ConstantUtil.CAT_IMAGE + "/";
        PrintWriter out = response.getWriter();
        /**
         * 查询
         */
        if ("query".equals(op)) {
            List<Dfb_merchant_cat> list = new Dfb_merchant_catDao().queryAll();
            request.setAttribute("listCat", list);
        }
        /**
         * 查询全部
         */
        if ("queryAll".equals(op)) {
            String pageNo = request.getParameter("pageNo");
            int no = 1;

            if (pageNo != null && !"".equals(pageNo)) {
                no = Integer.parseInt(pageNo);
            }
            PageModel<Dfb_merchant_cat> pm = new Dfb_merchant_catDao().queryAllCat(no);

            request.setAttribute("pm", pm);
        }
        /**
         * 根据大组Id查询大组信息
         */
        if ("queryById".equals(op)) {
            String catId = request.getParameter("catId");
            Dfb_merchant_cat cat = new Dfb_merchant_catDao().queryById(catId);
            request.setAttribute("cat", cat);
        }
        /***
         * 删除
         */
        if ("delete".equals(op)) {
            String catId = request.getParameter("catId");
            //删除大组小组关系表信息
            new Dfb_catgroup_mapDao().delCatgroupByCatId(catId);
            //删除大组表信息
            boolean bool = new Dfb_merchant_catDao().deleteById(catId);

            if (bool) {
                List<String> list = new Dfb_merchant_catDao().queryImageById(catId);
                DeleteServicesImage.deleteServicesImage(request, ConstantUtil.CAT_IMAGE, list);
            }

            out.print(bool);

            out.flush();
            out.close();
        }
        /**
         * 修改大组信息
         */
        if ("update".equals(op)) {
            try {
                //开始编写上传的代码
                SmartUpload smart = new SmartUpload();
                //2. 初始化
                smart.initialize(super.getServletConfig(), request, response);

                //3. 调用上传方法
                smart.upload();
                String photo = smart.getRequest().getParameter("photo");
                String name = smart.getRequest().getParameter("name");
                String catId = smart.getRequest().getParameter("catId");
                //对上传的文件进行重命名
                Files fs = smart.getFiles();
                for (int i = 0; i < fs.getCount(); i++) {
                    File f = fs.getFile(i);
                    if (f.isMissing() == false) {
                        String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
                        String imgName = System.currentTimeMillis() + str;
                        f.saveAs("/" + ConstantUtil.CAT_IMAGE + "/" + imgName);
                        photo = imgName;
//							new CompressPicDemo().compressPic(dir, dir, imgName, imgName,75,75,true);//压缩图片
                    }
                }
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                boolean bool = new Dfb_merchant_catDao().updateById(name, photo, time, catId);
                if (bool) {
                    request.setAttribute("addMsg", "恭喜您!大组添加成功!");
                } else {
                    request.setAttribute("addMsg", "系统繁忙,请稍后在试.....");
                }

            } catch (SmartUploadException e) {

                e.printStackTrace();
            } finally {
                response.sendRedirect("admin/cat_table.jsp");
            }
        }
        /**
         * 添加大组
         */
        if ("add".equals(op)) {
            try {
                //开始编写上传的代码
                SmartUpload smart = new SmartUpload();
                //2. 初始化
                smart.initialize(super.getServletConfig(), request, response);

                //3. 调用上传方法
                smart.upload();
                String photo = "";
                String name = smart.getRequest().getParameter("name");

                //对上传的文件进行重命名
                Files fs = smart.getFiles();
                for (int i = 0; i < fs.getCount(); i++) {
                    File f = fs.getFile(i);
                    if (f.isMissing() == false) {
                        String str = f.getFileName().substring(f.getFileName().lastIndexOf("."));
                        String imgName = System.currentTimeMillis() + str;
                        f.saveAs("/" + ConstantUtil.CAT_IMAGE + "/" + imgName);
                        photo = imgName;
//							new CompressPicDemo().compressPic(dir, dir, imgName, imgName,75,75,true);//压缩图标
                    }
                }
                if (photo == null || photo == "") {
                    photo = "null.jpg";
                }
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                boolean bool = new Dfb_merchant_catDao().addCat(name, photo, time);
                if (bool) {
                    request.setAttribute("addMsg", "恭喜您!大组添加成功!");
                } else {
                    request.setAttribute("addMsg", "系统繁忙,请稍后在试.....");
                }

            } catch (SmartUploadException e) {

                e.printStackTrace();
            } finally {
                request.getRequestDispatcher("admin/cat_table.jsp").forward(request, response);
            }
        }
    }

}
