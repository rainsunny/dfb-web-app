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
    <base href="<%=basePath%>">
    
    <title>My JSP 'show.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
			tr{line-height: 28px;}
		</style>
		<link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
		<!-- Reset Stylesheet -->
		<link rel="stylesheet" href="admin/resources/css/reset.css" type="text/css" media="screen" />
		<!-- Main Stylesheet -->
		<link rel="stylesheet" href="admin/resources/css/invalid.css" type="text/css" media="screen" />
		<!-- jQuery -->
		<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#chiose").change(function(){
					window.location.href="<%=basePath%>admin/merchant_set_group_table.jsp?chiose="+$(this).val()+"&mid="+$("#mid").val();
				});
				var ch = '${param['chiose']}';
				if(ch=="yes"){
					$("#okbtn").val("移除");
				}
				if(ch=="no"){
					$("#okbtn").val("加入");
				}
				$("#okbtn").click(function(){
					  var flag= false;
					  var str ="";
					  var chk = document.getElementsByName("chk");
					  for(var i=0; i<chk.length;i++){
						  if(chk[i].checked==true){
							  str+=chk[i].value+",";
							  flag= true;
						  }
					  }
					  if(flag){
						  var mid = $("#mid").val();
						  if(ch=="no"){
							  //加入
							  $.post("Dfb_groupmerchant_mapServlet?op=add_mh&mid="+mid+"&groupIds="+str,function(data){
									top.table.location.reload(true);
							  });
						  }
						  if(ch=="yes"){
							  //移除
							 $.post("Dfb_groupmerchant_mapServlet?op=delete_mh&mid="+mid+"&groupIds="+str,function(data){
									top.table.location.reload(true);
							 });
						  }
					  }else{
						  alert("至少选择一条数据!");
					  }
				});
			});
		</script>
  </head>
  
  <body>
<div class="content-box">
	<c:import url="../Dfb_merchant_groupServlet?op=querybymid" />
	<div class="content-box-header" style="align: center">
		<h3 id="local">
			商家组管理<input type="hidden" id="mid" value="${param['mid']}">
		</h3>
	<select id="chiose" name="chiose" style="margin-top: 10px;">
		<option value="yes" selected="selected" ${param['chiose']=='yes'?'selected':''}>所在组</option>
		<option value="no" ${param['chiose']=='no'?'selected':''}>未在组</option>
	</select>
	<c:if test="${!empty pm.data}">
		<input type="button" value="移除" id="okbtn" class="button">
	</c:if>
	</div>
	<!-- End .content-box-header -->
	<div class="content-box-content" id="table_row">
		<div class="tab-content default-tab" id="tab1">
			<!-- This is the target div. id must match the href of this div's tab -->
	<table id="table">
		<thead>
			<tr>
				<th><input type="checkbox" id="chkAll"/>全选/反选</th>
				<th>ID</th>
				<th>名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pm.data}" var="group">
				<tr>
					<td width="30%" align="center"><input type="checkbox" value="${group.id}" name="chk"/></td>
					<td width="30%" align="center">${group.id}</td>
					<td width="30%" align="center"><a href="admin/groupDetail_table.jsp?groupId=${group.id}">${group.name}</a></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="3">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/merchant_set_group_table.jsp" pm="${pm}" vague="&mid=${param['mid']}&chiose=${param['chiose']}"></cc:page>
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
