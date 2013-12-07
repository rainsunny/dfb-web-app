<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.doufangbian.util.ConstantUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
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
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>My JSP 'addUser.jsp' starting page</title>
    <link rel="stylesheet" href="admin/resources/css/showstyle.css"
			type="text/css" media="screen" />
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
			var username = $("#username").val();
			var password = $("#password").val();
			var phone = $("#phone").val();
			var qq = $("#qq").val();
			var cash = $("#cash").val();
			var photo = $("#photo").val();
			if(photo!=""){
					var str = photo.substring(photo.lastIndexOf("."));
					if(".jpg"!=str.toLowerCase() && ".png"!=str.toLowerCase() && ".gif"!=str.toLowerCase()){
						$("#msg").html("你上传的图片格式有误!");
						return false;
					}
				}
			if(username=="" || password=="" || phone =="" || qq=="" || cash==""){
				$("#msg").html("请完善相关信息后在进行修改操作,谢谢!");
				return false;
			}
			$("#msg").html("恭喜您修改用户信息成功!");
			$("input:text").each(function(){
				$(this).value="";
			});
			return true;
		}
		function checkName(obj,name){
			var username = obj.value;
			if(username!=name){
				$.ajax({
				   type: "POST",
				   contentType:"application/x-www-form-urlencoded;charset=utf-8",
				   url: "UsersServlet",
				   data: "op=checkName&username="+username,
				   success: function(data){
						if(data.indexOf("true")==-1){
							$("#checkName").html("用户名已经存在!");
						}else{
							$("#checkName").html("用户名可用!");
						}
				   }
				});
			}else{
				$("#checkName").html("");
			}
			
		}
	</script>
	<style type="text/css">
		#facebox{margin-top: -60px;}
	</style>
  </head>
  
  <body>
    <div id="messages" >
    <form action="UsersServlet?op=update" method="post" enctype="multipart/form-data" target="table" onSubmit="return checkSubmit();">
		<h3>
			修改用户信息
			<input type="hidden" name="uid" value="${user.id}">
			<input type="hidden" value="${param['pageNo']}" name="pageNo"/>
			<input type="hidden" value="&keywords=<%=keywords %>" name="vague"/>
		</h3>
		<c:import url="../UsersServlet?op=queryById"></c:import>
        <table width="527">
          <tr>
            <td width="86">用户名</td>
            <td width="177"><input type="text" name="username" id="username" value="${user.username}" onKeyUp="checkName(this,'${user.username}');" />
            </td>
            <td colspan="2" id="checkName">&nbsp;</td>
          </tr>
          <tr>
            <td> 密码 </td>
            <td><input type="text" name="password" id="password"  value="${user.password}"/>
            </td>
            <td colspan="2" rowspan="3"><img style="margin-left:20px;" width=100 height=100 src="<%=basePath %>/<%=ConstantUtil.USER_IMAGE %>/${user.headphoto}"></td>
          </tr>
          <tr>
            <td>QQ</td>
            <td><input type="text" name="qq" id="qq" value="${user.qq}"/>
            </td>
          </tr>
          <tr>
            <td> 性别 </td>
            <td><select name="gender" >
                <option value="1" ${user.gender==1?'selected':''}>男</option>
                <option value="0" ${user.gender==0?'selected':''}>女</option>
              </select>
                <input type="hidden" value="${user.headphoto}" name="photo"></td>
          </tr>
          <tr>
            <td> 电话 </td>
            <td><input type="text" name="phone" id="phone" value="${user.phone}"/>
            </td>
			<td width="69">
					新头像			</td>
				<td width="175">
			<input type="file" id="photo" name="a"  size="15"/>				</td>
          </tr>
          <tr>
            <td> 豆币 </td>
            <td><input type="text" name="cash" value="${user.cash}" id="cash"/>
            </td>
            <td>状态</td>
            <td><select name="status" >
                <option value="1" ${user.status==1?'selected':''}>正常</option>
                <option value="0" ${user.status==0?'selected':''}>禁用</option>
              </select>
            </td>
          </tr>
          <tr>
            <td><input name="submit" type="submit" class="button" value="确认修改">
            </td>
            <td id="msg">&nbsp;</td>
            <td colspan="2" id="msg">&nbsp;</td>
          </tr>
      </table>
    </form>
	</div>
  </body>
</html>
