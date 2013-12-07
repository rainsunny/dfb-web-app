<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		function checkSubmit(){
			var name = $("#name").val();
				var photo = $("#photo").val();
				if(name==""){
					$("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
					return false;
				}
				if(photo!=""){
					var str = photo.substring(photo.lastIndexOf("."));
					if(".jpg"!=str.toLowerCase() && ".png"!=str.toLowerCase() && ".gif"!=str.toLowerCase()){
						$("#msg").html("你上传的图片格式有误!");
						return false;
					}
				}
			}
		}
	</script>

  </head>
  
  <body>
  <div id="messages" >
  	<form action="Dfb_merchant_catServlet?op=add" method="post" enctype="multipart/form-data" target="table" onsubmit="return checkSubmit();">
    <table class="lie_center">
		<tr>
			<td><label for="name">大组名称:</label></td>
			<td><input type="text" id="name" name="name"/></td>
		</tr>
		<tr>
			<td><label for="name">大组图标:</label></td>
			<td><input type="file" id="photo" name="photo"/></td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="添加大组" id="okbtn" class="button"/>
			</td>
			<td align="center"><span id="msg"></span></td>
		</tr>
	</table>
</form>
</div>
  </body>
</html>
