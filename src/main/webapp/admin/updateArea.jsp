<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<link rel="stylesheet" href="admin/resources/css/showstyle.css"
type="text/css" media="screen" />
<script type="text/javascript" src="js/jquery-1.4.1.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	$(function(){
		$("#xian").focus(function(){
			$("#msg").html("");
		});
		$("#wapUrl").focus(function(){
			$("#msg").html("");
		});
		
		$("#updateMsg").click(function(){
			var xian = $("#xian").val();
			var wapUrl = $("#wapUrl").val();
			var countyID = $("#countyID").val();
			if(xian=="" || wapUrl==""){
				$("#msg").html("请完善相关信息后在进行修改操作,谢谢!");
				return;
			}
			$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "AreasServlet",
			   data: "op=check&xian="+xian,
			   success: function(data){
					if(data.indexOf("true")==-1 || $("#oldxian").val()==xian){
						$.ajax({
						   type: "POST",
						   contentType:"application/x-www-form-urlencoded;charset=utf-8",
						   url: "AreasServlet",
						   data: "op=update&xian="+xian+"&wapUrl="+wapUrl+"&countyID="+countyID,
						   success: function(data){
								if(data.indexOf("true")==-1){
									$("#msg").html("系统繁忙,请稍后再试...");
									
								}else{
									$("#msg").html("恭喜你修改信息成功!");		
								}
						   }
						});
						
					}else{
						$("#msg").html("您输入的县名称已存在,请仔细检查输入");		
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
			修改地区信息
		</h3>
		<table width="495" height="80">
			<tr>
				<td colspan="3" >
					<c:import url="../AreasServlet?op=queryById"></c:import>
					<c:import url="../ProvincialServlet?op=queryById">
						<c:param name="provincialID">${cy.provincialID}</c:param>
					</c:import>
					<c:import url="../CityInfoServlet?op=queryById">
						<c:param name="cityId">${cy.cityId}</c:param>
					</c:import>
					<span style="font-size: 14px;">${provin.name}>>${c.name}</span>
					<input type="hidden" value="${cy.countyID}" id="countyID"/>
					<input type="hidden" value="${cy.name}" id="oldxian"/>
					<input type="text" id="xian" style="width: 130px" class="input-medium" placeholder="请输入地区" name="xian" value="${cy.name}"/>
					<input type="text" id="wapUrl" style="width: 180px" class="input-medium" placeholder="请输入WapUrl地址" value="${cy.wapUrl}" />
				</td>
			</tr>
			<tr>
				<td><input type="button" value="确认修改" id="updateMsg" class="button"/></td>
				<td colspan="2" ><span id="msg" style="color: red"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
