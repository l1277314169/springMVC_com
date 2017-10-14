
<html>
<#assign base=request.contextPath />
<head>
    <meta charset="utf-8">
    <title>角色</title>
<#include "comm/head.ftl">
<#include "comm/foot.ftl"/>
<#import "comm/paginationMonitor.ftl" as pagination/>
</head>
<body>
<div>
    <form action="${base}/role/query" method="post" name="userForm" id="userForm">
        <table>
            <tbody>
            <tr>
                <td>
                    <label>角色名称：</label>
                </td>
                <td>
                    <input type="text" name="name" id="a1" ng-model="name"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div id="page-content" class="clearfix">
    <div id="result" class="clearfix">
        <div id="div1" class="row-fluid">
            <table id="table_report" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>id</th>
                    <th>角色名称</th>
                    <th>描述</th>
                    <th width="120px">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list pageParam.items as x>
                <tr>
                    <td>${x.id}</td>
                    <td>${x.role}</td>
                    <td>fff</td>
                    <td width="80px">
                            <a href="javascript:void(0)" ng-click="showAllocationPage($index)">分配权限</a>
                            <a href="javascript:void(0)" ng-click="showUpdateData($index)">修改</a>
                            <a href="javascript:void(0)" ng-click="del($index)">删除</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        <#--分页标签-->
        <@pagination.paging pageParam/>
        </div>
    </div>
</div>
</body>
</html>
