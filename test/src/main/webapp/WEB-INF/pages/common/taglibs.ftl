<#macro baseVersion>20150817</#macro>
<#macro basePath>
    <#if springMacroRequestContext.getContextPath()=="/">
        <#else>
    ${springMacroRequestContext.getContextPath()}
    </#if>

</#macro>
<#global contextPath><@basePath/></#global>
<#global cVer><@baseVersion/></#global>