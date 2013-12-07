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
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#chiose").change(function(){
					var chiose = $(this).val();
					var catId = $("#catId").val();
					window.location.href="<%=basePath%>admin/catDetail_table.jsp?pageNo=1&chiose="+chiose+"&catId="+catId;
				});
				var ch = '${param['chiose']}';
				if(ch=="yes"){
					$("#okbtn").val("移除");
				}
				if(ch=="no"){
					$("#okbtn").val("加入");
				}
				if(ch=="" || ch==0){
					$("#okbtn").hide();
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
						  var catId = $("#catId").val();
						  if(ch=="no"){
						  //加入
							   $.post("Dfb_catgroup_mapServlet?op=add&groupIds="+str+"&catId="+catId,function(data){
									top.table.location.reload(true);
							  });
						  }
						  if(ch=="yes"){
							  //移除
							   $.post("Dfb_catgroup_mapServlet?op=delete&groupIds="+str+"&catId="+catId,function(data){
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
	<!-- Start Content Box -->
	<c:import url="../Dfb_merchant_catServlet?op=queryById"></c:import>
	<form action="admin/catDetail_table.jsp" method="post">
	<div class="content-box-header" style="align: center">
		<h3>
			大组管理>>${cat.name}
			<input type="hidden" value="${param['catId']}"  id="catId"  name="catId"/>
		</h3>
		<select id="chiose" name="chiose">
			<option selected="selected" value="0">请选择</option>
			<option value="yes" ${param['chiose']=='yes'?'selected':''}>已入组</option>
			<option value="no" ${param['chiose']=='no'?'selected':''}>未入组</option>
		</select>
		<input type="text" id="keywords" class="input-medium" name="keywords"
			placeholder="请输入小组名称" value="<%=keywords %>" />
		<input class="button" type="submit" value="搜索" id="sougle"/>
		<input type="button" id="okbtn" value="此按钮完成两个功能">
		<br />
	</div>
	</form>
	<!-- End .content-box-header -->
	<div class="content-box-content">
		<div class="tab-content default-tab">
	<table id="table">
		<thead>
			<tr>
			<c:import url="../Dfb_merchant_groupServlet?op=queryAll" />
				<th><input type="checkbox" id="chkAll"/>全选/反选</th>
				<th>编号</th>
				<th>小组名称</th>
				<th>管理</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${pm.data}" var="group">
				<tr>
					<td width="25%"><input type="checkbox" name="chk" value="${group.id}"/></td>
					<td width="25%" align="center">${group.id}</td>
					<td width="25%" align="center"><a href="admin/groupDetail_table.jsp?groupId=${group.id}">${group.name}</a></td>
					<td width="25%" align="center">
						<a href='admin/updataUser.jsp?groupId=${group.id}'  rel="modal" title='编辑'><img src='admin/resources/images/icons/pencil.png' /></a>
						<a href="javascript:del('${userinfo.id}')" title='删除'><img src='admin/resources/images/icons/cross.png' alt='Delete' /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="4">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/catDetail_table.jsp" pm="${pm}" vague="&catId=${param['catId']}&chiose=${param['chiose']}&keywords=${keywords}"></cc:page>
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
