<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>会员招募</title>
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
			
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;">
					<table class="table table-bordered data-table" id="c_list" style="margin-top:10px">
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
						
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201508>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201509>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201510>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201511>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201512>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201601>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201602>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201603>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201604>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201605>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201606>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==1 &&s.monthId==201607>${s.value}</#if></#list></td>
						</tr>
						
						
					</table>
			</div>
			
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;margin-top:20px;">
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
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201508>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201509>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201510>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201511>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201512>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201601>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201602>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201603>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201604>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201605>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201606>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==2 &&s.monthId==201607>${s.value}</#if></#list></td>
						</tr>
					</table>
			</div>
			
			
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;margin-top:20px;">
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
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201508>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201509>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201510>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201511>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201512>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201601>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201602>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201603>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201604>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201605>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201606>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==3 &&s.monthId==201607>${s.value}</#if></#list></td>
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
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201508>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201509>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201510>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201511>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201512>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201601>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201602>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201604>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201604>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201605>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201606>${s.value}</#if></#list></td>
							<td class="fill_td"><#list wcds as s><#if s.dataType==4 &&s.monthId==201607>${s.value}</#if></#list></td>
						</tr>
					</table>
			</div>
			
			
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;margin-top:20px;">
					<table class="table table-bordered data-table" id="c_list">
	    				<tr>
							<th class="fill_td">招募礼</th>
							<th class="fill_td">门店价格</th>
							<th class="fill_td">其他招募礼（名称）</th>
							<th class="fill_td">门店价格</th>
							<th class="fill_td">具体内容</th>
							<th class="fill_td">F确保陈列面积（节）</th>
							<th class="fill_td">其他支持内容</th>
						</tr>
	    				<tr>
							<td class="fill_td">${wc.gift}</td>
							<td class="fill_td">${wc.giftPrice}</td>
							<td class="fill_td">${wc.otherGift}</td>
							<td class="fill_td">${wc.otherPrice}</td>
							<td class="fill_td">${wc.optionsOfInvest}</td>
							<td class="fill_td">${wc.valOfShelf}</td>
							<td class="fill_td">${wc.otherInvest}</td>
						</tr>
					</table>
			</div>
			
			
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;margin-top:20px;">
					<table class="table table-bordered data-table" id="c_list">
	    				<tr>
							<th class="fill_td">是否能够提供发票</th>
							<th class="fill_td">发票类型</th>
							<th class="fill_td">其他</th>
							<th class="fill_td">类型</th>
							<th class="fill_td">账号类型</th>
							<th class="fill_td">开户行</th>
							<th class="fill_td">账号</th>
							<th class="fill_td">时间</th>
							<th class="fill_td">礼品发放数量</th>
							<th class="fill_td">会员数量</th>
							<th class="fill_td">收款人</th>
						</tr>
	    				<tr>
							<td class="fill_td">${wc.hasInvoice}</td>
							<td class="fill_td">${wc.invoiceType}</td>
							<td class="fill_td">${wc.otherInvoice}</td>
							<td class="fill_td">${wc.licenseType}</td>
							<td class="fill_td">${wc.acctType}</td>
							<td class="fill_td">${wc.bankName}</td>
							<td class="fill_td">${wc.bankAcct}</td>
							<td class="fill_td">${wy.monthId}</td>
							<td class="fill_td">${wy.numOfGift}</td>
							<td class="fill_td">${wy.numOfMember}</td>
							<td class="fill_td">${wc.acctHolder}</td>
						</tr>
						
						<tr>
							<td colspan="11"  style="text-align:center;" >
								<button data-dismiss="dialog" type="button" class="btn btn-danger">返回</button>
							</td>
						</tr>
						
					</table>
			</div>
			
			
		</div>
		
</body>
</html>
<script type="text/javascript">
 
	
	
	
	$(".btn-danger").bind("click",function(){
		var url = "${contextPath}/contract/query";
		window.location.href=url;
 	});
	
</script>