<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<#assign base=request.contextPath />
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>左侧导航</title>
    <head>

        <style type="text/css">
            body {
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }

            html, body {
                height: 100%;
            }

            .top {
                width: 100%;
                height: 77px;
                background-image: url("${base}/images/top.jpg");
            }

            .top a {
                text-align: right;
                display: block;
                color: aquamarine;

            }

            img {
                border: none;
            }

            * {
                font-family: '微软雅黑';
                font-size: 12px;
                color: #626262;
            }

            dl, dt, dd {
                display: block;
                margin: 0;
            }

            a {
                text-decoration: none;
            }

            #bg {
                background-image: url(${base}/images/content/dotted.png);
            }

            .container {
                width: 100%;
                height: 100%;
                margin: auto;
            }

            /*left*/
            .leftsidebar_box {
                width: 160px;
                height: auto !important;
                overflow: visible !important;
                position: fixed;
                height: 100% !important;
                background-color: #3992d0;
            }

            .line {
                height: 2px;
                width: 100%;
                background-image: url(${base}/images/left/line_bg.png);
                background-repeat: repeat-x;
            }

            .leftsidebar_box dt {
                padding-left: 40px;
                padding-right: 10px;
                background-repeat: no-repeat;
                background-position: 10px center;
                color: #f5f5f5;
                font-size: 14px;
                position: relative;
                line-height: 48px;
                cursor: pointer;
            }

            .leftsidebar_box dd {
                background-color: #4DA5E2;
                padding-left: 40px;
            }
            .leftsidebar_box dd:hover{
                background-color: #147DC5;
                padding-left: 40px;
            }

            .leftsidebar_box dd a {
                color: #f5f5f5;
                line-height: 20px;
            }

            .leftsidebar_box dt img {
                position: absolute;
                right: 10px;
                top: 20px;
            }

            .system_log dt {
                background-image: url(${base}/images/left/system.png)
            }

            .custom dt {
                background-image: url(${base}/images/left/custom.png)
            }

            .channel dt {
                background-image: url(${base}/images/left/channel.png)
            }

            .app dt {
                background-image: url(${base}/images/left/app.png)
            }

            .cloud dt {
                background-image: url(${base}/images/left/cloud.png)
            }

            .syetem_management dt {
                background-image: url(${base}/images/left/syetem_management.png)
            }

            .source dt {
                background-image: url(${base}/images/left/source.png)
            }

            .statistics dt {
                background-image: url(${base}/images/left/statistics.png)
            }

            .leftsidebar_box dl dd:last-child {
                padding-bottom: 10px;
            }
        </style>

    </head>

<body id="bg">

<div class="top">
    <a href="javascript:void(0);" id="but">退出登录</a>
</div>
<div>
    <div class="leftsidebar_box">
        <dl class="system_log">
            <dt>系统管理${base}<img src="images/left/select_xl01.png"></dt>
            <#list activeUser.menus as menu>
                <dd><a target="mainFrame" href="${base}/${menu.url}">${menu.description}</a></dd>
            </#list>
        </dl>

        <dl class="custom">
            <dt onClick="changeImage()">客户管理<img src="images/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">客户管理</a></dd>
            <dd><a href="#">试用/成交客户管理</a></dd>
            <dd><a href="#">未成交客户管理</a></dd>
            <dd><a href="#">即将到期客户管理</a></dd>
        </dl>

        <#--<dl class="channel">-->
            <#--<dt>渠道管理<img src="images/left/select_xl01.png"></dt>-->
            <#--<dd class="first_dd"><a href="#">渠道主页</a></dd>-->
            <#--<dd><a href="#">渠道标准管理</a></dd>-->
            <#--<dd><a href="#">系统通知</a></dd>-->
            <#--<dd><a href="#">渠道商管理</a></dd>-->
            <#--<dd><a href="#">渠道商链接</a></dd>-->
        <#--</dl>-->

        <#--<dl class="app">-->
            <#--<dt onClick="changeImage()">APP管理<img src="images/left/select_xl01.png"></dt>-->
            <#--<dd class="first_dd"><a href="#">App运营商管理</a></dd>-->
            <#--<dd><a href="#">开放接口管理</a></dd>-->
            <#--<dd><a href="#">接口类型管理</a></dd>-->
        <#--</dl>-->

        <#--<dl class="cloud">-->
            <#--<dt>大数据云平台<img src="images/left/select_xl01.png"></dt>-->
            <#--<dd class="first_dd"><a href="#">平台运营商管理</a></dd>-->
        <#--</dl>-->

        <#--<dl class="syetem_management">-->
            <#--<dt>系统管理<img src="images/left/select_xl01.png"></dt>-->
            <#--<dd class="first_dd"><a href="#">后台用户管理</a></dd>-->
            <#--<dd><a href="#">角色管理</a></dd>-->
            <#--<dd><a href="#">客户类型管理</a></dd>-->
            <#--<dd><a href="#">栏目管理</a></dd>-->
            <#--<dd><a href="#">微官网模板组管理</a></dd>-->
            <#--<dd><a href="#">商城模板管理</a></dd>-->
            <#--<dd><a href="#">微功能管理</a></dd>-->
            <#--<dd><a href="#">修改用户密码</a></dd>-->
        <#--</dl>-->

        <#--<dl class="source">-->
            <#--<dt>素材库管理<img src="images/left/select_xl01.png"></dt>-->
            <#--<dd class="first_dd"><a href="#">图片库</a></dd>-->
            <#--<dd><a href="#">链接库</a></dd>-->
            <#--<dd><a href="#">推广管理</a></dd>-->
        <#--</dl>-->

        <#--<dl class="statistics">-->
            <#--<dt>统计分析<img src="images/left/select_xl01.png"></dt>-->
            <#--<dd class="first_dd"><a href="#">客户统计</a></dd>-->
        <#--</dl>-->

    </div>

    <div style="margin-left: 162px;">
        <iframe name="mainFrame" id="mainFrame" frameborder="0" style="width: 100%;height: 870px;"></iframe>
    </div>


</div>

<script type="text/javascript" src="${base}/js/jquery-1.9.0.min.js"></script>

<script type="text/javascript">
    $(".leftsidebar_box dt").css({"background-color": "#3992d0"});
    $(".leftsidebar_box dt img").attr("src", "${base}/images/left/select_xl01.png");
    $(function () {
        $(".leftsidebar_box dd").hide();
        $(".leftsidebar_box dt").click(function () {
            $(".leftsidebar_box dt").css({"background-color": "#3992d0"})
            $(this).css({"background-color": "#317eb4"});
            $(this).parent().find('dd').removeClass("menu_chioce");
            $(".leftsidebar_box dt img").attr("src", "${base}/images/left/select_xl01.png");
            $(this).parent().find('img').attr("src", "${base}/images/left/select_xl.png");
            $(".menu_chioce").slideUp();
            $(this).parent().find('dd').slideToggle();
            $(this).parent().find('dd').addClass("menu_chioce");
        });


    })
    $("#but").click(function () {
        location.href="${base}/logout";
    });
</script>
</body>
</html>
