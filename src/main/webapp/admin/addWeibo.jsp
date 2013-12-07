<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addWeibo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

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
		var content = $("#content").val();
		var up = $("#up").val();
		var down = $("#down").val();
		var share = $("#share").val();
		var uid = $("#uid").val();
		var photo = $("#photo").val();
		if(photo!=""){
			var str = photo.substring(photo.lastIndexOf("."));
			if(".jpg"!=str.toLowerCase() && ".png"!=str.toLowerCase() && ".gif"!=str.toLowerCase()){
				$("#msg").html("你上传的图片格式有误!");
				return false;
			}
		}
		if(content=="" || up=="" || down=="" || share=="" ||uid==""){
			$("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
			return false;
		}
		$("#msg").html("添加微博成功!");
		return true;
	}
	function queryUserNameById(obj){
		var uid = obj.value;
		if(isNaN(uid)){
			$("#username").html("用户ID格式有误!");
			return;
		}
		if(uid=="" ){
			$("#username").html("用户ID不能为空!");
		}else{
			$.post("UsersServlet?op=queryNameById&uid="+uid,function(data){
				if(data==null || data==""){
					$("#username").html("此用户ID不存在!");
				}else{
					$("#username").html(data);
				}
			});
		}
	}
</script>

</head>

<body>

	<div id="messages" >
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			添加微博
		</h3>
	<form action="WeibosServlet?op=add" method="post" enctype="multipart/form-data" target="table" onSubmit="return checkSubmit();">
		<table class="lie_center">
			<tr>
				<td width="48"><label for="content">内容</label></td>
			</tr>
			<tr>
				<td colspan="3">
			<textarea name="content" id="content" class="text_field" placeholder="请输入您要发布的内容" cols="50" rows="8"></textarea>
			</td>
			</tr>
			<tr>
				<td><label for="up">顶</label></td>
				<td width="221"><input type="text" id="up" value="0" name="up" /></td>
			    <td width="226">&nbsp;</td>
			</tr>
			<tr>
				<td><label for="down">踩</label></td>
				<td><input type="text" id="down" value="0" name="down"/></td>
			    <td>&nbsp;</td>
			</tr>
			<tr>
				<td><label for="share">分享</label></td>
				<td><input type="text" id="share" value="0" name="share" /></td>
			    <td>&nbsp;</td>
			</tr>
			<tr>
				<td><label for="uid">用户ID</label></td>
				<td><input type="text"id="uid" value="${userInfo.ID}" style="width: 80px;" onKeyUp="queryUserNameById(this);" name="uid"/>
					<span id="username">${userInfo.username}</span>				</td>
			    <td>&nbsp;</td>
			</tr>
			<tr>
			  <td width="70"><label for="photo">上传图片</label></td>
			  <td colspan="2"><input type="file" name="photo" id="photo"></td>
		  </tr>
			<tr>
				<td style="text-align:center;">
					<input type="submit" class="button" value="添加微博" >				
				</td>
				<td colspan="2" align="center"><span id="msg" style="color: red"></span></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>
