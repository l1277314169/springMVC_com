<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<body class="main_body">
         <#assign privCode="C3M1"> 
		 <#include "/base.ftl" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">系统设置</a>
					<a class="linkPage active" href="${contextPath}/mobileVersion/query">手机版本管理</a>
				</div>
		 	</div>
		<form id="dataForm" action="#" class="form-horizontal" method="post" class="main_body">
		<input type="hidden" name="clientId" value="${clientId}">
		<input type="hidden" name="mobileVersionId" value="${mobileVersion.mobileVersionId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label"><nobr><i class="cc1">*</i>APP版本号：</nobr></td>
                    <td class="td_control"><input type="text" name="version" value="${mobileVersion.version}" required=true></td>
                </tr>
                <tr>
                	<td class="td_label"><i class="cc1">*</i>APP类型：</td>
                    <td class="td_control">
	                    <select id="superior" name="appCode"  required=true value="${mobileVersion.appCode}">
	                    <option value="">--请选择--</option>
			                    <#list appType as option>
				                    <#if mobileVersion.appCode == option.getKey()>
			        					<option value="${option.getKey()}" selected="true">${option.getCnName()}</option>
			        				<#else>
			        					<option value="${option.getKey()}" >${option.getCnName()}</option>
			        				</#if>
		        				</#list>	
	                    </select>
                    </td>
                </tr>
                 <tr>
                   <td  class="td_label" >生效时间：</td>
                    <td class="td_control">
	                   <input type="text" id="enableDate" name="enableDate" class="startDatePicker"  <#if (mobileVersion.enableDate)??> value=" ${(mobileVersion.enableDate)?string("yyyy-MM-dd HH:mm:ss")}"</#if> readonly>
                    </td> 
                    <td><span>示例 :2015-01-01 00:00:00</span></td>
                </tr>
                <tr>
                 	<td class="td_label" ><i class="cc1">*</i>连接地址：</td>
                    <td colspan="3" class="td_control">
	                    <input type="text"  name="appLink" value="${mobileVersion.appLink}" style="width:450px" required=true>
                    </td> 
                </tr>
                <tr>
                	<td class="td_label" >强制更新：</td>
                 	<td colspan="3" class="td_control">
                    	<input type="checkbox" id="coerce" name="coerce" style="margin-top: -1;" <#if mobileVersion.mustUpdate == 1>checked="checked"</#if>>
                    	<input type="hidden" id="mustUpdate" name="mustUpdate">
                    </td> 
                <tr>
                 <tr>
                	 <td class="td_label" >关联人员：</td>
                    <td  class="td_control">
                    	全部：<input  type="checkbox" id="relateClientUserIdbox"  name="relateClientUserIdbox" style="margin-top: -1;" <#if mobileVersion.forAll == 1>checked="checked"</#if> >
                    	<input type="hidden" id="forAll" name="forAll">
                    	<button type="button" id="relateClientUserIdButn" class="btn btn-success margin-left-18px">已关联<font id="clientUserCheckbox" color="#ff0000"><#if  !mobileVersionDetailList?? >0<#else>${mobileVersionDetailList}</#if></font>个人员</button>
                    <td>
                </tr>
                <tr>
                	  <td class="td_label" >版本说明：</td>
                    <td colspan="3" class="td_control"><textarea   maxlength=50 name="versionDesc" placeholder="不超过50个字">${mobileVersion.versionDesc}</textarea></td>
                </tr>
                <tr>
                	<td class="td_label">备注：</td>
                    <td  colspan="3" class="td_control">
                    	<textarea   maxlength=200 name="remark" placeholder="不超过200个字">${mobileVersion.remark}</textarea> 
                   </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button class="btn btn-danger" id="backButton" type="button" style="margin-left:-701px">返回</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
          		<input type="hidden" id="checkboxId" name="checkboxId" value="${clientUserIds}">
            </tbody>
        </table>
		</form>
		<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
	</div>
	<#include "/footer.ftl" />
</body>
<script type="text/javascript">

var addDialog;

	$("#backButton").bind("click",function(){
		$('#breadcrumb a:last-child',window.top.document).remove();
		window.location.href = "${contextPath}/mobileVersion/query";
	});
$("#savetButton").bind("click",function(){
	$(this).attr("disabled","disabled");
	//验证
	if(!$("#dataForm").validate({
       rules : {
				channelName: {
					maxlength: 50,
					isChinese: true,
				},
				channelNameEn: {
					isEnglish : true
				},
				enableDate:{
				}
			}}).form()){
			$("#savetButton").removeAttr("disabled");
			return false;
	}
	$.ajax({
	url : "${contextPath}/mobileVersion/updateMobileVersion",
	type : "post",
	dataType:"json",
	async: false,
	data : $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
		   layer.alert(result.message,function(){
	   			//window.location.reload();
   				//editDialog.close();
   				parent.document.location.href = "${contextPath}/mobileVersion/query";
                parent.layer.closeAll('iframe');
		   });
		}else {
			layer.alert(result.message);
			$("#savetButton").removeAttr("disabled");
   		}
	},
	error:function(){
		$("#savetButton").removeAttr("disabled");
	}
	});						
});

$(function(){
  $('#cancelId').click(function(){
  	editDialog.close();
  });
  //失效时间
$('#enableDate').datetimepicker({
 		showSecond: true,
	 	timeFormat: 'HH:mm:ss'
});
//判断全部是否选中
if( $('#relateClientUserIdbox').prop("checked")){
	$('#relateClientUserIdButn').attr("disabled",true);
}else{
	$('#relateClientUserIdButn').attr("disabled",false);
}

//强制更新
$('#coerce').click(function(){
	if($(this).prop("checked")){
		$('#mustUpdate').val("1");
	}else{
		$('#mustUpdate').val("0");
	}
});
	
  $('#relateClientUserIdbox').click(function(){
	   if($(this).prop("checked")){
   	 		$('#relateClientUserIdButn').attr("disabled",true);
   	 		$('#forAll').val("1");
   	 		$('#clientUserCheckbox').html("0");
	   	 	$('#checkboxId').val('');
	   }else{
	    	$('#relateClientUserIdButn').attr("disabled",false);
	    	$('#forAll').val("0");
	   }
  });
  
  	$("#relateClientUserIdButn").bind("click",function(){
		var url = "${contextPath}/mobileVersion/relateClientUser";
		layer.open({
			    type: 2,
			    title: '关联人员',
			    closeBtn: 1,
			    area: ['800px', '650px'],
			    content: url
			});
	});
  
  
  });
  

</script>


