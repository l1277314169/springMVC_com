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

<div id="div_step_detail" class="div_step_detail" style="width: 100%;height: 100%;margin-bottom: 20px;">
	<div class="tabs no_bottom_border">
		<ul id="stepTbas" class="margin-left-1">
			<#list blocks as b>
				<#if b_index==0>
					<li class="tab_current_b colgate_tab" id="${b.blockId}">${b.blockName}</li>
				<#else>
					<li class="tab_normal_b colgate_tab" id="${b.blockId}">${b.blockName}</li>
				</#if>
			</#list>
		</ul>
	</div>
	<form id="dataForm" name="dataForm" method="POST" action="${contextPath}/survey/doAddSurvey" enctype="multipart/form-data" >
		<input type="hidden" id="_feedbackId" name="_feedbackId" value="${feedbackId}" />
		<input type="hidden" id="show" name="show" value="${show}" />
		<input type="hidden" id="clientId" name="clientId" value="${clientId}" />
		<input type="hidden" id="surveyId" name="surveyId" value="${surveyId}" />
		<input type="hidden" id="feedbackDate" name="feedbackDate" value="${feedbackDate?datetime}" />
		<input type="hidden" id="storeNo" name="storeNo" value="${storeNo}" />
		<input type="hidden" id="visitor" name="visitor" value="${visitor}" />
		
		<input type="hidden" id="blockId6" name="blockId6" />
		<input type="hidden" id="blockId3" name="blockId3" />
		<input type="hidden" id="blockId4" name="blockId4" />
		<input type="hidden" id="blockId5" name="blockId5" />
		
	</form>
	<div id="tabs-body" class="div_data_detail" style="width: 100%;height: 100%;padding-bottom:50px;overflow:visible;"></div>
</div>
</div>
<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
var errfalg = true;
	$(document).ready(function() {
		
		$("#stepTbas > li").click(function(){
			//在切换页面的时候出发保存按钮
			var obj = $(this);
			var blockId = $("#_blockId").val();
			$("#sava_survey_but").attr("disabled","disabled");
			var dataFlag = true;
	
			$(".intType").each(function(){			
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(!isNaN(value)){
					if(parseInt(value)!=value && value!=""){
						dataFlag = false;						
					 	layer.tips('必须为整数', '#'+id,{
						    tips: [2, '#3595CC'],
						    time: 15000,
						    tipsMore: true
						});
					}
				}else{
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}				
			});

			$(".float").each(function(){
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(isNaN(value)){
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}else{//如果为数字需要验证精确到多少位
					var scale = $(this).attr("scale");
					if(null!=scale && ''!=scale){
						var dot = value.indexOf(".");
			            if(dot != -1){
			                var dotCnt = value.substring(dot+1,value.length);
			                if(dotCnt.length > scale){
			                    dataFlag = false;
		 						layer.tips('小数位已超过'+scale+'位', '#'+id,{
								    tips: [2, '#3595CC'],
								    time: 15000,
								    tipsMore: true
								});
			                }
			            }
					}
				}
			});
			
			$(".require").each(function(){
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(value==null || ''==value){
					dataFlag = false;
		 			layer.tips('不能为空', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}
			});
			
			$(".maxValueValidate").each(function(){
				var currentValue = $(this).val();
				if(currentValue==''){
					currentValue = parseInt("0");
				}
				var maxValue = parseInt($(this).attr("maxvalue"));
				var minValue = parseInt($(this).attr("minvalue"));
				var id = $(this).attr("id");
				if(null!=currentValue && ''!= currentValue && currentValue>maxValue){
					dataFlag = false;
					layer.tips('不能超过最大值'+maxValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});				
				}else if(null!=currentValue && ''!= currentValue &&  currentValue<minValue){
					dataFlag = false;
					layer.tips('不能小于最小值'+minValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}						
			});
			
			setUploaderVal();
			var flag = checkSurvey(dataFlag);
			if(dataFlag && flag){
				$("body").showLoading();
				$("#feedbackId").val($("#_feedbackId").val());
				$.ajax({
				  url: '${contextPath}/survey/doAddColgateSurvey',
				  type: 'POST',
				  dataType: 'json',
				  data: $("#frm").serialize(),
				  success: function(data, textStatus, xhr) {
				  		var newBlockId = obj.attr("id");
						$("#blockId"+blockId).val(JSON.stringify(data));
						$("#sava_survey_but").removeAttr("disabled");
						if(obj.attr("class") == "tab_current_b") {
				        	return false;
				        } else {
				            obj.parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
				            obj.attr("class", "tab_current_b"); //设置当前选中项为选中样式
							loadData(newBlockId);
						}
				  },
				  error: function(xhr, textStatus, errorThrown) {
				    	layer.alert("问卷添加失败"+errorThrown);
				  }
				});	
			}else{
				$("#sava_survey_but").removeAttr("disabled");
				layer.msg('数据验证失败', {icon: 2,time: 15000});
			}
			
		});

		//load success
		var blockId = $(".tab_current_b").attr("id");
		loadData(blockId);
	});

	function loadData(blockId){
		$("body").showLoading();
		var show = $("#show").val();
		var action = "${contextPath}/survey/showAddColgateSurvey/"+blockId+"?show="+show;
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