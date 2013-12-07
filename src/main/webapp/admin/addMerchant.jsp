<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'addWeibo.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="js/jquery-1.4.1.js"></script>
    <script type="text/javascript">
        $(function () {
            $("input:text").each(function () {
                $(this).focus(function () {
                    $("#msg").html("");
                });
            });
        });

        function checkSubmit() {
            var name = $("#name").val();
            var major = $("#major").val();
            var address = $("#address").val();
            var phone = $("#phone").val();
            var introduction = $("#introduction").val();
            var news = $("#news").val();
            var up = $("#up").val();
            var status = $("#status").val();

            if (name == "" || major == "" || address == "" || phone == "" || introduction == "" || news == "" || up == "" || status == "") {
                $("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
                return false;
            }
            $("#msg").html("恭喜你,添加商家信息成功!");
            return true;
        }

    </script>

</head>

<body>

<div id="messages">
    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
    <h3>
        添加商家
    </h3>

    <form action="MerchantsServlet?op=add" method="post" enctype="multipart/form-data" target="table"
          onSubmit="return checkSubmit();">
        <table class="lie_center">
            <tr>
                <td width="64"><label for="name">名称</label></td>
                <td width="183"><input type="text" name="name" id="name"/></td>
                <td width="120">&nbsp;</td>
            </tr>
            <tr>
                <td><label for="major">主营</label></td>
                <td><input name="major" type="text" id="major"/></td>
                <td width="120">&nbsp;</td>
            </tr>
            <tr>
                <td><label for="address">地址</label></td>
                <td><input type="text" name="address" id="address"/></td>
                <td width="120">&nbsp;</td>
            </tr>
            <tr>
                <td><label for="phone">电话</label></td>
                <td><input type="text" name="phone" id="phone"/></td>
                <td width="120">&nbsp;</td>
            </tr>
            <tr>
                <td><label for="logo">商家logo</label></td>
                <td colspan="2"><input type="file" name="photo" id="logo"/></td>
            </tr>
            <tr>
                <td valign="top"><label for="introduction">商家简介</label></td>
                <td colspan="2"><textarea name="introduction" rows="4" style="width:300px;"
                                          id="introduction"></textarea></td>
            </tr>
            <tr>
                <td valign="top"><label for="news">最新动态</label></td>
                <td colspan="2"><textarea name="news" rows="4" style="width:300px;" id="news"></textarea></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td class="form-inline">
                    <label class="checkbox inline"><input type="checkbox" name="isrecommend" value="1" checked="checked" id="isrecommend">推荐</label>
                </td>
                <td class="form-inline">
                    <label class="inline">顶&nbsp;<input type="text" name="up" value="0" style="width:40px;" id="up"/></label>
                </td>
            </tr>
            <tr>
                <td><label for="status">商家类别</label></td>
                <td colspan="2">
                    <select name="status" style="width:300px;" id="status">
                        <option value="0">普通用户</option>
                        <option value="1">付费用户</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="group">商家分组</label></td>
                <td colspan="2">
                    <select name="group" style="width:300px;" id="group">
                        <option value="0">分类查询</option>
                        <option value="1">公众服务</option>
                        <option value="3">商企信息目录</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="添加商家" class="button" id="dosubmit"></td>
                <td colspan="2"><span id="msg" style="color: red"></span></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
