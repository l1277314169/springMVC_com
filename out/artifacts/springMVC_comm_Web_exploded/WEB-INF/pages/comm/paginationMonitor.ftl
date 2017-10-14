<#--
	分页组件，当前的分页使用get方式读取数据，使用注意：不允许在form表单当中嵌套分页组件；
	
	样例
	<@import "/base/pagination.ftl" as pagination>
	pageinfo ----------------分页对象
	ajax---------------------是否是ajax读取数据
	selector-----------------ajax读取数据时才使用，表示数据显示的位置,
		例如页面上对应的<div id="contentDiv" class="divClass"></div>,
		该地方需要写成"#contentDiv","div.divClass"
	<@pagination.paging pageInfo ajax selector>
		<a class="btn btn_cc1">领单</a><a class="btn btn_cc1">分单</a>
	</@pagination.paging>
	或
	<@pagination.paging pageInfo/>
-->
<#macro paging pageInfo="" ajax=false selector="">
<#if pageInfo??>
<div class="pages darkstyle">	
    <div class="fl operate"><#nested/></div>
    <form action="${pageInfo.requestURI}"  id="paginationForm" name="pagination">
    <#list pageInfo.params as param>
    <#if param.name != 'pageNum'>
    <input type="hidden" name="${param.name}" value="${param.value}">
    </#if>
    </#list>
    <input type="hidden" name="page"/>
    <p class="pages_go">到第 <input type="text" class="w1" name="pageNum" number=true totalPage="${pageInfo.totalPages}"> 页 <button type="button"  class="btn btn-mini pagination">确定</button></p>
    <div class="Pages">
    <p class="page_msg cc3">共 <em class="cc1">${pageInfo.totalResultSize}</em> 条记录，每页显示 ${pageInfo.pageSize} 条记录</p>
    <#if pageInfo.hasPrev()>
    	<a href="javascript:void(0)" class="PrevPage" page="${pageInfo.currentPage-1}"><i class="ui-arrow-left blue-ui-arrow-left"></i>上一页</a>
    <#else>
    	<span class="PrevPage"><i class="ui-arrow-left blue-ui-arrow-left"></i>上一页</span>
    </#if>
    <#if pageInfo.totalPages lt 11>
    	<#local begin=1>
    	<#local end=pageInfo.totalPages>
    <#elseif pageInfo.totalPages-pageInfo.currentPage lt 5>
    	<#local end = pageInfo.totalPages>
    	<#local begin = pageInfo.totalPages-10>
    <#elseif pageInfo.currentPage lt 5>
    	<#local begin = 1>
    	<#local end = 10>
    <#else>
    	<#local begin = pageInfo.currentPage-4>
    	<#local end = pageInfo.currentPage+5>
    </#if>
    <#list begin..end as pp>
    	<#if pp == pageInfo.currentPage>
    		<span class="PageSel">${pageInfo.currentPage}</span>
    	<#else>
    		<a href="javascript:void(0)" page="${pp}">${pp}</a>
    	</#if>
    </#list>
    <#if end lt pageInfo.totalPages>
    	<span class="PageMore">...</span><a href="javascript:void(0)" page="${pageInfo.totalPages}">${pageInfo.totalPages}</a>
    </#if>
    <#if pageInfo.hasNext()>
	    <a href="javascript:void(0)" page="${pageInfo.currentPage+1}" class="NextPage">下一页<i class="ui-arrow-right blue-ui-arrow-right"></i></a>
    <#else>
    </#if> 
    <span> 
    显示<select name="pageSize" id="pageSize" style="width:80px;">
    <option value="10">10</option>
    <option value="20">20</option>
    <option value="50">50</option>
    <option value="100">100</option>
    </select>条
    </span>  
    </div>
    </form>
    <script type="text/javascript">
      var pageSize = 10;
      var isSubmitted = false;
    	$(function(){
    		$("div.pages a[page],div.pages button.pagination").click(function(){
    			if(isSubmitted) {
    				return false;
    			} 	
    			var $this = $(this);
    			var $form=$(this).parents("form");
                var form = document.forms[0];
				alert(form.attr('id'));

                var page=0;
    			if($this.get(0).tagName=="A"||$this.get(0).tagName=="a"){
    				page = $this.attr("page");
    			}else{
    			
    				//验证
					if(!$("#paginationForm").validate().form()){
						return;
					}
					
    				var $input=$form.find("input[name='pageNum']");
    				page = $input.val();
    				
    				var totalPage = $input.attr("totalPage");
    				if($.trim(page)==""||parseInt(page)>parseInt(totalPage)||page<1){
    					alert("页码不正确");
    					return false;
    				}
    				
    			}
    			$form.find("input[name='page']").val(page);
    			<#if ajax>
    			$("${selector}").load($form.attr("action"),$form.serialize());
    			<#else>
    			$form.submit();
    			</#if>
    			isSubmitted = true;
    			$("div.pages a[page],div.pages button.pagination").each(function() {
    				$(this).attr("disabled", true);
    				$(this).css("color", "#ccc");
    				$(this).css("cursor", "default");
    				$(this).hover(function() {$(this).css("background-color", "#fff").css("border-color", "#ccc").css("cursor", "default");}, function() {});
    			});
    			$("input[name=pageNum]").each(function() {
    				$(this).attr("disabled", true);
    			});
    			$("select[name =pageSize]").each(function() {
    				$(this).attr("disabled", true);
    			});
    			
    			$(".ui-arrow-left").css("border-right-color","#ccc");
				$(".ui-arrow-right").css("border-left-color","#ccc");
				
				
    		});
    		//选择性显示每页的记录数
    		$("select[name=pageSize] option").each(function(){
    		  if($(this).val()==${pageInfo.pageSize}){
    		    $(this).attr("selected", "selected");
    		  }   		
    		});
    		$("input:hidden[name='pageSize']:last").remove(); 		
    		$("select[name =pageSize]").change(function(){
    		   var pageSize_change= $("select[name =pageSize]").val();
    		   pageSize = pageSize_change;
    		   var $form=$(this).parents("form");
                var form = $('#userForm');
				alert(form.attr('id'));
    		  $("input[name='pageSize']",$form).val(pageSize);
    			<#if ajax>
    			$("${selector}").load($form.attr("action"),form.serialize());
    			<#else>
    			$form.submit();
    			</#if>
    			 isSubmitted = true;
    			$("div.pages a[page],div.pages button.pagination").each(function() {
    				$(this).attr("disabled", true);
    				$(this).css("color", "#ccc");
    				$(this).css("cursor", "default");
    				$(this).hover(function() {$(this).css("background-color", "#fff").css("border-color", "#ccc").css("cursor", "default");}, function() {});
    			});
    			$("input[name=pageNum]").each(function() {
    				$(this).attr("disabled", true);
    			});
    			$("select[name =pageSize]").each(function() {
    				$(this).attr("disabled", true);
    			});
    			
    			$(".ui-arrow-left").css("border-right-color","#ccc");
				$(".ui-arrow-right").css("border-left-color","#ccc");
    		});
    		
    	});
    </script>
</div>
</#if>
</#macro>