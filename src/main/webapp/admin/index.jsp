<%@ page language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Simpla Admin</title>
    <!-- Reset Stylesheet -->
    <link rel="stylesheet" href="admin/resources/css/reset.css" type="text/css" media="screen"/>
    <!-- Main Stylesheet -->
    <link rel="stylesheet" href="admin/resources/css/style.css" type="text/css" media="screen"/>
    <!-- Invalid Stylesheet. This makes stuff look pretty. Remove it if you want the CSS completely valid -->
    <link rel="stylesheet" href="admin/resources/css/invalid.css" type="text/css" media="screen"/>
    <!-- jQuery -->
    <script type="text/javascript" src="js/jquery-1.4.1.js"></script>
    <!-- jQuery Configuration -->
    <script type="text/javascript" src="admin/resources/scripts/simpla.jquery.configuration.js"></script>
    <!-- Facebox jQuery Plugin -->
    <script type="text/javascript" src="admin/resources/scripts/facebox.js"></script>
    <!-- jQuery WYSIWYG Plugin -->
    <script type="text/javascript" src="admin/resources/scripts/jquery.wysiwyg.js"></script>
    <script type="text/javascript">
        $(function () {
            $("a").each(function () {
                $(this).click(function () {
                    if ('${userInfo.username}' == null) {
                        window.top.location.href = "<%=basePath%>admin/login.jsp";
                    }
                });
            });
            $("#admin_user").change(function () {
                var areid = $(this).val();
                var str = areid.split(";");
                $.post("AreasServlet?op=addUser&areid=" + str[0] + "&lv=" + str[1], function (data) {
                    window.top.location.href = "<%=basePath%>admin/index.jsp";
                });
            });
        });

    </script>
</head>
<body>
<input type="hidden" value="" id="menu"/>

<div id="body-wrapper">
    <!-- Wrapper for the radial gradient background -->
    <div id="sidebar">
        <div id="sidebar-wrapper">
            <!-- Sidebar with logo and menu -->
            <h1 id="sidebar-title">
                <a href="#">Sd</a>
            </h1>
            <!-- Logo (221px wide) -->
            <a href="#">
                <img id="logo" src="admin/resources/images/logo.png" alt="Simpla Admin logo"/>
            </a>
            <!-- Sidebar Profile links -->
            <div id="profile-links">
                <select id="admin_user">
                    <c:forEach var="lst" items="${admin_user_list}">
                        <option id="areid"
                                value="${lst.areaId };${lst.level}" ${areaID == lst.areaId?'selected':''}>${lst.areaName}</option>
                    </c:forEach>
                </select>
                |
                <a href="#" title="Sign Out">退出</a>
            </div>
            <ul id="main-nav">
                <!-- Accordion Menu -->
                <li>
                    <a href="admin/merchant_table.jsp" target="table" class="nav-top-item no-submenu" id="menu_1">
                        <!-- Add the class "no-submenu" to menu items with no sub menu -->
                        商家管理</a>
                    <ul>
                        <c:if test="${level!=2}">
                            <li>
                                <a href="admin/addMerchant.jsp" rel="modal">添加商家</a>
                            </li>
                        </c:if>
                        <!-- Add class "current" to sub menu items also -->
                        <c:if test="${level==0}">
                            <li>
                                <a href="admin/cat_table.jsp" target="table">分类管理</a>
                            </li>
                        </c:if>
                        <li>
                            <a href="admin/group_table.jsp" target="table">小组管理</a>
                        </li>
                        <li>
                            <a href="admin/merchant_comment_all_table.jsp" target="table">评论管理</a>
                        </li>
                    </ul>
                </li>

                <c:if test="${level==0}">
                    <li>
                        <a href="admin/user_table.jsp" target="table" class="nav-top-item no-submenu" id="menu_2"
                           onclick="menuhidden(2)">
                            <!-- Add the class "current" to current menu item -->
                            用户管理 </a>
                        <ul>

                            <li>
                                <a href="admin/addUser.jsp" rel="modal">添加用户</a>
                            </li>
                            <!-- Add class "current" to sub menu items also -->
                            <li>
                                <a href="javascript:alert('正在建设之中...')">发送广播</a>
                            </li>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <a href="admin/weibo_table.jsp" target="table" class="nav-top-item" id="menu_3">
                        微博管理 </a>
                    <ul>

                        <c:if test="${level==3}">
                            <li>
                                <a href="admin/addWeibo.jsp" rel="modal">添加微博</a>
                            </li>
                        </c:if>

                    </ul>
                </li>

                <c:if test="${level!=3}">
                    <li id="id_diqu">
                        <a href="admin/area_table.jsp" target="table" class="nav-top-item"
                           onclick="load_table_title(5)"> 地区管理 </a>
                        <ul>
                            <li>
                                <a href="admin/addArea.jsp" rel="modal"> 添加地区 </a>
                            </li>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <c:if test="${level==0}">
                        <a href="admin/admin_table.jsp" target="table" class="nav-top-item no-submenu" id="menu_2"
                           onclick="menuhidden(6)">
                            管理员设置 </a>
                    </c:if>
                    <c:if test="${level!=0}">
                        <a href="javascript:void(0)" target="table" class="nav-top-item no-submenu" id="menu_2"
                           onclick="menuhidden(6)">
                            管理员设置 </a>
                    </c:if>
                    <ul>
                        <c:if test="${level==0}">
                            <li>
                                <a href="admin/addAdmin.jsp" rel="modal">添加管理员</a>
                            </li>
                        </c:if>
                        <li>
                            <a href="admin/updateAdminPwd.jsp" rel="modal">修改密码</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <!-- End #sidebar -->
    <div id="main-content">
        <!-- Main Content Section with everything -->
        <noscript>
            <!-- Show a notification if the user has disabled javascript -->
            <div class="notification error png_bg">
                <div>
                    Javascript is disabled or is not supported by your browser.
                    Please
                    <a href="http://browsehappy.com/"
                       title="Upgrade to a better browser">upgrade</a> your browser or
                    <a
                            href="http://www.google.com/support/bin/answer.py?answer=23852"
                            title="Enable Javascript in your browser">enable</a> Javascript
                    to navigate the interface properly. Download From
                    <a href="http://www.exet.tk">exet.tk</a>
                </div>
            </div>
        </noscript>
        <!-- Page Head -->
        <h2>
            欢迎您，${userInfo.username}
        </h2>
        <iframe src="${skip_url}" name="table" width="100%" height="535"></iframe>
        <div id="footer">
            <small> <!-- Remove this notice or replace it with whatever you want -->
                &#169; Copyright 2010 Your Company | Powered by <a href="#">admin
                    templates</a> | <a href="admin/#">Top</a></small>
        </div>
        <!-- End #footer -->
    </div>
    <!-- End #main-content -->
</div>


</body>
<!-- Download From www.exet.tk-->
</html>
