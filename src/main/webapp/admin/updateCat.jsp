<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.doufangbian.util.ConstantUtil"%>
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
	<link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
  </head>
  
  <body>
  <c:import url="../Dfb_merchant_catServlet?op=queryById"></c:import>
   <form action="Dfb_merchant_catServlet?op=update" method="post" enctype="multipart/form-data" target="table" onSubmit="return checkSubmit();">
    <table class="lie_center">
		<tr>
		  <td width="84">大组名称</td>
		  <td width="235"><input type="text" id="name" value="${cat.name }" name="name"/></td>
		  <td width="66" rowspan="2"><img width=50 height=50 src="<%=basePath %>/<%=ConstantUtil.CAT_IMAGE %>/${cat.cat_image}"></img>	</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" value="${cat.cat_image}" name="photo">
				新图标</td>
			<td><input type="file" name="a" id="photo"/></td>
	    </tr>
		<tr>
			<td>
				<input type="hidden" value="${cat.id}" name="catId" id="catId">
				<input type="submit" value="确认修改" class="button" id="okbtn"/>			</td>
			<td colspan="2"><span id="msg"></span></td>
		</tr>
	</table>
</form>
  </body>
</html>
