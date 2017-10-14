<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>
	<sitemesh:write property="title" />
</title>
<sitemesh:write property="head" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/matrix-media.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/matrix-style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.gritter.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/matrix.js"></script>
</head>
<body>
<%System.out.println("this is just the default.jsp !!!");%>
<!--LOGO-->
<div id="header">
  <h1 style="background: url('<%=request.getContextPath()%>/img/logo.png') no-repeat scroll 0 0 transparent;"><a href="index.html" style="font-size:20px;">Channel Plus</a></h1>
</div>
<!--end LOGO--> 

<!-- begin 顶部菜单 -->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">欢迎您</span><b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a href="#"><i class="icon-user"></i> 个人信息</a></li>
        <li class="divider"></li>
        <li><a href="#"><i class="icon-check"></i> 修改密码</a></li>
        <li class="divider"></li>
        <li><a href="/logout"><i class="icon-key"></i> 退出</a></li>
      </ul>
    </li>
    <li class="dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">信息</span><!-- <span class="label label-important">5</span>--> <b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a class="sAdd" title="" href="#"><i class="icon-plus"></i> 未读信息</a></li>
        <li class="divider"></li>
        <li><a class="sInbox" title="" href="#"><i class="icon-envelope"></i> 收取信息</a></li>
        <li class="divider"></li>
        <li><a class="sOutbox" title="" href="#"><i class="icon-arrow-up"></i> 发送信息</a></li>
        <li class="divider"></li>
        <li><a class="sTrash" title="" href="#"><i class="icon-trash"></i> 已删除</a></li>
      </ul>
    </li>
    <li class=""><a title="" href="/logout"><i class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
  </ul>
</div>
<!-- end 顶部菜单 -->

<!-- 左侧菜单 -->
<jsp:include page="/WEB-INF/pages/layouts/left.jsp"/>
<!-- end 左侧菜单 -->

<!-- 页面内容 -->
<div id="content" style="height:100%; ">
	<!-- 导航 -->
	<div id="content-header">
		<div id="breadcrumb"> <a href="" title="回主页" class="tip-bottom"><i class="icon-home"></i> 主页</a></div>
 	</div>
	<!-- end 导航 -->
		<sitemesh:write property='body'/>	
	<!-- 底部 -->
	<div class="footer ">
		<div id="footer"> Copyright &copy; 2015  Mobilizer Information Technology Co., Ltd.</div>
	</div>
	<!--end 底部 -->
</div>
<!-- end页面内容 -->
</body>
</html>