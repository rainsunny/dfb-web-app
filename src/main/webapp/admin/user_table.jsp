<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.doufangbian.util.ConstantUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String keywords = request.getParameter("keywords");
if(keywords!=null && keywords!="" && "get".equalsIgnoreCase(request.getMethod())){
	keywords = new String(keywords.getBytes("iso-8859-1"),"utf-8");
}
if(keywords==null){
	keywords="";
}
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
	#table tbody tr td{
		padding-top:8px;
	}
	</style>
		<link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
		<!-- Reset Stylesheet -->
		<link rel="stylesheet" href="admin/resources/css/reset.css" type="text/css" media="screen" />
		<!-- Main Stylesheet -->
		<link rel="stylesheet" href="admin/resources/css/invalid.css" type="text/css" media="screen" />
		<!-- jQuery -->
		<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
		<!-- jQuery Configuration -->
		<script type="text/javascript" src="admin/resources/scripts/facebox.js"> </script>
		<!-- jQuery WYSIWYG Plugin -->
		<script type="text/javascript" src="admin/resources/scripts/jquery.wysiwyg.js"></script>
		<script type="text/javascript" src="admin/resources/scripts/simpla.jquery.configuration.js"></script>
		
		<script type="text/javascript">
			function del(obj){
				if(confirm("您确定要删除该用户吗?")){
					$.post("UsersServlet?op=delete&uid="+obj,function(data){
						if(data.indexOf("true")==-1){
							alert("系统繁忙,请稍后再试!");
						}else{
							alert("删除成功!");
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
	<form action="admin/user_table.jsp" method="post">
	<div class="content-box-header" style="align: center">
		<h3 id="local">
			用户管理
		</h3>
		<input type="text" id="keywords" class="input-medium" name="keywords"
			placeholder="请输入用户名" value="<%=keywords %>" />
		<input class="button" type="submit" value="搜索" id="sougle"/>

		<br />

	</div>
	</form>
	<!-- End .content-box-header -->
	<div class="content-box-content">

		<div class="tab-content default-tab">
			<!-- This is the target div. id must match the href of this div's tab -->
	<table id="table">
		<thead>
			<tr>
			<c:import url="../UsersServlet?op=query" />
				<th>ID</th>
				<th>头像</th>
				<th>用户名</th>
				<th>性别</th>
				<th>电话</th>
				<th>豆币</th>
				<th>状态</th>
				<th>管理</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${pm.data}" var="userinfo">
				<tr>
					<td width="10%" align="center">${userinfo.id}</td>
					<td width="15%" align="center">
						<img src="<%=basePath %>/<%=ConstantUtil.USER_IMAGE %>/${userinfo.headphoto}" style="width: 30px; height: 26px;">
					</td>
					<td width="20%" align="center">${userinfo.username}</td>
					<td width="10%" align="center">${userinfo.gender==1?'男':'女'}</td>
					<td width="20%" align="center">
						<c:if test="${empty userinfo.phone}">
							暂无电话
						</c:if>
						<c:if test="${!empty userinfo.phone}">
							${userinfo.phone}
						</c:if>
					</td>
					<td width="10%" align="center">${userinfo.cash}</td>
					<td width="10%" align="center">${userinfo.status==1?'正常':禁用}</td>
					<td width="20%" align="center">
						<a href="admin/updataUser.jsp?uid=${userinfo.id}&pageNo=${param['pageNo']}&keywords=<%=keywords %>"  rel="modal" title='编辑'><img src='admin/resources/images/icons/pencil.png' /></a>
						<a href="javascript:del('${userinfo.id}')" title='删除'><img src='admin/resources/images/icons/cross.png' alt='Delete' /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="8">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/user_table.jsp" pm="${pm}" vague="&keywords=${keywords}"></cc:page>
					</c:if>
					<c:if test="${empty pm.data}">
						<span>对不起,系统没有找到<b>${param['keywords']}</b>相关数据!</span>
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
