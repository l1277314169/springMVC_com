<html>  
<body  style="font-family:'Arial Unicode MS'">
	 
  <table align="center" >
  <tr align="center"><h2 align="center" > 2015年联合会员招募项目合作协议</h2></tr>
  		<tr>
  		<td >本合作协议（下称“本协议”）由以下双方于
  		<#if wc.signDate??>
  		${wc.signDate?string("yyyy年MM月dd日")}
  		</#if>
  		在（     ）订立：</td>
  		</tr>
	  	<tr>
	  		<td>甲方：
	  		上海奥维思市场营销服务有限公司</td>
	  	</tr>
	  	<tr>
	  		<td>乙方(母婴店)：
	  		<#if wc.storeName??>
	  		${wc.storeName}</td>
	  		</#if>
	  	</tr>
	  	<tr>
	  		<td >鉴于，双方有意就惠氏妈妈俱乐部会员与乙方联合会员招募进行合作，在自愿平</td>
	  	</tr>
	  	<tr>
	  		<td >等的基础上，双方协商一致达成本协议如下：</td>
	  	</tr>
	  	 <tr>
	  		<th  align="left">一、合作描述：</th>
	  	</tr>
	  	<tr>
	  		<td  >1、合作期间：
	  		<#if wc.startDate??>
	  		${wc.startDate?string("yyyy年MM月dd日")}
	  		</#if> 至
	  		<#if wc.endDate??>
	  		  ${wc.endDate?string("yyyy年MM月dd日")}
	  		</#if>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td  >2、在合作期间内，甲方与乙方就联合会员招募：</td>
	  	</tr>
	  	<tr>
	  		<td  >乙方负责：</td>
	  	</tr>
	  	 	<tr>
	  		<td  >A. 提供专用陈列位置（入口处/收银台）用以安放 X 展架和台卡</td>
	  	</tr>
	  	 	<tr>
	  		<td  >B. 配合联合会员招募的海报、吊旗和宣传单张等宣传工具使用</td>
	  	</tr>
	  	 	<tr>
	  		<td  >C. 开放乙方的 APP/微信公众号/妈妈班/消费者活动等系统和平台宣传</td>
	  	</tr>
	  		<tr>
	  		<td  >推广联合招募（具体内容由双方商定）</td>
	  	</tr>
	  	<tr>
	  		<td  >D. 开展店长/店员动员和培训，了解相关流程，配合联合会员招募</td>
	  	</tr>
	  	<tr>
	  		<td  >E. 指导门店或店员手机安装“会员 招募验证 APP 小助手”</td>
	  	</tr>
	  	<tr>
	  		<td  >F. 乙方借用惠氏妈妈俱乐部会员系统对乙方的会员数据进行管理</td>
	  	</tr>
	  	<tr>
	  		<td  >3、服务费</td>
	  	</tr>
	  	 	<tr>
	  		<td  >就以上乙方在联合会员招募中发生的成本，甲方同意支付<#if money??>${money}</#if>元的联合</td>
	  	</tr>
	  	 	<tr>
	  		<td  >会员招募服务费。具体金额按实际发生。</td>
	  	</tr>
	  <tr>
	  	<td   >4、执行方案：</td>
	  	</tr>
	  <tr>
	  	<td  >联合会员招募礼品发放预计：</td>
	  </tr>
	  </table>
	  <table align="center"  border="1" cellspacing="0" >
	  
	   <tr>
	   	<td></td>
	   	<#if wcds??>
	    <#list wcds as wcd>
	  	<td>${wcd.monthId?c}</td>
	  	</#list>
	  	</#if>
	  </tr>
	  <tr>
	  	<td>预计招募礼发放份数</td>
	  	<#if wcds??>
	  	 <#list wcds as wcd>
	  	<td>${wcd.value}</td>
	  	</#list>
	  	</#if>
	  	
	  </tr>
	  
	  </table>
	<table align="center" >
	  <tr>
	  	<td>5、联合会员招募费用甲方按月支付乙方，支付金额按甲方确认的实际联合会员</td>
	  </tr>
	   <tr>
	  	<td>招募数量进行结算。乙方应提供符合甲方要求的支持文件及合法有效的发票，甲</td>
	  </tr>
	   <tr>
	  	<td>方同意承担相应税点。</td>
	  </tr>
	   <tr>
	  	<td>6、甲方会对招募的联合会员质量进行一定比例抽查，用以确认实际招募会员数</td>
	  </tr>
	  <tr>
	  	<td>量 。 如发现乙方有严重不诚信行为，甲方有权即刻终止本协议。</td>
	  </tr>
	  <tr>
	  <td>7、联合会员招募项目不涉及0-12个月产品推广和宣传</td>
	  </tr>
	  <tr>
	  <th align="left">二、乙方保证</th>
	  </tr>
	  <tr>
	  <td>1、乙方向甲方保证，乙方履行本协议不会违反其组成文件、公司章程、营业执</td>
	  </tr>
	 <tr>
	  <td>照记载的经营范围和方式、任何与第三方达成的协议/合同或者承诺及任何政府</td>
	  </tr>
	  <tr>
	  <td>或者司法/仲裁机构对其的要求。</td>
	  </tr>
	 <tr>
	  <td>2、乙方向甲方保证，乙方拥有充足和完全的能力履行本协议项下义务。</td>
	  </tr>
	   <tr>
	  <th align="left">三、保密和物品返还</th>
	  </tr>
	   <tr>
	  <td>乙方确认，其仅应出于履行本协议项下义务的目的使用 （Ⅰ）从甲方或通</td>
	  </tr>
	   <tr>
	  <td>过甲方获得的惠氏妈妈俱乐部材料、产品、广告宣传画、促销资料、设计图、模</td>
	  </tr>
	   <tr>
	  <td>板、工具、图纸、草图、模型、样品、记录和文件等一切财产，和/或（Ⅱ）乙</td>
	  </tr>
	   <tr>
	  <td>方通过甲方获得的惠氏营养品的公司名称、商号、标志、商标、标识或其他类似</td>
	  </tr>
	   <tr>
	  <td>标记（以下统称“惠氏营养品商标/标记”）的贴纸、包装或相关物品等。在甲方要</td>
	  </tr>
	   <tr>
	  <td>求时或合作期届满或本协议终止时（以较早发生者为准），乙方应将本条描述的</td>
	  </tr>
	   <tr>
	  <td>前述财产和/或物品悉数交还甲方，或者在甲方提出明示指示的前提下将前述财</td>
	  </tr>
	   <tr>
	  <td>产和/或物品予以销毁，乙方不得保留任何该等财产和/或物品或其复制品，或向</td>
	  </tr>
	   <tr>
	  <td>任何第三方提供、披露、销售、允许其使用前述财产和/或物品或其复制品。</td>
	  </tr>
	   <tr>
	  <th align="left">四、法律适用和争议解决</th>
	  </tr>
	   <tr>
	  <td>1、本协议一切事宜适用中国法律并依其解释。</td>
	  </tr>
	   <tr>
	  <td>2、凡在执行本协议或与本协议有关的事情时，若发生争议，本着平等互利的原</td>
	  </tr>
	   <tr>
	  <td>则，先应友好协商，如协商不成，则任何一方有权将前述争议提交至上海国际经</td>
	  </tr>
	   <tr>
	  <td>济贸易仲裁委员会根据该委员会届时有效的仲裁规则在上海进行， 仲裁裁决对双</td>
	  </tr>
	   <tr>
	  <td>方均具有约束力。</td>
	  </tr>
	   <tr>
	   <th align="left">五、反腐败和反贿赂规定</th>
	  </tr>
	   <tr>
	  <td></td>
	  </tr>
	   <tr>
	  <td>提供任何好处，以不恰当的影响或者收买政府官员，双方均不能支付或者转移，</td>
	  </tr>
	   <tr>
	  <td>接受或者暗示索取任何具有公开或者商业贿赂性质的回扣或者其他非法的或者</td>
	  </tr>
	   <tr>
	  <td>不正当的利益以获取生意。 双方均同意遵守任何适用于双方的任何的反贿赂反腐</td>
	  </tr>
	   <tr>
	  <td>败的法律、法规。</td>
	  </tr>
	   <tr>
	 
	    <th align="left">六、WHO Code  FTSE4GOOD</th>
	  </tr>
	   <tr>
	  <td>双方保证并遵照执行世界卫生组织《国际母乳代用品销售守则》、中国《母乳</td>
	  </tr>
	   <tr>
	  <td>代用品销售管理办法》以及中国政府颁布的相关法规、雀巢公司关于实施世界卫</td>
	  </tr>
	   <tr>
	  <td>生组织《国际母乳代用品销售守则》的政策与指示及雀巢宪章、惠氏营养品中国</td>
	  </tr>
	   <tr>
	  <td>《母乳代用品销售政策》的政策与规程以及惠氏营养品中国的相关政策，不针对</td>
	  </tr>
	   <tr>
	  <td>0-12 个月婴幼儿配方奶粉进行广告宣传和任何形式的促销。所有礼品不适用于</td>
	  </tr>
	   <tr>
	  <td>0-12 个月的婴幼儿。</td>
	  </tr>
	   <tr>
	  <td>七、协议文本</td>
	  </tr>
	    <tr>
	  <td>  本协议一式贰（2）份，双方各执壹（1）份。兹此，本协议由双方正式授</td>
	  </tr>
	    <tr>
	  <td>权的代表在本协议文首载明的时间和地点签署本协议，本协议自签字之日起生</td>
	  </tr>
	    <tr>
	  <td>效。</td>
	  </tr>
	  <tr>
		 <span>甲方:上海奥维思市场营销服务有限公司</span>
		 <span style="margin-left:8px;">乙方:<#if wc.storeName??>${wc.storeName}</#if></span>
	</tr>
	  <tr>
		 <span>代表:</span>
		 <span style="margin-left:250px;">代表:</span>
	</tr>
	  <tr>
		 <span>盖章:</span>
		 <span style="margin-left:250px;">盖章:</span>
	</tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
</table>
<table align="center" >
  <tr align="center"><h2 align="center" > 回执</h2></tr>
  <tr>
  <td>乙方：<#if wc.storeName??>${wc.storeName}</#if> 同意配合甲方：上海奥维思市场营销服务有</td>
  </tr>
   <tr>
  <td>限公司在门店进行联合会员招募活动。</td>
  </tr>
     <tr>
  <td>乙方承诺将为联合会员招募投入：</td>
  </tr>
    <tr>
  <td>
  <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("A")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>
  A. 专用陈列位置(入口处/收银台)用以安放 X 展架和台卡</td>
  </tr>
     <tr>
  <td>
  <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("B")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>
  B. 配合联合会员招募的海报、吊旗和宣传单张等宣传工具使用</td>
  </tr>
     <tr>
  <td>
  <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("C")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>
  C. 乙方 APP/微信公众号/妈妈班/消费者活动等系统和平台免费宣传推广联</td>
  </tr>
     <tr>
  <td>合招募（具体内容由双方商定）</td>
  </tr>
     <tr>
  <td>
 <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("D")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>
  D. 开展店长/店员动员和培训，了解相关流程，配合会员联合招募</td>
  </tr>
     <tr>
  <td>
 <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("E")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>
  E. 指导门店或店员手机安装”会员招募验证 APP 小助手”</td>
  </tr>
    <tr>
  <td>
 <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("F")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>
  F. 确保惠氏陈列面积（金装+启赋）<#if wc.valOfShelf??>${wc.valOfShelf}</#if> 节</td>
  </tr>
    <tr>
  <td>
  <#if wc.optionsOfInvest??>
  <#if wc.optionsOfInvest?contains("G")>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	<#else>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
 </#if>G. 其他支持内容<#if wc.otherInvest??>${wc.otherInvest}</#if></td>
  </tr>
    <tr>
  <td>乙方联合会员招募项目开票信息：</td>
  </tr>
  <tr>
  <td>
   <#if wc.hasInvoice??>
  	<#if wc.hasInvoice=='Y'>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	 </#if>
  	<#if wc.hasInvoice=='N'>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  	</#if>
  </#if>
  A. 提供联合会员礼品采购的销售发票复印件</td>
  
  </tr>
    <tr>
  <td>
   <#if wc.hasInvoice??>
  	<#if wc.hasInvoice=='Y'>
  	<img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  	 </#if>
  	<#if wc.hasInvoice=='N'>
  	<img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  	</#if>
  </#if>
  B. 提供联合会员招募服务费发票原件</td>
  </tr>
  <tr>
  <td>
  <#if wc.otherInvoice??&&wc.otherInvoice!=''>
  <img  href="${contextPath}/2_05.png" src="${contextPath}/2_05.png"/>
  <#else>
  <img  href="${contextPath}/2_03.png" src="${contextPath}/2_03.png"/>
  </#if>
  C. 其他:<#if wc.otherInvoice??&&wc.otherInvoice!=''>${wc.otherInvoice}</#if></td>
  </tr>
  <tr>
  <td>乙方费用核销账号：（账号号码）${wc.bankAcct}</td>
  </tr>
  <tr>
  <td><span style="margin-left:144px;">（账号户名）${wc.acctHolder}</span></td>
  </tr>
   <tr>
  <td><span style="margin-left:144px;">（开户行）${wc.bankName}</span></td>
  </tr>
  <tr>
  <td>如无法提供对公账号请说明原因：(私人账户名需为营业执照法人，并请同时</td>
  </tr>
   <tr>
  <td>提供营业执照复印件备案)</td>
  </tr>
<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
 </table>
   <table align="center" >
  <tr align="center"><h2 align="center" >2015年会员招募礼品采购协议</h2></tr>
  		<tr>
  		<td >本合作协议（下称“本协议”）由以下双方于
  		<#if wc.signDate??>
  		${wc.signDate?string("yyyy年MM月dd日")}
  		</#if>
  		在（     ）订立：</td>
  		</tr>
	  	<tr>
	  		<td>甲方：
	  		上海奥维思市场营销服务有限公司</td>
	  	</tr>
	  	<tr>
	  		<td>乙方(母婴店)：
	  		<#if wc.storeName??>
	  		${wc.storeName}</td>
	  		</#if>
	  	</tr>
	  	<tr>
	  		<td >鉴于，甲方有意就惠氏妈妈俱乐部会员招募向乙方进行礼品采购，在自愿平等的</td>
	  	</tr>
	  	<tr>
	  		<td >基础上，双方协商一致达成本协议如下：</td>
	  	</tr>
	  	 <tr>
	  		<th  align="left">一、合作描述：</th>
	  	</tr>
	  	<tr>
	  		<td  >1、合作期间：
	  		<#if wc.startDate??>
	  		${wc.startDate?string("yyyy年MM月dd日")}
	  		</#if> 至
	  		<#if wc.endDate??>
	  		  ${wc.endDate?string("yyyy年MM月dd日")}
	  		</#if>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td  >2、在合作期间内，甲方向乙方进行礼品采购用以支持惠氏妈妈俱乐部会员招募活</td>
	  	</tr>
	  	<tr>
	  		<td  >动：</td>
	  	</tr>
	  	<tr>
	  		<td  >1）甲方向乙方采购价值为30元/份（含税）的会员招募礼<#if money2??>${money2}</#if>元</td>
	  	</tr>
	  	 	<tr>
	  		<td  >（礼品不适用于0-12个月婴幼儿）用于在乙方店内招募惠氏妈妈俱乐部会员，并</td>
	  	</tr>
	  	 	<tr>
	  		<td  >按照实际礼品发放数量进行费用计算。</td>
	  	</tr>
	  	 	<tr>
	  		<td  >2）乙方负责提供1)所列礼品（礼品不适用于0-12个月婴幼儿）用于店内惠</td>
	  	</tr>
	  		<tr>
	  		<td  >氏妈妈俱乐部会员招募活动，按照双方约定发放礼品并提供礼品的发放明细给甲</td>
	  	</tr>
	  	<tr>
	  		<td  >方。</td>
	  	</tr>
	  	<tr>
	  		<td  >3、礼品费用</td>
	  	</tr>
	  	
	  <tr>
	  	<td  >1）会员招募礼品发放预计：</td>
	  </tr>
	  </table>
	  <table align="center"  border="1" cellspacing="0" >
	  
	   <tr>
	   	<td></td>
	   	<#if wcds2??>
	    <#list wcds2 as wcd>
	  	<td>${wcd.monthId?c}</td>
	  	</#list>
	  	</#if>
	  </tr>
	  <tr>
	  	<td>预计招募礼发放份数</td>
	  	<#if wcds2??>
	  	 <#list wcds2 as wcd>
	  	<td>${wcd.value}</td>
	  	</#list>
	  	</#if>
	  	
	  </tr>
	  
	  </table>
	<table align="center" >
	  <tr>
	  	<td>每月乙方向甲方提供上个月礼品发放明细，甲方确认无误后通知乙方开具发</td>
	  </tr>
	   <tr>
	  	<td>票，并在收到乙方开具的发票后支付礼品费用。</td>
	  </tr>
	  <tr>
	  <th align="left">二、乙方保证</th>
	  </tr>
	  <tr>
	  <td>1、乙方向甲方保证，乙方履行本协议不会违反其组成文件、公司章程、营业执</td>
	  </tr>
	 <tr>
	  <td>照记载的经营范围和方式、任何与第三方达成的协议/合同或者承诺及任何政府</td>
	  </tr>
	  <tr>
	  <td>或者司法/仲裁机构对其的要求。</td>
	  </tr>
	 <tr>
	  <td>2、乙方向甲方保证，乙方拥有充足和完全的能力履行本协议项下义务。</td>
	  </tr>
	   <tr>
	  <th align="left">三、保密和物品返还</th>
	  </tr>
	   <tr>
	  <td>乙方确认，其仅应出于履行本协议项下义务的目的使用 （Ⅰ）从甲方或通</td>
	  </tr>
	   <tr>
	  <td>过甲方获得的惠氏妈妈俱乐部材料、产品、广告宣传画、促销资料、设计图、模</td>
	  </tr>
	   <tr>
	  <td>板、工具、图纸、草图、模型、样品、记录和文件等一切财产，和/或（Ⅱ）乙</td>
	  </tr>
	   <tr>
	  <td>方通过甲方获得的惠氏营养品的公司名称、商号、标志、商标、标识或其他类似</td>
	  </tr>
	   <tr>
	  <td>标记（以下统称“惠氏营养品商标/标记”）的贴纸、包装或相关物品等。在甲方要</td>
	  </tr>
	   <tr>
	  <td>求时或合作期届满或本协议终止时（以较早发生者为准），乙方应将本条描述的</td>
	  </tr>
	   <tr>
	  <td>前述财产和/或物品悉数交还甲方，或者在甲方提出明示指示的前提下将前述财</td>
	  </tr>
	   <tr>
	  <td>产和/或物品予以销毁，乙方不得保留任何该等财产和/或物品或其复制品，或向</td>
	  </tr>
	   <tr>
	  <td>任何第三方提供、披露、销售、允许其使用前述财产和/或物品或其复制品。</td>
	  </tr>
	   <tr>
	  <th align="left">四、法律适用和争议解决</th>
	  </tr>
	   <tr>
	  <td>1、本协议一切事宜适用中国法律并依其解释。</td>
	  </tr>
	   <tr>
	  <td>2、凡在执行本协议或与本协议有关的事情时，若发生争议，本着平等互利的原</td>
	  </tr>
	   <tr>
	  <td>则，先应友好协商，如协商不成，则任何一方有权将前述争议提交至上海国际经</td>
	  </tr>
	   <tr>
	  <td>济贸易仲裁委员会根据该委员会届时有效的仲裁规则在上海进行， 仲裁裁决对双</td>
	  </tr>
	   <tr>
	  <td>方均具有约束力。</td>
	  </tr>
	   <tr>
	   <th align="left">五、反腐败和反贿赂规定</th>
	  </tr>
	   <tr>
	  <td></td>
	  </tr>
	   <tr>
	  <td>提供任何好处，以不恰当的影响或者收买政府官员，双方均不能支付或者转移，</td>
	  </tr>
	   <tr>
	  <td>接受或者暗示索取任何具有公开或者商业贿赂性质的回扣或者其他非法的或者</td>
	  </tr>
	   <tr>
	  <td>不正当的利益以获取生意。 双方均同意遵守任何适用于双方的任何的反贿赂反腐</td>
	  </tr>
	   <tr>
	  <td>败的法律、法规。</td>
	  </tr>
	   <tr>
	 
	    <th align="left">六、WHO Code  FTSE4GOOD</th>
	  </tr>
	   <tr>
	  <td>双方保证并遵照执行世界卫生组织《国际母乳代用品销售守则》、中国《母乳</td>
	  </tr>
	   <tr>
	  <td>代用品销售管理办法》以及中国政府颁布的相关法规、雀巢公司关于实施世界卫</td>
	  </tr>
	   <tr>
	  <td>生组织《国际母乳代用品销售守则》的政策与指示及雀巢宪章、惠氏营养品中国</td>
	  </tr>
	   <tr>
	  <td>《母乳代用品销售政策》的政策与规程以及惠氏营养品中国的相关政策，不针对</td>
	  </tr>
	   <tr>
	  <td>0-12 个月婴幼儿配方奶粉进行广告宣传和任何形式的促销。所有礼品不适用于</td>
	  </tr>
	   <tr>
	  <td>0-12 个月的婴幼儿。</td>
	  </tr>
	   <tr>
	  <td>七、协议文本</td>
	  </tr>
	    <tr>
	  <td>  本协议一式贰（2）份，双方各执壹（1）份。兹此，本协议由双方正式授</td>
	  </tr>
	    <tr>
	  <td>权的代表在本协议文首载明的时间和地点签署本协议，本协议自签字之日起生</td>
	  </tr>
	    <tr>
	  <td>效。</td>
	  </tr>
	  <tr>
		 <span>甲方:上海奥维思市场营销服务有限公司</span>
		 <span style="margin-left:8px;">乙方:<#if wc.storeName??>${wc.storeName}</#if></span>
	</tr>
	  <tr>
		 <span>代表:</span>
		 <span style="margin-left:250px;">代表:</span>
	</tr>
	  <tr>
		 <span>盖章:</span>
		 <span style="margin-left:250px;">盖章:</span>
	</tr>
	
</table>
</body>  
</html>