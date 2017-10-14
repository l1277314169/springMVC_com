<ul>
  	<#list menuList2 as menu>
	  		<#if cp_index = 0>
		  		<li class="submenu <#if privCode[0..1]==menu.privCode>active open currentM</#if>"><a href="#"><i class="icon ${menu.cssClass}"></i><span>${menu.privName}</span></a>
		  	<#else>
		  		<li class="submenu <#if privCode[0..1]==menu.privCode>active open currentM</#if>"><a href="#"><i class="icon ${menu.cssClass}"></i><span>${menu.privName}</span></a>
		  	</#if>
		      <ul>
		      	<#list menu.childMenus as menu_ch>
			        	<li <#if menu_ch.privCode=='${privCode}' >class="linkPage <#if menu_ch.privCode=='${privCode}'>active</#if>"</#if> ><a <#if menu_ch.url ?? && menu_ch.url != "">href="${contextPath}${menu_ch.url}"</#if>>${menu_ch.privName}</a></li>
		        </#list>
		      </ul>
		    </li>
    </#list>
  </ul>
  <input type="hidden" id="logoPrefix" name="logoPrefix" value="<#if clientUser.logoPrefix??>${clientUser.logoPrefix}</#if>" />