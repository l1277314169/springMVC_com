<!--LOGO-->
<div id="header" style="margin-top:0px;">
  <h1><a href="index.html" style="font-size:20px;" id="PIMG"></a></h1>
</div>
<!--end LOGO--> 

<!-- begin 顶部菜单 -->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">欢迎您 <@shiro.principal property="name" /></span><b class="caret"></b></a>
      <ul class="dropdown-menu" style="height:120px;">
        <li><a href="#"><i class="icon-user"></i> 个人信息</a></li>
        <li class="divider"></li>
        <@shiro.hasPermission name="C2M3F9">
       		 <li ><a href="#" id="password" ><i class="icon-check"></i> 修改密码</a></li>
        </@shiro.hasPermission>
        <li class="divider"></li>
        <li><a href="${contextPath}/logout"><i class="icon-key"></i> 退出</a></li>
      </ul>
    </li>
    <li class="dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">信息</span><!-- <span class="label label-important">5</span>--> <b class="caret"></b></a>
      <ul class="dropdown-menu" style="height:160px;">
        <li><a class="sAdd" title="" href="#"><i class="icon-plus"></i> 未读信息</a></li>
        <li class="divider"></li>
        <li><a class="sInbox" title="" href="#"><i class="icon-envelope"></i> 收取信息</a></li>
        <li class="divider"></li>
        <li><a class="sOutbox" title="" href="#"><i class="icon-arrow-up"></i> 发送信息</a></li>
        <li class="divider"></li>
        <li><a class="sTrash" title="" href="#"><i class="icon-trash"></i> 已删除</a></li>
      </ul>
    </li>
    <li class=""><a title="" href="${contextPath}/logout"><i class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
  </ul>
</div>
<!-- end 顶部菜单 -->

<!-- 左侧菜单 -->
<div id="sidebar">
  
</div>
<!-- end 左侧菜单 -->
<script type="text/javascript">
	$(document).ready(function() {
		jQuery.ajax({
		  url: '${contextPath}/getMenus?privCode=${privCode}',
		  type: 'POST',
		  dataType: 'html',
		  complete: function(xhr, textStatus) {
		   	
		  },
		  success: function(data, textStatus, xhr) {
		    $("#sidebar").html(data);
		    var logoPrefix = $("#logoPrefix").val();
		    if(logoPrefix!=''){
		    	var img = "<img src='${contextPath}/img/"+logoPrefix+"logo.png' id='img_logo' style='border:0px;align:center;margin-top:15px;'/>";
		    	$("#PIMG").html(img);
		    }else{
		    	var img = "<img src='${contextPath}/img/logo.png' id='img_logo' style='border:0px;align:center;margin-top:15px;'/>";
		    	$("#PIMG").html(img);
		    }
		    openMenu();
		  },
		  error: function(xhr, textStatus, errorThrown) {
		    
		  }
		});
		
		function openMenu(){
			var lis = $(".currentM").children("ul").children("li");
			$(".currentM").children("ul").show();
			$.each(lis, function(index, val) {
				 $(val).show();
			});
		}
		
	});
</script>