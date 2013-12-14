<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'addCat.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="js/jquery-1.4.1.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#okbtn").click(function () {
                var name = $("#name").val();
                var catId = $("#cat").val();
                if (name == "" || catId == "0") {
                    $("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
                    return;
                }
                var form_data = "op=add&name=" + name + "&catId=" + catId;
                $.ajax({
                    type: "POST",
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    url: "Dfb_merchant_groupServlet",
                    data: form_data,
                    success: function (data) {
                        if (data.indexOf("true") == -1) {
                            $("#msg").html("系统繁忙,请稍后再试...");

                        } else {
                            $("#msg").html("恭喜你添加小组成功!");
                        }
                    }
                });
            });
        });
    </script>

</head>

<body>
<table class="lie_center">
    <tr>
        <td><label for="name">小组名称:</label></td>
        <td><input type="text" id="name"/></td>
    </tr>
    <tr>
        <td><label for="cat">所属分类</label></td>
        <c:import url="../Dfb_merchant_catServlet?op=query"/>
        <td>
            <select id="cat" name="catId">
                <option value="0" selected="selected">选择分类</option>
                <c:forEach items="${listCat}" var="cat">
                    <option value="${cat.id}">${cat.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <input type="button" value="添加" id="okbtn"/>
        </td>
        <td><span id="msg"></span></td>
    </tr>
</table>

</body>
</html>
