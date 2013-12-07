<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="pm" required="true"  type="com.doufangbian.entity.PageModel"%>
<%@ attribute name="url" required="true" type="java.lang.String" %>
<%@ attribute name="vague" required="false" type="java.lang.String" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
  
 	$(function(){
		var pm_sumPage = parseInt($("#pm_sumPage").val());
		if( pm_sumPage!= null){
			if('${param['pageNo']}'==""){
				a_click(1,pm_sumPage);
			}else{
				a_click(parseInt(${param['pageNo']}),pm_sumPage);				
			}
		}
	});
	
	/*a标签*/
	function a_click(pageNo,pm_sumPage){

		var n = pageNo;
		
		$("#show").html("");
		$("#show").html(n);
		
		var u = n + 4;
		if(u>pm_sumPage){
			u = pm_sumPage; 
		}
		if(n-5>0){
		
			$("#ashow").html("");
			
			for(var i=(n-5);i<=u;i++){
			
				$("#ashow").append("<a href='${url}?pageNo="+i+"${vague}&vague=${vague}' class='number' id='a_"+i+"'>"+i+"</a>");
				
				$("#a_"+n).attr("class","number current");
				
			}
			$("#ashow a").click(function(){
				a_click(parseInt($(this).html()),pm_sumPage);
			});	
			
		}else{
		
			$("#ashow").html("");
			var p=10;
			if(pm_sumPage<=10){
				p=pm_sumPage;
			}
			
			for(var i=1;i<=p;i++){
				$("#ashow").append("<a href='${url}?pageNo="+i+"${vague}&vague=${vague}' class='number' id='a_"+i+"'>"+i+"</a>");
				
				$("#a_"+n).attr("class","number current");
				
			}
			
			$("#ashow a").click(function(){
			
				a_click(parseInt($(this).html()),pm_sumPage);
				
			});	
			
		}
		
	}
	
	/*上一页*/
	function kbd(){
		var pm_sumPage = parseInt($("#pm_sumPage").val());
		var show = parseInt($("#show").html());
		var pageNo = show-6;
		if(pageNo<=0){
			pageNo =  pageNo+6;
		}
		to_go_href(pageNo);
	}
	/*下一页*/
	function dfn(){
		var pm_sumPage = parseInt($("#pm_sumPage").val());
		var show = parseInt($("#show").html());
		var pageNo = show+5;
		if(pageNo < 11){
			
			pageNo=11;
		}
		
		if(pageNo>pm_sumPage){
			return;
		}
		to_go_href(pageNo);
	}
	/*首页*/
	function frist(){
		var pm_sumPage = parseInt($("#pm_sumPage").val());
		to_go_href(1);
	}
	/*尾页*/
	function last(){
		var pm_sumPage = parseInt($("#pm_sumPage").val());
		to_go_href(pm_sumPage);
	}
	var pageNo =1;
	
	/*回车跳转*/
	function key_up(obj){
		var pm_sumPage = parseInt($("#pm_sumPage").val());
		if(event.keyCode>=48 && event.keyCode<=57 || event.keyCode==8 || event.keyCode>=96 && event.keyCode<=105){
			if(isNaN(obj.value)){
				obj.value = pageNo;
			}else{
				pageNo = obj.value;
				if(parseInt(pageNo)<1){
					obj.value = 1;
					pageNo = 1;
				}
				if(parseInt(pageNo)>pm_sumPage){
					obj.value = pm_sumPage;
					pageNo = pm_sumPage;
				}
			}
			
		}else{
			obj.value=pageNo; 
		}
	}
	function cg(){
		var pageNo = $("#TMD").val();
		to_go_href(pageNo);
	}
	/*页面跳转*/
	function to_go_href(pageNo){
		
		location.href="<%=basePath%>${url}?pageNo="+pageNo+'${vague}&vague=${vague}';	
		
	}
	
 </script>
 
<style type="text/css" >



.pagination {
                padding: 20px 0 5px 0;
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 10px;
                }
.pagination a {
                margin: 0 5px 0 0;
                padding: 3px 6px;
                }

.pagination a.number {
				border: 1px solid #ddd;
                }

.pagination a.current {
                background: #469400 url('admin/resources/images/bg-button-green.gif') top left repeat-x !important;
                border-color: #459300 !important;
                color: #fff !important;
                }
				
.pagination a.current:hover {
				text-decoration: underline;
                }
                
             
                
                
/*--Page End----------------------------------------*/
</style>


<input type="hidden"  value="${pm.sumPage}" id="pm_sumPage"/>

<var class="pagination" ><b>总条数：${pm.sumCount}</b><b>总页数：${pm.sumPage}</b> <b id="show" style="display: none;">0</b>
	<kbd class="hd"><a href="javascript:frist();">首页</a></kbd>
	<kbd class="disable"><a href="javascript:kbd();">上一页</a></kbd>
	<code id="ashow"></code>
	<dfn class="disable"><a href="javascript:dfn();" >下一页</a></dfn>
	<kbd class="hd"><a href="javascript:last();" >尾页</a></kbd>
	<input type="text" class="pageinput" title="输入数字" onkeyup="key_up(this);" value="${param['pageNo']==null?1:param['pageNo']}" size="3"  id="TMD"/>
	<input type="button" value="转" onclick="cg();" style="cursor: pointer;"class="button">
</var>