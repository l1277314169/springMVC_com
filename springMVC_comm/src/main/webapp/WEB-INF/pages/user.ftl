
<html>

<head>
    <meta charset="utf-8">
    <title>用户</title>
<#include "comm/head.ftl">
<#include "comm/foot.ftl"/>

</head>
<body ng-app="myApp" ng-controller="myCtrl">
    <div>
        <form action="${contextPath}/user/query" method="post" name="userForm" id="userForm">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <label>姓名：</label>
                        </td>
                        <td>
                            <input type="text"  name="name" id="a1" ng-model="name"/>&nbsp;&nbsp;&nbsp;
                        </td>
                        <#--<td>-->
                            <#--<label>排序：</label>-->
                        <#--</td>-->
                        <#--<td>-->
                            <#--Sort by:<select ng-model="sortFlag" >-->
                                        <#--<option value="id">id</option>-->
                                        <#--<option value="username">用户名</option>-->
                                    <#--</select>&nbsp;&nbsp;&nbsp;-->
                        <#--</td>-->
                        <td>
                            <input id="subtn" type="submit" value="查询"/>
                            <a href="${contextPath}/user/exportExcel" >导出</a>
                            <a href="javascript:void(0)" ng-click="show_Add_update_page()">新增</a>

                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
    <div id="page-content" class="clearfix">
        <div  id="result" class="clearfix">
            <div id="div1" class="row-fluid">
                <table  id="table_report" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th><a href="javascript:void(0);" ng-click="sort()" style="display: block">id</a></th>
                            <th>用户名</th>
                            <th>密码</th>
                            <th width="10px">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list userlist as x>
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

                    </tbody>
                </table>
                <div class="page-header position-relative">
                    <table style="width:100%;">
                        <tr>
                            <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

    </div>

</body>
</html>
<script type="text/javascript">


</script>

