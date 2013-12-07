<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addCat.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#okbtn").click(function(){
				var groupId = $("#groupId").val();
				var name = $("#name").val();
				if(name==""){
					$("#msg").html("请完善相关信息后在进行修改操作,谢谢!");
					return;
				}
				$.ajax({
				   type: "POST",
				   contentType:"application/x-www-form-urlencoded;charset=utf-8",
				   url: "Dfb_merchant_groupServlet",
				   data: "op=update&name="+name+"&groupId="+groupId,
				   success: function(data){
						if(data.indexOf("true")==-1){
							$("#msg").html("系统繁忙,请稍后再试...");
							
						}else{
							$("#msg").html("恭喜你修改信息成功!");		
						}
				   }
				});
			});
		});
	</script>
	<link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
  </head>
  
  <body>
  <c:import url="../Dfb_merchant_groupServlet?op=queryById"></c:import>
    <table class="lie_center">
		<tr>
			<td>小组名称</td>
			<td><input type="text" id="name" value="${group.name }"/></td>
		</tr>
		<tr>
			<td>
				<input type="hidden" value="${group.id}" id="groupId">
				<input type="button" value="确认修改" class="button" id="okbtn"/>
			</td>
			<td><span id="msg" style="color: red;"></span></td>
		</tr>
	</table>

  </body>
</html>
