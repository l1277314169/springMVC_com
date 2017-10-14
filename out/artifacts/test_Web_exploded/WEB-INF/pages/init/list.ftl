<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Channel Plus</title>
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/channel-style.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/dialog.css"/>
<script type="text/javascript" src="${contextPath}/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/pandora-dialog.js"></script>
<script type="text/javascript" src="${contextPath}/js/channel-dialog.js"></script>
</head>
<body>
<div style="padding-top: 50px;text-align: center;">
<button type="button" id="option" class="btn btn-success">刷新option数据</button>&nbsp;
<button type="button" id="definition" class="btn btn-success">刷新clientBusinessDefinition数据</button>
</div>
<div style="padding-top: 50px;text-align: center;">
key：<input type="text" id="memKey" name="memKey" value="" style="width: 250px;" />&nbsp;<button type="button" id="memBtn" class="btn btn-danger">清除memcached缓存</button>
</div>
<div id="info" style="padding-top: 50px;text-align: center;color: red;font-size: 20px;">
</div>
<script type="text/javascript">
	$(document).ready(function () {
		$("#memBtn").bind("click",function(){
			$("#info").html("");
			var memKey = $("#memKey").val();
			if(memKey ==null || memKey == ""){
				layer.alert("key 不能为空！");
				return false;
			}
		    $.confirm("确认清除memcached缓存？", function () {
		    	var resultCode; 
				$.ajax({
					url : "${contextPath}/sysInit/cleanMemcached/"+memKey,
					type : "post",
					dataType:'JSON',
					success : function(result) {
						 $("#info").html(result.message);
					},
				   	error: function(result) {
						$("#info").html("清除失败");
					}
				});
		   });
		   return false;
		});
		$("#option").bind("click",function(){
			$("#info").html("");
		    $.confirm("确认刷新option数据？", function () {
		    	var resultCode; 
				$.ajax({
					url : "${contextPath}/sysInit/updateOption/",
					type : "post",
					dataType:'JSON',
					success : function(result) {
						 $("#info").html(result.message);
					},
				   	error: function(result) {
						$("#info").html("更新失败");
					}
				});
		   });
		   return false;
		});
		
		$("#definition").bind("click",function(){
			$("#info").html("");
			$.confirm("确认刷新clientBusinessDefinition数据？", function () {
		    	var resultCode; 
				$.ajax({
					url : "${contextPath}/sysInit/updateDefinition/",
					type : "post",
					dataType:'JSON',
					success : function(result) {
						 $("#info").html(result.message);
					},
				   	error: function(result) {
						$("#info").html("更新失败");
					}
				});
		   });
		   return false;
		});
	})
</script>
</body>
</html>