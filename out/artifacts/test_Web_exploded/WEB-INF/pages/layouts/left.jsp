<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>left</title>
</head>
<body>
<%System.out.println("this is just the left.jsp !!!");%>
<div id="sidebar">
  <ul id="leftMenu">
  </ul>
</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var url = '<%=request.getContextPath()%>/layouts/ajaxLeft';
	$.ajax({  
	    type: 'GET',
	    contentType : 'application/json',
	    url: url,
		dataType : 'json',
		success : function(data) {
			//alert('success');
			$.each(data, function(i, item) {
				var hm = "<li class='submenu'><a href='#'><i class='icon "+item.cssClass+"'></i><span>"+item.privName+"</span></a>";
				hm += "<ul>";
				$.each(item.childMenus, function(j, item_ch) {
					hm += "<li><a class='linkPage' href='"+item_ch.url+"' target=''>"+item_ch.privName+"</a></li>";
				});
				hm += "</ul></li>"
				
				$('#leftMenu').append(hm);
			});
		},
		error : function(text) {
			alert('菜单加载失败！');
		}
	});
}); 
</script>
</html>