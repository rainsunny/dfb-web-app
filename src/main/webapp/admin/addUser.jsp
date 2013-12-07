<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
    <title>My JSP 'addUser.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
	<script type="text/javascript">
		$(function(){
			$("input:text").each(function(){
				$(this).focus(function(){
					$("#msg").html("");
				});		
			});
		});
		function checkSubmit(){
			var username = $("#username").val();
			var password = $("#password").val();
			var phone = $("#phone").val();
			var qq = $("#qq").val();
			var cash = $("#cash").val();
			var photo = $("#photo").val();
			if(photo!=""){
				var str = photo.substring(photo.lastIndexOf("."));
				if(".jpg"!=str.toLowerCase() && ".png"!=str.toLowerCase() && ".gif"!=str.toLowerCase()){
					$("#msg").html("你上传的图片格式有误!");
					return false;
				}
			}
			if(username=="" || password=="" || phone =="" || qq=="" || cash==""){
				$("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
				return false;
			}
			$("#msg").html("恭喜您添加用户信息成功!");
			$("input:text").each(function(){
				$(this).value="";
			});
			return true;
		}
		function checkName(obj){
			var username = obj.value;
			$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "UsersServlet",
			   data: "op=checkName&username="+username,
			   success: function(data){
					if(data.indexOf("true")==-1){
						$("#checkName").html("用户名已经存在!");
					}else{
						$("#checkName").html("用户名可用!");
					}
			   }
			});
		}
	</script>
  </head>
  
  <body>
    <div id="messages" >
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			添加用户
		</h3>
		<form action="UsersServlet?op=add" method="post" enctype="multipart/form-data" target="table" onsubmit="return checkSubmit();">
		<table  class="lie_center">
			<tr>
				<td>
					<label for="username">用户名</label>
				</td>
				<td>
					<input type="text" name="username" id="username" onkeyup="checkName(this);" />
					<span id="checkName"></span>
				</td>
			</tr>
			<tr>
				<td>
					<label for="password">密码</label>
				</td>
				<td>
					<input type="text" name="password"  id="password"/>
				</td>
			</tr>
			<tr>
				<td>
					<label for="password">头像</label>
				</td>
				<td>
					<input type="file" name="photo" id="photo"/>
				</td>
			</tr>
			<tr>
				<td>
					<label for="gender">性别</label>
				</td>
				<td>
					<select name="gender" >
						<option value="1" selected="selected">男</option>
						<option value="0">女</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label for="phone">电话</label>
				</td>
				<td>
					<input type="text" name="phone" id="phone"/>
				</td>
			</tr>
			<tr>
				<td>
					<label for="qq">QQ</label>
				</td>
				<td>
					<input type="text" name="qq" id="qq"/>
				</td>
			</tr>
			<tr>
				<td>
					<label for="cash">豆币</label>
				</td>
				<td>
					<input type="text" name="cash" value="0" id="cash"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" class="button" value="添加用户">
				</td>
				<td id="msg">&nbsp;</td>
			</tr>
		</table>
		</form>
	</div>
  </body>
</html>
