<html>
<head>
<#include "/common/head.ftl" />
<title>选择角色</title>
<#include "/common/foot.ftl" />
</head>
<body>
<div>
	<div class="widget-content nopadding">
		<table class="table table-bordered data-table" id="c_list">
			<tr>
				<th class="th_checkbox">选择</th>
				<th width="200px">角色名称</th>
			</tr>
			<tbody>
				<#list clientRoles as clientRole>
					<tr>
						<td ><input name="chkItem" type="checkbox" class="checkboxRole" value="${clientRole.id!''}" data="${clientRole.roleName!''}"/></td>
						<td title=${clientRole.name!''}>${clientRole.name!''}</td>
					</tr>
				</#list>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript" language="javascript">
	$(document).ready(function () {
		//初始化选中框
		var roleObj = $(window.parent.document).find("#rolesParameterDatas");
		var parameterRoleCount = $(window.parent.document).find("#parameterRoleCount");
		var roleIds = roleObj.val();
		var array = roleIds.split(',');
		$("#checkCount").html(array.length);
		roleObj.html(array.length);
		
        $("[name = chkItem]:checkbox").each(function () {
        	for (var index in array) {
                if($(this).val() == array[index] && array[index]!='') {
                    $(this).attr("checked", "checked");
                    break;
                }
            }
     	});
		
		$(".checkboxRole").click(function(){
			//alert($(this).prop("checked"));
			roleIds = roleObj.val();
			if($(this).prop("checked")){
				roleIds += ","+$(this).val();
				roleObj.attr("value",roleIds);
				array = roleIds.split(',');
				$("#checkCount").html(array.length-1);
				parameterRoleCount.html(array.length-1);
			}
			if(!$(this).prop("checked")){
				var roleId = $(this).val();
				roleIds = roleObj.val();
				array = roleIds.split(',');
				array.splice($.inArray(roleIds,array),1);
				roleIds = array.join(",");
				roleObj.attr("value",roleIds);
				$("#checkCount").html(array.length);
				parameterRoleCount.html(array.length);
			}
		});
	});
</script>
</html>