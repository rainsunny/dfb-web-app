<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
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

<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
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
			var up = $("#up").val();
			var down = $("#down").val();
			var share = $("#share").val();
			if(content=="" || up=="" || down=="" || share=="" ||uid==""){
				$("#msg").html("请完善相关信息后在进行修改操作,谢谢!");
				return;
			}
			
			$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "WeibosServlet",
			   data: "op=updateById&content="+content+"&up="+up+"&down="+down+"&share="+share+"&id="+id+"&uid="+uid,
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

</head>

<body>
	
	<div id="messages" style="width: 700px;">
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			微博评论回复
		</h3>
		<table width="100%">
			<c:import url="../Dfb_weibo_replyServlet?op=queryById"></c:import>
			<tr>
				<td colspan="4" width="600"><label for="content">${reply.username} 评论  ${reply.content}</label></td>
			</tr>
			<c:import url="../WeibocomentreplylistInfoServlet?op=query"></c:import>
			<c:forEach items="${weibocomentList}" var="wbComent">
				<tr>
					<td>${wbComent.username_1}  回复     ${wbComent.id_2}:</td>
					<td><input type="text" value="${wbComent.replycontent}"></td>
					<td>${wbComent.replyTime}</td>
					<td><a href="#">修改</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
