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
<#if pageInfo!0 gt 0>
<div class="pages darkstyle">	
    <div class="fl operate"><#nested/></div>
    <form action="${pageInfo.requestURI}" name="pagination">
    <#list pageInfo.params as param>
    <#if param.name != 'pageNum'>
    <input type="hidden" name="${param.name}" value="${param.value}">
    </#if>
    </#list>
    <input type="hidden" name="page"/>
    <!--<p class="pages_go">到第 <input type="text" class="w1" name="pageNum" totalPage="${pageInfo.totalPages}"> 页 <button type="button"  class="btn btn-mini pagination">确定</button></p>-->
    <div class="Pages">
    <!--<p class="page_msg cc3">总共 <em class="cc1">${pageInfo.totalResultSize}</em> 条记录，每页显示 ${pageInfo.pageSize} 条记录</p>-->
    <span>总共 <span class="PageCount">${pageInfo.totalResultSize}</span> 条记录</span>
    <#if pageInfo.hasPrev()>
    	<a href="javascript:void(0)" class="PrevPage" page="${pageInfo.currentPage-1}">上一页</a> 
    <#else>
    	<a href="javascript:void(0)" class="PrevPage">上一页</a> 
    </#if>
    <#if pageInfo.totalPages lt 11>
    	<#local begin=1>
    	<#local end=pageInfo.totalPages>
    <#elseif pageInfo.totalPages-pageInfo.currentPage lt 5>
    	<#local end = pageInfo.totalPages>
    	<#local begin = pageInfo.totalPages-5>
    <#elseif pageInfo.currentPage lt 5>
    	<#local begin = 1>
    	<#local end = 9>
    <#else>
    	<#local begin = pageInfo.currentPage-4>
    	<#local end = pageInfo.currentPage+5>
    </#if>
    <#if pageInfo.currentPage gt 5>
		<a href="javascript:void(0)" class="PageLink" page="1">1</a><span class="PageMore">...</span>
	</#if>
    <#list begin..end as pp>
    	<#if pp == pageInfo.currentPage>
    		<span class="PageSel">${pageInfo.currentPage}</span>
    	<#else>
    		<a href="javascript:void(0)" class="PageLink" page="${pp}">${pp}</a>
    	</#if>
    </#list>
    <#if end lt pageInfo.totalPages>
    	<span class="PageMore">...</span><a href="javascript:void(0)" page="${pageInfo.totalPages}">${pageInfo.totalPages}</a>
    </#if>
    <#if pageInfo.hasNext()>
	    <a href="javascript:void(0)" page="${pageInfo.currentPage+1}" class="NextPage">下一页</a>
    <#else>
    	<a href="javascript:void(0)" class="PrevPage">下一页</a> 
    </#if> 
    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("div.pages a[page],div.pages button.pagination").click(function(){
    			jQuery("body").showLoading();
    			var $this = $(this);
    			var $form=$(this).parents("form");
    			var page=0;
    			if($this.get(0).tagName=="A"||$this.get(0).tagName=="a"){
    				page = $this.attr("page");
    			}else{
    				var $input=$form.find("input[name='pageNum']");
    				page = $input.val();
    				var totalPage = $input.attr("totalPage");
    				
    				if($.trim(page)==""||page>totalPage||page<1){
    					alert("页码不正确");
    					return false;
    				}
    			}
    			$form.find("input[name='page']").val(page);
    			<#if ajax>
    			//$("${selector}").load($form.attr("action"),$form.serialize());
    			
    			$("${selector}").load($form.attr("action"),$form.serialize(),function(){
    					jQuery("body").hideLoading();
    				}
  				 );
    			<#else>
    			$form.submit();
    			jQuery("body").hideLoading();
    			</#if>
    		});
    	});
    </script>
</div>
</#if>
</#macro>