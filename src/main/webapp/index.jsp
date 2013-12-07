<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

    <script type="text/javascript" src="admin/ajax.js">

    </script>


</head>


<body onload="areas_loadcbo()">

<div id="messages" style="display">
    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
    <h3>
        添加地区
    </h3>

    <form action="http://www.baidu.com" method="get">


        <select id="selecte_group_provincial"
                onchange="provincial_changecbo(this)">

            <option value="0" selected="selected">
                全国
            </option>


        </select>


        <select id="group_city" onchange="city_changecbo(this)">

            <option value="0" selected="selected">
                所有市
            </option>

        </select>
        <input type="text" id="selecte_keyword" class="input-medium"
               placeholder="关键词"/>


    </form>
</div>

</body>
</html>