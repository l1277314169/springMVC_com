<html>
<head>
<title>填写问卷</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css" media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body class="main_body">

	<#assign privCode="C5M1">
	<#include "/base.ftl" />
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">问卷管理</a>
				<a class="linkPage active" href="${contextPath}/survey/query">问卷查看</a>
			</div>
	 	</div>

<div id="div_step_detail" class="div_step_detail" style="width: 100%;height:auto;border:none;background: #fff;margin-left:0px;">
	
	<div class="tabs no_bottom_border">
		<ul id="stepTbas" class="margin-left-1">
			<#list blocks as b>
				<#if b_index==0>
					<li class="tab_current_b" id="${b.blockId}">${b.blockName}</li>
				<#else>
					<li class="tab_normal_b" id="${b.blockId}">${b.blockName}</li>
				</#if>
			</#list>
		</ul>
	</div>

	<input type="hidden" id="_feedbackId" name="_feedbackId" value="${feedbackId}" />
	<input type="hidden" id="show" name="show" value="${show}" />
	<div id="tabs-body" class="div_data_detail" style="width: 100%;height: 100%;overflow:visible;background: #fff;"></div>
</div>
</div>
<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#stepTbas > li").click(function(){
			if($(this).attr("class") == "tab_current_b") {
	        	return false;
	        } else {
	            $(this).parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
	            $(this).attr("class", "tab_current_b"); //设置当前选中项为选中样式
				var blockId = $(this).attr("id");
				loadData(blockId);
			}
		});

		//load success
		var blockId = $(".tab_current_b").attr("id");
		loadData(blockId);
		
	});

	function loadData(blockId){
		$("body").showLoading();
		var show = $("#show").val();
		var action = "${contextPath}/survey/showAddSurvey/"+blockId+"?show="+show;
		$("#tabs-body").load(action,function(){
			 $("body").hideLoading();
			 setViewPage();
		});
	}


	function setViewPage(){
		var show = $("#show").val();
		if(show==1){
			var inputObj = $(".input_cons_cls");
			for (var i = inputObj.length - 1; i >= 0; i--) {
				$(inputObj[i]).attr("disabled","disabled");
			}
		}		
	}

</script>