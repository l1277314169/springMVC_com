<div id="sidebar">
  <ul>
  	<#list cpList as cp>
	  	<#if cp.parentId??>
	  	<#else>
	  		<#if cp_index = 0>
		  		<li class="submenu"><a href="#"><i class="icon ${cp.cssClass}"></i><span>${cp.privName}</span></a>
		  	<#else>
		  		<li class="submenu"><a href="#"><i class="icon ${cp.cssClass}"></i><span>${cp.privName}</span></a>
		  	</#if>
		      <ul>
		      	<#list cpList as cp_ch>
			      	<#if cp_ch.parentId == cp.clientPrivilegeId>
			        	<li><a class="linkPage" <#if cp_ch.url ?? && cp_ch.url != "">href="${contextPath}${cp_ch.url}"</#if> target="main">${cp_ch.privName}</a></li>
			        </#if>
		        </#list>
		      </ul>
		    </li>
	    </#if>
    </#list>
  </ul>
</div>