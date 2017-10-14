<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "/common/head.ftl" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${contextPath}/css/bat.css" type="text/css">
<link rel="stylesheet" href="${contextPath}/css/header.css" type="text/css">
<script type="text/javascript" src="${contextPath}/js/jquery-1.7.min.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
function logout(url){
	window.parent.location.href= url;
}
</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="194" height="0" align="center" bgcolor="#999999" class="logo">Channel Plus</td>
			<td align="center" valign="bottom">
				<table cellspacing="0" cellpadding="0" border="0" width="100%" height="55">
					<tbody>
						<tr bgcolor="#999999">
							<td width="30" align="left">
							</td>
							<td width="320" align="left">&nbsp;</td>
							<td align="right" nowrap="nowrap" class="topbar">&nbsp;</td>
							<td width="150"align="left" valign="middle" class="topbar">
								<a id="viewUserInfo" href="javascript:void(0)" class="l-btn">
									<span class="l-btn-left">
										<span class="l-btn-text">${loginName!''}</span>
										<span class="m-btn-downarrow"></span>
										<span class="m-btn-line"></span>
									</span>
								</a>
								<a href="javascript:void(0)" class="l-btn" onclick="logout('${contextPath}/logout');"><span class="l-btn-text">退出</span></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td></td>
						</tr>
					</tbody>
				</table>
				<span></span>
			</td>
		</tr>
		<tr height="6">
			<td bgcolor="#1F3A65"></td>
		</tr>
	</table>
	<div id="userMenu" style="width:100px;z-index: 110119; display:none">
		<div>个人资料</div>
		<div>修改密码</div>
	</div>
<script language="javascript">
$(function(){
	//$('#viewUserInfo').myHoverTip('userMenu');
});

<!--
var displayBar=true;
function switchBar(obj){
	if (displayBar)
	{
		parent.frame.cols="0,*";
		displayBar=false;
		obj.title="打开左边管理菜单";
	}
	else{
		parent.frame.cols="195,*";
		displayBar=true;
		obj.title="关闭左边管理菜单";
	}
}

	$.fn.myHoverTip = function(divId) {  
	    var div = $("#" + divId); //要浮动在这个元素旁边的层
	    div.css("position", "absolute");//让这个层可以绝对定位
	    var self = $(this); //当前对象
	    self.hover(function() {
	        div.css("display", "block");
	        var p = self.position(); //获取这个元素的left和top
	        var h = self.height();
	        //alert(self.height());
	        //alert(p.top);
	        var x = p.left + self.width();//获取这个浮动层的left
	        var docWidth = $(document).width();//获取网页的宽  
	        if (x > docWidth - div.width() - 20) {  
	            x = p.left - div.width();  
	        }  
	        div.css("left", x);  
	        div.css("top", p.top+h);  
	        div.show();  
	    },  
	    function() {  
	        div.css("display", "none");  
	    }  
	    );  
	    return this;  
	}
	//-->
</script>
</body>
</html>
