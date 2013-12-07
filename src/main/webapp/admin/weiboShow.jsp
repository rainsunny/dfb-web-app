<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.doufangbian.util.ConstantUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
<style type="text/css">
	#facebox{margin-top: -60px;}
</style>
</head>

<body>
	<c:import url="../WeibosServlet?op=queryById"></c:import>

		<div id="messages" >
		<h3>
			微博详情
		</h3>
	     <form action="WeibosServlet?op=updateById" method="post" enctype="multipart/form-data" target="table" onSubmit="return checkSubmit();">
	    	<input type="hidden" value="${wb.id}" name="id" id="id">
	      <table id="cctable" width="500" height="300">
            <tr>
              <td width="121" >内容</td>
            </tr>
            <tr>
              <td colspan="3"><textarea name="content" id="content" class="text_field" placeholder="请输入您要发布的内容" cols="50" rows="5">${wb.content}</textarea>              </td>
              
            </tr>
            <tr>
              <td>顶</td>
              <td width="242"><input type="text" id="up" value="${wb.up}" name="up" /></td>
			  <td rowspan="5"><c:if test="${!empty wb.photo}">
              		<img width=120 height=120 src="<%=basePath %>/<%=ConstantUtil.WEIBO_IMAGE %>/${wb.photo}"></img>              	</c:if></td>
            </tr>
            <tr>
              <td>踩</td>
              <td><input type="text" id="down" value="${wb.down}" name="down"/></td>
		    </tr>
            <tr>
              <td>分享</td>
              <td><input type="text" id="share" value="${wb.share}" name="share" /></td>
		    </tr>
            <tr>
              <td>用户ID</td>
              <td><input type="text"id="uid" value="${wb.uid}" style="width: 80px;" onKeyUp="queryUserNameById(this);" name="uid"/>
                  <span id="username">${userInfo.username}</span> </td>
		    </tr>
          </table>
	    </form>
	</div>
</body>
</html>
