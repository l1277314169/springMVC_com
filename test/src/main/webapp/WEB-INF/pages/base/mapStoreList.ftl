
<#list stores as s>
	<div class="map_list_main_bg" storeid='${s.storeId}' lng='${s.longitude}' lat='${s.latitude}' >
		<div class="list_map_storeName">店名：${s.storeName}</div>
		<div>地址：${s.addr}</div>
		<div>编号：${s.storeNo}</div>
	</div>
</#list>
	<input type="hidden" id="_page" name="_page" value="${page}" />
	<input type="hidden" id="_allPages" name="_allPages" value="${allPages}" />
	<div class="left_page">
		<a id="left_page_home">首页</a>
		<a id="left_page_pre">上一页<a>
		<a id="left_page_next">下一页<a>
		<a id="left_page_end">尾页</a>
		<a>${page}/${allPages}页</a>
	</div>