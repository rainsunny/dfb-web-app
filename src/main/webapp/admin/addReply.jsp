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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	$(function(){
		$("input:text").each(function(){
			$(this).focus(function(){
				$("#msg").html("");
			});		
		});
		$("#dosubmit").click(function(){
			var content = $("#content").val();
			var uid = $("#uid").val();
			var wid = $("#wid").val();
			if(content=="" || uid==""){
				$("#msg").html("请完善相关信息后在进行评论操作,谢谢!");
				return;
			}
			
			$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "Dfb_weibo_replyServlet",
			   data: "op=add&content="+content+"&uid="+uid+"&wid="+wid,
			   success: function(data){
					if(data.indexOf("true")==-1){
						$("#msg").html("系统繁忙,请稍后再试...");
						
					}else{
						$("#msg").html("恭喜你评论微博成功!");		
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

	<div id="messages" >
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			微博评论<input type="hidden" id="wid" value="${param['wid']}"/>
		</h3>
		<table class="lie_center">
			<tr>
				<td colspan="3"><label for="content">内容</label></td>
			</tr>
			<tr>
				<td colspan="3"><textarea id="content" class="text_field" placeholder="请输入您要评论的内容"></textarea></td>
			</tr>
			<tr>
				<td><label for="uid">用户ID</label></td>
				<td><input type="text" id="uid" value="${userInfo.ID}" onkeyup="queryUserNameById(this);" /></td>
				<td id="username">${userInfo.username}</td>
			</tr>
			<tr>
				<td style="text-align:center;">
					<input type="button" class="btn btn-success" value="提交" id="dosubmit">
				</td>
				<td colspan="2"><span id="msg" style="color: red"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
