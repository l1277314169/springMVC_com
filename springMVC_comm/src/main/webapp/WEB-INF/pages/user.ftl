<!DOCTYPE html>
<html>
<#assign base=request.contextPath />
<head>
    <meta charset="utf-8">
    <title></title>

    <script type="text/javascript" src="${base}/js/angular.min.js" ></script>
    <script type="text/javascript" src="${base}/js/jquery-1.9.0.min.js" ></script>

</head>
<body>
<div ng-app="myApp" ng-controller="myCtrl">
    <div id="div1">
        <label>姓名：</label></label><input type="text"  name="name" id="a1" ng-model="name"/>
        <a href="javascript:void(0)" >查询</a>
        <table border="1px" cellpadding="0" cellspacing="0" style="margin: 20px;">
            <tr style="background-color: #3F87FE;color: #FFFFFF;">
                <th>id</th>
                <th>姓名</th>
                <th>密码</th>
                <th width="10px">操作</th>
            </tr>
            <#list userList as x>
                <tr>
                    <td>${x.id}</td>
                    <td>${x.username}</td>
                    <td>${x.password}</td>
                    <td width="80px">
                        <@shiro.hasPermission name="C1M1F2">
                            <a href="javascript:void(0)" ng-click="showUpdateData($index)">修改</a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="C1M1F3">
                            <a href="javascript:void(0)" ng-click="del($index)">删除</a>
                        </@shiro.hasPermission>
                    </td>
                </tr>
            </#list>
        </table>
    </div>
</div>
</body>
</html>
