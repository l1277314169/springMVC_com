<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>EHR同步</title>
</head>
<body>
	<div>
	<div class="widget-content nopadding">
		<form class="form-horizontal" action="#" id="searchForm">
			<div class="form-actions">
				<button type="button" id="new_btn" class="btn btn-success">EHR同步</button>
				<p id="info" style="margin-top:10px;color:red;"></p>
			</div>
		</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#new_btn").click(function(){
			jQuery.ajax({
			  url: '${contextPath}/clientUser/synchroEHR',
			  type: 'POST',
			  dataType: 'json',
			  success: function(data, textStatus, xhr) {
			  	$("#info").empty();
			  	$("#info").html(data.attributes.time);
			  },
			  error: function(xhr, textStatus, errorThrown) {
			    layer.alert("系统异常，同步失败");
			  }
			});
		})
	});
</script>
</html>