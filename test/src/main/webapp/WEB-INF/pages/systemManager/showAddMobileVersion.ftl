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
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">系统设置</a>
					<a class="linkPage active" href="${contextPath}/mobileVersion/query">手机版本管理</a>
				</div>
		 	</div>
<form id="dataForm" action="#" class="form-horizontal" method="post"  >
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label"><nobr><i class="cc1">*</i>APP版本号：</nobr></td>
                    <td class="td_control"><input type="text" name="version" required=true></td>
                </tr>
                <tr>
                	 <td class="td_label"><i class="cc1">*</i>APP类型：</td>
                     <td class="td_control">
	                    <select id="superior" name="appCode"  required=true>
	                    <option value="">--请选择--</option>
			                    <#list appType as option>
		        					<option value="${option.getKey()}">${option.getCnName()}</option>
		        				</#list>
	                    </select>
                    </td>
                </tr> 
                 <tr>
                   <td  class="td_label" >生效时间：</td>
                    <td class="td_control">
	                  	<input type="text" id="enableDate" name="enableDate" readonly>
                    </td> 
                    <td><span>示例 :2015-01-01 00:00:00</span></td>
                </tr>
                <tr>
                 	<td class="td_label" ><i class="cc1">*</i>连接地址：</td>
                    <td colspan="3" class="td_control">
	                    <input type="text"  name="appLink" style="width:450px" required=true>
                    </td> 
                </tr>
                 <tr>
                	<td class="td_label" >强制更新：</td>
                 	<td colspan="3" class="td_control">
                    	<input type="checkbox" id="coerce" name="coerce" style="margin-top: -1;">
                    	<input type="hidden" id="mustUpdate" name="mustUpdate">
                    </td> 
                <tr>
                 <tr>
                	 <td class="td_label" >关联人员：</td>
                    <td  class="td_control">
                    	全部：<input  type="checkbox" id="relateClientUserIdbox"  name="relateClientUserIdbox" style="margin-top: -1;">
                    	<input type="hidden" id="forAll" name="forAll">
                    	<button id="relateClientUserIdButn" type="button" class="btn btn-success margin-left-18px">已关联<font id="clientUserCheckbox" color="#ff0000">0</font>个人员</button>
                    <td>
                </tr>
                <tr>
                	<td class="td_label" >版本说明：</td>
                    <td colspan="3" class="td_control">
                    	<textarea   maxlength=50 name="versionDesc" placeholder="不超过50个字"></textarea>
                    </td>
                </tr>
                <tr>
                	<td class="td_label">备注：</td>
                    <td  colspan="3" class="td_control">
                    	<textarea   maxlength=200 name="remark" placeholder="不超过200个字"></textarea>
                   </td>
                </tr>
                <tr>
					<td colspan="3" class="td_buttons">
							<button class="btn btn-danger" id="backButton" type="button" style="margin-left:-701px">返回</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
			   <input type="hidden" id="checkboxId" name="checkboxId">
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
				remark: {
					maxlength: 200
				},
				enableDate:{
				}
		}}).form()){
			$("#savetButton").removeAttr("disabled");
			return false;
	}
$.ajax({
	url : "${contextPath}/mobileVersion/addMobileVersion",
	type : "post",
	dataType:"json",
	async: false,
	data : $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
		 	layer.alert(result.message,function(){
	   		//	window.location.reload();
		 	//	addDialog.close();
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
//失效时间
$('#enableDate').datetimepicker({
	 showSecond: true,
	 timeFormat: 'HH:mm:ss'
});
$("#cancelId").click(function(){
  	addDialog.close();
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
//强制更新
$('#coerce').click(function(){
	if($(this).prop("checked")){
		$('#mustUpdate').val("1");
	}else{
		$('#mustUpdate').val("0");
	}
});
});

  	$("#relateClientUserIdButn").bind("click",function(){
		var url = "${contextPath}/mobileVersion/relateClientUser";
		//addDialog = new xDialog(url, {}, {title:"关联人员",iframe:true,width:800,height:650 });
		//return false;	
		 layer.open({
			    type: 2,
			    title:'关联人员',
			    closeBtn: 1,
			    area: ['800px', '650px'],
			    content: url
				});
	});

</script>


