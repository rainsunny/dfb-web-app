<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'weibo_reply.jsp' starting page</title>
    
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
	<link rel="stylesheet" href="admin/resources/css/showstyle.css"
		type="text/css" media="screen" />
	<link rel="stylesheet" href="admin/resources/css/reset.css" type="text/css" media="screen" />
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
			if(confirm("此操作将会删除此评论以及所有此评论下的回复信息,确定删除?")==true){
				$.post("Dfb_weibo_replyServlet?op=deleteById&replyId="+obj,function(data){
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
  	<div class="content-box-header" style="align: center">
		<h3 id="local">
			<c:import url="../WeibosServlet?op=queryById&id=${param['wid']}"></c:import>
			<a href="admin/addReply.jsp?wid=${wb.id}" rel="modal">微博评论</a>(<span title="${wb.content}">${fn:substring(wb.content, 0,40)}</span>)
		</h3>
		<br />
	</div>
    <div class="content-box-content" id="table_row">
	
	<div class="tab-content default-tab" id="tab1">
		<c:import url="../Dfb_weibo_replyServlet?op=query"></c:import>
		<table id="table">
		<c:if test="${!empty pm.data}">
		<thead>
			<tr>
				<th align="center" width="15%">ID</th>
				<th align="center" width="25%">内容</th>
				<th align="center" width="20%">时间</th>
				<th align="center" width="20%">用户名</th>
				<th align="center" width="20%">管理</th>
		</thead>
		<tbody>
			<c:forEach items="${pm.data}" var="reply">
				<tr>
					<td align="center">${reply.id }</td>
					<td align="center" title="${reply.replycontent }">${fn:substring(reply.replycontent, 0,13)}</td>
					<td align="center">${fn:substring(reply.time, 0,19)}</td>
					<td align="center">${reply.username }</td>
					<td align="center">
						<a href="javascript:del('${reply.id }')" title='删除'><img src='admin/resources/images/icons/cross.png' alt='Delete' /></a>
						<a href='admin/updateReply.jsp?replyId=${reply.id}'  rel="modal" title='Edit Meta'><img src='admin/resources/images/icons/hammer_screwdriver.png' alt='Edit Meta' /> </a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="5" width="100%" >
					<cc:page  url="admin/weibo_reply.jsp" pm="${pm}" vague="&wid=${param['wid']}"></cc:page>
				</td>
			</tr>
		</tfoot>
		</c:if>
		<c:if test="${empty pm.data}">
		<tfoot>
			<tr>
				<td id="page_go" colspan="5" width="100%">
					<a href="admin/addReply.jsp?wid=${wb.id}" rel="modal">评论微博</a>
				</td>
			</tr>
		</tfoot>
		</c:if>
	</table>					
</div>
	<!-- End #tab1 -->

	</div>
	<!-- End .content-box-content -->
</div>
  </body>
</html>
