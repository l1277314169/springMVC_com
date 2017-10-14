<!-- 底部 -->
<div class="row-fluid">
  <div id="footer" class="span12">Copyright &copy; 2014-2015  Mobilizer Information Technology Co., Ltd.</div>
</div>
<!--end 底部 -->
<script type="text/javascript">
	$(document).ready(function() {
		var content_h =  $("#content").height();
		var userNav_h =  $("#user-nav").outerHeight();
		var footer_h =  $("#footer").outerHeight();
		var header_h =  $("#header").outerHeight();
		
		//alert("content_h:"+content_h+"userNav_h:"+userNav_h+"footer_h:"+footer_h);
		var b_h = $(window).height();
		
		var this_h = content_h + userNav_h +footer_h;
		
		var height = (b_h > this_h?b_h:this_h);
		//alert("b_h:"+b_h+"this_h:"+this_h);
		
		$(".main_body").height(height-header_h-10);
	});
</script>