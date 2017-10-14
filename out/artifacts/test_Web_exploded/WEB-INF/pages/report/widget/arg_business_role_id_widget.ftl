<!-- 人员角色为手机-业务代表和手机-业务代表主管的角色 -->
<input type="text" id="${fls.arg}" name="${fls.arg}" class="input-medium" dataRoles="${filterMap[fls.arg]}"/>
<script type="text/javascript">
jQuery(document).ready(function(e) {
	var businessRoleId = $("#${fls.arg}").attr("dataRoles");
	loadBusinessRole(businessRoleId);
});

function loadBusinessRole(businessRoleId){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientRoles/getRolesBusiness",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.roleId+"\",\"text\":\""+item.roleName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.roleId+"\",\"text\":\""+item.roleName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#${fls.arg}").select2({
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
			$("#${fls.arg}").select2("val", businessRoleId);
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};

</script>
