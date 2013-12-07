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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	$(function(){
		var lv = '${param['level']}';
		if(lv==1){
			$("#div_0").show();
			$("#div_1").hide();
			$("#div_2").hide();
		}
		if(lv==2){
			$("#div_0").show();
			$("#div_1").show();
			$("#div_2").hide();
		}
		if(lv==3){
			$("#div_0").show();
			$("#div_1").show();
			$("#div_2").show();
		}
		$(":radio").click(function(){
			var radio = $(":radio:checked").val();
			$("#msg").html("");
			if(radio==0){
				$("#div_0").show();
				$("#div_1").hide();
				$("#div_2").hide();
				//动态加载省
				provincial();
			}else if(radio==1){
				$("#div_0").show();
				$("#div_1").show();
				$("#div_2").hide();
				//动态加载省
				provincial();
				//动态加载市
				city();
			}else{
				$("#div_0").show();
				$("#div_1").show();
				$("#div_2").show();
				
			}
		});
		//动态加载省
		function provincial(){
			$.post("ProvincialServlet?op=sel",function(data){
				$("#search_group_provincial").html(data);
			});
		}
		//动态添加市
		function city(){
			var pid = $("#search_group_provincial").val();
			$.post("CityInfoServlet?op=sel&provincialID="+pid,function(data){
				$("#search_group_city").html(data);
			});
		}
		//动态添加县
		function area(){
			var cityId = $("#search_group_city").val();
			$.post("AreasServlet?op=sel&cityId="+cityId,function(data){
				$("#search_group_area").html(data);
			});
		}
		$("#search_group_provincial").change(function(){
			city();
		});
		$("#search_group_city").change(function(){
			area();
		});
		//提交数据
		$("#sougle").click(function(){
			var id = $("#id").val();
			var radio = $(":radio:checked").val();
			var username = $("#username").val();
			var password = $("#password").val();
			var areaId = 0;
			var level = 0;
			if(radio==0){
				level = 1;
				areaId = $("#search_group_provincial").val();
			}else if(radio==1){
				level = 2;
				areaId = $("#search_group_city").val();
			}else if(radio==2){
				level = 3;
				areaId = $("#search_group_area").val();
			}
			
			if(areaId==0 || username=="" || password==""){
				$("#msg").html("请完善相关信息后在进行相关操作!");
				return;
			}
			$.ajax({
			   type: "POST",
			   contentType:"application/x-www-form-urlencoded;charset=utf-8",
			   url: "AdminLoginServlet",
			   data: "op=checkName&username="+username,
			   success: function(data){
					if(data.indexOf("true")==-1 || username==$("#uname").val()){
						$.ajax({
			 			   type: "POST",
						   contentType:"application/x-www-form-urlencoded;charset=utf-8",
						   url: "AdminLoginServlet",
						   data: "op=update&username="+username+"&password="+password+"&areaId="+areaId+"&level="+level+"&id="+id,
						   success: function(data){
								if(data.indexOf("true")==-1){
									$("#msg").html("系统繁忙,请稍后再试...");
									
								}else{
									$("#msg").html("恭喜你修改管理员信息成功!");		
								}
						   }
						});
					}else{
						$("#msg").html("您添加的管理员名称已存在,请仔细检查输入");
					}
			   }
			});
			
		});
			
			
	});
</script>
<style type="text/css">
	tr{line-height: 30px;}
</style>
</head>

<body>
	<c:import url="../AdminLoginServlet?op=queryById"></c:import>
	<div id="messages" >
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			添加地区
		</h3>
		<table width="495" height="138">
			<tr>
				<td width="33%" align="center"><input type="radio" value="0" name="radio" ${adminUser.level==1?'checked':''}/>添加省管理员</td>
				<td width="33%" align="center"><input type="radio" value="1" name="radio" ${adminUser.level==2?'checked':''}/>添加市管理员</td>
				<td width="33%" align="center"><input type="radio" value="2" name="radio" ${adminUser.level==3?'checked':''}/>添加县管理员</td>
			</tr>
			<tr >
				<td id="div_0">
					<c:import url="../ProvincialServlet?op=queryAll"></c:import>
					<select id="search_group_provincial">
						<option value="0" selected="selected">全国</option>
						<c:forEach items="${provList}" var="prov">
							<option value="${prov.provincialID}" ${prov.provincialID == adminUser.areaId?'selected':''}>${prov.name}</option>
						</c:forEach>
					</select>
				</td>
				<td id="div_1">	
					<c:import url="../CityInfoServlet?op=queryAll"></c:import>
					<select id="search_group_city">
						<option value="0" selected="selected">所有市</option>
						<c:forEach items="${cityList}" var="city">
							<option value="${city.cityID}" ${city.cityID == adminUser.areaId?'selected':''}>${city.name}</option>
						</c:forEach>
					</select>
				</td>
				<td id="div_2">
					<c:import url="../AreasServlet?op=queryAll"></c:import>
					<select id="search_group_area">
						<option value="0" selected="selected">所有县</option>
						<c:forEach items="${cyList}" var="cy">
							<option value="${cy.countyID}" ${cy.countyID == adminUser.areaId?'selected':''}>${cy.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>用户名</td>
				<td colspan="2">
					<input type="text" name="username" id="username" value="${adminUser.username}"/>
					<input type="hidden" id="uname" value="${adminUser.username}"/>
					<input type="hidden" id="id" value="${adminUser.ID}"/>
				</td>
			</tr>
			<tr>
				<td>密&nbsp;&nbsp;码</td>
				<td colspan="2"><input type="text" name="password" id="password" value="${adminUser.password}"/></td>
			</tr>
			<tr>
				<td><input class="button" type="button" value="修改管理员" id="sougle"/></td>
				<td colspan="2" ><span id="msg" style="color: red"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
