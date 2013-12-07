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
		<script type="text/javascript" src="js/common.js"></script>
		
		<script type="text/javascript">
		
		function del(id){
			if(confirm("您确定要删除此评论信息吗?")==true){
				$.post("Dfb_merchant_commentServlet?op=delete&id="+id,function(data){
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
<div class="content-box-header" style="align: center">
<c:import url="../MerchantsServlet?op=queryById" />
	<h3>
		商家详情>>${merchant.name}
	</h3>
</div>
<!-- End .content-box-header -->
<div class="content-box-content" id="table_row">
	<div class="tab-content default-tab" id="tab1">
		<!-- This is the target div. id must match the href of this div's tab -->
		<table>
		<tr>
			<td><b>主营:</b>${merchant.major}</td>
		</tr>
		<tr>
			<td><b>电话:</b>${merchant.phone}</td>
		</tr>
		<tr>
			<td><b>地址:</b>${merchant.address}</td>
		</tr>
		<tr>
			<td><b>最新资讯:</b>${merchant.news}</td>
		</tr>
		<tr>
			<td><b>简介:</b>${merchant.introduction}</td>
		</tr>
	</table>
		<table id="table">
		<thead>
			<tr>
			<c:import url="../Dfb_merchant_commentServlet?op=query">
				<c:param name="mid">${merchant.id}</c:param>
			</c:import>
				<th>ID</th>
				<th>用户姓名</th>
				<th>内容</th>
				<th>评论时间</th>
				<th>操作</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${pm.data}" var="comment">
				<tr>
					<td width="15%" align="center">${comment.id}</td>
					<td width="15%" align="center">${comment.username}</td>
					<td width="25%" align="center" title="${comment.content}">${comment.content}</td>
					<td width="15%" align="center">${fn:substring(comment.time, 0,19)}</td>
					<td width="20%" align="center">
						<a href='admin/updateComment.jsp?id=${comment.id}' title='编辑' rel="modal"><img src='admin/resources/images/icons/pencil.png' /></a>
						<a href="javascript:del('${comment.id}')" title='删除'><img src='admin/resources/images/icons/cross.png' alt='Delete' /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="5">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/merchantDetail_table.jsp" pm="${pm}" vague="&merchantId=${param['merchantId']}&mid=${param['mid']}"></cc:page>
					</c:if>
					<c:if test="${empty pm.data}">
						暂无评论
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
