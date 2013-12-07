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
    
    <title>My JSP 'addWeibo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("input:text").each(function(){
			$(this).focus(function(){
				$("#msg").html("");
			});		
		});
		$("#dosubmit").click(function(){
			var id = $("#id").val();
			var uid = $("#uid").val();
			var content = $("#content").val();
			if(content=="" ||uid==""){
				$("#msg").html("请完善相关信息后在进行修改操作,谢谢!");
				return;
			}
			
			$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "Dfb_merchant_commentServlet",
			   data: "op=updateById&content="+content+"&id="+id+"&uid="+uid,
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
	<c:import url="../Dfb_merchant_commentServlet?op=queryById"></c:import>
	<input type="hidden" value="${comment.id}" id="id">
	<div id="messages" >
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			修改微博
		</h3>
		<table>
			<tr>
				<td><label for="content">内容</label></td>
			</tr>
			<tr>
				<td colspan="2"><textarea id="content" rows="5" cols="50" placeholder="请输入您要发布的内容">${comment.content}</textarea></td>
			</tr>
			<tr>
				<td><label for="uid">用户ID</label></td>
				<td><input type="text" value="${comment.uid}" id="uid" onkeyup="queryUserNameById(this);"/><span id="username">${comment.username}</span></td>
			</tr>
			<tr>
				<td>
					<input type="button" class="button" value="修改微博"  id="dosubmit">
				</td>
				<td><span id="msg" style="color: red"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
