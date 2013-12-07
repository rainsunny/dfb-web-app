<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.doufangbian.util.ConstantUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<script type="text/javascript">
		function del(id){
			if(confirm("您确定要删除此照片吗?")==true){
				var pageNo = '${param['pageNo']}';
				var mid= $("#mid").val();
				$.post("Dfb_merchant_imageServlet?op=deleteById&id="+id+"&pageNo="+pageNo+"&mid="+mid,function(data){
					window.location.href="admin/merchantImage_table.jsp?mid="+mid+"&pageNo="+pageNo;
				});
			}
		}
		function checkSubmit(){
			var photo = $("#image").val();
			if(photo==""){
				return false;
			}
			if(photo!=""){
				var str = photo.substring(photo.lastIndexOf("."));
				if(".jpg"!=str.toLowerCase() && ".png"!=str.toLowerCase() && ".gif"!=str.toLowerCase()){
					$("#msg").html("你上传的图片格式有误!");
					return false;
				}
			}
			$("#msg").html("图片正在上传,请稍候......(由于图片过大,上传比较慢,请谅解!)");
			return true;
		}
	</script>
  </head>
  <body>
<div class="content-box">
<!-- Start Content Box -->
<div class="content-box-header" style="align: center">
	<h3>
	商家图片管理
	</h3>
</div>
<c:import url="../Dfb_merchant_imageServlet?op=query"></c:import>
<div class="content-box-content">
	<div class="tab-content default-tab">
	<table width="100%">
			<tr>
				<td>
					<form action="Dfb_merchant_imageServlet?op=add" method="post" enctype="multipart/form-data" onsubmit="return checkSubmit();">
						<input type="hidden" value="${param['mid']}" name="mid" id="mid">
						<input type="file" name="image" id="image">
						<input type="submit" value="上传图片" class="button" id="button">
						<span id="msg"></span>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<ul>
						<c:forEach items="${pm.data}" var="image">
							<li class="span4">
								<div style="float: left; color: red;margin:5px; margin-top: 30px;">
									<img src="<%=basePath%>/<%=ConstantUtil.MERCHANT_IMAGE %>/${image.image}" style="width: 160px;height: 175px; "/>
									<div style="margin-top: -18px;" ><a href="javascript:del('${image.id}');">删除</a></div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<td id="page_go" style="text-align: center;padding-top: 10px;">
					<c:if test="${!empty pm.data}">
						<cc:page url="admin/merchantImage_table.jsp" pm="${pm}" vague="&mid=${param['mid']}"/>
					</c:if>
					<c:if test="${empty pm.data}">
						暂无图片，赶快上传吧!
					</c:if>
				</td>
				
			</tr>
	</table>
	</div>
		
	</div>
	<!-- End .content-box-content -->
</div>
  </body>
</html>
