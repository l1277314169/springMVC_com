<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post" name="dataForm">
		<input type="hidden" id="surveyId" name="surveyId" value= "${survey.surveyId}" />
		<input type="hidden" id="cycleType" name="cycleType" value= "${survey.cycleType}" />
		
        <table class="table_white_bg" style="width:500px;" align="center">
            <tbody>
                <tr>
                    <td class="td_label"><i class="cc1">*</i>访问日期：</td>
                    <td class="td_control">
                        <input type="text" id="feedbackDate" name="feedbackDate" class="input-medium" required=true />
                       	<#assign dateFtlName="feedbackDate">
                        <#include "/widgets/date_select_one2.ftl" />
                    </td>
                </tr>
                
                <tr>
                    <td class="td_label"><i class="cc1">*</i>周期：</td>
                    <td class="td_control">
                    	<#if survey.cycleType==4>
                    		<input type="hidden" id="cycle" name="cycle" value="${month}" readonly class="input-medium" />
                        	<#assign month="cycle">
					  		<#include "/widgets/month_widget.ftl" />
                    	</#if>
                    </td>
                </tr>

           	 	<tr>
                    <td class="td_label"><i class="cc1">*</i>门店编号：</td>
                    <td class="td_control">
                    	<input type="text" id="storeNo" name="storeNo" class="input-medium">
                    </td>                	
                </tr>

                <tr>
                	<td class="td_label"><i class="cc1">*</i>访问员：</td>
                    <td class="td_control">
                    	<input type="text" id="visitor" name="visitor" class="input-medium" required=true />
                    </td>
                </tr>
               
                <tr>
					<td colspan="2" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        $("#savetButton").click(function(){
        
   		 $("#savetButton").attr("disabled","disabled");
		var validobj = $("#dataForm").validate({
			ignore: [],
			onkeyup: false,
			errorClass: "error",
			rules : { 
				storeNo:{
					required : true,
					maxlength: 25,
					isContainsSpecialChar:true,
					remote:{
						url: "${contextPath}/commService/getStoreNoExists/", 
					    type: "post",
					    dataType: "json",
					    async:false,            
					    data: {                
					        storeNo: function() {
					            return $("#storeNo").val();
					        }
					   	 }
					}
				}	
			},
			messages: {
	            storeNo: {
	                remote: "名店编号不存在"
	            }
		    },
			errorPlacement: function (error, element) {
	            var elem = $(element);
	            error.insertAfter(element);
	        },
			highlight: function (element, errorClass, validClass) {
				var elem = $(element);
	            if (elem.prev().hasClass("select2-container")) {
	                $parent = elem.prev();
	                $parent.find('a.select2-choice').addClass(errorClass);
	            } else {
	                elem.addClass(errorClass);
	            }
			},
	        unhighlight: function (element, errorClass, validClass) {
	            var elem = $(element);
	            if (elem.prev().hasClass("select2-container")) {
	                $parent = elem.prev();
	                $parent.find('a.select2-choice').removeClass(errorClass);
	            } else {
	                elem.removeClass(errorClass);
	            }
	        }
		});
		
		if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}else{
			if(checkeCycle()){
				submitFrm();
			}else{
				$("#savetButton").removeAttr("disabled");
				layer.alert("执行日期与周期不匹配");
			}
		}
	});
	
	
	function checkeCycle(){
		var feedbackDate = $("#feedbackDate").val();
		var str = feedbackDate.split('-').join('').substring(0,6);
		var cycle = $("#cycle").val();
		if(str==cycle){
			return true;
		}else{
			return false;
		}		
	}
	
	function submitFrm(){
		$.ajax({
            url : "${contextPath}/survey/checkSurveyCycle",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
               if(result.code=="success"){
                    addSurveyFeedBack();
                }else {
                	$("#savetButton").removeAttr("disabled");
                    layer.alert(result.message);
                }
            },
           error:function(){
                layer.alert("周期验证失败");
           }
        });
	}
	
	function addSurveyFeedBack(){
		var surveyId = $("#surveyId").val();
		$.ajax({
            url : "${contextPath}/survey/addSurveyFeedback",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
               if(result.code=="success"){
                    layer.alert("问卷新增成功",function(){
                		parent.document.location.href = "${contextPath}/survey/showSurveyMain/"+surveyId+"/?feedbackId="+result.message;
                    	parent.layer.closeAll('iframe');
                    });
                }else {
                    layer.alert(result.message);
                }
            },
           error:function(){
                layer.alert("保存问卷失败");
           }
        });
	}
});
//门店编号
$("#storeNo").select2({
		width:145,
		placeholder: "输入字符查询",
		minimumInputLength: 2,
		allowClear : true,
		ajax: {
			url: "${contextPath}/store/queryStoreListByNumber",
			dataType: 'json',
			quietMillis: 250,
			data: function (term, page) {
				return {
					q: term,
					page: page
				};
			},
			results: function (data,page) {
				var more = page;
				return { results: data,more: more };
			}
		},
		formatResult: repoFormatResult,
		formatSelection: repoFormatSelection,
		escapeMarkup: function (m) { return m; }
	});
	function repoFormatResult(repo) {
		return repo.storeNo;
	}
	function repoFormatSelection(repo) {
		$("#storeNo").val(repo.storeNo)
		layer.tips(repo.storeName, '#s2id_storeNo', {
		    tips: [2, '#3595CC'],
		    time:0
		});
		return repo.storeNo;
	}
</script>