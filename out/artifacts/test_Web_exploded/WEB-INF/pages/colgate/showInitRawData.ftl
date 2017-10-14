<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Colgate In-Store  Tracking</title> 
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" /> 
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
<script type="text/javascript">
	$(document).ready(function() {
		$("#initBut").click(function(){
			var _url = "/colgate/initRawData";
			$('#initBut').attr("disabled","disabled");
			$("#alertMsg").html("数据正在生成，请耐心等待！");
			jQuery.ajax({
				url : _url,
				type : 'POST',
				timeout : 600000, //超时时间设置，单位毫秒
				dataType : 'json',
				cache : false,
				data : $("#form1").serialize(),
				success : function(data, textStatus, xhr) {
					$("#alertMsg").html(data.message);
					$('#initBut').removeAttr("disabled");
				},
				error : function(xhr, textStatus, errorThrown) {
					$("#alertMsg").html("数据生成失败");
					$('#initBut').removeAttr("disabled");
				}
			});
		})
	});
</script>
<body>
<form id="form1" name="form1" method="post" style="margin-top:20px;margin-left:20px;">
	月份：<input type="hidden" id="month" name="month" value="201401" />
	<#assign month="month">
	<#include "/widgets/month_widget.ftl" />
	<input type="button" id="initBut" name="initBut" value="确认生成数据" />
	<br/><font id="alertMsg" color="red"></font>
</form>
</body>
</html>