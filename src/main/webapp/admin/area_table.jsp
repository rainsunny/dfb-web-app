<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    <base href="<%=basePath%>" />
    
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
		<script type="text/javascript" src="admin/ajax.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#search_group_provincial").change(function(){
					if($(this).val()==0){
						$("#search_group_city").val("0");
						window.location.href="<%=basePath%>admin/area_table.jsp?provincialID="+$(this).val();
					}else{
						$("#search_group_city").val("0");
						window.location.href="<%=basePath%>admin/area_table.jsp?provincialID="+$(this).val()+"&cityId="+$("#search_group_city").val();
					}
				});
				$("#search_group_city").change(function(){
					window.location.href="<%=basePath%>admin/area_table.jsp?cityId="+$(this).val()+"&provincialID="+$("#search_group_provincial").val();
				});
				
			});
			function admin_home(areid){
				$.post("AreasServlet?op=addUser&areid="+areid,function(data){
					window.top.location.href="<%=basePath%>admin/index.jsp";
				});
			}
		</script>
  </head>
  
  <body>
    <div class="content-box">
		<!-- Start Content Box -->
		<form action="admin/area_table.jsp" method="post">
		<div class="content-box-header" style="align: center">
			<h3 id="local">
				地区管理
			</h3>
			<c:import url="../ProvincialServlet?op=query" />
			<select id="search_group_provincial" name="provincialID">
				<c:if test="${level==0}">
					<option value="0" selected="selected">
						全国
					</option>
				</c:if>
				<c:forEach items="${proList}" var="pro">
					<option value="${pro.provincialID}" ${param['provincialID']==pro.provincialID?'selected':''}>${pro.name}</option>
				</c:forEach>
			</select>

		<c:import url="../CityInfoServlet?op=query" />
			<select id="search_group_city" name="cityId">
				
				<c:if test="${level!=2}">
				<option value="0" selected="selected">
					所有市
				</option>
				</c:if>
				<c:forEach items="${cityList}" var="city">
					<option value="${city.cityID}" ${city.cityID==param['cityId']?'selected':''}>${city.name}</option>
				</c:forEach>

			</select>

			<input type="text" id="keywords" name="keywords" class="input-medium"
				placeholder="关键词" value="<%=keywords %>" />
				
			<input class="button" type="submit" value="搜索" id="sougle"/>
			<br />

		</div></form>
	<!-- End .content-box-header -->
<div class="content-box-content" id="table_row">

	<div class="tab-content default-tab" id="tab1">
		<!-- This is the target div. id must match the href of this div's tab -->
		<table id="table">
		<thead>
			<tr >
				<c:import url="../AreasServlet?op=query" />
					<th>ID</th>
					<th>地区名称</th>
					<th>咨询网址</th>
					<th>管理</th>
				
			</tr>
			
		</thead>
		
		<tbody >
			<c:forEach items="${pm.data}" var="county">
				<tr>
					<td width="20%" align="center">${county.countyID}</td>
					<td width="30%" align="center"><a href="javascript:admin_home(${county.countyID})"  >${county.name}</a></td>
					<td width="35%" align="center"><a href="${county.wapUrl}">${county.wapUrl}</a></td>
					<td width="25%" align="center">
						<a href='admin/updateArea.jsp?countyId=${county.countyID}' title='编辑' rel="modal" ><img src='admin/resources/images/icons/pencil.png' /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" width="100%" colspan="4">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/area_table.jsp" pm="${pm}" vague="&cityId=${param['cityId']}&provincialID=${param['provincialID']}&keywords=${keywords}"></cc:page>
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
