<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>协议修改</title>
</head>
<body class="main_body">
<#assign privCode="C2M14">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础数据管理</a>
					<a class="linkPage active" href="${contextPath}/contract/query">会员招募</a>
				</div>
		 	</div>
	<form id="dataForm" method="post">
		<div class="widget-content nopadding" style="overflow:auto;width:auto;">	
			
					<table class="table table-bordered data-table" id="c_list" style="margin-top:10px">
					<tr>
						<th scope="col" style="text-align:center;" colspan="12">协议基本信息</th>
						<tr>
							<input  type="hidden" name=contractId value=${wc.contractId}>
							<th class="fill_td" >Store Code</th>
							<th class="fill_td" >Zone</th>
							<th class="fill_td" >City</th>
							<th class="fill_td" scope="col" colspan="2">StoreName</th>
							<th class="fill_td" >Chain</th>
							<th class="fill_td" scope="col" colspan="2" >签订日期</th>
							<th class="fill_td" scope="col" colspan="2">合作起始日期</th>
							<th class="fill_td" scope="col" colspan="2">合作结束日期</th>
							
						</tr>
						<tr>
						<th class="fill_td">
						<input id="storeNo"  disabled="true" style="width:80px;height:30px" name="storeNo" type="text" value="${wc.storeNo}"/>
						</th>
						<th class="fill_td">
						<input id="zone"  disabled="true" style="width:80px;height:30px" name="structureName" type="text" value="${wc.structureName}"/>
						</th>
						<th class="fill_td">
						<input id="city"  disabled="true" style="width:80px;height:30px" name="city" type="text" value="${wc.city}"/>
						</th>
						<th class="fill_td"  scope="col" colspan="2">
						<input id="storeName"  disabled="true" style="width:200px;height:30px" name="storeName" type="text" value="${wc.storeName}"/>
						</th>
						<th class="fill_td">
						<input id="chainName"  disabled="true" style="width:80px;height:30px" name="chainName" type="text" value="${wc.chainName}"/>
						</th>
						<th class="fill_td" scope="col" colspan="2">
						<input id="signDate"  disabled="true" style="width:200px;height:30px" name="signDate" type="text" value="${wc.signDate?string("yyyy年MM月dd日")}"/>
						</th>
						<th class="fill_td" scope="col" colspan="2">
						<input id="startDate"  disabled="true" style="width:200px;height:30px" name="startDate" type="text" value="${wc.startDate?string("yyyy年MM月dd日")}"/>
						</th>
						<th class="fill_td" scope="col" colspan="2">
						<input id="endDate"  disabled="true"  style="width:200px;height:30px" name="endDate" type="text" value="${wc.endDate?string("yyyy年MM月dd日")}"/>
						</th>
						</tr>						
					</tr>
						<tr> 
	    					<th scope="col" style="text-align:center;" colspan="12">会员招募礼品发放预计份数</th> 
	    				</tr> 
	    				<tr>
	    					<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
						</tr>
	    				<tr>
							<th class="fill_td">8月</th>
							<th class="fill_td">9月</th>
							<th class="fill_td">10月</th>
							<th class="fill_td">11月</th>
							<th class="fill_td">12月</th>
							<th class="fill_td">1月</th>
							<th class="fill_td">2月</th>
							<th class="fill_td">3月</th>
							<th class="fill_td">4月</th>
							<th class="fill_td">5月</th>
							<th class="fill_td">6月</th>
							<th class="fill_td">7月</th>
						</tr>						
						<tr>
							<td class="fill_td">
							<input  type="hidden" name="wcds[0].dataType" value="1" />
							<input  type="hidden" name="wcds[0].monthId" value="201508" />
							<input id="value"  name="wcds[0].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201508>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[0].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201508>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[1].dataType" value="1" />
							<input  type="hidden" name="wcds[1].monthId" value="201509" />
							<input id="value"  name="wcds[1].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201509>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[1].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201509>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[2].dataType" value="1" />
							<input  type="hidden" name="wcds[2].monthId" value="201510" />
							<input id="detailId"  name="wcds[2].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201510>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[2].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201510>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[3].dataType" value="1" />
							<input  type="hidden" name="wcds[3].monthId" value="201511" />
							<input id="value"  name="wcds[3].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201511>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[3].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201511>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[4].dataType" value="1" />
							<input  type="hidden" name="wcds[4].monthId" value="201512" />
							<input id="value"  name="wcds[4].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201512>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[4].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201512>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[5].dataType" value="1" />
							<input  type="hidden" name="wcds[5].monthId" value="201601" />
							<input id="value"  name="wcds[5].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201601>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[5].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201601>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[6].dataType" value="1" />
							<input  type="hidden" name="wcds[6].monthId" value="201602" />
							<input id="value"  name="wcds[6].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201602>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[6].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201602>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[7].dataType" value="1" />
							<input  type="hidden" name="wcds[7].monthId" value="201603" />
							<input id="value"  name="wcds[7].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201603>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[7].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201603>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[8].dataType" value="1" />
							<input  type="hidden" name="wcds[8].monthId" value="201604" />
							<input id="value"  name="wcds[8].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201604>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[8].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201604>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[9].dataType" value="1" />
							<input  type="hidden" name="wcds[9].monthId" value="201605" />
							<input id="value"  name="wcds[9].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201605>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[9].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201605>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[10].dataType" value="1" />
							<input  type="hidden" name="wcds[10].monthId" value="201606" />
							<input id="value"  name="wcds[10].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201606>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[10].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201606>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[11].dataType" value="1" />
							<input  type="hidden" name="wcds[11].monthId" value="201607" />
							<input id="value"  name="wcds[11].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201607>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[11].value" type="text" value="<#list wcds as s><#if s.dataType==1 &&s.monthId==201607>${s.value}</#if></#list>"/>
							</td>
						</tr>
						
						
					</table>
		
			<table class="table table-bordered data-table" id="c_list">
						<tr> 
	    					<th scope="col" style="text-align:center;" colspan="12">联合招募惠氏妈妈俱乐部会员目标数量	</th> 
	    				</tr> 
	    				<tr>
	    					<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
						</tr>
	    				<tr>
							<th class="fill_td">8月</th>
							<th class="fill_td">9月</th>
							<th class="fill_td">10月</th>
							<th class="fill_td">11月</th>
							<th class="fill_td">12月</th>
							<th class="fill_td">1月</th>
							<th class="fill_td">2月</th>
							<th class="fill_td">3月</th>
							<th class="fill_td">4月</th>
							<th class="fill_td">5月</th>
							<th class="fill_td">6月</th>
							<th class="fill_td">7月</th>
						</tr>
						<tr>
							<td class="fill_td">
							<input  type="hidden" name="wcds[12].dataType" value="2" />
							<input  type="hidden" name="wcds[12].monthId" value="201508" />
							<input id="value"  name="wcds[12].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201508>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[12].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201508>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[13].dataType" value="2" />
							<input  type="hidden" name="wcds[13].monthId" value="201509" />
							<input id="value"  name="wcds[13].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201509>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[13].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201509>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[14].dataType" value="2" />
							<input  type="hidden" name="wcds[14].monthId" value="201510" />
							<input id="detailId"  name="wcds[14].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201510>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[14].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201510>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[15].dataType" value="2" />
							<input  type="hidden" name="wcds[15].monthId" value="201511" />
							<input id="value"  name="wcds[15].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201511>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[15].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201511>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[16].dataType" value="2" />
							<input  type="hidden" name="wcds[16].monthId" value="201512" />
							<input id="value"  name="wcds[16].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201512>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[16].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201512>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[17].dataType" value="2" />
							<input  type="hidden" name="wcds[17].monthId" value="201601" />
							<input id="value"  name="wcds[17].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201601>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[17].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201601>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[18].dataType" value="2" />
							<input  type="hidden" name="wcds[18].monthId" value="201602" />
							<input id="value"  name="wcds[18].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201602>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[18].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201602>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[19].dataType" value="2" />
							<input  type="hidden" name="wcds[19].monthId" value="201603" />
							<input id="value"  name="wcds[19].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201603>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[19].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201603>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[20].dataType" value="2" />
							<input  type="hidden" name="wcds[20].monthId" value="201604" />
							<input id="value"  name="wcds[20].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201604>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[20].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201604>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[21].dataType" value="2" />
							<input  type="hidden" name="wcds[21].monthId" value="201605" />
							<input id="value"  name="wcds[21].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201605>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[21].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201605>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[22].dataType" value="2" />
							<input  type="hidden" name="wcds[22].monthId" value="201606" />
							<input id="value"  name="wcds[22].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201606>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[22].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201606>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[23].dataType" value="2" />
							<input  type="hidden" name="wcds[23].monthId" value="201607" />
							<input id="value"  name="wcds[23].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201607>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[23].value" type="text" value="<#list wcds as s><#if s.dataType==2 &&s.monthId==201607>${s.value}</#if></#list>"/>
							</td>
							
						</tr>
						
					</table>
			
					<table class="table table-bordered data-table" id="c_list">
						<tr> 
	    					<th scope="col" style="text-align:center;" colspan="13">在合作期间,乙方惠氏一二阶段产品的精准度需达到95%及以上	</th> 
	    				</tr> 
	    				<tr >
	    					<th class="fill_td" >类型</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2015年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
							<th class="fill_td">2016年</th>
						</tr>
	    				<tr >
	    					<th  class="fill_td" >进货金额</th>
							<th class="fill_td">8月</th>
							<th class="fill_td">9月</th>
							<th class="fill_td">10月</th>
							<th class="fill_td">11月</th>
							<th class="fill_td">12月</th>
							<th class="fill_td">1月</th>
							<th class="fill_td">2月</th>
							<th class="fill_td">3月</th>
							<th class="fill_td">4月</th>
							<th class="fill_td">5月</th>
							<th class="fill_td">6月</th>
							<th class="fill_td">7月</th>
						</tr>
						<tr>
							<td class="fill_td"></td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[24].dataType" value="3" />
							<input  type="hidden" name="wcds[24].monthId" value="201508" />
							<input id="value"  name="wcds[24].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201508>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[24].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201508>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[25].dataType" value="3" />
							<input  type="hidden" name="wcds[25].monthId" value="201509" />
							<input id="value"  name="wcds[25].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201509>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[25].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201509>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[26].dataType" value="3" />
							<input  type="hidden" name="wcds[26].monthId" value="201510" />
							<input id="value"  name="wcds[26].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201510>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[26].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201510>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[27].dataType" value="3" />
							<input  type="hidden" name="wcds[27].monthId" value="201511" />
							<input id="value"  name="wcds[27].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201511>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[27].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201511>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[28].dataType" value="3" />
							<input  type="hidden" name="wcds[28].monthId" value="201512" />
							<input id="value"  name="wcds[28].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201512>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[28].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201512>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[29].dataType" value="3" />
							<input  type="hidden" name="wcds[29].monthId" value="201601" />
							<input id="value"  name="wcds[29].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201601>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[29].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201601>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[30].dataType" value="3" />
							<input  type="hidden" name="wcds[30].monthId" value="201602" />
							<input id="value"  name="wcds[30].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201602>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[30].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201602>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[31].dataType" value="3" />
							<input  type="hidden" name="wcds[31].monthId" value="201603" />
							<input id="value"  name="wcds[31].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201603>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[31].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201603>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[32].dataType" value="3" />
							<input  type="hidden" name="wcds[32].monthId" value="201604" />
							<input id="value"  name="wcds[32].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201604>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[32].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201604>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[33].dataType" value="3" />
							<input  type="hidden" name="wcds[33].monthId" value="201605" />
							<input id="value"  name="wcds[33].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201605>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[33].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201605>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[34].dataType" value="3" />
							<input  type="hidden" name="wcds[34].monthId" value="201606" />
							<input id="value"  name="wcds[34].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201606>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[34].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201606>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[35].dataType" value="3" />
							
							<input  type="hidden" name="wcds[35].monthId" value="201607" />
							<input id="value"  name="wcds[35].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201607>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[35].value" type="text" value="<#list wcds as s><#if s.dataType==3 &&s.monthId==201607>${s.value}</#if></#list>"/>
							</td>
						</tr>
						<tr>
	    					<th class="fill_td">POS金额</th>
							<th class="fill_td">8月</th>
							<th class="fill_td">9月</th>
							<th class="fill_td">10月</th>
							<th class="fill_td">11月</th>
							<th class="fill_td">12月</th>
							<th class="fill_td">1月</th>
							<th class="fill_td">2月</th>
							<th class="fill_td">3月</th>
							<th class="fill_td">4月</th>
							<th class="fill_td">5月</th>
							<th class="fill_td">6月</th>
							<th class="fill_td">7月</th>
						</tr>
						<tr>
							<td class="fill_td"></td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[36].dataType" value="4" />
							<input  type="hidden" name="wcds[36].monthId" value="201508" />
							<input id="value"  name="wcds[36].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201508>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[36].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201508>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[37].dataType" value="4" />
							<input  type="hidden" name="wcds[37].monthId" value="201509" />
							<input id="value"  name="wcds[37].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201509>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[37].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201509>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[38].dataType" value="4" />
							<input  type="hidden" name="wcds[38].monthId" value="201510" />
							<input id="value"  name="wcds[38].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201510>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[38].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201510>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[39].dataType" value="4" />
							<input  type="hidden" name="wcds[39].monthId" value="201511" />
							<input id="value"  name="wcds[39].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201511>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[39].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201511>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[40].dataType" value="4" />
							<input  type="hidden" name="wcds[40].monthId" value="201512" />
							<input id="value"  name="wcds[40].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201512>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[40].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201512>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[41].dataType" value="4" />
							<input  type="hidden" name="wcds[41].monthId" value="201601" />
							<input id="value"  name="wcds[41].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201601>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[41].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201601>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[42].dataType" value="4" />
							<input  type="hidden" name="wcds[42].monthId" value="201602" />
							<input id="value"  name="wcds[42].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201602>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[42].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201602>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[43].dataType" value="4" />
							<input  type="hidden" name="wcds[43].monthId" value="201603" />
							<input id="value"  name="wcds[43].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201603>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[43].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201603>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[44].dataType" value="4" />
							<input  type="hidden" name="wcds[44].monthId" value="201604" />
							<input id="value"  name="wcds[44].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201604>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[44].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201604>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[45].dataType" value="4" />
							<input  type="hidden" name="wcds[45].monthId" value="201605" />
							<input id="value"  name="wcds[45].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201605>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[45].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201605>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[46].dataType" value="4" />
							<input  type="hidden" name="wcds[46].monthId" value="201606" />
							<input id="value"  name="wcds[46].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201606>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[46].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201606>${s.value}</#if></#list>"/>
							</td>
							<td class="fill_td">
							<input  type="hidden" name="wcds[47].dataType" value="4" />
							<input  type="hidden" name="wcds[47].monthId" value="201607" />
							<input id="value"  name="wcds[47].detailId" type="hidden" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201607>${s.detailId}</#if></#list>"/>
							<input id="value" style="width:80px;height:30px" name="wcds[47].value" type="text" value="<#list wcds as s><#if s.dataType==4 &&s.monthId==201607>${s.value}</#if></#list>"/>
							</td>
						</tr>
					</table>
	
			
			
		
					<table class="table table-bordered data-table" id="c_list">
	    				<tr>
							<th class="fill_td" scope="col" colspan="2">招募礼</th>
							<th class="fill_td" scope="col" colspan="2">门店价格</th>
							<th class="fill_td" scope="col" colspan="2">其他招募礼（名称）</th>
							<th class="fill_td" scope="col" colspan="2">门店价格</th>
							<th class="fill_td" scope="col" colspan="2">具体内容</th>
							<th class="fill_td" scope="col" colspan="2">F确保陈列面积（节）</th>
							<th class="fill_td" scope="col" colspan="2">其他支持内容</th>
						</tr>
	    				<tr>
							<td class="fill_td" scope="col" colspan="2">
							<input id="gift" style="width:160px;height:30px" name="gift" type="text" value="${wc.gift}"/>
							</td>
							<td class="fill_td" scope="col" colspan="2">
							<input id="giftPrice" style="width:160px;height:30px" name="giftPrice" type="text" value="${wc.giftPrice}"/>
							</td>
							<td class="fill_td" scope="col" colspan="2">
							<input id="otherGift" style="width:160px;height:30px" name="otherGift" type="text" value="${wc.otherGift}"/>
							</td>
							<td class="fill_td" scope="col" colspan="2">
							<input id="otherPrice" style="width:160px;height:30px" name="otherPrice" type="text" value="${wc.otherPrice}"/>
							</td>
							<td class="fill_td" scope="col" colspan="2">
							<input id="optionsOfInvest" style="width:160px;height:30px" name="optionsOfInvest" type="text" value="${wc.optionsOfInvest}"/>
							</td>
							<td class="fill_td" scope="col" colspan="2">
							<input id="valOfShelf" style="width:160px;height:30px" name="valOfShelf" type="text" value="${wc.valOfShelf}"/>
							</td>
							<td class="fill_td" scope="col" colspan="2"	>
							<input id="otherInvest" style="width:160px;height:30px" name="otherInvest" type="text" value="${wc.otherInvest}"/>
							</td>
						</tr>
					</table>
			
			
			
			
					<table class="table table-bordered data-table" id="c_list">
	    				<tr>
							<th class="fill_td" >是否能够提供发票</th>
							<th class="fill_td">发票类型</th>
							<th class="fill_td">其他</th>
							<th class="fill_td">类型</th>
							<th class="fill_td">账号类型</th>
							<th class="fill_td">开户行</th>
							<th class="fill_td">账号</th>
							<th class="fill_td">收款人</th>
						</tr>
	    				<tr>
							<td class="fill_td">
							<input id="hasInvoice" style="width:130px;height:30px" name="hasInvoice" type="text" value="${wc.hasInvoice}"/>
							</td>
							<td class="fill_td">
							<input id="hasInvoice" style="width:130px;height:30px" name="invoiceType" type="text" value="${wc.invoiceType}"/>
							</td>
							<td class="fill_td">
							<input id="hasInvoice" style="width:130px;height:30px" name="otherInvoice" type="text" value="${wc.otherInvoice}"/>
							</td>
							<td class="fill_td">
							<input id="licenseType" style="width:130px;height:30px" name="licenseType" type="text" value="${wc.licenseType}"/>
							</td>
							<td class="fill_td">
							<input id="acctType" style="width:130px;height:30px" name="acctType" type="text" value="${wc.acctType}"/>
							</td>
							<td class="fill_td">
							<input id="bankName" style="width:130px;height:30px" name="bankName" type="text" value="${wc.bankName}"/>
							</td>
							<td class="fill_td">
							<input id="bankAcct" style="width:130px;height:30px" name="bankAcct" type="text" value="${wc.bankAcct}"/>
							</td>
							<td class="fill_td">
							<input id="acctHolder" style="width:130px;height:30px" name="acctHolder" type="text" value="${wc.acctHolder}"/> 
							</td>
						</tr>
						
						<tr>
							<td colspan="11"  style="text-align:center;" >
								<button data-dismiss="edit" type="button" id="edit" class="btn btn-success" >修改</button>
								<button data-dismiss="dialog" type="button" class="btn btn-danger">取消</button>
							</td>
						</tr>
						
					</table>
			
			
			
		
		</div>
</form>
</body>
</html>
<script type="text/javascript">
var sexData = [{ id: 'Y', text: '是' }, { id:'N', text: '否' }];
$(function(){

 $("#hasInvoice").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: sexData
        });

})
	//协议导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/contract/showImportDialog";
		importDialog = new xDialog(url, {}, {title:"协议导入",width:650,height:330 });
		return true;
	});
		//核销导入窗口
	$("#importBtn2").bind("click",function(){
		var url = "${contextPath}/contract/showImportDialog2";
		importDialog = new xDialog(url, {}, {title:"核销导入",width:650,height:330 });
		return true;
	});
	
	
	$(".btn-danger").bind("click",function(){
		var url = "${contextPath}/contract/query";
		window.location.href=url;
 	});
 	$("#edit").bind("click",function(){
		layer.confirm("确认修改?",function(){
					$.ajax({
						url : "${contextPath}/contract/updateContract",
						type : "post",
						dataType:"json",
						async: false,
						data : $("#dataForm").serialize(),
						success : function(result) {
							layer.alert('修改成功！',function(){
							//window.location.reload();
							window.location="${contextPath}/contract/query";
							})
						},
						error:function(){
					layer.msg('修改失败！') ;
				}
						})
						
				})
 	});
	
</script>