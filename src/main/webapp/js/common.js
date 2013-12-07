$(function(){
	$("#close").click(function(){
		top.table.location.reload(true);
	});
	$("#chkAll").click(function(){
		
		var status = $(this).attr("checked");
		
		$("input[name='chk']").each(function(){
			
			$(this).attr("checked",status);
			
		});
	});
	
	$("input[name='chk']").click(function(){		
		check();		
	});
	
	function check(){		
		var flag = true;		
		$("input[name='chk']").each(function(){		
			if($(this).attr("checked")==false){			
				flag=false;				
			}
		});
		if(flag){			
			$("#chkAll").attr("checked",true);
		
		}else{		
			$("#chkAll").attr("checked",false);		
		}
	}
});
