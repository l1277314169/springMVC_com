<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>用户</title>
<#include "comm/head.ftl">
<#include "comm/foot.ftl"/>
    <script type="text/javascript">
        var app = angular.module('myApp', [],function ($httpProvider) {
            $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
        });
        app.controller('myCtrl', function($scope, $rootScope,$http) {
            $scope.names = ${userlist};
            $scope.sortFlag = '-id'; //声明标识变量并初始化排序方式
            $scope.sort = function(){
                $scope.sortFlag = $scope.sortFlag === 'id' ? '-id' : 'id';
                //'-score' 减号，变换排序方式
            }

            $(".chzn-select").chosen();
            //$(".chzn-select-deselect").chosen({allow_single_deselect:true});
//           $scope.saveOrupdate=function(id){
//                if(id == null){
//			   		var data = $("#addForm").serialize();//自动将form表单封装成json
//			   		var jsonfom=$scope.conveterParamsToJson(data);
//			   		var obj = jQuery.parseJSON(JSON.stringify(jsonfom));
//                    var obj = $scope.person;
//                    $scope.names.unshift(obj);
//                    //$('#addForm')[0].reset();
//                    $scope.person={};
//                    alert("d");
//                }else{
//                    //$('#addForm')[0].reset();
//                    $scope.person={};
//                }
//
//            };
            //delete操作
            $scope.del=function(idx){
                layer.confirm('您确定要删除吗？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $http({
                        method:'post',
                        url:'${contextPath}/user/deleteUser',
                        params:{userId:idx}
                    }).success(
                            function(response) {
                                    $scope.responseFun(response,null,"删除");
                            }
                    )
                    //取消触发
                }, function(index){
                    layer.close(index);
                });
            };

            //显示add和update的页面
            $scope.show_Add_update_page=function(index) {
                var names_index = undefined;
                $.each($scope.names,function(n,value){
                            if(index ==value.id ){
                                names_index = n;
                                return false;
                            }
                });
                var length = length-1;
                var title=undefined;
                if(index == null || index == undefined){
                    $scope.user={};
                     title="新增";
                }else{
                    var obj =$scope.names[names_index];
                    $scope.user = obj;
                    title="修改";
                }
                layer.open({
                    type: 1,
                    title: title,
                    area: ['500px', '300px'],
                    content: $('#div2'),
                    btn:['保存'],
                    yes:function (index, layero) {
                        $scope.ajax_Add_update_Send(index,title);
                    }
                });
            }

            //处理请求ajax 的add和update
            $scope.ajax_Add_update_Send=function(index,title) {
                var url =undefined;
                if(title == "新增"){
                    url = "${contextPath}/user/addUser";
                }else{
                    url = "${contextPath}/user/updateUser";
                }
                $http({
                    method:'post',
                    url:url,
                    data:$('#addForm').serialize()
                }).success(
                        function(response) {
                            $scope.responseFun(response,index,title);
                        }
                )
            }


            $scope.responseFun =  function(response,index,flag) {
                if(response.code == 'success'){
                    if(index != null){
                        layer.close(index);
                    }
                    layer.alert(flag+'成功', {
                        icon: 1,
                        skin: 'layer-ext-seaning',
                        closeBtn: 0
                    }, function(){
                        $("#subtn").click();
                    });
                }else{
                    if(index != null){
                        layer.close(index);
                    }
                    layer.alert(flag+'失败', {
                        icon: 11,
                        skin: 'layer-ext-seaning',
                        closeBtn: 0
                    }, function(){
                        $("#subtn").click();
                    });
                }
            }


        });
            alert("ada");


    </script>
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

                            <select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;width: 120px;">
                                <option value=""></option>
                                <#--<option value="">全部</option>-->
                                <#list roleslist as role>
                                    <#--<option value="${role.id }" <c:if test="${pd.ROLE_ID==role.id}">selected</c:if>>${role.ROLE_NAME }</option>-->
                                        <option value="${role.id}">${role.role }</option>
                                </#list>
                            </select>
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
                        <#--<#list userList as x>-->
                            <#--<tr>-->
                                <#--<td>${x.id}</td>-->
                                <#--<td>${x.username}</td>-->
                                <#--<td>${x.password}</td>-->
                                <#--<td width="80px">-->
                                    <#--<@shiro.hasPermission name="C1M1F2">-->
                                        <#--<a href="javascript:void(0)" ng-click="showUpdateData($index)">修改</a>-->
                                    <#--</@shiro.hasPermission>-->
                                    <#--<@shiro.hasPermission name="C1M1F3">-->
                                        <#--<a href="javascript:void(0)" ng-click="del($index)">删除</a>-->
                                    <#--</@shiro.hasPermission>-->
                                <#--</td>-->
                            <#--</tr>-->
                        <#--</#list>-->

                        <tr ng-repeat="x in names | filter:{username:name} | orderBy:sortFlag">
                             <td ng-bind="x.id"></td>
                             <td ng-bind="x.username"></td>
                             <td ng-bind="x.password"></td>
                             <td width="80px">
                                <@shiro.hasPermission name="C1M1F2">
                                    <a href="javascript:void(0)" ng-click="show_Add_update_page(x.id)">修改</a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="C1M1F3">
                                    <a href="javascript:void(0)" ng-click="del(x.id)">删除</a>
                                </@shiro.hasPermission>
                                </td>
                        </tr>

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


        <div id="div2" style="display:none;text-align: center;margin-top: 76px;">
            <form id="addForm">
                <span style="display: none">id：</span><input type="hidden" name="id" ng-value="user.id">
                <span>姓名：</span><input type="text" name="username" ng-value="user.username"></br>
                <span>密码：</span><input type="text" name="password" ng-value="user.password"></br>
                <span>角色：</span><select class="chzn-select" id="roleId" name="role"  data-placeholder="请选择角色" style="vertical-align:top;width: 120px;">
                                        <option value=""></option>
                                        <option value="">全部</option>
                                            <#list roleslist as role>
                                            <#--<option value="${role.id }" <c:if test="${pd.ROLE_ID==role.id}">selected</c:if>>${role.ROLE_NAME }</option>-->
                                                <option value="${role.id}">${role.role }</option>
                                            </#list>
                                    </select>
            </form>
        </div>


    </div>

</body>
</html>
<script type="text/javascript">

alert("ada");

</script>

