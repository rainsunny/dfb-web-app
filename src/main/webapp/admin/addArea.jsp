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
		$("#div_0").show();
		$("#div_1").hide();
		$("#div_2").hide();
		$("#provinaial").focus(function(){
			$("#msg").html("");
		});
		$("#city").focus(function(){
			$("#msg").html("");
		});
		$("#xian").focus(function(){
			$("#msg").html("");
		});
		$("#wapUrl").focus(function(){
			$("#msg").html("");
		});
		$(":radio").click(function(){
			var radio = $(":radio:checked").val();
			$("#msg").html("");
			if(radio==0){
				$("#div_0").show();
				$("#div_1").hide();
				$("#div_2").hide();
				
			}else if(radio==1){
				$("#div_0").hide();
				$("#div_1").show();
				$("#div_2").hide();
				//动态加载省
				provincial();
			}else{
				$("#div_0").hide();
				$("#div_1").hide();
				$("#div_2").show();
				//动态加载省
				provincial();
				//动态加载市
				city();
			}
		});
		//动态加载省
		function provincial(){
			$.post("ProvincialServlet?op=sel",function(data){
				$("#search_group_provincial").html(data);
				$("#search_group_provincia").html(data);
			});
		}
		//动态添加市
		function city(){
			var pid = $("#search_group_provincial").val();
			$.post("CityInfoServlet?op=sel&provincialID="+pid,function(data){
				$("#search_group_city").html(data);
			});
		}
		$("#search_group_provincial").change(function(){
			city();
			
		});
		//提交
		$("#sougle").click(function(){
			var radio = $(":radio:checked").val();
			if(radio==0){
				var provinaial = $("#provinaial").val();
				if(provinaial==""){
					$("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
					return;
				}
				
				$.ajax({
				   type: "POST",
				   contentType:"application/x-www-form-urlencoded;charset=utf-8",
				   url: "ProvincialServlet",
				   data: "op=check&provinaial="+provinaial,
				   success: function(data){
						if(data.indexOf("true")==-1){
							$.ajax({
				 			   type: "POST",
							   contentType:"application/x-www-form-urlencoded;charset=utf-8",
							   url: "ProvincialServlet",
							   data: "op=add&provinaial="+provinaial,
							   success: function(data){
									if(data.indexOf("true")==-1){
										$("#msg").html("系统繁忙,请稍后再试...");
										
									}else{
										$("#msg").html("恭喜你添加信息成功!");		
									}
							   }
							});
						}else{
							$("#msg").html("您添加的省名称已存在,请仔细检查输入");
						}
				   }
				});
			}else if(radio==1){
				var provincial = $("#search_group_provincia").val();
				var city = $("#city").val();
				if(provincial==0){
					$("#msg").html("请选择省!");
					return;
				}
				if(city==""){
					$("#msg").html("请完善相关信息后在进行添加操作,谢谢!");	
					return;
				}
				$.ajax({
				   type: "POST",
				   contentType:"application/x-www-form-urlencoded;charset=utf-8",
				   url: "CityInfoServlet",
				   data: "op=check&city="+city,
				   success: function(data){
						if(data.indexOf("true")==-1){
							$.ajax({
							   type: "POST",
							   contentType:"application/x-www-form-urlencoded;charset=utf-8",
							   url: "CityInfoServlet",
							   data: "op=add&provincial="+provincial+"&city="+city,
							   success: function(data){
									if(data.indexOf("true")==-1){
										$("#msg").html("系统繁忙,请稍后再试...");
										
									}else{
										$("#msg").html("恭喜你添加信息成功!");		
									}
							   }
							});
							
						}else{
							$("#msg").html("您添加的市名称已存在,请仔细检查输入");		
						}
				   }
				});
			}else{
				var provincial = $("#search_group_provincial").val();
				var city = $("#search_group_city").val();
				var xian = $("#xian").val();
				var wapUrl = $("#wapUrl").val();				
				if(provincial==0){
					$("#msg").html("请选择省!");
					return;
				}
				if(city==0){
					$("#msg").html("请选择市!");
					return;
				}
				if(xian=="" || wapUrl==""){
					$("#msg").html("请完善相关信息后在进行添加操作,谢谢!");
					return;
				}
				$.ajax({
				   type: "POST",
				   contentType:"application/x-www-form-urlencoded;charset=utf-8",
				   url: "AreasServlet",
				   data: "op=check&xian="+xian,
				   success: function(data){
						if(data.indexOf("true")==-1){
							$.ajax({
							   type: "POST",
							   contentType:"application/x-www-form-urlencoded;charset=utf-8",
							   url: "AreasServlet",
							   data: "op=add&provincial="+provincial+"&city="+city+"&xian="+xian+"&wapUrl="+wapUrl,
							   success: function(data){
									if(data.indexOf("true")==-1){
										$("#msg").html("系统繁忙,请稍后再试...");
										
									}else{
										$("#msg").html("恭喜你添加信息成功!");		
									}
							   }
							});
							
						}else{
							$("#msg").html("您添加的县名称已存在,请仔细检查输入");		
						}
				   }
				});
			}
		});
	});
</script>

</head>

<body>

	<div id="messages" >
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>
			添加地区
		</h3>
		<table width="495" height="138">
			<tr>
				<td width="33%" align="center"><input type="radio" value="0" name="radio" checked="checked"/>添加省</td>
				<td width="33%" align="center"><input type="radio" value="1" name="radio"/>添加市</td>
				<td width="33%" align="center"><input type="radio" value="2" name="radio"/>添加县</td>
			</tr>
			<tr id="div_0">
				<td colspan="3" >
					<input type="text" id="provinaial" style="width: 300px;" class="input-medium" placeholder="请输入您要添加的省" />
				</td>
			</tr>
			<tr  id="div_1">
				<td colspan="3">
					<select id="search_group_provincia">
						<option value="0" selected="selected">全国</option>
					</select>
					<input type="text" id="city" style="width: 300px" class="input-medium" placeholder="请输入您要添加的市 " />
				</td>
			</tr>
			<tr id="div_2">
				<td colspan="3" >
					<select id="search_group_provincial">
						<option value="0" selected="selected">全国</option>
					</select>
					<select id="search_group_city">
						<option value="0" selected="selected">所有市</option>
					</select>
					<input type="text" id="xian" style="width: 130px" class="input-medium" placeholder="请输入地区" />
					<input type="text" id="wapUrl" style="width: 180px" class="input-medium" placeholder="请输入WapUrl地址" />
				</td>
			</tr>
			<tr>
				<td><input class="button" type="button" value="添加地区" id="sougle"/></td>
				<td colspan="2" ><span id="msg" style="color: red"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
