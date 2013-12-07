<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<base href="<%=basePath%>" />
<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
<script type="text/javascript">
	$(function(){
		$("#password").focus(function(){
			$("#msg").html("");
		});
		$("#btn").click(function(){
			var password = $("#password").val();
		if(password==""){
			$("#msg").html("密码不能为空....");
			return;
		}
		$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "AdminLoginServlet",
			   data: "op=updatePassword&newPassword="+password,
			   success: function(data){
					if(data.indexOf("true")==-1){
						$("#msg").html("系统繁忙,请稍后再试...");
						
					}else{
						alert("修改密码成功!");
						window.top.location.href="<%=basePath%>admin/login.jsp";
					}
			   }
			});
		});
	});
</script>

</head>

<body>

	<div id="messages" >
		<h3>
			修改密码
		</h3>
		<table>
			<tr>
				<td>新密码</td>
				<td>
					<input type="text" id="password"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="修改密码" id="btn" class="button" />
				</td>
				<td align="center"><span id="msg" style="color: red;"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
