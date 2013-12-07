<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.doufangbian.util.ConstantUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String keywords = request.getParameter("keywords");
if(keywords!=null && keywords!="" && "get".equalsIgnoreCase(request.getMethod())){
	keywords = new String(keywords.getBytes("iso-8859-1"),"utf-8");
}
if(keywords==null){
	keywords="";
}
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


<link rel="stylesheet" href="admin/resources/css/showstyle.css" type="text/css" media="screen" />
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
		var merchantId = $("#merchantId").val();
		var name = $("#name").val();
		var major = $("#major").val();
		var address = $("#address").val();
		var phone = $("#phone").val();
		var introduction = $("#introduction").val();
		var news = $("#news").val();
		var up = $("#up").val();
		var status = $("#status").val();
		
		if(name=="" || major=="" || address=="" || phone=="" ||introduction=="" || news ==" || up=="" || status==""){
			$("#msg").html("请完善相关信息后在进行修改操作,谢谢!");
			return false;
		}
		return true;
	}
</script>
<style type="text/css">
	#facebox{margin-top: -60px;}
	#cctbale tr{line-height: 13px;}
</style>
</head>

<body>
	<c:import url="../MerchantsServlet?op=queryById"></c:import>
	<div>
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<form action="MerchantsServlet?op=update" method="post" enctype="multipart/form-data" target="table" onSubmit="return checkSubmit();">
		<h3>
			修改商家
			<input type="hidden" value="${merchant.id}" name="merchantId" id="merchantId"/>
			<input type="hidden" value="${param['pageNo']}" name="pageNo"/>
			<input type="hidden" value="&catId=${param['catId']}&groupId=${param['groupId']}&keywords=<%=keywords %>" name="vague"/>
		</h3>
		<table id="cctable">
			<tr>
			  <td width="81">名称</td>
			  <td colspan="2"><input type="text" name="name"  id="name"  value="${merchant.name}"/></td>
			    <td width="150" rowspan="4"><img src="<%=basePath %>/<%=ConstantUtil.MERCHANT_IMAGE %>/${merchant.log}" style="width: 150px; height: 125px;"/></td>
			</tr>
			<tr><td >主营</td>
				<td colspan="2" ><input name="major" type="text" id="major" value="${merchant.major}" /></td>
		    </tr>
			<tr>
				<td>地址</td>
				<td colspan="2"><input type="text" name="address" id="address" value="${merchant.address}"/></td>
		    </tr>
			<tr>
				<td>电话</td>
				<td colspan="2"><input type="text" name="phone" id="phone" value="${merchant.phone}"/></td>
		    </tr>
			<tr>
			  <td width="81">新商家logo</td>
			  <td colspan="3">
			  	<input type="hidden" name="photo" value="${merchant.log}"/>
		  	  <input type="file" name="p" id="photo"/>			  </td>
		  </tr>
			<tr>
				<td valign="top">商家简介</td>
				<td colspan="3"><textarea name="introduction" rows="3" style="width:300px;" id="introduction">${merchant.introduction}</textarea></td>
			</tr>
			<tr>
				<td valign="top">最新动态</td>
				<td colspan="3"><textarea name="news" rows="3" style="width:300px;" id="news">${merchant.news}</textarea></td>
			</tr>
			<tr><td>商家类别</td>
				<td width="98">		<select name="status" id="status">
						<option value="0" ${merchant.status==0?'selected':''}>普通用户</option>
						<option value="1" ${merchant.status==1?'selected':''}>付费用户</option>
			  </select>		</td>
		      <td width="78"><input type="checkbox" name="isrecommend" value="1" ${merchant.isrecommend==1?'checked':''} id="isrecommend">推荐</td>
			    <td>&nbsp;
                  	顶&nbsp;
                  <input type="text" name="up" value="${merchant.up}"  style="width:40px;" id="up"/>                  </td>
			</tr>
			
			<tr>
				<td>
					<input type="submit" value="修改商家" class="button" id="dosubmit">				</td>
				<td colspan="3"><span id="msg" style="color: red"></span></td>
			</tr>
	  </table>
	  </form>
	</div>
</body>
</html>
