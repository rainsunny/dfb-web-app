<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cc" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String keywords = request.getParameter("keywords");
String key = request.getParameter("key");
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
    
    <title>My JSP 'show.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
	tr {
		line-height: 35px;
	}
	#table tbody tr td{
		padding-top:14px;
	}
	</style>
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
		<script type="text/javascript" src="js/common.js"></script>
		
		<script type="text/javascript">
		
		$(function(){
			$("#search_group_provincial").change(function(){
				if($(this).val()==0){
					$("#search_group_city").val("0");
					window.location.href="<%=basePath%>admin/merchant_table.jsp?catId="+$(this).val();
				}else{
					$("#search_group_city").val("0");
					window.location.href="<%=basePath%>admin/merchant_table.jsp?catId="+$(this).val()+"&groupId="+$("#search_group_city").val();
				}
			});
			
			$("#search_group_city").change(function(){
				window.location.href="<%=basePath%>admin/merchant_table.jsp?groupId="+$(this).val()+"&catId="+$("#search_group_provincial").val();
			});
		});
		function del(id){
			if(confirm("您确定要删除此评论吗?")==true){
				$.post("Dfb_merchant_commentServlet?op=delete&id="+id,function(data){
					if(data.indexOf("true")==-1){
						alert("系统繁忙,请稍后再试!");
					}else{
						alert("删除成功!");
						top.table.location.reload(true);
					}
				});
			}
		}
		function delAll(){
			var ids="";
			$("input[name='chk']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    ids += $(this).val()+","
                }
            })
			if(ids==""){
				alert("亲,至少选中一个在进行此操作!");
				return ;
			}
			if(confirm("您确定要删除当前选中的评论吗?")==true){
				$.post("Merchant_comment_allServlet?op=delAll&ids="+ids,function(data){
					if(data.indexOf("true")==-1){
						alert("系统繁忙,请稍后再试!");
					}else{
						alert("删除成功!");
						top.table.location.reload(true);
					}
				});
			}
		}
		</script>
  </head>
  
  <body>
<div class="content-box">
<!-- Start Content Box -->
<form action="admin/merchant_comment_all_table.jsp" method="post">
<div class="content-box-header" style="align: center">
	<h3 id="local">
		商家管理
	</h3>
	<select id="key" name="key">
		<option value="content" selected="selected" ${param['key']=='content'?'selected':''}>评论内容</option>
		<option value="name" ${param['key']=='name'?'selected':''}>商家名称</option>
		<option value="username" ${param['key']=='username'?'selected':''}>用户姓名</option>
	</select>
	<input type="text" id="keywords" class="input-medium" name="keywords"
		placeholder="请输入关键字" value="<%=keywords %>" />
	<input class="button" type="submit" value="搜索"/>
	<input class="button" type="button" value="删除评论" style="margin-left: 570px;" onclick="delAll()"/>
</div>
</form>
<!-- End .content-box-header -->
<div class="content-box-content" id="table_row">

	<div class="tab-content default-tab" id="tab1">
		<!-- This is the target div. id must match the href of this div's tab -->
		<table id="table">
		<thead>
			<tr>
			<c:import url="../Merchant_comment_allServlet?op=queryAll" />
				<th><input type="checkbox" id="chkAll"/></th>
				<th>ID</th>
				<th>评论内容</th>
				<th>商家</th>
				<th>用户名</th>
				<th>评论时间</th>
				<th>操作</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${pm.data}" var="comment">
				<tr>
					<td width="5%"><input type="checkbox" name="chk" value="${comment.id}"/></td>
					<td width="5%" align="center">${comment.id}</td>
					<td width="30%" align="center" title="${comment.content}">${fn:substring(comment.content, 0,16)}</td>
					<td width="20%" align="center" title="${comment.name}"><a href="admin/merchantDetail_table.jsp?merchantId=${comment.mid}">${comment.name}</a></td>
					<td width="15%" align="center" title="${comment.username}">${comment.username}</td>
					<td width="15%" align="center">${fn:substring(comment.time, 0,19)}</td>
					<td width="10%" align="right">
						<a href="javascript:del('${comment.id}')" title='删除'>删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td id="page_go" colspan="6">
					<c:if test="${!empty pm.data}">
						<cc:page  url="admin/merchant_comment_all_table.jsp" pm="${pm}" vague="&key=${key}&keywords=${keywords}"></cc:page>
					</c:if>
					<c:if test="${empty pm.data}">
						<span>对不起,系统没有找到<b>${param['keywords']}</b>相关数据!</span>
					</c:if>
				</td>
			</tr>
		</tfoot>
	</table>
		</div>
		<!-- End #tab1 -->
	</div>
	<!-- End .content-box-content -->
</div>
  </body>
</html>
