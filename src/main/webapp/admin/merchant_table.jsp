<%@ page language="java" import="com.doufangbian.util.ConstantUtil" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

    <script type="text/javascript">

        $(function () {
            $("#search_group_provincial").change(function () {
                var state;
                var enabled = $("#enabled").val();
                if (enabled == '查看禁用商家') {
                    state = 1;
                } else {
                    state = 0;
                }
                if ($(this).val() == 0) {
                    $("#search_group_city").val("0");
                    window.location.href = "<%=basePath%>admin/merchants.do?catId=" + $(this).val() + "&state=" + state;
                } else {
                    $("#search_group_city").val("0");
                    window.location.href = "<%=basePath%>admin/merchants.do?catId=" + $(this).val() + "&groupId=" + $("#search_group_city").val() + "&state=" + state;
                }
            });

            $("#search_group_city").change(function () {
                window.location.href = "<%=basePath%>admin/merchants.do?groupId=" + $(this).val() + "&catId=" + $("#search_group_provincial").val();
            });

            $("#seach").click(function () {
                var enabled = $("#enabled").val();
                if (enabled == '查看禁用商家') {
                    $("#state").val("1");
                } else {
                    $("#state").val("0");
                }
            });
        });

        function del(id) {
            if (confirm("您确定要删除此商家的信息吗?") == true) {
                $.post("MerchantsServlet?op=delete&merchantId=" + id, function (data) {
                    if (data.indexOf("true") == -1) {
                        alert("系统繁忙,请稍后再试!");
                    } else {
                        alert("删除成功!");
                        top.table.location.reload(true);
                    }
                });
            }
        }

        function enabled(id, state) {
            if (confirm("您确定要--" + (state == 0 ? '禁用' : '启用') + "--此商家吗?") == true) {
                $.post("MerchantsServlet?op=enabled&merchantId=" + id + "&state=" + state, function (data) {
                    if (data.indexOf("true") == -1) {
                        alert("系统繁忙,请稍后再试!");
                    } else {
                        alert((state == 0 ? '禁用' : '启用') + "成功!");
                        top.table.location.reload(true);
                    }
                });
            }
        }
    </script>
</head>

<body>

<div class="content-box">
    <!-- Start Content Box -->
    <form action="admin/merchants.do" method="post">
        <div class="content-box-header">
            <h3>
                商家管理
            </h3>

            <%--<c:import url="../Dfb_merchant_catServlet?op=query"/>--%>
            <select id="search_group_provincial" name="catId">
                <option value="0" selected="selected">所有大组</option>
                <c:forEach items="${listCat}" var="cat">
                    <option value="${cat.id}" ${param['catId']==cat.id?'selected':''}>${cat.name}</option>
                </c:forEach>
            </select>

            <%--<c:import url="../Dfb_merchant_groupServlet?op=query"/>--%>
            <select id="search_group_city" name="groupId">
                <c:if test="${level!=2}">
                    <option value="0" selected="selected">所有小组</option>
                </c:if>
                <c:forEach items="${listGroup}" var="group">
                    <option value="${group.id}" ${group.id==param['groupId']?'selected':''}>${group.name}</option>
                </c:forEach>
            </select>

            <input type="text" id="keywords" class="input-medium" name="keywords"
                   placeholder="请输入商家名称" value="${keywords}"/>
            <input class="button" type="submit" value="搜索商家" id="seach"/>

            <input type="hidden" value="${empty param['state']?0:param['state']==1?0:1}" name="state" id="state">
            <input class="button" type="submit" id="enabled"
                   value="${empty param['state']?'查看禁用商家':param['state']==1?'查看禁用商家':'查看启用商家'}"
                   style="float: right;margin-right: 20px;"/>

            <br/>
        </div>
    </form>

    <div class="content-box-content">
        <div class="tab-content default-tab">
            <table id="table">

                <thead>
                <tr>
                    <%--<c:import url="../MerchantsServlet?op=query"/>--%>
                    <th>ID</th>
                    <th>Logo</th>
                    <th>名称</th>
                    <th>主营</th>
                    <th>顶</th>
                    <th>表现形式</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${pm.data}" var="merchantinfo">
                    <tr class="alt-row">
                        <td width="10%" align="center">${merchantinfo.id}</td>
                        <td width="10%" align="center">
                            <img src="<%=basePath %>/<%=ConstantUtil.MERCHANT_IMAGE %>/${merchantinfo.log}"
                                 style="width: 30px; height: 26px;">
                        </td>
                        <td width="20%" align="center"><a
                                href="admin/merchantDetail_table.jsp?merchantId=${merchantinfo.id}"
                                title="${merchantinfo.name}">${fn:substring(merchantinfo.name, 0,15)}</a></td>
                        <td width="25%" align="center"
                            title="${merchantinfo.major}">${fn:substring(merchantinfo.major, 0,16)}</td>
                        <td width="10%" align="center">${merchantinfo.up}</td>
                        <td width="10%" align="center">${merchantinfo.status==1?"付费":"未付费"}</td>
                        <td width="20%" align="center">
                            <c:if test="${level==3}">
                                <c:if test="${merchantinfo.countyID!=-1}">
                                    <a href="admin/merchant_set_group_table.jsp?mid=${merchantinfo.id}">设置组</a>
                                    <a href="admin/updateMerchant.jsp?merchantId=${merchantinfo.id}&pageNo=${param['pageNo']}&catId=${param['catId']}&groupId=${param['groupId']}&keywords=${keywords}"
                                       title='编辑' rel="modal"><img src='admin/resources/images/icons/pencil.png'/></a>
                                    <a href="admin/merchantImage_table.jsp?mid=${merchantinfo.id}">图片</a>
                                    <a href="javascript:del('${merchantinfo.id}')" title='删除'><img
                                            src='admin/resources/images/icons/cross.png' alt='Delete'/></a>
                                </c:if>
                                <c:if test="${merchantinfo.countyID==-1}">
                                    <a href="admin/merchant_set_group_table.jsp?mid=${merchantinfo.id}">设置组</a>
                                </c:if>
                            </c:if>
                            <c:if test="${level==2 || level==1}">
                                <c:if test="${merchantinfo.countyID!=-1}">
                                    <a href="admin/updateMerchant.jsp?merchantId=${merchantinfo.id}&pageNo=${param['pageNo']}&catId=${param['catId']}&groupId=${param['groupId']}&keywords=${keywords}"
                                       title='编辑' rel="modal"><img src='admin/resources/images/icons/pencil.png'/></a>
                                    <a href="admin/merchantImage_table.jsp?mid=${merchantinfo.id}">图片</a>
                                    <a href="javascript:del('${merchantinfo.id}')" title='删除'><img
                                            src='admin/resources/images/icons/cross.png' alt='Delete'/></a>
                                </c:if>
                                <c:if test="${merchantinfo.countyID==-1}">
                                    无操作权限
                                </c:if>
                            </c:if>
                            <c:if test="${level==0}">
                                <a href="admin/updateMerchant.jsp?merchantId=${merchantinfo.id}&pageNo=${param['pageNo']}&catId=${param['catId']}&groupId=${param['groupId']}&keywords=${keywords}"
                                   title='编辑' rel="modal"><img src='admin/resources/images/icons/pencil.png'/></a>
                                <a href="admin/merchantImage_table.jsp?mid=${merchantinfo.id}">图片</a>
                                <a href="javascript:del('${merchantinfo.id}')" title='删除'><img
                                        src='admin/resources/images/icons/cross.png' alt='Delete'/></a>
                            </c:if>
                            <c:if test="${merchantinfo.state==1}">
                                <a href="javascript:enabled('${merchantinfo.id}',0);">禁用</a>
                            </c:if>
                            <c:if test="${merchantinfo.state==0}">
                                <a href="javascript:enabled('${merchantinfo.id}',1);">启用</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>

                <tfoot>
                <tr>
                    <td id="page_go" colspan="7">
                        <c:if test="${!empty pm.data}">
                            <cc:page url="admin/merchants.do" pm="${pm}"
                                     vague="&catId=${param['catId']}&groupId=${param['groupId']}&keywords=${keywords}&state=${param['state']}"></cc:page>
                        </c:if>
                        <c:if test="${empty pm.data}">
                            <span>对不起,系统没有找到<b>${keywords}</b>相关数据!</span>
                        </c:if>
                    </td>
                </tr>
                </tfoot>

            </table>
        </div>
    </div>
</div>
</body>
</html>
