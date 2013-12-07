<%@ page language="java" import="com.doufangbian.util.ConstantUtil" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'show.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <style type="text/css">
        tr {
            line-height: 34px;
        }

        #table tbody tr td {
            padding-top: 8px;
        }

        .th1, .th2, .th3, .th4 {
            width: 25%;
            text-align: center;
        }

    </style>
    <link rel="stylesheet" href="admin/resources/css/showstyle.css" type="text/css" media="screen"/>
    <!-- Reset Stylesheet -->
    <link rel="stylesheet" href="admin/resources/css/reset.css" type="text/css" media="screen"/>
    <!-- Main Stylesheet -->
    <link rel="stylesheet" href="admin/resources/css/invalid.css" type="text/css" media="screen"/>
    <!-- jQuery -->
    <script type="text/javascript" src="js/jquery-1.4.1.js"></script>
    <!-- jQuery Configuration -->
    <script type="text/javascript" src="admin/resources/scripts/facebox.js"></script>
    <!-- jQuery WYSIWYG Plugin -->
    <script type="text/javascript" src="admin/resources/scripts/jquery.wysiwyg.js"></script>
    <script type="text/javascript" src="admin/resources/scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript">
        function del(obj) {
            if (confirm("您确定要删除该大组相关信息吗?")) {
                $.post("Dfb_merchant_catServlet?op=delete&catId=" + obj, function (data) {
                    if (data.indexOf("true") == -1) {
                        alert("系统繁忙,请稍后再试!");
                    } else {
                        alert("删除成功!");
                        window.location.href = "<%=basePath%>admin/cat_table.jsp";
                    }
                });
            }
        }
    </script>
</head>

<body>
<div class="content-box">
    <!-- Start Content Box -->
    <div class="content-box-header" style="align: center">
        <h3 id="local">分类管理</h3>
        <c:if test="${level==0}">
            <h3 style="float: right;"><a href="admin/addCat.jsp" title="添加大组" rel="modal">添加大组</a></h3>
        </c:if>
        <br/>
    </div>
    <!-- End .content-box-header -->
    <div class="content-box-content" id="table_row">
        <div class="tab-content default-tab" id="tab1">
            <!-- This is the target div. id must match the href of this div's tab -->
            <table id="table">
                <thead>
                <tr>
                    <c:import url="../Dfb_merchant_catServlet?op=queryAll"/>
                    <th class="th1">ID</th>
                    <th class="th2">标识</th>
                    <th class="th3">名称</th>
                    <c:if test="${level==0}">
                        <th class="th4">管理</th>
                    </c:if>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${pm.data}" var="cat">
                    <tr>
                        <td>${cat.id}</td>
                        <td>
                            <img src="<%=basePath %>/<%=ConstantUtil.CAT_IMAGE%>/null.jpg"
                                 style="width: 30px; height: 26px;">
                        </td>
                        <td>
                            <a href="admin/catDetail_table.jsp?catId=${cat.id}">${cat.name}</a>
                        </td>
                        <c:if test="${level==0}">
                            <td>
                                <a href='admin/updateCat.jsp?catId=${cat.id}' rel="modal" title='编辑'>
                                    <img src='admin/resources/images/icons/pencil.png'/>
                                </a>
                                <a href="javascript:del('${cat.id}')" title='删除'>
                                    <img src='admin/resources/images/icons/cross.png' alt='Delete'/>
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td id="page_go" colspan="4">
                        <c:if test="${!empty pm.data}">
                            <cc:page url="admin/cat_table.jsp" pm="${pm}"></cc:page>
                        </c:if>
                        <c:if test="${empty pm.data}">
                            <span>对不起,系统没有找到<b>${param['keywords']}</b>相关数据!</span>
                        </c:if>
                    </td>
                </tr>
                </tfoot>
            </table>

        </div>
        <!-- End #tab1 -->
    </div>
    <!-- End .content-box-content -->
</div>
</body>
</html>
