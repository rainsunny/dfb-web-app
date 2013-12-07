<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
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
	<style type="text/css">
	tr {
		line-height: 35px;
	}
	#table tbody tr td{
		padding-top:14px;
	}
	</style>
		<script type="text/javascript">
			function del(id){
				if(confirm("您确定要删除这条微博的内容吗?")==true){
					$.post("WeibosServlet?op=deleteById&id="+id,function(data){
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
				微博管理
			</h3>
		<input type="text" id="keywords" class="input-medium" name="keywords"
						placeholder="请输入微博内容" value="<%=keywords %>" />
		<input class="button" type="submit" value="搜索" id="sougle"/>
			<br />
		</div>
		</form>
<div class="content-box-content">
	<div class="tab-content default-tab">
		<table id="table">
		<thead>
			<tr>
			<c:import url="../WeibosServlet?op=query" />
				<th>ID</th>
				<th>内容</th>
				<th>顶</th>
				<th>踩</th>
				<th>分享</th>
				<th>用户</th>
				<th>地区</th>
				<th>评论</th>
				<th>发布时间</th>
				<th>管理</th>
			</tr>
			
		</thead>
		
		<tbody>
			<c:forEach items="${pm.data}" var="weiboinfo">
				<tr>
					<td width="5%" >${weiboinfo.id}</td>
					<td width="20%" ><a href="admin/weiboShow.jsp?id=${weiboinfo.id}'"  rel="modal" title="${weiboinfo.content}">${fn:substring(weiboinfo.content, 0,11)}</a></td>
					<td width="5%" >${weiboinfo.up}</td>
					<td width="5%" >${weiboinfo.down}</td>
					<td width="5%" >${weiboinfo.share}</td>
					<td width="13%" title="${weiboinfo.username}">${fn:substring(weiboinfo.username, 0,6)}</td>
					<td width="10%" >${weiboinfo.name}</td>
					<td width="10%" ><a href="admin/weibo_reply.jsp?wid=${weiboinfo.id}" target="table">评论(${weiboinfo.countPL})</a></td>
					<td width="20%" >${fn:substring(weiboinfo.time, 0,19)}</td>
					<td width="20%" >
						<a href='admin/updateWeibo.jsp?id=${weiboinfo.id}'  rel="modal" title='编辑'><img src='admin/resources/images/icons/pencil.png' /></a>
						<a href="javascript:del('${weiboinfo.id}')" title='删除'><img src='admin/resources/images/icons/cross.png' alt='Delete' /></a>
						<a href='admin/weiboShow.jsp?id=${weiboinfo.id}'  rel="modal" title='Edit Meta'><img src='admin/resources/images/icons/hammer_screwdriver.png' alt='Edit Meta' /> </a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="10" width="100%">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/weibo_table.jsp" pm="${pm}" vague="&keywords=${keywords}"></cc:page>
					</c:if>
					<c:if test="${empty pm.data}">
						<span>对不起,系统没有找到<b>${param['keywords']}</b>相关数据!</span>
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
