<%--
  User: Jie
  Date: 12/10/13
  Time: 10:29 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>商户管理平台</title>

    <style type="text/css">

    </style>

    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>

    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

    <script type="text/javascript">

        $(function () {
            load('merchant!info');
        });

        function load(url) {
            $.get(url, function (data) {
                $("#content").html(data);
            });
        }

    </script>
</head>
<body>

<div id="navigate">
    <ul>
        <li><a href="javascript:" onclick="load('merchant!info')">基本资料</a></li>
        <li><a href="javascript:" onclick="load('merchant!album')">图册管理</a></li>
        <li><a href="javascript:" onclick="load('merchant!comment')">点评管理</a></li>
        <li><a href="javascript:" onclick="load('merchant!message')">消息推送</a></li>
        <li><a href="javascript:" onclick="load('merchant!talk')">对话</a></li>
        <li><a href="javascript:" onclick="load('merchant!config')">设置</a></li>
    </ul>
</div>

<div id="content"></div>

<div id="foot"></div>

</body>
</html>