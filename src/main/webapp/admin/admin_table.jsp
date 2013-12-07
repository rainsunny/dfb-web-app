<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		line-height: 35px;
	}
	#table tbody tr td{
		padding-top:14px;
	}
	</style>
		<link rel="stylesheet" href="admin/resources/css/reset.css" type="text/css" media="screen" />
		<!-- Main Stylesheet -->
		<link rel="stylesheet" href="admin/resources/css/showstyle.css" type="text/css" media="screen" />
		<!-- Invalid Stylesheet. This makes stuff look pretty. Remove it if you want the CSS completely valid -->
		<link rel="stylesheet" href="admin/resources/css/invalid.css" type="text/css" media="screen" />
		<!-- jQuery -->
		<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
		<!-- jQuery Configuration -->
		<script type="text/javascript" src="admin/resources/scripts/simpla.jquery.configuration.js"></script>
		<!-- Facebox jQuery Plugin -->
		<script type="text/javascript" src="admin/resources/scripts/facebox.js"> </script>
		<!-- jQuery WYSIWYG Plugin -->
		<script type="text/javascript" src="admin/resources/scripts/jquery.wysiwyg.js"></script>

		<script type="text/javascript">
			function del(id){
				if(confirm("您确定要删除此管理员的内容吗?")==true){
					$.post("AdminLoginServlet?op=delete&id="+id,function(data){
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
		<form action="admin/weibo_table.jsp" method="post">
		<div class="content-box-header" style="align: center">
			<h3>
				管理员管理
			</h3>
			<br />
		</div>
		</form>
<div class="content-box-content">

	<div class="tab-content default-tab">
		<!-- This is the target div. id must match the href of this div's tab -->
		<table id="table">
		<thead>
			<tr>
			<c:import url="../AdminLoginServlet?op=query" />
				<th>ID</th>
				<th>用户名</th>
				<th>密码</th>
				<th>地区</th>
				<th>管理</th>
			</tr>
			
		</thead>
		
		<tbody>
			<c:forEach items="${pm.data}" var="admin">
				<tr>
					<td width="20%" align="center">${admin.ID}</td>
					<td width="20%" align="center">${admin.username }</td>
					<td width="20%" align="center">${admin.password}</td>
					<td width="20%" align="center">${admin.areaName}</td>
					<td width="20%" align="center">
						<a href='admin/updateAdmin.jsp?id=${admin.ID}&level=${admin.level}'  rel="modal" title='编辑'><img src='admin/resources/images/icons/pencil.png' /></a>
						<a href="javascript:del('${admin.ID}')" title='删除'><img src='admin/resources/images/icons/cross.png' alt='Delete' /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="5" width="100%">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/admin_table.jsp" pm="${pm}"></cc:page>
					</c:if>
					<c:if test="${empty pm.data}">
						<span>对不起,系统没有找到相关数据!</span>
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
